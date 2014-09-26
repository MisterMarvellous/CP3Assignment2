/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lewi0146
 */
public interface Vertex extends Comparable<Vertex>{

    /**
     * Get a label associated with this vertex.
     * @return 
     */
    String getLabel();

    /**
     * returns true if this vertex is in an undiscovered state
     * @return true if undiscovered, false otherwise
     */
    boolean isUndiscovered();

    /**
     * Set this vertex to a discovered state.
     */
    void setToDiscovered();

    /**
     * Set this vertex to a finished state.
     */
    void setToFinished();

    /**
     * Set this vertex to an undiscovered state.
     */
    void setToUndiscovered();

    /**
     * Create a string representation of this vertex
     * @return a string.
     */
    @Override
    String toString();
    
}
