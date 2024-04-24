package demo;

abstract class Job {
	private static int idTotal = 0;
	private int id;
	private double firstArrival;
	private double jobArrive;
	private double jobComplete;

	//Constructor for known arrival, unknown completion time
	public Job(double arrivalTime) {
		this.id = idTotal;
		this.firstArrival = arrivalTime;
		this.jobArrive = arrivalTime;
		idTotal++;
	}
	//setters and getters
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
	//initial arrival into restaurant
	public double getFirstArrival(){
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
    private int id;
    private static int caridTotal = 0;
	private String origin;
    private String destination;
    private String nextNode;
    private String lastNode;
    public Car(String origin, String destination, double arrivalTime) {
        super(arrivalTime);
        this.id = caridTotal;
        this.origin = origin;
        this.destination = destination;
        caridTotal++;
    }
    public String getOrigin(){
        return origin;
    }
    public String getDestination(){
        return destination;
    }
    public int getID(){
        return id;
    }
}
