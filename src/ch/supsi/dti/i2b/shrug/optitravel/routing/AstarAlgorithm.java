package ch.supsi.dti.i2b.shrug.optitravel.routing;

import java.util.*;

public class AstarAlgorithm<T> {


    public void route(Node<T> from, Node<T> to){

       // Map<Node<T>, Double> calculatedNodes = new Hash
        Node<T> currentNode = from;
        from.setG(0);
        for(;;) {
            System.out.println(currentNode);
            Node<T> closestNode = null;
            for (Node<T> n : currentNode.getNeighbours().keySet()) {

                System.out.println(n);
                double newG = (currentNode.getG() == -1 ? 0 : currentNode.getG()) + currentNode.getNeighbours().get(n);
                System.out.println(newG + " - " + n.getG());
                if (n.getG() > newG || n.getG() == -1) {
                    n.setG(newG);
                    n.setFrom(currentNode);
                }
                if (closestNode == null || (closestNode.getF() > n.getF() && !closestNode.getVisited())) {
                    System.out.println("Found " + n);
                    closestNode = n;
                }

            }

            System.out.println("---------------");

            System.out.println("Closest: " + closestNode);

            currentNode.setVisited();
            currentNode = closestNode;

            if(currentNode == to){
                System.out.println("END!");
                List<Node<T>> path = new ArrayList<>();
                while(currentNode.getFrom() != null){
                    path.add(currentNode);
                    currentNode = currentNode.getFrom();

                }
                path.add(currentNode);
                Collections.reverse(path);
                path.forEach(System.out::println);

                return;
            }


        }

    }



}
