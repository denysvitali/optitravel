package ch.supsi.dti.i2b.shrug.optitravel.routing.AStar;

import java.util.*;

public class Algorithm<T> {


    public List<Node<T>> route(Node<T> from, Node<T> to){

        TreeSet<Node<String>> calculatedNodes = new TreeSet<>((a, b) -> a.getF() >= b.getF() ? 1 : -1);

        Node<String> first = new Node<>("first");
        first.setG(1);
        first.setH(1);
        Node<String> middle = new Node<>("middle");
        middle.setG(2);
        middle.setH(1);
        Node<String> last = new Node<>("last");
        last.setG(3);
        last.setH(1);
        Node<String> newFirsteroni = new Node<>("newf");
        newFirsteroni.setG(0);
        newFirsteroni.setH(1);
        calculatedNodes.add(middle);
        calculatedNodes.add(last);
        calculatedNodes.add(first);
        Node<String> hh = calculatedNodes.pollFirst();
        calculatedNodes.add(newFirsteroni);
        boolean test = calculatedNodes.remove(newFirsteroni);
        middle.setH(0);
        middle.setG(0);
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
                if (n.getF() > newF || n.getG() == -1) {
                    n.setG(newG);
                    n.setFrom(currentNode);
                  //  calculatedNodes.add(n);
                }
                if (!n.getVisited() && (closestNode == null || (closestNode.getF() > n.getF()))) {
                    closestNode = n;
                }

            }

            currentNode.setVisited();
            currentNode = closestNode;

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
