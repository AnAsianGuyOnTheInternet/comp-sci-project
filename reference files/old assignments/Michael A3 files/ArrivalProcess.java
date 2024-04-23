/*
Michael Wenzel	
CMIS202
02/26/24
ArrivalProcess.java
Handles new Jobs and the time at which they will arrive
*/
public class ArrivalProcess {
	private ExponentialDistribution arrivalDist;
	private double nextArrivalTime;
	
	public ArrivalProcess(double arrivalRate) {
		this.arrivalDist = new ExponentialDistribution(arrivalRate);
		this.nextArrivalTime = arrivalDist.sample();
	}
	//create new Job and assign arrival time
	public Job nextJob() {
		Job newJob = new Job(nextArrivalTime);
		nextArrivalTime += arrivalDist.sample();
		return newJob;
	}
}