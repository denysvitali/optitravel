package ch.supsi.dti.i2b.shrug.optitravel.routing.AStar;



import ch.supsi.dti.i2b.shrug.optitravel.models.Location;
import ch.supsi.dti.i2b.shrug.optitravel.models.Stop;

import java.util.HashMap;
import java.util.Map;

public class NodeLocationTime {
    private Stop element;
    private Map<NodeLocationTime, Double> neighbours;
    private double g;
    private double h;
    private boolean visited;
    private NodeLocationTime from;
    private int/*TODO: Time Class*/ arrivalTime;

    NodeLocationTime(Stop element){
        neighbours = new HashMap<>();
        this.element = element;
        visited = false;
        g = -1;
        h = -1;
    }
    void addNeighbour(NodeLocationTime neighbourElement, double distanceFromNode){

        neighbours.put(neighbourElement, distanceFromNode);
    }

    public Map<NodeLocationTime, Double> getNeighbours() {
        return neighbours;
    }

    public Location getElement() {
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

    public void setFrom(NodeLocationTime node) {
        from =  node;
    }

    public void setVisited() {
        setVisited(true);
    }

    public void setVisited(boolean b) {
        visited = b;
    }

    public NodeLocationTime getFrom() {
        return from;
    }
    @Override
    public String toString(){ return element.toString(); }

    public boolean getVisited() {
        return visited;
    }

    public void findNeighbours() {
        element.findNeighbours(arrivalTime);
    }
}
