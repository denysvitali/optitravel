package ch.supsi.dti.i2b.shrug.optitravel.routing.AStar;

import java.util.HashMap;
import java.util.Map;

public class Node<T> {
    private T element;
    private Map<Node<T>, Double> neighbours;
    private double g;
    private double h;
    private boolean visited;
    private Node<T> from;

    Node(T element){
        neighbours = new HashMap<>();
        this.element = element;
        visited = false;
        g = -1;
        h = -1;
    }
    void addNeighbour(Node<T> neighbourElement, double distanceFromNode){

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

    public double getF(){
        return g+h; }

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
}
