/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public interface Graph {

    /**
     * Add edge v-w.  Convenience method for adding using a string label.
     * @param v vertex v of the edge
     * @param w vertex w of the edge
     */
    void addEdge(String v, String w);

    /**
     * Add edge v-w.
     * @param v vertex v of the edge
     * @param w vertex w of the edge
     */
    void addEdge(Vertex v, Vertex w);

    /**
     * Add edge v-w.  Convenience method for adding using a string label.
     * @param v vertex v of the edge
     * @param w vertex w of the edge
     * @param weight the weight of the edge
     */
    void addEdge(String v, String w, float weight);

    /**
     * Add edge v-w.
     * @param v vertex v of the edge
     * @param w vertex w of the edge
     * @param weight the weight of the edge
     */
    void addEdge(Vertex v, Vertex w, float weight);

    /**
     * Neigbours of vertex v.  Convenience method for using a string label.
     * @param v the vertex to find the neighbours of.
     * @return
     */
    Iterable<Vertex> adjacentTo(String v);

    /**
     * Neigbours of vertex v.
     * @param v the vertex to find the neighbours of.
     * @return
     */
    Iterable<Vertex> adjacentTo(Vertex v);

    /**
     * number of neighbours of vertex v.   Convenience method for using a string label.
     * @param v
     * @return
     */
    int degree(String v);

    /**
     * number of neighbours of vertex v.
     * @param v
     * @return
     */
    int degree(Vertex v);

    /**
     * Get all the vertices associated with the graph.
     * @return
     */
    Iterable<Vertex> getVertices();

    /**
     * is v-w an edge in the graph.   Convenience method for using a string label.
     * @param v
     * @param w
     * @return
     */
    boolean hasEdge(String v, String w);

    /**
     * is v-w an edge in the graph
     * @param v
     * @param w
     * @return
     */
    boolean hasEdge(Vertex v, Vertex w);

    /**
     * is v a vertex in the graph.  Convenience method for using a string label.
     * @param v
     * @return
     */
    boolean hasVertex(String v);

    /**
     * is v a vertex in the graph
     * @param v
     * @return
     */
    boolean hasVertex(Vertex vertex);
    
    /**
     * Gets the vertex in the graph with the label v
     * @param v
     * @return
     */
    Vertex getVertex(String v);
    
}
