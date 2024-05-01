package demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class Node {
    private final String name;
    private final List<Route> routes;

    public Node(String name) {
        this.name = name;
        this.routes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void addRoute(Route route) {
        routes.add(route);
    }
}

class Route {
    private final Node source;
    private final Node destination;
    private final int distance;
    private final int speedLimit;
    private final List<Job> jobsToDestination; // List to store jobs to destination

    public Route(Node source, Node destination, int distance, int speed) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.speedLimit = speed;
        this.jobsToDestination = new ArrayList<>(); // Initialize the list
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

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void addJobToDestination(Job job, double startTime) {
        job.setArrival(startTime); // Set the arrival time for the job
        jobsToDestination.add(job); // Add the job to the list
    }
}


public class RoadGridModel {
    private List<Node> nodes;
    private ExponentialDistribution exponentialDistribution;
    private Random random;

    public RoadGridModel(double lambda, String filePath) {
        this.nodes = new ArrayList<>();
        this.exponentialDistribution = new ExponentialDistribution(lambda);
        this.random = new Random();
        initializeNodesAndRoutes(filePath);
    }

    private void initializeNodesAndRoutes(String filePath) {
        LocationParser parser = new LocationParser(filePath);
        List<LocationParser.RoadSegment> roadSegments = parser.loadRoadSegments();
        Map<String, Node> nodeMap = new HashMap<>();

        Node previousNode = null; // Properly declare and initialize previousNode.

        for (LocationParser.RoadSegment segment : roadSegments) {
            // Ensure each node is only created once and reused if referenced multiple times.
            Node currentNode = nodeMap.computeIfAbsent(segment.roadName, Node::new);

            // If there is a previous node, create a route from it to the current node.
            if (previousNode != null) {
                Route route = new Route(previousNode, currentNode, (int) segment.distance, segment.speedLimit);
                previousNode.addRoute(route); // Add the route to the previous node's route list.
            }

            // Update previousNode to be the current node for the next iteration.
            previousNode = currentNode;
        }

        nodes.addAll(nodeMap.values());

        // Add logging to track progress
        System.out.println("Nodes and routes initialized successfully.");
    }

    
    public void simulateTraffic(int numCars, int numBuses, double startTime) {
        double currentTime = startTime;
    
        // Check if nodes list is not empty
        if (nodes.isEmpty()) {
            System.out.println("No nodes available for traffic simulation.");
            return;
        }
    
        for (int i = 0; i < numCars; i++) {
            int startIndex = random.nextInt(nodes.size());
            Node startNode = nodes.get(startIndex);
    
            if (startNode.getRoutes().isEmpty()) {
                System.out.println("No routes available from " + startNode.getName());
                continue; // Skip to the next vehicle if no routes are available from this node
            }
    
            Route route = startNode.getRoutes().get(random.nextInt(startNode.getRoutes().size()));
            if (route.getDestination() == null) {
                System.out.println("Invalid route from " + startNode.getName() + ". Skipping this route.");
                continue; // Skip this route if destination node is null
            }
    
            double jobStartTime = currentTime + exponentialDistribution.sample(); // vehicles arrive over time
    
            Car newCar = new Car(startNode.getName(), route.getDestination().getName(), jobStartTime);
            route.addJobToDestination(newCar, jobStartTime); // Safely add vehicles only in the valid direction
            System.out.println("Car " + newCar.getID() + " added to route from " + route.getSource().getName() +
                                " to " + route.getDestination().getName() + " at " + jobStartTime);
    
            // Additional checks for route attributes
            if (route.getDistance() != 0 && route.getSpeedLimit() != 0) {
                double travelTime = route.getDistance() / (double) route.getSpeedLimit();
                newCar.setCompletion(jobStartTime + travelTime);
            } else {
                System.out.println("Invalid route attributes for car " + newCar.getID() + ". Skipping this route.");
            }
    
            currentTime = jobStartTime; // Advance time as vehicles arrive
        }
    
        for (int i = 0; i < numBuses; i++) {
            int startIndex = random.nextInt(nodes.size());
            Node startNode = nodes.get(startIndex);
    
            if (startNode.getRoutes().isEmpty()) {
                System.out.println("No routes available from " + startNode.getName() + " for the bus.");
                continue; // Skip to the next bus if no routes are available from this node
            }
    
            Route route = startNode.getRoutes().get(random.nextInt(startNode.getRoutes().size()));
            if (route.getDestination() == null) {
                System.out.println("Invalid route from " + startNode.getName() + " for the bus. Skipping this route.");
                continue; // Skip this route if destination node is null
            }
    
            double jobStartTime = currentTime + exponentialDistribution.sample(); // buses arrive over time
    
            Bus newBus = new Bus(startNode.getName(), route.getDestination().getName(), jobStartTime);
            route.addJobToDestination(newBus, jobStartTime); // Safely add buses only in the valid direction
            System.out.println("Bus " + newBus.getID() + " added to route from " + route.getSource().getName() +
                                " to " + route.getDestination().getName() + " at " + jobStartTime);
    
            // Additional checks for route attributes
            if (route.getDistance() != 0 && route.getSpeedLimit() != 0) {
                double travelTime = route.getDistance() / (double) route.getSpeedLimit();
                newBus.setCompletion(jobStartTime + travelTime);
            } else {
                System.out.println("Invalid route attributes for bus " + newBus.getID() + ". Skipping this route.");
            }
    
            currentTime = jobStartTime; // Advance time as buses arrive
        }
    }
    
    public static void main(String[] args) {
        double lambda = 0.1; // Rate of vehicle arrivals
        String filePath = "demo\\locationdata.txt"; 
    
        RoadGridModel roadGridModel = new RoadGridModel(lambda, filePath);
        roadGridModel.simulateTraffic(900, 5, 0);
    }
}
