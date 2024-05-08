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
    private final List<Job> northboundJobs; // List to store jobs for northbound traffic
    private final List<Job> southboundJobs; // List to store jobs for southbound traffic

    public Route(Node source, Node destination, int distance, int speed) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.speedLimit = speed;
        this.northboundJobs = new ArrayList<>(); // Initialize the list for northbound traffic
        this.southboundJobs = new ArrayList<>(); // Initialize the list for southbound traffic
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

    public void addNorthboundJob(Job job, double startTime) {
        job.setArrival(startTime); // Set the arrival time for the job
        northboundJobs.add(job); // Add the job to the northbound traffic list
    }

    public void addSouthboundJob(Job job, double startTime) {
        job.setArrival(startTime); // Set the arrival time for the job
        southboundJobs.add(job); // Add the job to the southbound traffic list
    }
}


class RoadGridModel {
    private List<Node> nodes;
    private ExponentialDistribution exponentialDistribution;
    private Random random;
    private Map<Route, SingleServerQueue> routeTraffic;
    private double lambda;  // Define lambda as a class field
    private Clock clock = new Clock();

    public RoadGridModel(double lambda, String filePath) {
        this.lambda = lambda;  // Initialize lambda
        this.nodes = new ArrayList<>();
        this.exponentialDistribution = new ExponentialDistribution(lambda);
        this.random = new Random();
        this.routeTraffic = new HashMap<>();
        initializeNodesAndRoutes(filePath);  // No need to pass lambda here
    }

    private void initializeNodesAndRoutes(String filePath) {
        LocationParser parser = new LocationParser(filePath);
        List<LocationParser.RoadSegment> roadSegments = parser.loadRoadSegments();
        Map<String, Node> nodeMap = new HashMap<>();
        Node previousNode = null;

        for (LocationParser.RoadSegment segment : roadSegments) {
            Node currentNode = nodeMap.computeIfAbsent(segment.roadName, Node::new);
            if (previousNode != null) {
                Route route = new Route(previousNode, currentNode, (int) segment.distance, segment.speedLimit);
                Route reverseRoute = new Route(currentNode, previousNode, (int) segment.distance, segment.speedLimit);
                previousNode.addRoute(route);
                currentNode.addRoute(reverseRoute);
                routeTraffic.put(route, new SingleServerQueue(this.lambda)); // Use this.lambda
                routeTraffic.put(reverseRoute, new SingleServerQueue(this.lambda));
            }
            previousNode = currentNode;
        }
        nodes.addAll(nodeMap.values());
        System.out.println("Nodes and routes initialized successfully.");
    }

    public void simulationStep(int numCars, double startTime) {
        this.clock.setTime(startTime);

        for (int i = 0; i < numCars; i++) {
            Node source = selectNode(); // Custom method to select source node
            Node destination = selectNode(); // Custom method to select destination node
            if (source.equals(destination)) continue; // Skip if source and destination are the same

            List<Route> routePlan = findRoute(source, destination); // Find route from source to destination
            if (routePlan == null || routePlan.isEmpty()) {
                System.out.println("No valid route found from " + source.getName() + " to " + destination.getName());
                continue;
            }

            double arrivalTime = clock.getTime() + exponentialDistribution.sample();
            double completionTime = 0;
            Car newCar = new Car(source.getName(), destination.getName(), arrivalTime);
            routePlan.forEach(route -> routeTraffic.get(route).add(newCar, arrivalTime));
            for (Route route : routePlan) {
                routeTraffic.get(route).add(newCar, arrivalTime);
                completionTime += route.getDistance() * (route.getSpeedLimit() / 60); // gets minutes required to travel
            } // Manage cars in queues, determine completion times
            this.clock.addEntranceEvent(newCar, arrivalTime, completionTime);

            System.out.println("Car " + newCar.getID() + " will travel from " + source.getName() +
                                " to " + destination.getName() + " starting at " + arrivalTime);
    
        }
    }

    private Node selectNode() {
        return nodes.get(random.nextInt(nodes.size()));
    }

    private List<Route> findRoute(Node source, Node destination) {
        List<Route> path = new ArrayList<>();
        Node current = source;
    
        while (current != null && !current.equals(destination)) {
            // Assuming there's always one route from each node in the direction of the destination
            if (current.getRoutes().isEmpty()) {
                return new ArrayList<>(); // No route available from current node
            }
    
            Route nextRoute = current.getRoutes().get(0); // Get the first (and supposedly only) route
            if (nextRoute.getDestination().equals(destination)) {
                path.add(nextRoute);
                break; // Reached the destination
            } else if (nextRoute.getDestination().equals(source)) {
                return new ArrayList<>(); // Avoid looping back to the source
            } else {
                path.add(nextRoute); // Add the route to the path and move to the next node
                current = nextRoute.getDestination();
            }
        }
    
        if (!path.isEmpty() && path.get(path.size() - 1).getDestination().equals(destination)) {
            return path; // Successfully found a route to the destination
        }
    
        return new ArrayList<>(); // Return an empty list if no valid path is found
    }
}