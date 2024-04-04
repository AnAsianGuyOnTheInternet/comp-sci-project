/*
Michael Wenzel	
CMIS202
02/26/24
SingleServerQueue.java
Handles servicing of jobs after arrival to completion.
*/
public class SingleServerQueue {
	private Queue waitList;
	private Job jobInService;
	private double nextEndServiceTime;
	private RandomDistribution serviceTimeDistribution;

	//construct Single Server Queue
	public SingleServerQueue(double serviceLambda) {
		this.waitList = new Queue();
		this.jobInService = null;
		this.nextEndServiceTime = Double.MAX_VALUE;
		this.serviceTimeDistribution  = new ExponentialDistribution(serviceLambda);
	}

	//add Job to queue
	public void add(Job job, double currentTime) {
		//start job if none in service
		if (jobInService == null) {
			jobInService = job;
			nextEndServiceTime = currentTime + serviceTimeDistribution.sample();
			jobInService.setArrival(currentTime);
			jobInService.setCompletion(nextEndServiceTime);
		}
		//add job to waitlist if other job in service
		else {
			waitList.enqueue(job);
			job.setArrival(currentTime);
			job.setCompletion(nextEndServiceTime + serviceTimeDistribution.sample());
		}
	}

	public double getEndServiceTime() {
		return nextEndServiceTime;
	}

	//Advance waitlist to service next job and return completed job
	public Job complete(double currentTime) {
        Job completedJob = jobInService;
        jobInService = waitList.dequeue();
        if (jobInService != null) {
            nextEndServiceTime = currentTime + serviceTimeDistribution.sample();
            jobInService.setArrival(currentTime);
            jobInService.setCompletion(nextEndServiceTime);
        } else {
            nextEndServiceTime = Double.MAX_VALUE;
        }
        return completedJob;
    }
}