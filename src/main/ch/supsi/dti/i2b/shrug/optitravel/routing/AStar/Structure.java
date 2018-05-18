package ch.supsi.dti.i2b.shrug.optitravel.routing.AStar;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.RouteStopPattern;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.ScheduleStopPair;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.models.Location;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Structure {

    private static final double AVERAGE_WALKING_SPEED = 3/3.6;
    private static final int MAXIMUM_RADIUS = 100;
    private Map<String, Stop> mappaStops = new HashMap<>();
    private Map<String, RouteStopPattern> mappaRsps = new HashMap<>();
    private Map<ScheduleStopPair, String> mappaSsps = new HashMap<>();

    Structure(Map<String, Stop> mappaStops, Map<String, RouteStopPattern> mappaRsps, Map<ScheduleStopPair, String> mappaSsps){
        this.mappaRsps = mappaRsps;
        this.mappaSsps = mappaSsps;
        this.mappaStops = mappaStops;
    }

    public void addNearStopsAsNeighbours(NodeLocationTime node){


        for(ch.supsi.dti.i2b.shrug.optitravel.models.Stop place : mappaStops.values()){
            double distance = Distance.distance(node.getElement().getCoordinate(), place.getCoordinate());

            if(distance <= MAXIMUM_RADIUS){
                NodeLocationTime newNode = new NodeLocationTime(place);
                LocalTime newTime = LocalTime.of(
                        node.getArrivalTime().getHour(),
                        node.getArrivalTime().getMinute(),
                        node.getArrivalTime().getSecond()
                );
                newTime.plusSeconds(60/*tempo di "sicurezza"*/ + (long) (distance / AVERAGE_WALKING_SPEED));
                newNode.setArrivalTime(newTime);
                node.addNeighbour(newNode, distance/*TODO: Calcolo del peso più specifico*/);
            }
        }
    }
    public void addSameRSPStopAsNeighbour(NodeLocationTime node){

        RouteStopPattern rspAttuale = mappaRsps.get(node.getCurrentRsp());
        if(rspAttuale != null){

            int currentIndex = rspAttuale.getStopPattern().indexOf(node.getElement());

            try{
                NodeLocationTime newNode = new NodeLocationTime(rspAttuale.getStopPattern().get(currentIndex+1));

                // TODO: Set arrival time using schedule

                node.addNeighbour(newNode, 0/*TODO: Calcolo del peso più specifico*/);

            }catch(IndexOutOfBoundsException e){
                e.printStackTrace();
            }

        }

    }
    public void addDifferentRSPStopsAsNeighbours(NodeLocationTime node){

    }


}
