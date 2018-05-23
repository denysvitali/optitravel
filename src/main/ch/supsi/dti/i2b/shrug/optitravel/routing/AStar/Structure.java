package ch.supsi.dti.i2b.shrug.optitravel.routing.AStar;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Route;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.RouteStopPattern;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.ScheduleStopPair;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.models.Location;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Structure {

    private Coordinate destinazione;
    private static final double AVERAGE_WALKING_SPEED = 3/3.6;
    private static final int MAXIMUM_RADIUS = 500;
    private Map<String, Stop> stopsMap;
    private Map<String, RouteStopPattern> rspsMap;
    private Map<String, List<RouteStopPattern>> rspsTraversingStop;
    private Map<String, Map<String, List<ScheduleStopPair>>> tripOfRSP;

    public Structure(Coordinate destinazione,
                     Map<String, Stop> stopsMap,
                     Map<String, RouteStopPattern> rspsMap,
                     Map<String, List<RouteStopPattern>> rspsTraversingStop,
                     Map<String, Map<String, List<ScheduleStopPair>>> tripOfRSP){

        this.destinazione = destinazione;
        this.rspsMap = rspsMap;
        this.stopsMap = stopsMap;
        this.rspsTraversingStop = rspsTraversingStop;
        this.tripOfRSP = tripOfRSP;
    }

    public void addNearStopsAsNeighbours(NodeLocationTime node){


        for(ch.supsi.dti.i2b.shrug.optitravel.models.Stop place : stopsMap.values()){
            double distance = Distance.distance(node.getElement().getCoordinate(), place.getCoordinate());

            if(node.getElement() != place && distance <= MAXIMUM_RADIUS){
                NodeLocationTime newNode = new NodeLocationTime(place);

                LocalTime newTime = node.getArrivalTime().plusSeconds(60/*tempo di "sicurezza"*/ + (long) (distance / AVERAGE_WALKING_SPEED));
                newNode.setArrivalTime(newTime);
                newNode.setH(Distance.distance(newNode.getElement().getCoordinate(), destinazione));
                node.addNeighbour(newNode, (distance+100)*10/*TODO: Calcolo del peso più specifico*/);
            }
        }
    }
    public void addSameRSPStopAsNeighbour(NodeLocationTime node){

        RouteStopPattern rspAttuale = rspsMap.get(node.getCurrentRsp());
        if(rspAttuale != null){

            Stop stopInNode = (Stop) node.getElement();
            int currentIndex = rspAttuale.getStopPattern().indexOf(stopInNode.getId());

            Stop nextStop = null;
            try{
                nextStop = stopsMap.get(rspAttuale.getStopPattern().get(currentIndex+1));
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
            if(nextStop != null){

            //   NodeLocationTime newNode = new NodeLocationTime(nextStop);

                LocalTime arrivalTime = node.getArrivalTime();

                List<ScheduleStopPair> ssps = tripOfRSP.get(node.getCurrentRsp()).get(stopInNode.getId());

                for(ScheduleStopPair ssp : ssps){
                    if(ssp.getDestination_onestop_id().equals(nextStop.getId()) && arrivalTime.equals(LocalTime.parse(ssp.getOrigin_arrival_time()))){
                        NodeLocationTime newNode = new NodeLocationTime(nextStop);
                        newNode.setArrivalTime(LocalTime.parse(ssp.getDestination_arrival_time()));
                        newNode.setH(Distance.distance(newNode.getElement().getCoordinate(), destinazione));
                        newNode.setCurrentRsp(rspAttuale.getId());
                        long minutes = ChronoUnit.MINUTES.between(arrivalTime, LocalTime.parse(ssp.getDestination_arrival_time()));
                        node.addNeighbour(newNode, 1+minutes/*TODO: Calcolo del peso più specifico*/);
                    }
                }

            }

        }

    }
    public void addDifferentRSPStopsAsNeighbours(NodeLocationTime node){

        Stop stopInNode = (Stop) node.getElement();
        List<RouteStopPattern> rspsTraversingNode = rspsTraversingStop.get(stopInNode.getId());

        for(RouteStopPattern rsp : rspsTraversingNode) {

            if(!rsp.getId().equals(node.getCurrentRsp())) {
                int currentIndex = rsp.getStopPattern().indexOf(stopInNode.getId());

                Stop nextStop = null;
                try {
                    nextStop = stopsMap.get(rsp.getStopPattern().get(currentIndex + 1));
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                if (nextStop != null) {

                    LocalTime arrivalTime = node.getArrivalTime();

                    List<ScheduleStopPair> ssps = tripOfRSP.get(rsp.getId()).get(stopInNode.getId());

                    for (ScheduleStopPair ssp : ssps) {


                        LocalTime originDeparture = LocalTime.parse(ssp.getOrigin_departure_time());

                        long seconds = ChronoUnit.SECONDS.between(arrivalTime, originDeparture);


                        if (seconds >= 0 && seconds <= 1800) {
                            NodeLocationTime newNode = new NodeLocationTime(nextStop);
                            newNode.setArrivalTime(LocalTime.parse(ssp.getDestination_arrival_time()));
                            newNode.setH(Distance.distance(newNode.getElement().getCoordinate(), destinazione));
                            newNode.setCurrentRsp(rsp.getId());
                            node.addNeighbour(newNode, 2000 + seconds/*TODO: Calcolo del peso più specifico*/);
                        }

                    }

                }
            }

        }
    }


}
