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

    public Node() {
        this.name = "Default";
        this.ID = total_nodes_count++;
        this.set = set.unknown;
    }
    public Node(String name) {
        this.name = name;
        this.ID = total_nodes_count++;
        this.set = set.unknown;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.ID + "/" + this.name + ":" + this.set.toString();
    }
}
