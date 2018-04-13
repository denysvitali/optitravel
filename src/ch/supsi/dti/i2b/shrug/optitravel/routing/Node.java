package ch.supsi.dti.i2b.shrug.optitravel.routing;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    private T element;
    private List<Node<T>> neighbours;
    private double g;
    private double h;
    private boolean visited;

    Node(T element){
        neighbours = new ArrayList<>();
        this.element = element;
        visited = false;
    }
    void addNeighbour(Node<T> neighbourElement){
        neighbours.add(neighbourElement);
    }

    public List<Node<T>> getNeighbours() {
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
}
