import java.util.*;

public class AdjacencyListDirectedGraph implements Graph {

    private TreeMap<Vertex, TreeMap<Edge, Vertex>> adjacencyList;
    private HashMap<String, Vertex> vertexList;
    private int edgeCount;

    public AdjacencyListDirectedGraph() {
	adjacencyList = new TreeMap<Vertex, TreeMap<Edge, Vertex>>();
	vertexList = new HashMap<String, Vertex>();
	edgeCount = 0;
    }

    /**
     * Add edge v-w.  Convenience method for adding using a string label.
     * @param v vertex v of the edge
     * @param w vertex w of the edge
     */
    public void addEdge(String v, String w) {
	addEdge(v, w, 1.0f);
    }

    /**
     * Add edge v-w.
     * @param v vertex v of the edge
     * @param w vertex w of the edge
     */
    public void addEdge(Vertex v, Vertex w) {
	addEdge(v, w, 1.0f);
    }

    public void addEdge(String v, String w, float weight) {
	if (!vertexList.containsKey(v)) {
	    vertexList.put(v, new AdjacencyListVertex(v));
	}
	if (!vertexList.containsKey(w)) {
	    vertexList.put(w, new AdjacencyListVertex(w));
	}

	addEdge(vertexList.get(v), vertexList.get(w), weight);
    }

    public void addEdge(Vertex v, Vertex w, float weight) {
	if (!vertexList.containsKey(v.getLabel())) {
	    vertexList.put(v.getLabel(), v);
	}
	if (!vertexList.containsKey(w.getLabel())) {
	    vertexList.put(w.getLabel(), w);
	}
	if (!adjacencyList.containsKey(v)) {
	    adjacencyList.put(v, new TreeMap<Edge, Vertex>());
	}
	if (!adjacencyList.containsKey(w)) {
	    adjacencyList.put(w, new TreeMap<Edge, Vertex>());
	}
	if (!adjacencyList.get(v).containsValue(w)) {
	    adjacencyList.get(v).put(new Edge(Integer.toString(++edgeCount), weight, v, w), w);
	}
    }

    /**
     * Neigbours of vertex v.  Convenience method for using a string label.
     * @param v the vertex to find the neighbours of.
     * @return
     */
    public Iterable<Vertex> adjacentTo(String v) {
	return adjacentTo(vertexList.get(v));
    }

    /**
     * Neigbours of vertex v.
     * @param v the vertex to find the neighbours of.
     * @return
     */
    public Iterable<Vertex> adjacentTo(Vertex v) {
	return adjacencyList.get(v).values();
    }

    /**
     * number of neighbours of vertex v.   Convenience method for using a string label.
     * @param v
     * @return
     */
    public int degree(String v) {
	return degree(vertexList.get(v));
    }

    /**
     * number of neighbours of vertex v.
     * @param v
     * @return
     */
    public int degree(Vertex v) {
	return adjacencyList.get(v).size();
    }

    /**
     * Get all the vertices associated with the graph.
     * @return
     */
    public Iterable<Vertex> getVertices() {
	return adjacencyList.keySet();
    }

    /**
     * is v-w an edge in the graph.   Convenience method for using a string label.
     * @param v
     * @param w
     * @return
     */
    public boolean hasEdge(String v, String w) {
	return hasEdge(vertexList.get(v), vertexList.get(w));
    }

    /**
     * is v-w an edge in the graph
     * @param v
     * @param w
     * @return
     */
    public boolean hasEdge(Vertex v, Vertex w) {
	return adjacencyList.get(v).containsValue(w);
    }

    /**
     * is v a vertex in the graph.  Convenience method for using a string label.
     * @param v
     * @return
     */
    public boolean hasVertex(String v) {
	return vertexList.containsKey(v);
    }

    /**
     * is v a vertex in the graph
     * @param v
     * @return
     */
    public boolean hasVertex(Vertex vertex) {
	return vertexList.containsKey(vertex.getLabel());
    }
    
    /**
     * Gets the vertex in the graph with the label v
     * @param v
     * @return
     */
    public Vertex getVertex(String v) {
	return vertexList.get(v);
    }

    public static TreeMap<Vertex, String> getShortestPaths(Graph g, Vertex s) {
	Iterable<Vertex> allVerts = g.getVertices();
	for (Iterator<Vertex> i = allVerts.iterator(); i.hasNext();) {
	    i.next().setToUndiscovered();
	}

	return new TreeMap<Vertex, String>();
    }

    public int getInDegree(String v) {
	return getInDegree(vertexList.get(v));
    }

    public int getInDegree(Vertex v) {
	int inDegree = 0;
	Collection<TreeMap<Edge, Vertex>> vertLists = adjacencyList.values();
	Collection<Vertex> verts = new LinkedList<Vertex>();
	for (Iterator<TreeMap<Edge, Vertex>> i = vertLists.iterator(); i.hasNext();) {
	    verts.addAll(i.next().values());
	}
	
	for (Iterator<Vertex> i = verts.iterator(); i.hasNext();) {
	    if (((AdjacencyListVertex)v).compareTo(i.next())==0) {
		inDegree++;
	    }
	}

	return inDegree;
    }

    public Iterable<Vertex> getZeroInDegree() {
	LinkedList<Vertex> zeroInDegreeVerts = new LinkedList<Vertex>();
	Iterable<Vertex> verts = getVertices();
	for (Iterator<Vertex> i = verts.iterator(); i.hasNext();) {
	    Vertex v = i.next();
	    if (getInDegree(v) == 0) {
		zeroInDegreeVerts.add(v);
	    }
	}
	return zeroInDegreeVerts;
    }
    
}
