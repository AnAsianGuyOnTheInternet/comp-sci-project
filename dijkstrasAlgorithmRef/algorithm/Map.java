package dijkstrasAlgorithmRef.algorithm;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;

public class Map {

    private ArrayList<Node> nodeList;
    private ArrayList<Arc> arcList;
    private Random random = new Random();

    public Map(int depth) {
        this.nodeList = new ArrayList<Node>();
        this.arcList = new ArrayList<Arc>();
        for (int i = 0; i < depth; i++) {
            this.nodeList.add(new Node());
        }
        Arc tempArc;
        for (int i = 0; i < depth * 3; i++) {
            tempArc = 
            nodeList.get(random.nextInt(depth - 1))
            .addConnection(nodeList.get(random.nextInt(depth - 1)));
            if (tempArc != null)
                this.arcList.add(tempArc);
        }
    }
    
    private boolean outliersArePresent() {
        for (Node node : this.nodeList) {
            if (node.getConnections().size() == 0)
                return true;
        }

        return false;
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        for (Arc arc : this.arcList) {
            result.append(arc.toString() + "\n");
        }

        return result.toString();
    }
}
