/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;

/**
 *
 * @author lewi0146
 */
public class DepthFirstTraversal {

    public static List<Vertex> traverse(Graph g) {
	LinkedList<Vertex> l = new LinkedList<Vertex>();
	Iterable<Vertex> verts = g.getVertices();
	for (Iterator<Vertex> i = verts.iterator(); i.hasNext();) {
	    i.next().setToUndiscovered();
	}
	for (Iterator<Vertex> i = verts.iterator(); i.hasNext();) {
	    Vertex v = i.next();
	    if (v.isUndiscovered()) {
		visit(g, v, l);
	    }
	}

	return l;
    }

    private static List<Vertex> visit(Graph g, Vertex s, List<Vertex> l) {
	s.setToDiscovered();

	Iterable<Vertex> adjs = g.adjacentTo(s);
	for (Iterator<Vertex> i = adjs.iterator(); i.hasNext();) {
	    Vertex v = i.next();
	    if (v.isUndiscovered()) {
		visit(g, v, l);
	    }
	}

	s.setToFinished();
	l.add(s);
	return l;
    }

    public static List<Vertex> topologicalSort(AdjacencyListDirectedGraph g) {
	LinkedList<Vertex> l = new LinkedList<Vertex>();
	Iterable<Vertex> allVerts = g.getVertices();
	Iterable<Vertex> verts = g.getZeroInDegree();
	for (Iterator<Vertex> i = allVerts.iterator(); i.hasNext();) {
	    i.next().setToUndiscovered();
	}
	for (Iterator<Vertex> i = verts.iterator(); i.hasNext();) {
	    visit(g, i.next(), l);
	}
	Collections.reverse(l);
        return l;
    }

}
