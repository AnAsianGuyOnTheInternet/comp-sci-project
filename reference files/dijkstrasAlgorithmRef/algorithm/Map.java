package dijkstrasAlgorithmRef.algorithm;
import java.util.ArrayList;
import java.util.Random;

public class Map {

    private ArrayList<Node> nodeList;
    private ArrayList<Arc> arcList;

    public Map() {
        this.nodeList = new ArrayList<Node>();
        this.arcList = new ArrayList<Arc>();
    }

    public void populateWithRandom(int depth) {
        Random random = new Random();
        for (int i = 0; i < depth; i++) {
            this.nodeList.add(new Node());
        }
        Arc tempArc;
        for (int i = 0; i < depth * 3; i++) {
            tempArc = new Arc(nodeList.get(random.nextInt(depth - 1)), 
            nodeList.get(random.nextInt(depth - 1)), 
            random.nextInt(depth));
            if (tempArc != null)
                this.arcList.add(tempArc);
        }
    }

    /**
     * Add a node to the map, which is differentiated by name.
     * 
     * @param name
     * @return success? name must be unique.
     */
    public boolean addNode(String name) {
        this.nodeList.add(new Node(name));
        if (this.findNodeFromName(name) != null)
            return false;
        return true;
    }
    /**
     * Adds a connection between two already existing nodes. 
     * The names belonging to each node must be an exact match.
     * 
     * @param sourceName
     * @param destinationName
     * @param distance
     * @return success? names must exist and connection be possible-
     * it doesnt connect to itself, or duplicate another connection.
     */
    public boolean addConnection(String sourceName, String destinationName, double distance) {
        Node sourceNode = findNodeFromName(sourceName);
        Node destinationNode = findNodeFromName(destinationName);
        if (sourceNode == null || destinationNode == null)
            return false;

        this.arcList.add(new Arc(sourceNode, destinationNode, distance));
        return true;
    }
    /** 
     * Finds shortest path from entrance to exit based on existing arcs.
     * Will return null if entrance or exit do not exist. 
     * Will return an empty Arc[] if no possible path exists.
     */
    public Arc[] solve(String entrance, String exit) {
        Node entranceNode = this.findNodeFromName(entrance);
        Node exitNode = this.findNodeFromName(exit);
        if (entranceNode == null || exitNode == null)
            return null;

        Arc[] results = new Arc[this.arcList.size()]
        solveStep(entranceNode, exitNode, results);

        for (Node node : this.nodeList) {
            if (node.isSet('B')) {
                boolean areAllB = true;
                for (Arc arc : getConnections(node)) {
                    if (arc.isSet('C')) {
                        areAllB = false;
                    }
                }

                if (areAllB) {
                    if (node.equals(exitNode))
                        return results;
                    else
                        node.setSet('A');
                        solveStep(node, exitNode, resultList);
                }
            }
        }
    }

    private Arc[] solveStep(Node entranceNode, Node exitNode, Arc[] results) {
        entranceNode.setSet('A');

        for (Arc arc : getConnections(entranceNode)) {
            arc.setSet('B');
            Node toNode = arc.getToNode(entranceNode);
            if (toNode.isSet('B')) {
                if (entranceNode.getShortestPathLength() + arc.getWeight() < toNode.getShortestPathLength()) {
                    toNode.getLastArcInPath().setSet('D');
                    toNode.setLastArcInPath(arc);
                    toNode.setShortestPathLength(entranceNode.getShortestPathLength() + arc.getWeight());
                }
            }
            else if (toNode.isSet('C')) {
                toNode.setSet('B');
                toNode.setLastArcInPath(arc);
                toNode.setShortestPathLength(entranceNode.getShortestPathLength() + arc.getWeight());
            }
        }
    }

    private Arc[] getConnections(Node fromNode) {
        int connections = 0;
        for (int i = 0; i < arcList.size(); i++) {
            if (arcList.get(i).isConnecting(fromNode))
                connections++;
        }
        Arc[] result = new Arc[connections];
        int resultCount = 0;
        Arc fromArc;
        for (int i = 0; i < arcList.size(); i++) {
            fromArc = arcList.get(i);
            if (fromArc.isConnecting(fromNode))
                result[resultCount++] = fromArc;
        }

        return result;
    }
    
    private Node findNodeFromName(String name) {
        for (Node node : this.nodeList) {
            if (node.getName().equals(name))
                return node;
        }

        return null;
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        for (Arc arc : this.arcList) {
            result.append(arc.toString() + "\n");
        }

        return result.toString();
    }
}
