/*
Michael Wenzel	
CMIS202
03/11/24
Job.java
Job class for holding customer data.
*/
public class Job {

	private static int idTotal = 0;
	private int id;
	private double firstArrival;
	private double jobArrive;
	private double jobComplete;
	private int foodCount;

	//Constructor for known arrival, unknown completion time
	public Job(double arrivalTime) {
		this.id = idTotal;
		this.firstArrival = arrivalTime;
		this.jobArrive = arrivalTime;
		idTotal++;
		this.foodCount = 0;
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
	//Food 
	void addFood() {
		foodCount++;
	}
	public int getFoodCount() {
		return foodCount;
	}
	public void resetFoodCount() {
		this.foodCount = 0;
	}

	@Override
	public String toString() {
		return "ID: " + id + 
				" Arrive: " + jobArrive +
				" Complete: " + jobComplete;
	}
}