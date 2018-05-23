package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.GPSCoordinates;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.RouteStopPattern;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.ScheduleStopPair;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.models.Location;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.NodeLocationTime;
import ch.supsi.dti.i2b.shrug.optitravel.routing.AStar.Structure;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.*;

public class PuraTest {

    private List<Stop> stopsInBBox;
    private List<RouteStopPattern> routeStopPatternsInBBox;
    private List<ScheduleStopPair> scheduleStopPairsInBBox;

    int count = 0;
    private static TransitLandAPIWrapper transitLandAPIWrapper = new TransitLandAPIWrapper();
    @Test
    public void testData(){

        if(System.getenv().get("JENKINS_URL") != null){
            return;
        }

        Runnable r = ()->{
            GPSCoordinates gps1 = new GPSCoordinates(51.523587, -0.140157);
            GPSCoordinates gps2 = new GPSCoordinates(51.501660, -0.088609);
            transitLandAPIWrapper.AgetStopsByBBox(gps1, gps2, (stops)->{

                System.out.println(stops);
                stopsInBBox = stops;
                synchronized (transitLandAPIWrapper){
                    count++;
                    transitLandAPIWrapper.notify();
                }

            });
            transitLandAPIWrapper.AgetRouteStopPatternsByBBox(gps1, gps2, (rsps)->{

                System.out.println(rsps);
                routeStopPatternsInBBox = rsps;
                synchronized (transitLandAPIWrapper){
                    count++;
                    transitLandAPIWrapper.notify();
                }

            });
            transitLandAPIWrapper.AgetScheduleStopPairsByBBox(gps1, gps2, (ssps)->{

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
            List<ScheduleStopPair> schedulesWithSameRSP = new ArrayList<>();
            for (ScheduleStopPair ssp : scheduleStopPairsInBBox) {

                if(ssp.getRoute_stop_pattern_onestop_id().equals(rsp.getId())){
                    schedulesWithSameRSP.add(ssp);
                }


            }
            for(ScheduleStopPair ssp : schedulesWithSameRSP){
                List<ScheduleStopPair> ssps = schedulesFromStopInRSP.get(ssp.getOrigin_onestop_id());
                if (ssps != null) {
                    ssps.add(ssp);
                }
            }
            tripOfRSP.put(rsp.getId(), schedulesFromStopInRSP);
        }




        Stop stopP = mappaStops.get("s-gcpvj0wdme-embankmentundergroundstation");
        Stop stopA = mappaStops.get("s-gcpvjdgx5r-farringdonundergroundstation"/*"s-gcpvjcxw36-bankdlrstation"*/);

        Structure timeToTest = new Structure(stopA.getCoordinate(), mappaStops, mappaRsps, rspsTraversingStop, tripOfRSP);

        NodeLocationTime nodeA = new NodeLocationTime(stopA);
        NodeLocationTime nodeP = new NodeLocationTime(stopP);

        nodeP.setArrivalTime(LocalTime.parse("10:10:00"));



        // TODO: @denvit verify performance of tree set removeIf
        TreeSet<NodeLocationTime> calculatedNodes = new TreeSet<>((a, b) -> a.getF() >= b.getF() ? 1 : -1);

        List<Location> visited = new ArrayList<>();
        NodeLocationTime currentNode = nodeP;
        nodeP.setG(0);
        currentNode.findNeighbours(timeToTest);
        for(;;) {
            if(currentNode == null){
                break;
            }

            NodeLocationTime closestNode = null;
            for (NodeLocationTime n : currentNode.getNeighbours().keySet()) {
                double newG = currentNode.getG() + currentNode.getNeighbours().get(n);
                double newF = n.getH() + newG;

                if(visited.contains(n.getElement())){
                    calculatedNodes.removeIf(s -> s.getElement().equals(n.getElement()));
                }

                n.setG(newG);
                n.setFrom(currentNode);
                calculatedNodes.add(n);
            }

            currentNode.setVisited();
            visited.add(currentNode.getElement());
            currentNode = calculatedNodes.pollFirst();

            if(currentNode != null && currentNode.getElement().equals(nodeA.getElement())){
                List<NodeLocationTime> path = new ArrayList<>();
                while(currentNode.getFrom() != null){
                    path.add(currentNode);
                    currentNode = currentNode.getFrom();
                }
                path.add(currentNode);
                Collections.reverse(path);
            }
            currentNode.findNeighbours(timeToTest);
        }
    }
}
