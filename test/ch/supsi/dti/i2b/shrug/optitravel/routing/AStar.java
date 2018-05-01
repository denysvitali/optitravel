package ch.supsi.dti.i2b.shrug.optitravel.routing;

import org.junit.jupiter.api.Test;

public class AStar {

    @Test
    void initialization(){

        Node<String> s = new Node<>("S");
        s.setH(10);
        Node<String> a = new Node<>("A");
        a.setH(9);
        Node<String> b = new Node<>("B");
        b.setH(7);
        Node<String> d = new Node<>("D");
        d.setH(8);
        Node<String> h = new Node<>("H");
        h.setH(6);
        Node<String> f = new Node<>("F");
        f.setH(6);
        Node<String> l = new Node<>("L");
        l.setH(6);
        Node<String> c = new Node<>("C");
        c.setH(8);
        Node<String> j = new Node<>("J");
        j.setH(4);
        Node<String> k = new Node<>("K");
        k.setH(3);
        Node<String> g = new Node<>("G");
        g.setH(3);
        Node<String> i = new Node<>("I");
        i.setH(4);
        Node<String> e = new Node<>("E");
        e.setH(0);

        s.addNeighbour(a,7);
        s.addNeighbour(b, 2);
        s.addNeighbour(c, 3);

        a.addNeighbour(s, 7);
        a.addNeighbour(b, 3);
        a.addNeighbour(d, 4);

        b.addNeighbour(a, 3);
        b.addNeighbour(s, 2);
        b.addNeighbour(d, 4);
        b.addNeighbour(h, 1);

        c.addNeighbour(s, 3);
        c.addNeighbour(l, 2);

        d.addNeighbour(a, 4);
        d.addNeighbour(b, 4);
        d.addNeighbour(f, 5);

        f.addNeighbour(d, 5);
        f.addNeighbour(h, 3);

        g.addNeighbour(h, 2);
        g.addNeighbour(e, 2);

        h.addNeighbour(b, 1);
        h.addNeighbour(f, 3);
        h.addNeighbour(g, 2);

        i.addNeighbour(l, 4);
        i.addNeighbour(k, 4);
        i.addNeighbour(j, 6);

        l.addNeighbour(c, 2);
        l.addNeighbour(i, 4);
        l.addNeighbour(j, 4);

        j.addNeighbour(l, 4);
        j.addNeighbour(i, 6);
        j.addNeighbour(k, 4);

        k.addNeighbour(i, 4);
        k.addNeighbour(j, 4);
        k.addNeighbour(e, 5);

        e.addNeighbour(g, 2);
        e.addNeighbour(k, 5);



        AstarAlgorithm<String> astar = new AstarAlgorithm<>();

        astar.route(s, e);





    }
}
