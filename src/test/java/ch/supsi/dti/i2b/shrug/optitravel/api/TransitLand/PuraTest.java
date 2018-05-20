package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.GPSCoordinates;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.RouteStopPattern;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.ScheduleStopPair;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuraTest {

    private List<Stop> stopsInBBox;
    private List<RouteStopPattern> routeStopPatternsInBBox;
    private List<ScheduleStopPair> scheduleStopPairsInBBox;

    int count = 0;
    private static TransitLandAPIWrapper transitLandAPIWrapper = new TransitLandAPIWrapper();
    @Test
    public void testData(){


        Runnable r = ()->{
            transitLandAPIWrapper.AgetStopsByBBox(new GPSCoordinates(45.488243, 9.150641), new GPSCoordinates(45.455767, 9.205024), (stops)->{

                System.out.println(stops);
                stopsInBBox = stops;
                synchronized (transitLandAPIWrapper){
                    count++;
                    transitLandAPIWrapper.notify();
                }

            });
            transitLandAPIWrapper.AgetRouteStopPatternsByBBox(new GPSCoordinates(45.488243, 9.150641), new GPSCoordinates(45.455767, 9.205024), (rsps)->{

                System.out.println(rsps);
                routeStopPatternsInBBox = rsps;
                synchronized (transitLandAPIWrapper){
                    count++;
                    transitLandAPIWrapper.notify();
                }

            });
            transitLandAPIWrapper.AgetScheduleStopPairsByBBox(new GPSCoordinates(45.488243, 9.150641), new GPSCoordinates(45.455767, 9.205024), (ssps)->{

                System.out.println(ssps);
                scheduleStopPairsInBBox = ssps;
                synchronized (transitLandAPIWrapper){
                    count++;
                    transitLandAPIWrapper.notify();
                }

            });

        };
        Thread t = new Thread(r);
        t.start();

        synchronized (transitLandAPIWrapper){
            while(count!=3){
                try {
                    transitLandAPIWrapper.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("ricevuti");
        System.out.println(routeStopPatternsInBBox.get(0).getId());
        System.out.println(stopsInBBox.get(0).getId());
        //     stopsInBBox = transitLandAPIWrapper.sortStops(new GPSCoordinates(/*46.004962,8.950784*/46.003135, 8.759909), stopsInBBox);
        GPSCoordinates c = new GPSCoordinates(46.164871, 8.917476);
        List<Stop> sortedStops = transitLandAPIWrapper.sortStops(c, stopsInBBox);
        sortedStops.forEach(stop -> System.out.println(stop.getName() + " - " + Distance.distance(c.asCoordinate(), stop.getCoordinate())));
        System.out.println(sortedStops.get(0).getId());


        /**
         * Creating the association between id and the respective object that has said id
         */
        Map<String, Stop> mappaStops = new HashMap<>();
        for(Stop stop : sortedStops){
            mappaStops.put(stop.getId(), stop);
        }
        Map<String, RouteStopPattern> mappaRsps = new HashMap<>();
        for(RouteStopPattern rsp : routeStopPatternsInBBox){
            mappaRsps.put(rsp.getId(), rsp);
        }

        Map<String, List<RouteStopPattern>> rspsTraversingStop = new HashMap<>();
        Map<String, Map<String, List<ScheduleStopPair>>> tripOfRSP = new HashMap<>();

        /**
         * Creating the association between stops id and RouteStopPatterns that traverse said stop and at the same time
         * the association between RouteStopPatterns id and Schedules that have as origin one of the stops in the same
         * RouteStopPattern
        **/
        for(Stop stop : sortedStops){
            rspsTraversingStop.put(stop.getId(), new ArrayList<>());
        }
        for(RouteStopPattern rsp : routeStopPatternsInBBox) {

            Map<String, List<ScheduleStopPair>> schedulesFromStopInRSP = new HashMap<>();
            List<String> stopsInRSP = rsp.getStopPattern();
            for (String stop : stopsInRSP) {

                schedulesFromStopInRSP.put(stop, new ArrayList<>());

                List<RouteStopPattern> rsps = rspsTraversingStop.get(stop);
                if (rsps != null) {
                    rsps.add(rsp);
                }
            }
            for (ScheduleStopPair ssp : scheduleStopPairsInBBox) {

                List<ScheduleStopPair> ssps = schedulesFromStopInRSP.get(ssp.getOrigin_onestop_id());
                if (ssps != null) {
                    ssps.add(ssp);
                }
            }
            tripOfRSP.put(rsp.getId(), schedulesFromStopInRSP);
        }
    }
}
