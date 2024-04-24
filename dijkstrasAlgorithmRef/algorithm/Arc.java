package dijkstrasAlgorithmRef.algorithm;

public class Arc {

    private enum set {
        shortest,
        known,
        unknown,
        reject
    };
    private set set;
    private double weight;
    private Node source;
    private Node destination;

    public Arc(Node source, Node destination, double weight) {
        this.set = set.unknown;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public boolean isConnecting(Node fromNode) {
        if (this.source.equals(fromNode) || this.destination.equals(fromNode))
            return true;
        else
        return false;
    }

    /**
     * A - shortest known path, connects nodes in A
     * B - connections from A nodes to B nodes
     * D - Rejects
     * 
     * @param setChar
     */
    public void setSet(char setChar) {
        if (setChar == 'A')
            this.set = set.shortest;
        if (setChar == 'B')
            this.set = set.known;
        if (setChar == 'D')
            this.set = set.reject;
        else
            this.set = set.unknown;
    }

    public Node getToNode(Node fromNode) {
        if (this.source.equals(fromNode))
            return this.destination;
        if (this.destination.equals(fromNode))
            return this.source;
        else
            return null;
    }

    public String toString() {
        return "source: " + this.source.toString() + ", "
        + "destination: " + this.destination.toString() + ", "
        + this.set.toString();
    }
}
