/*
Michael Wenzel	
CMIS202
03/11/24
SimulationTest.java
Simulates the arrival and completion of "jobs" entering a buffet-style restaurant, collecting as much food as they'd like, and checking out.
*/
public class SimulationTest {
    private SingleServerQueue[] queues;
    private String[] queueNames = {
        "Entrance", "Appetizer", "Proteins", "Sides", "Salad Bar", "Dessert", "Checkout"
    };
	private double currentTime;
	private ArrivalProcess arrival;
	private Queue completedJobs;
	private double arrivalRate;
	private double serviceRate;

    //Construct
	public SimulationTest(double arrivalRate, double serviceRate) {
		this.arrivalRate = arrivalRate;
		this.serviceRate = serviceRate;
		this.currentTime = 0;
		this.arrival = new ArrivalProcess(arrivalRate);
		this.completedJobs = new Queue();

        // Initialize queues
        this.queues = new SingleServerQueue[queueNames.length];
        for (int i = 0; i < queues.length; i++) {
            queues[i] = new SingleServerQueue(serviceRate);
        }

	}
	//run simulation for sim_time time units
    public void run(double sim_time) {
        //First arrival
        Job firstJob = new Job(currentTime);
        handleArrival(firstJob);

        while (currentTime < sim_time) {
            // Get the next arrival time and next end service time
			double nextArrivalTime = arrival.nextJob().getFirstArrival();
            double nextEndServiceTime = getNextEndServiceTime();

            // Check if the next event is an arrival or completion
            if (nextArrivalTime < nextEndServiceTime) {
                currentTime = nextArrivalTime; 
                Job arrivingJob = new Job(currentTime); 
                handleArrival(arrivingJob);
            } else {
                currentTime = nextEndServiceTime;
                handleServiceCompletion();
            }
        }

        //print results of simulation
        printSimulationData(completedJobs, sim_time);
    }

    
    // Get next end service time
    private double getNextEndServiceTime() {
        double nextEventEnd = Double.MAX_VALUE;
        //iterate through queues to find out which finishes next
        for (SingleServerQueue queue : queues) {
            double endTime = queue.getEndServiceTime();
            if (endTime < nextEventEnd) {
                nextEventEnd = endTime;
            }
        }
        return nextEventEnd;
    }

    // Handle arrival of a new job
    private void handleArrival(Job arrivingJob) {
        queues[0].add(arrivingJob, currentTime);
    }

    // Handle completion of service
    private void handleServiceCompletion() {
        for (int i = 0; i < queues.length; i++) {
            if (queues[i].getEndServiceTime() == currentTime) {
                Job completedJob = queues[i].complete(currentTime);
                //Move to another station, else complete if at checkout
                if (i < queues.length - 1) {
                    routeToNextQueue(completedJob, i);
                } 
                else {
                    completedJobs.enqueue(completedJob);
                }
            }
        }
    }

    //
    private void routeToNextQueue(Job completedJob, int currentIndex) {
        int nextQueueIndex;
        //ensure customer doesn't go straight to checkout
        if (currentIndex == 0) {
            nextQueueIndex = (int)Math.round(1 + Math.random() * 4);
        }
        else { 
            nextQueueIndex = (int)Math.round(1 + Math.random() * 5);
        }
        if (nextQueueIndex != 6) {
            // Increment food count
            completedJob.addFood();
        }
        queues[nextQueueIndex].add(completedJob, currentTime); 
    }


	//Calculate quantities of interest from completed job data
    private void printSimulationData(Queue completedQueue, double sim_time) {
        int jobsCompleted = 0;
        double runningTotalTime = 0;
        double maxPassageTime = 0;

        // Iterate through completedJob queue to count completed jobs and total time spent on each
        Job activeJob = completedJobs.dequeue();
        while (activeJob != null) {
            double jobTime = activeJob.getCompletion() - activeJob.getFirstArrival();
            runningTotalTime += jobTime;
            //keep track of completed job with max passage time
            if (jobTime > maxPassageTime) {
                maxPassageTime = jobTime;
            }
            jobsCompleted++;
            activeJob = completedJobs.dequeue();
        }

        // Average time spent per job
        double passageTime = jobsCompleted == 0 ? 0 : runningTotalTime / jobsCompleted;

        // Jobs completed per unit time
        double throughput = sim_time == 0 ? 0 : jobsCompleted / sim_time;

        // Format the data
        // Print simulation data
        String dataFormat = "| %15.2f | %15.2f | %15.2f | %15d | %15.2f | %15.2f | %15.3f |%n";
        System.out.printf(dataFormat, arrivalRate, serviceRate, sim_time, jobsCompleted, maxPassageTime, passageTime, throughput);
    }
}