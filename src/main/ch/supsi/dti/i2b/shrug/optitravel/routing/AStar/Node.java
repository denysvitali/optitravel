package ch.supsi.dti.i2b.shrug.optitravel.routing.AStar;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Node<T> {
    private T element;
    private HashMap<Node<T>, Double> neighbours;
    private double g;
    private double h;
    private boolean visited;
    private Node<T> from;
    private boolean computed_neighbours = false;

    public Node(T element){
        neighbours = new HashMap<>();
        this.element = element;
        visited = false;
        g = -1;
        h = -1;
    }
    public void addNeighbour(Node<T> neighbourElement, double distanceFromNode){
        neighbours.put(neighbourElement, distanceFromNode);
    }

    public Map<Node<T>, Double> getNeighbours() {
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
    public void setFrom(Node<T> node) {
        from =  node;
    }

    public void setVisited() {
        setVisited(true);
    }

    public void setVisited(boolean b) {
        visited = b;
    }

    public Node<T> getFrom() {
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
		Node<?> node = (Node<?>) o;
		return Double.compare(node.g, g) == 0 &&
				Double.compare(node.h, h) == 0 &&
				visited == node.visited &&
				computed_neighbours == node.computed_neighbours &&
				Objects.equals(element, node.element) &&
				Objects.equals(neighbours, node.neighbours) &&
				Objects.equals(from, node.from);
	}

	@Override
	public int hashCode() {
		return Objects.hash(element);
	}
}
