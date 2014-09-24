import java.util.*;

public class AdjacencyListDirectedGraph implements Graph {

    private TreeMap<Vertex, TreeMap<String, Edge>> adjacencyList;
    private HashMap<String, Vertex> vertexList;
    private int edgeCount;

    public AdjacencyListDirectedGraph() {
	adjacencyList = new TreeMap<Vertex, TreeMap<String, Edge>>();
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
	Vertex vv = vertexList.get(v);
	Vertex vw = vertexList.get(w);

	if (!adjacencyList.containsKey(vv)) {
	    adjacencyList.put(vv, new TreeMap<String, Edge>());
	}
	if (!adjacencyList.containsKey(vw)) {
	    adjacencyList.put(vw, new TreeMap<String, Edge>());
	}
	Set<String> keys = adjacencyList.get(vv).keySet();
	int sameEdgeCount = 0;
	for (Iterator<String> i = keys.iterator(); i.hasNext();) {
	    sameEdgeCount = Math.max(Integer.parseInt(i.next().split(" ")[1]), sameEdgeCount);
	}
	
	adjacencyList.get(vv).put(w+" "+sameEdgeCount, new Edge(Integer.toString(++edgeCount), weight, vv, vw));

    }

    public void addEdge(Vertex v, Vertex w, float weight) {
	if (!vertexList.containsKey(v.getLabel())) {
	    vertexList.put(v.getLabel(), v);
	}
	if (!vertexList.containsKey(w.getLabel())) {
	    vertexList.put(w.getLabel(), w);
	}
	addEdge(v.getLabel(), w.getLabel(), weight);
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
	Iterable<Edge> incidentEdges = adjacencyList.get(v).values();
	TreeSet<Vertex> adjs = new TreeSet<Vertex>();
	for (Iterator<Edge> i = incidentEdges.iterator(); i.hasNext();) {
	    adjs.add(i.next().getDestination());
	}
	return adjs;
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
	return adjacencyList.get(v).containsKey(w.getLabel()+" 0");
    }

    public Iterable<Edge> getEdges(String v, String w) {
	return getEdges(vertexList.get(v), vertexList.get(w));
    }

    public Iterable<Edge> getEdges(Vertex v, Vertex w) {
	LinkedList<Edge> matchingEdges = new LinkedList<Edge>();
	for (int i = 0;;i++) {
	    Edge e = adjacencyList.get(v).get(w.getLabel()+" "+i);
	    if (e==null) break;
	    matchingEdges.add(e);
	}
	return matchingEdges;
    }

