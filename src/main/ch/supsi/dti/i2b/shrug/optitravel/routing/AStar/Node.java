package ch.supsi.dti.i2b.shrug.optitravel.routing.AStar;

import ch.supsi.dti.i2b.shrug.optitravel.models.Location;
import ch.supsi.dti.i2b.shrug.optitravel.models.TimedLocation;
import ch.supsi.dti.i2b.shrug.optitravel.planner.DataGathering;

import java.util.HashMap;
import java.util.Objects;

public class Node<T extends TimedLocation, L extends Location> {
    private T element;
    private HashMap<Node<T,L>, Double> neighbours;
    private double g;
    private double h;
    private boolean visited;
    private Node<T,L> from;
    private boolean computed_neighbours = false;
    private DataGathering dg;
    private Algorithm<T,L> algorithm;
    private double wait_total = 0.0;
    private int changes = 0;

	public void setDg(DataGathering dg) {
		this.dg = dg;
	}

	public void setAlgorithm(Algorithm<T, L> algorithm) {
		this.algorithm = algorithm;
	}

	public Node(T element){
        neighbours = new HashMap<>();
        this.element = element;
        visited = false;
        g = -1;
        h = -1;
    }
    public void addNeighbour(Node<T,L> neighbourElement, double distanceFromNode){
        neighbours.put(neighbourElement, distanceFromNode);
    }

    public HashMap<Node<T,L>, Double> getNeighbours() {
    	if(!computed_neighbours && dg != null){
    		neighbours = dg.getNeighbours(this, algorithm);
		}
        return neighbours;
    }

    public T getElement() {
        return element;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getF(){ return g+h; }
    public void setFrom(Node<T,L> node) {
        from =  node;
    }

    public void setVisited() {
        setVisited(true);
    }

    public void setVisited(boolean b) {
        visited = b;
    }

    public Node<T,L> getFrom() {
        return from;
    }

    @Override
    public String toString(){ return element.toString(); }

    public boolean getVisited() {
        return visited;
    }

	public boolean isComputedNeighbours() {
		return computed_neighbours;
	}

	public void setComputedNeighbours(boolean computed_neighbours) {
		this.computed_neighbours = computed_neighbours;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Node<?,?> node = (Node<?,?>) o;
		return Objects.equals(node.element, element);
	}

	public double getWaitTotal() {
		return wait_total;
	}

	public int getChanges() {
		return changes;
	}

	public void addToWaitTotal(double minute_wait) {
		wait_total += minute_wait;
	}

	public void setChanges(int i) {
		changes = i;
	}

	public void setWaitTotal(double waitTotal) {
		wait_total = waitTotal;
	}

	public void addChange() {
		changes++;
	}
}
