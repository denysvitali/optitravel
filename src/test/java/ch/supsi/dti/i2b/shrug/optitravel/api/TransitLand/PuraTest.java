package ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand;

import ch.supsi.dti.i2b.shrug.optitravel.api.BlaBlaCar.BlaBlaCarWrapper;
import ch.supsi.dti.i2b.shrug.optitravel.api.BlaBlaCar.models.BlaBlaTrip;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PuraTest {

    private List<Stop> stopsInBBox;
    private List<RouteStopPattern> routeStopPatternsInBBox;
    private List<ScheduleStopPair> scheduleStopPairsInBBox = new ArrayList<>();

    int count = 0;
    int richieste = -2;
    private static TransitLandAPIWrapper transitLandAPIWrapper = new TransitLandAPIWrapper();
    @Test
    public void testData(){

        if(System.getenv().get("JENKINS_URL") != null){
            return;
        }

        Runnable r = ()->{


            BlaBlaCarWrapper wrap = new BlaBlaCarWrapper();


            List<BlaBlaTrip> list = wrap.getBlaBlaTrips(new Coordinate(45.465622,9.186730), new Coordinate(41.903376,12.493745));

            LocalDateTime loc = LocalDateTime.parse(list.get(0).getDeparture_date(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

            GPSCoordinates gps1 = new GPSCoordinates(41.905981, 12.463507);
            GPSCoordinates gps2 = new GPSCoordinates(41.869486, 12.497432);


            //Roma
            /*GPSCoordinates gps1 = new GPSCoordinates(41.905615, 12.484637);
            GPSCoordinates gps2 = new GPSCoordinates(41.896346, 12.505849);*/


            // San Francisco
            /*
            GPSCoordinates gps1 = new GPSCoordinates(37.764487, -122.507609);
            GPSCoordinates gps2 = new GPSCoordinates(37.739887, -122.469078);*/
            transitLandAPIWrapper.AgetStopsByBBox(gps1, gps2, (stops)->{

                System.out.println(stops.size());
                stopsInBBox = stops;
                synchronized (transitLandAPIWrapper){
                    count++;
                    transitLandAPIWrapper.notify();
                }

            });
            transitLandAPIWrapper.AgetRouteStopPatternsByBBox(gps1, gps2, (rsps)->{

                System.out.println(rsps.size());
                routeStopPatternsInBBox = rsps;
          /*      richieste = routeStopPatternsInBBox.size();
                for(RouteStopPattern rsp : routeStopPatternsInBBox){

                    transitLandAPIWrapper.AgetScheduleStopPairsByBBoxAndRSP(rsp.getId(), (ssps)->{


                        if(ssps != null && ssps.size() != 0){

                            synchronized (transitLandAPIWrapper) {
                                System.out.println(ssps.size());
                                scheduleStopPairsInBBox.addAll(ssps);
                            }
                        }

                        synchronized (transitLandAPIWrapper){
                            count++;
                            transitLandAPIWrapper.notify();
                        }

                    });
                }*/
                synchronized (transitLandAPIWrapper){
                    count++;
                    transitLandAPIWrapper.notify();
                }

            });

            transitLandAPIWrapper.AgetScheduleStopPairsByBBox(gps1, gps2, (ssps)->{

                System.out.println(ssps.size());
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




      //  Stop stopP = mappaStops.get("s-9q8ytnbje3-taravalst~19thave"/*"s-gcpvj0wdme-embankmentundergroundstation"*/);
      //  Stop stopA = mappaStops.get("s-9q8yudjy7u-judahst~31stave"/*"s-gcpvjdgx5r-farringdonundergroundstation"/*"s-gcpvjcxw36-bankdlrstation"*/);

        Stop stopP = mappaStops.get("s-sr2ykhm0pz-tritone~barberinima");
        Stop stopA = mappaStops.get(/*"s-sr2yk7yf4k-terminima~mb~fs"*/"s-sr2yhnduhw-romaostiense");


        Structure timeToTest = new Structure(stopA.getCoordinate(), mappaStops, mappaRsps, rspsTraversingStop, tripOfRSP);

        NodeLocationTime nodeA = new NodeLocationTime(stopA);
        NodeLocationTime nodeP = new NodeLocationTime(stopP);

        nodeP.setArrivalTime(LocalTime.parse("10:10:00"));



        // TODO: @denvit verify performance of tree set removeIf
        TreeSet<NodeLocationTime> calculatedNodes = new TreeSet<>((a, b) -> a.getF() >= b.getF() ? 1 : -1);
        List<NodeLocationTime> calculatedNodesAll = new ArrayList<>();

        NodeLocationTime currentNode = nodeP;
        nodeP.setG(0);
        currentNode.findNeighbours(timeToTest, calculatedNodesAll);
        for(;;) {
            if(currentNode == null){
                break;
            }

            NodeLocationTime closestNode = null;
            for (NodeLocationTime n : currentNode.getNeighbours().keySet()) {
                double newG = (currentNode.getG() == -1 ? 0 : currentNode.getG()) + currentNode.getNeighbours().get(n);
                double newF = n.getH() + newG;


                if(n.getG() == -1){
                    n.setG(newG);
                    n.setFrom(currentNode);
                    calculatedNodes.add(n);
                    calculatedNodesAll.add(n);
                }
                else if((n.getF() > newF) && !n.getVisited()){
                    calculatedNodes.removeIf(s -> s == n);
                    n.setG(newG);
                    n.setFrom(currentNode);
                    calculatedNodes.add(n);
                    calculatedNodesAll.add(n);
                }
                else{
                    System.out.println("ignorato");
                }
            }

            currentNode.setVisited();
            currentNode = calculatedNodes.pollFirst();

            if(currentNode != null && currentNode.getElement().equals(nodeA.getElement())){
                List<NodeLocationTime> path = new ArrayList<>();
                while(currentNode.getFrom() != null){
                    path.add(currentNode);
                    currentNode = currentNode.getFrom();
                }
                path.add(currentNode);
                Collections.reverse(path);
                return;
            }
            currentNode.findNeighbours(timeToTest, calculatedNodesAll);
        }
    }
}
