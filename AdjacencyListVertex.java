/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lewi0146
 */
public class AdjacencyListVertex implements Vertex, Comparable<Vertex> {

    private String label;
    private boolean discovered;
    private boolean finished;

    public AdjacencyListVertex(String label) {
	this.label = label;
	discovered = false;
	finished = false;
    }

    /**
     * Get a label associated with this vertex.
     * @return 
     */
    public String getLabel() {
	return label;
    }

    /**
     * returns true if this vertex is in an undiscovered state
     * @return true if undiscovered, false otherwise
     */
    public boolean isUndiscovered() {
	return !discovered;
    }

    /**
     * Set this vertex to a discovered state.
     */
    public void setToDiscovered() {
	discovered = true;
    }

    /**
     * Set this vertex to a finished state.
     */
    public void setToFinished() {
	discovered = true;
	finished = true;
    }

    /**
     * Set this vertex to an undiscovered state.
     */
    public void setToUndiscovered() {
	discovered = false;
    }

    /**
     * Create a string representation of this vertex
     * @return a string.
     */
    @Override
    public String toString() {
	return label;
    }

    @Override
    public int compareTo(Vertex v) {
       	return this.getLabel().compareTo(v.getLabel());
    }
    
}