    public static Edge getSmallestEdge(Iterable<Edge> edges) {
	Edge smallestEdge = new Edge(null, Float.MAX_VALUE, null, null);
	for (Iterator<Edge> i = edges.iterator(); i.hasNext();) {
	    Edge e = i.next();
	    if (e.getWeight() < smallestEdge.getWeight()) {
		smallestEdge = e;
	    }
	}
	return smallestEdge;
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

    public static TreeMap<Vertex, String> getShortestPathsPriorityQueue(Graph g, Vertex s) {
	Iterable<Vertex> allVerts = g.getVertices();
	HashMap<Vertex, Float> distance = new HashMap<Vertex, Float>();
	HashMap<Vertex, Vertex> predecessor = new HashMap<Vertex, Vertex>();
	PriorityQueue<Vertex> q = new PriorityQueue<Vertex>(((Collection)allVerts).size(),
							    new Comparator<Vertex>() {
							       public int compare(Vertex a, Vertex b) {
								   return (int)(distance.get(a) - distance.get(b));
							       }
							    });	

	for (Iterator<Vertex> i = allVerts.iterator(); i.hasNext();) {
	    Vertex v = i.next();
	    v.setToUndiscovered();
	    distance.put(v, (((AdjacencyListVertex)v).compareTo(s)==0?0.0f:Float.MAX_VALUE));
	    predecessor.put(v, null);
	    q.offer(v);
	}

	while (q.size() > 0) {
	    Vertex v = q.poll();
	    if (v.isUndiscovered()) {
		if (distance.get(v) == Float.MAX_VALUE) { return null; }
		v.setToDiscovered();
		Iterable<Vertex> adjVerts = g.adjacentTo(v);
		for (Iterator<Vertex> i = adjVerts.iterator(); i.hasNext();) {
		    Vertex w = i.next();
		    if (w.isUndiscovered()) {
			Edge e = AdjacencyListDirectedGraph.getSmallestEdge(g.getEdges(v, w));
			if (distance.get(v) + e.getWeight() < distance.get(w)) {
			    distance.remove(w);
			    distance.put(w, distance.get(v) + e.getWeight());
			    predecessor.remove(w);
			    predecessor.put(w, v);
			    q.remove(w);
			    q.offer(w);
			}
		    }
		}
	    }
	}

	TreeMap<Vertex, String> resultMap = new TreeMap<Vertex, String>();
	for (Iterator<Vertex> i = allVerts.iterator(); i.hasNext();) {
	    Vertex v = i.next();
	    resultMap.put(v, Float.toString(distance.get(v)) + " " + predecessor.get(v));
	}

	return resultMap;
    }

    public static TreeMap<Vertex, String> getShortestPathsList(Graph g, Vertex s) {
	Collection<Vertex> allVerts = (Collection<Vertex>)(g.getVertices());
	HashMap<Vertex, Float> distance = new HashMap<Vertex, Float>();
	HashMap<Vertex, Vertex> predecessor = new HashMap<Vertex, Vertex>();
	List<Vertex> l = new LinkedList<Vertex>(allVerts);
	Comparator<Vertex> c = new Comparator<Vertex>() {
	    public int compare(Vertex a, Vertex b) {
		return (int)(distance.get(a) - distance.get(b));
	    }
	};

	for (Vertex v : allVerts) {
	    v.setToUndiscovered();
	    distance.put(v, (((AdjacencyListVertex)v).compareTo(s)==0?0.0f:Float.MAX_VALUE));
	    predecessor.put(v, null);
	}

	while (l.size() > 0) {
	    Collections.sort(l, c);
	    Vertex v = l.get(0);
	    l.remove(0);
	    if (v.isUndiscovered()) {
		if (distance.get(v) == Float.MAX_VALUE) { return null; }
		v.setToDiscovered();
		Iterable<Vertex> adjVerts = g.adjacentTo(v);
		for (Iterator<Vertex> i = adjVerts.iterator(); i.hasNext();) {
		    Vertex w = i.next();
		    if (w.isUndiscovered()) {
			Edge e = AdjacencyListDirectedGraph.getSmallestEdge(g.getEdges(v, w));
			if (distance.get(v) + e.getWeight() < distance.get(w)) {
			    distance.remove(w);
			    distance.put(w, distance.get(v) + e.getWeight());
			    predecessor.remove(w);
			    predecessor.put(w, v);
			}
		    }
		}
	    }
	}

	TreeMap<Vertex, String> resultMap = new TreeMap<Vertex, String>();
	for (Iterator<Vertex> i = allVerts.iterator(); i.hasNext();) {
	    Vertex v = i.next();
	    resultMap.put(v, Float.toString(distance.get(v)) + " " + predecessor.get(v));
	}

	return resultMap;
    }

    public int getInDegree(String v) {
	int inDegree = 0;
	Iterable<TreeMap<String, Edge>> adjMaps = adjacencyList.values();
	for (Iterator<TreeMap<String, Edge>> i = adjMaps.iterator(); i.hasNext();) {
	    Iterable<String> adjLabels = i.next().keySet();
	    for (Iterator<String> j = adjLabels.iterator(); j.hasNext();) {
		if (j.next().split(" ")[0].equals(v)) inDegree++;
	    }
	}
	return inDegree;
    }

    public int getInDegree(Vertex v) {
	return getInDegree(v.getLabel());
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
