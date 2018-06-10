package ch.supsi.dti.i2b.shrug.optitravel.routing.AStar;

import ch.supsi.dti.i2b.shrug.optitravel.geography.Coordinate;
import ch.supsi.dti.i2b.shrug.optitravel.geography.Distance;
import ch.supsi.dti.i2b.shrug.optitravel.models.Location;
import ch.supsi.dti.i2b.shrug.optitravel.models.TimedLocation;
import ch.supsi.dti.i2b.shrug.optitravel.params.PlannerParams;
import ch.supsi.dti.i2b.shrug.optitravel.planner.DataGathering;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Algorithm<T extends TimedLocation, L extends Location> {

	private DataGathering dg;
	private HashMap<L, ArrayList<T>> timedlocation_by_location = new HashMap<>();
	private ArrayList<L> locations = new ArrayList<>();
	private HashMap<String, L> uid_location = new HashMap<>();
	private Coordinate destination;
	private ArrayList<Node<T,L>> visited = new ArrayList<>();

	public Algorithm(DataGathering dg){
		this.dg = dg;
	}

	public void setDestination(Coordinate destination) {
		this.destination = destination;
	}

	public HashMap<L, ArrayList<T>> getTimedLocationByLocation() {
		return timedlocation_by_location;
	}

	public HashMap<String, L> getUidLocationHM() {
		return uid_location;
	}

	public ArrayList<Node<T, L>> getVisited() {
		return visited;
	}

	public List<Node<T,L>> route(Node<T,L> from, Coordinate to){destination = to;
		Node<T,L> currentNode = from;
		from.setG(0);
		TreeSet<Node<T,L>> treeset = new TreeSet<>(Comparator.comparingDouble(Node::getF));
		for(;;) {
			if(currentNode == null){
				currentNode = from;
				System.out.println("Unexpected end.");
				return null;
			}

			HashMap<Node<T,L>, Double> neighbours = currentNode.getNeighbours();
			for (Node<T,L> n : neighbours.keySet()) {
				double newG = (currentNode.getG() == -1 ? 0 : currentNode.getG()) + neighbours.get(n);
				double newF = n.getH() + newG;
				if (n.getG() == -1) {
					n.setG(newG);
					n.setFrom(currentNode);
					//     if(!treeset.contains(n) && !visited.contains(n)) {
					treeset.add(n);
					//		}
				}
			}

			currentNode = treeset.pollFirst();
			//  visited.add(currentNode);

			if(currentNode != null &&
					Distance.distance(
							currentNode
									.getElement()
									.getLocation()
									.getCoordinate()
							, to) < dg.getPlanPreference().destination_radius()
			){
				List<Node<T,L>> path = new ArrayList<>();
				while(currentNode.getFrom() != null){
					path.add(currentNode);
					currentNode = currentNode.getFrom();
				}
				path.add(currentNode);
				Collections.reverse(path);
				return path;
			}

			if(currentNode != null){
				/*System.out.println(String.format("%d, %s (%f + %f = %f), (C: %d, WT: %f) (last element F: %f)",
						treeset.size(), currentNode.getElement(), currentNode.getG(),
						currentNode.getH(), currentNode.getF(), currentNode.getChanges(),
						currentNode.getWaitTotal(), (treeset.size() != 0 ? treeset.last().getF(): -1)));*/

				//System.out.println(currentNode.getElement());
				/*System.out.println("Distance: " + Distance.distance(
						currentNode
								.getElement()
								.getLocation()
								.getCoordinate()
						, to));
                System.out.println(currentNode
                        .getElement().getLocation() + " is not close to " + to);*/
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
