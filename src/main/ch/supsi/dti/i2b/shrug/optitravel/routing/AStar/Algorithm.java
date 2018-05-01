package ch.supsi.dti.i2b.shrug.optitravel.routing.AStar;

import java.util.*;

public class Algorithm<T> {


    public List<Node<T>> route(Node<T> from, Node<T> to){

        // TODO: @denvit verify performance of tree set removeIf
        TreeSet<Node<T>> calculatedNodes = new TreeSet<>((a, b) -> a.getF() >= b.getF() ? 1 : -1);

        Node<T> currentNode = from;
        from.setG(0);
        for(;;) {
            if(currentNode == null){
                return null;
            }

            Node<T> closestNode = null;
            for (Node<T> n : currentNode.getNeighbours().keySet()) {
                double newG = (currentNode.getG() == -1 ? 0 : currentNode.getG()) + currentNode.getNeighbours().get(n);
                double newF = n.getH() + newG;

                if(n.getG() == -1){
                    n.setG(newG);
                    n.setFrom(currentNode);
                    calculatedNodes.add(n);
                }
                else if((n.getF() > newF) && !n.getVisited()){
                    calculatedNodes.removeIf(s -> s == n);
                    n.setG(newG);
                    n.setFrom(currentNode);
                    calculatedNodes.add(n);
                }
                /*
                if (!n.getVisited() && (closestNode == null || (closestNode.getF() > n.getF()))) {
                    closestNode = n;
                }*/

            }

            currentNode.setVisited();
            currentNode = calculatedNodes.pollFirst();

            if(currentNode == to && currentNode != null){
                List<Node<T>> path = new ArrayList<>();
                while(currentNode.getFrom() != null){
                    path.add(currentNode);
                    currentNode = currentNode.getFrom();
                }
                path.add(currentNode);
                Collections.reverse(path);
                return path;
            }
        }
    }



}
