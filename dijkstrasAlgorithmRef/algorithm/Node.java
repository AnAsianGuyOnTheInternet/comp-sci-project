package dijkstrasAlgorithmRef.algorithm;
import java.util.ArrayList;

public class Node {

    private String name;
    private static int total_nodes_count = 0;
    private int ID;
    private enum set {
        shortest,
        known,
        unknown
    };
    private set set;
    private ArrayList<Node> connections;

    public Node() {
        this.name = "Default";
        this.ID = total_nodes_count++;
        this.connections = new ArrayList<Node>();
        this.set = set.unknown;
    }
    public Node(String name) {
        this.name = name;
        this.ID = total_nodes_count++;
        this.connections = new ArrayList<Node>();
        this.set = set.unknown;
    }

    public Arc addConnection(Node node) {
        for (Node connection : connections) {
            if (connection.equals(node))
                return null;
        }
        if (node.equals(this))
            return null;
            
        this.connections.add(node);
        return new Arc(this, node);
    }

    public ArrayList getConnections() {
        return this.connections;
    }

    public String toString() {
        return this.ID + "/" + this.name + ":" + this.set.toString();
    }
}
