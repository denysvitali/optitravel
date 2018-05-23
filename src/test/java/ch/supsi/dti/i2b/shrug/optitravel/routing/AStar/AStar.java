package ch.supsi.dti.i2b.shrug.optitravel.routing.AStar;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AStar {

    @Test
    void initialization(){
/*
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

        Algorithm<String> astar = new Algorithm<>();
        List<Node<String>> route = astar.route(s, e);

        assertNotEquals(null, route);
        assertEquals(5, route.size());

        // S,B,H,G,E is our path!
        assertEquals("S", route.get(0).toString());
        assertEquals("B", route.get(1).toString());
        assertEquals("H", route.get(2).toString());
        assertEquals("G", route.get(3).toString());
        assertEquals("E", route.get(4).toString());

        assertEquals(0, s.getG());
        assertEquals(10, s.getH());
        assertEquals(10, s.getF());

        assertEquals(2, b.getG());
        assertEquals(7, b.getH());
        assertEquals(s, b.getFrom());
        assertEquals(9, b.getF());

        assertEquals(3, h.getG());
        assertEquals(6, h.getH());
        assertEquals(b, h.getFrom());
        assertEquals(9, h.getF());

        assertEquals(5, g.getG());
        assertEquals(3, g.getH());
        assertEquals(h, g.getFrom());
        assertEquals(8, g.getF());

        assertEquals(7, e.getG());
        assertEquals(0, e.getH());
        assertEquals(g, e.getFrom());
        assertEquals(7, e.getF());

*/
    }
}
