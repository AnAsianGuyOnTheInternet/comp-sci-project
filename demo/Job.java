package demo;

abstract class Job {
    private static int idTotal = 0;
    private int id;
    private double firstArrival;
    private double jobArrive;
    private double jobComplete;

    // Constructor for known arrival, unknown completion time
    public Job(double arrivalTime) {
        this.id = idTotal;
        this.firstArrival = arrivalTime;
        this.jobArrive = arrivalTime;
        idTotal++;
    }

    // Setters and getters
    void setArrival(double arrivalTime) {
        this.jobArrive = arrivalTime;
    }

    void setCompletion(double completionTime) {
        this.jobComplete = completionTime;
    }

    double getArrival() {
        return jobArrive;
    }

    double getCompletion() {
        return jobComplete;
    }

    public int getID() {
        return id;
    }

    // Initial arrival into the system
    public double getFirstArrival() {
        return firstArrival;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " Arrive: " + jobArrive +
                " Complete: " + jobComplete;
    }
}

class Car extends Job {
    private String origin;
    private String destination;

    public Car(String origin, String destination, double arrivalTime) {
        super(arrivalTime);
        this.origin = origin;
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }
}

class Bus extends Job {
    private String origin;
    private String destination;

    public Bus(String origin, String destination, double arrivalTime) {
        super(arrivalTime);
        this.origin = origin;
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

}