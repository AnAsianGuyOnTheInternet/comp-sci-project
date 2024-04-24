package demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.stream.Collectors;

class Node {
    private final String name;
    private final List<Route> routes;
    private int distanceFromSource;
    private Node previousNode;

    public Node(String name) {
        this.name = name;
        this.routes = new ArrayList<>();
        this.distanceFromSource = Integer.MAX_VALUE;
        this.previousNode = null;
    }
    public String getName() {
        return name;
    }
    public List<Route> getRoutes() {
        return routes;
    }
    public int getDistanceFromSource() {
        return distanceFromSource;
    }
    public void setDistanceFromSource(int distanceFromSource) {
        this.distanceFromSource = distanceFromSource;
    }
    public Node getPreviousNode() {
        return previousNode;
    }
    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }
    public void addRoute(Route route) {
        routes.add(route);
    }
    @Override
    public String toString() {
        return name;
    }
}

class Route {
    private final Node source;
    private final Node destination;
    private final int distance; 
    private final int speedLimit;

    public Route(Node source, Node destination, int distance, int speed) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.speedLimit = speed;
    }
    public Node getSource() {
        return source;
    }
    public Node getDestination() {
        return destination;
    }
    public int getDistance() {
        return distance;
    }
    public int getSpeed() {
        return speedLimit;
    }
    public int calculateTravelTime() {
        return (int) distance / speedLimit;
    }
}

class DijkstraAlgorithm {
    public List<Node> findShortestPath(Node source, Node destination, List<Node> allNodes) {
        PriorityQueue<Node> unvisited = new PriorityQueue<>(Comparator.comparingInt(Node::getDistanceFromSource));
    
        for (Node node : allNodes) {
            node.setDistanceFromSource(node == source ? 0 : Integer.MAX_VALUE);
            node.setPreviousNode(null);
            unvisited.add(node);
        }
    
        while (!unvisited.isEmpty()) {
            Node current = unvisited.poll(); // This never returns null if unvisited is not empty
    
            if (current.getDistanceFromSource() == Integer.MAX_VALUE) {
                break; // All remaining nodes are unreachable
            }
    
            for (Route route : current.getRoutes()) {
                Node neighbor = route.getDestination();
                int newDistance = current.getDistanceFromSource() + route.getDistance();
                if (newDistance < neighbor.getDistanceFromSource()) {
                    unvisited.remove(neighbor);
                    neighbor.setDistanceFromSource(newDistance);
                    neighbor.setPreviousNode(current);
                    unvisited.add(neighbor);
                }
            }
        }
    
        return buildShortestPath(destination);
    }

    private List<Node> buildShortestPath(Node destination) {
        List<Node> path = new ArrayList<>();
        for (Node node = destination; node != null; node = node.getPreviousNode()) {
            path.add(0, node);
        }
        return path;
    }
}


public class RoadGridModel {
    private final List<Node> nodes;
    private final ExponentialDistribution exponentialDistribution;

    public RoadGridModel(double lambda) {
        nodes = new ArrayList<>(); 
        nodes.add(new Node("Firstown"));
        nodes.add(new Node("Secondia"));
        nodes.add(new Node("Thirdland"));
        nodes.add(new Node("Forthen"));
        nodes.add(new Node("Fiverick"));

        nodes.get(0).addRoute(new Route(nodes.get(0), nodes.get(1), 30, 30));
        nodes.get(0).addRoute(new Route(nodes.get(0), nodes.get(2), 20, 40));
        nodes.get(1).addRoute(new Route(nodes.get(1), nodes.get(3), 40, 45));
        nodes.get(1).addRoute(new Route(nodes.get(1), nodes.get(2), 30, 40));
        nodes.get(2).addRoute(new Route(nodes.get(2), nodes.get(4), 60, 75));
        nodes.get(3).addRoute(new Route(nodes.get(3), nodes.get(2), 40, 40));
        nodes.get(3).addRoute(new Route(nodes.get(3), nodes.get(4), 10, 25));

        exponentialDistribution = new ExponentialDistribution(lambda);
    }

public List<Node> calculateShortestPath(String sourceName, String destinationName) {
    Node source = null;
    Node destination = null;
    for (Node node : nodes) {
        if (node.getName().equals(sourceName)) {
            source = node;
        } else if (node.getName().equals(destinationName)) {
            destination = node;
        }
    }
    
    if (source == null) {
        throw new IllegalArgumentException("Source node '" + sourceName + "' not found.");
    }
    if (destination == null) {
        throw new IllegalArgumentException("Destination node '" + destinationName + "' not found.");
    }

    DijkstraAlgorithm dijkstra = new DijkstraAlgorithm();
    return dijkstra.findShortestPath(source, destination, nodes);
}

public void simulateTraffic(int numVehicles) {
    Random random = new Random();
    for (int i = 0; i < numVehicles; i++) {
        double arrivalTime = exponentialDistribution.sample();
        int sourceIndex = random.nextInt(nodes.size());
        int destinationIndex;
        do {
            destinationIndex = random.nextInt(nodes.size());
        } while (destinationIndex == sourceIndex);

        List<Node> shortestPath = calculateShortestPath(nodes.get(sourceIndex).getName(), nodes.get(destinationIndex).getName());
        double totalTravelTime = 0;
        for (int j = 0; j < shortestPath.size() - 1; j++) {
            Node start = shortestPath.get(j);
            Node end = shortestPath.get(j + 1);
            for (Route route : start.getRoutes()) {
                if (route.getDestination().equals(end)) {
                    totalTravelTime += route.calculateTravelTime();
                    break;
                }
            }
        }

        String pathStr = shortestPath.stream().map(Node::toString).collect(Collectors.joining(" -> "));
        System.out.println("Vehicle " + (i + 1) + ": " + nodes.get(sourceIndex).getName() + " to " + nodes.get(destinationIndex).getName() + " via " + pathStr + " (Arrival Time: " + arrivalTime + ", Travel Time: " + totalTravelTime + " hrs)");
    }
}

    public static void main(String[] args) {
        double lambda = 0.1; 
        int numVehicles = 100;
        RoadGridModel roadGridModel = new RoadGridModel(lambda);
        roadGridModel.simulateTraffic(numVehicles);
    }
}