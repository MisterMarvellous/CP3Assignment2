public class Edge implements Comparable<Edge> {
    
    private String label;
    private float weight;
    private Vertex source;
    private Vertex destination;

    public Edge(String label, float weight, Vertex source, Vertex destination) {
	this.label = label;
	this.weight = weight;
	this.source = source;
	this.destination = destination;
    }

    public Edge(String label, Vertex source, Vertex destination) {
	this(label, 1.0f, source, destination);
    }

    public String getLabel() {
	return label;
    }

    public float getWeight() {
	return weight;
    }

    public Vertex getSource() {
	return source;
    }

    public Vertex getDestination() {
	return destination;
    }

    @Override
    public int compareTo(Edge e) {
	return this.label.compareTo(e.getLabel());
    }

}
