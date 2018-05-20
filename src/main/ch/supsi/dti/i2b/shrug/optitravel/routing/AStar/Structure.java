package ch.supsi.dti.i2b.shrug.optitravel.routing.AStar;

import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.RouteStopPattern;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.ScheduleStopPair;
import ch.supsi.dti.i2b.shrug.optitravel.api.TransitLand.models.Stop;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.models.Location;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Structure {

    private static final double AVERAGE_WALKING_SPEED = 3/3.6;
    private static final int MAXIMUM_RADIUS = 100;
    private Map<String, Stop> stopsMap;
    private Map<String, RouteStopPattern> rspsMap;
    private Map<String, List<RouteStopPattern>> rspsTraversingStop;
    private Map<String, Map<String, List<ScheduleStopPair>>> tripOfRSP;

    Structure(Map<String, Stop> stopsMap,
              Map<String, RouteStopPattern> rspsMap,
              Map<String, List<RouteStopPattern>> rspsTraversingStop,
              Map<String, Map<String, List<ScheduleStopPair>>> tripOfRSP){

        this.rspsMap = rspsMap;
        this.stopsMap = stopsMap;
        this.rspsTraversingStop = rspsTraversingStop;
        this.tripOfRSP = tripOfRSP;
    }

    public void addNearStopsAsNeighbours(NodeLocationTime node){


        for(ch.supsi.dti.i2b.shrug.optitravel.models.Stop place : stopsMap.values()){
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

        RouteStopPattern rspAttuale = rspsMap.get(node.getCurrentRsp());
        if(rspAttuale != null){

            int currentIndex = rspAttuale.getStopPattern().indexOf(node.getElement());

            try{
                NodeLocationTime newNode = new NodeLocationTime(
                        stopsMap.get(rspAttuale.getStopPattern().get(currentIndex+1))
                );

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
