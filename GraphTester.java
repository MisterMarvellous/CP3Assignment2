/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lewi0146
 */
public class GraphTester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	Graph g = readFile("graphPosMidA.txt");
	if (g == null) { System.exit(0); }
	printGraph(g);
	printShortestPaths(g, g.getVertex("1"));
    }

    public static void printGraph(Graph g) {
        Iterable<Vertex> vs = g.getVertices();
        System.out.println("Vertices: " + vs);
        for (Vertex v : vs) {
            System.out.print(v + ": ");
            Iterable<Vertex> adjVList = g.adjacentTo(v);
            for (Vertex av : adjVList) {
                System.out.print(av + " ");
            }
            System.out.println();

        }
    }

    public static void printShortestPaths(Graph g, Vertex v) {	
	TreeMap<Vertex, String> shortestPaths = AdjacencyListDirectedGraph.getShortestPaths(g, v);
	HashMap<Vertex, Float> distance = new HashMap<Vertex, Float>();
	HashMap<Vertex, Vertex> predecessor = new HashMap<Vertex, Vertex>();

	for (Map.Entry<Vertex, String> entry : shortestPaths.entrySet()) {
	    String[] data = entry.getValue().split(" ");
	    distance.put(entry.getKey(), Float.parseFloat(data[0]));
	    predecessor.put(entry.getKey(), g.getVertex(data[1]));
	}

	for (Vertex w : g.getVertices()) {
	    String path = "";
	    Vertex currentVertex = w;
	    while (currentVertex != null) {
		path = " " + currentVertex + path;
		currentVertex = predecessor.get(currentVertex);
	    }
	    System.out.println("Shortest path to " + w + ":" + path + ": cost = " + distance.get(w));
	}

    }
    
    public static AdjacencyListDirectedGraph readFile(String file) {
	AdjacencyListDirectedGraph g = new AdjacencyListDirectedGraph();
	System.out.println("Reading from file...");
	try {
	    Scanner s = new Scanner(new File(file));
	    int vertices = 0;
	    if (!s.hasNextLine()) {
		System.out.println("Invalid file contents.");
		return null;
	    }
	    vertices = Integer.parseInt(s.nextLine());
	    
	    for (int i = 0; i < vertices; i++) {
		if (!s.hasNextLine()) {
		    System.out.println("Invalid file contents.");
		    return null;
		}
		String[] line = s.nextLine().split("\\s+");

		String v = new String();
		String w = new String();
		float weight = 1.0f;

		for (int j = 0; j < line.length; j++) {
		    if (j == 0) { v = line[j]; }
		    else {
			switch ((j-1)%2) {
			case 0: 
			    w = line[j];
			    break;
			case 1: 
			    weight = Float.parseFloat(line[j]);
			    g.addEdge(v, w, weight);
			    break;
			}
		    }
		}
	    }
	    s.close();
	}
	catch (FileNotFoundException e) {
	    System.out.println("File not found. Check path and try again");
	    return null;
	}

	return g;
    }

}
