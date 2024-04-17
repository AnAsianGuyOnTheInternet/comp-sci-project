package dijkstrasAlgorithmRef.algorithm;

public class Arc {

    private enum set {
        shortest,
        known,
        unknown,
        reject
    };
    private set set;
    private Node source;
    private Node destination;

    public Arc(Node source, Node destination) {
        this.set = set.unknown;
        this.source = source;
        this.destination = destination;
    }

    public String toString() {
        return "source: " + this.source.toString() + ", "
        + "destination: " + this.destination.toString() + ", "
        + this.set.toString();
    }
}
