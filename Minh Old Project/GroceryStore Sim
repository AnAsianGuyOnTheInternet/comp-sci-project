
import java.util.Random;
import java.util.UUID;

public class Simulation  {

    double arrivalLambda = 0;
    double serviceLambda = 0;
    Random random = new Random(0);
    ArrivalProcess arrival;
    QueueSystem service;
    GroceryStore groceryStore;
    double checkoutTime = 0;
    Queue completeQueue = new Queue();
    public Simulation(double arrLambda, double servLambda, int aisle, int checkout) {
        arrivalLambda = arrLambda;
        serviceLambda = servLambda;
        this.arrival = new ArrivalProcess(new ExponentialDistribution(arrivalLambda, random));
        this.service = new QueueSystem(new ExponentialDistribution(serviceLambda, random));
        this.groceryStore = new GroceryStore(aisle,checkout, new ExponentialDistribution(1, random));
    }


    int count = 0;
    double servTime = 0;
    double totalTime = 0;


    //holds the jobs identification and when it started and ended its arrivals and/or service times together
    public class SimulatedJob {
        double startTime = 0.0;
        double endTime = 0.0;
        private UUID uuid;

        public SimulatedJob() {
            uuid = UUID.randomUUID();
        }

        public double getStartTime(double start) {
            startTime = start;
            return startTime;
        }

        public double getEndTime(double end) {
            endTime = end;
            return endTime;
        }

        //returns the identification UUID, starting time, and ending time of a job as a string
        public String toString() {
            return "Job ID number is: " + uuid + ", the job started at: " + startTime + " and ended: " + endTime;
        }
    }

    /**REMAKE ARRIVAL FOR GROCERY AISLES TIMES
     *
     */
    //if there is no job in service it sends the job to service. Else, it keeps the job in a queue
    public class ArrivalProcess {
        public ArrivalProcess(ExponentialDistribution dist) {
            distribution = dist;
        }

        private RandomDistribution distribution;
        private double nextArrivalTime = 0.0;
        //creates another job for the simulation loop
        public SimulatedJob nextJob(double currentTime) {
            SimulatedJob customer = new SimulatedJob();
            if (service.jobInService == null) {
                service.jobInService = customer;
                customer.getStartTime(currentTime);
                System.out.println("Added job to service");
            } else {
                service.queue.enqueue(customer);
                nextArrivalTime = currentTime + distribution.sample();
            }
            return customer;
        }
    }

    public class QueueSystem {
        Queue queue = new Queue();

        public QueueSystem(ExponentialDistribution dist) {
            distribution = dist;
        }
        private RandomDistribution distribution;
        private double nextServiceTimeEnd = 0.1;
        public SimulatedJob jobInService = null; //connected to ServiceTimeEnd


        public double getServiceTime() {
            nextServiceTimeEnd += distribution.sample();
            return nextServiceTimeEnd;
        }

        //if there is no job in service, then it adds a job to the service and creates a service time. If there is a job in service, then it adds the job to a queue
        public void add(SimulatedJob customer, double currentTime) {
            if (jobInService == null) {
                jobInService = customer;
                nextServiceTimeEnd = currentTime + distribution.sample();
                currentTime = nextServiceTimeEnd;
                checkoutTime = nextServiceTimeEnd;
                nextServiceTimeEnd = 0.0;
            } else {
                queue.enqueue(customer);

            }
        }

        //when a SimulatedJob is done, it gets added to a queue that stores all complete jobs
        public void complete(SimulatedJob customer, double currentTime) {
            if (jobInService != null) {
                customer.getEndTime(currentTime);
                completeQueue.enqueue(jobInService);
                jobInService = null;
                count++;
                // If there are jobs in the queue, start the next job
                if (!queue.isEmpty()) {
                    jobInService = queue.dequeue();
                    nextServiceTimeEnd = currentTime + distribution.sample();
                }
            }
        }
    }
    //for all the time when the current time used for jobs is less than the simulation time, then the loop continues to service jobs until the time runs out


    public class GroceryStore {


        //holds queues for each aisle and checkout
        public QueueSystem[] aisleArray;
        public QueueSystem[] checkoutArray;
        //initializes a coin that can flip heads or tails
        Random coinToss = new Random();

