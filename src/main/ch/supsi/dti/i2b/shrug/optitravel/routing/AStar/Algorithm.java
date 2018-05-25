package ch.supsi.dti.i2b.shrug.optitravel.routing.AStar;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.models.Location;
import ch.supsi.dti.i2b.shrug.optitravel.models.TimedLocation;
import ch.supsi.dti.i2b.shrug.optitravel.params.PlannerParams;
import ch.supsi.dti.i2b.shrug.optitravel.planner.DataGathering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Algorithm<T extends TimedLocation, L extends Location> {

    private DataGathering dg;
    private HashMap<L, ArrayList<T>> timedlocation_by_location = new HashMap<>();
    private ArrayList<L> locations = new ArrayList<>();
    private HashMap<String, L> uid_location = new HashMap<>();
    private Coordinate destination;

    public Algorithm(DataGathering dg){
    	this.dg = dg;
	}

	public HashMap<L, ArrayList<T>> getTimedLocationByLocation() {
		return timedlocation_by_location;
	}

	public HashMap<String, L> getUidLocationHM() {
		return uid_location;
	}

	public List<Node<T>> route(Node<T> from, Coordinate to){
    	destination = to;
        Node<T> currentNode = from;
        from.setG(0);
        for(;;) {
            if(currentNode == null){
                return null;
            }

            Node<T> closestNode = null;
            HashMap<Node<T>, Double> neighbours = dg.getNeighbours(currentNode, this);
            for (Node<T> n : neighbours.keySet()) {
                double newG = (currentNode.getG() == -1 ? 0 : currentNode.getG()) + neighbours.get(n);
                double newF = n.getH() + newG;
                if (n.getF() > newF || n.getG() == -1) {
                    n.setG(newG);
                    n.setFrom(currentNode);
                }
                if (!n.getVisited() && (closestNode == null || (closestNode.getF() > n.getF()))) {
                    closestNode = n;
                }

            }

            currentNode.setVisited();
            currentNode = closestNode;

            if(currentNode != null &&
					Distance.distance(
                    currentNode
							.getElement()
							.getLocation()
							.getCoordinate()
					, to) < PlannerParams.DESTINATION_RADIUS
			){
                List<Node<T>> path = new ArrayList<>();
                while(currentNode.getFrom() != null){
                    path.add(currentNode);
                    currentNode = currentNode.getFrom();
                }
                path.add(currentNode);
                Collections.reverse(path);
                return path;
            }

            if(currentNode != null){
                System.out.println(currentNode
                        .getElement().getLocation() + " is not close to " + to);
            }
        }
    }


	public List<L> getLocations() {
    	return this.locations;
	}

	public Coordinate getDestination() {
    	return destination;
	}
}
