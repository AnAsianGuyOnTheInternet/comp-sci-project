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
    private double shortestPathLength;
    private Arc lastArcInPath;

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
    public double getShortestPathLength() {
        return this.shortestPathLength;
    }
    public Arc getLastArcInPath() {
        return this.lastArcInPath;
    }
    /**
     * A - Shortest known path
     * B - Known path
     * 
     * @param setChar
     */
    public void setSet(char setChar) {
        if (setChar == 'A')
            this.set = set.shortest;
        if (setChar == 'B')
            this.set = set.known;
        else
            this.set = set.unknown;
    }
    public void setLastArcInPath(Arc lastArc) {
        this.lastArcInPath = lastArc;
    }
    public void setShortestPathLength(double length) {
        this.shortestPathLength = length;
    }
    /**
     * A - Shortest known path
     * B - Known path
     * C - Unknown path
     * 
     * @param setChar
     */
    public boolean isSet(char setChar) {
        if (setChar == 'A' && this.set == set.shortest)
            return true;
        if (setChar == 'B' && this.set == set.known)
            return true;
        if (setChar == 'C' && this.set == set.unknown)
            return true;
        else
            return false;
    }

    public String toString() {
        return this.ID + "/" + this.name + ":" + this.set.toString();
    }
}