        //initiates the number of aisles and checkouts and for each, a queue is added per checkout/aisle
        public GroceryStore(int aisleNumber, int checkoutNumber, ExponentialDistribution dist) {

            checkoutArray = new QueueSystem[checkoutNumber];
            aisleArray = new QueueSystem[aisleNumber];

            for (int i = 0; i < checkoutNumber; i++) {
                checkoutArray[i] = new QueueSystem(dist);
            }
            for (int i = 0; i < aisleNumber; i++) {
                aisleArray[i] = new QueueSystem(dist);
            }


        }

        //for every aisle in the store, a coin is flipped and checks if it lands heads or tails
        //if heads then the customer is put in that specific aisle queue and goes goes through and completes grabbbing the item before continuing to the next aisle and repeats the coin fip
        public SimulatedJob shoppingInAisles(SimulatedJob customer) {
            int i = 0;
            double currentTime = 0;
            while (i < aisleArray.length) {
                int entry = coinToss.nextInt(2);
                if (aisleArray[i].queue.isEmpty() && entry == 0) {

                    aisleArray[i].add(customer, currentTime);

                } else if (!aisleArray[i].queue.isEmpty() && entry == 0) {

                    aisleArray[i].queue.dequeue();
                    aisleArray[i].queue.enqueue(customer);
                    aisleArray[i].jobInService = aisleArray[i].queue.dequeue();
                }
                i++;
            }
            addCheckout(customer, currentTime);
            return null;
        }
        //customer goes to find empty checkout to scan and leave
        //if none are empty then randomly puts customer in a checkout

        public SimulatedJob addCheckout(SimulatedJob customer, double currentTime) {
            Random randomCheckout = new Random();
            SimulatedJob frontCustomer;
            int queueLine1 = checkoutArray[0].queue.queueSize();
            int queueLine2 = checkoutArray[1].queue.queueSize();
            int queueLine3 = checkoutArray[2].queue.queueSize();
            int queueLine4 = checkoutArray[3].queue.queueSize();
            SimulatedJob nextInLine;
            for (int i = 0; i < checkoutArray.length; i++) {
                if (checkoutArray[i].queue.isEmpty()) {

                    checkoutArray[i].add(customer, currentTime);
//
                    return customer;
                }
            }
            if(queueLine1 < queueLine2 && queueLine1 < queueLine3 && queueLine1 < queueLine4)
            {

                checkoutArray[0].queue.enqueue(customer);
                checkoutArray[0].complete(checkoutArray[0].queue.dequeue(),currentTime);
                totalTime += currentTime;
            }
            else if(queueLine2 <= queueLine1 && queueLine2 <= queueLine3 && queueLine2 < queueLine4)
            {

                checkoutArray[1].queue.enqueue(customer);
                checkoutArray[1].complete(checkoutArray[1].queue.dequeue(),currentTime);
                totalTime += currentTime;
            }
            else if(queueLine3 < queueLine1 && queueLine3 < queueLine2 && queueLine3 < queueLine4)
            {

                checkoutArray[2].queue.enqueue(customer);
                checkoutArray[2].complete(checkoutArray[2].queue.dequeue(),currentTime);
                totalTime += currentTime;
            }
            else if (queueLine4 < queueLine1 && queueLine4 < queueLine2 && queueLine4 < queueLine3)
            {

                checkoutArray[3].queue.enqueue(customer);
                checkoutArray[3].complete(checkoutArray[3].queue.dequeue(),currentTime);
                totalTime += currentTime;
            }
            else{
                int randomCheckoutQueue = randomCheckout.nextInt(4);

                checkoutArray[randomCheckoutQueue].queue.enqueue(customer);
                checkoutArray[randomCheckoutQueue].complete(checkoutArray[randomCheckoutQueue].queue.dequeue(),currentTime);
                totalTime += currentTime;
            }
            return customer;

        }
    }

    public void run(double simTime) {

        double currentTime = 0.0;

        while (totalTime < simTime) {

                SimulatedJob job = arrival.nextJob(currentTime);
                currentTime = arrival.nextArrivalTime;
                groceryStore.shoppingInAisles(job);
                totalTime += currentTime + servTime;

        }
        System.out.println("Amount of Jobs Completed: " + count + "\n" + "Average Time Complete: " + totalTime/count);
    }






    public void simulationUnitTest()
    {

        Simulation simulation = new Simulation(10,6,6,4);
        Simulation simulation1 = new Simulation(73,55,5,4);
        Simulation simulation2 = new Simulation(1,6,5,4);
        simulation.run(100);

        simulation1.run(40);
        simulation2.run(254);
    }

}

