// package demo;

// import dijkstrasAlgorithmRef.algorithm.Map;
// import michaela3files.*;

// public class testFirst {
//     private static Map testMap1 = new Map();
//     private ArrivalProcess arrival = new ArrivalProcess(0.5);
//     private double currentTime;
//     private Queue completedJobs = new Queue();
//     private double EastFredRate;
//     private double FredRockRate;
//     private double RockFredRate;  
//     SingleServerQueue queueEastToFred = new SingleServerQueue(EastFredRate);
//     SingleServerQueue queueFredToRock = new SingleServerQueue(FredRockRate);
//     SingleServerQueue queueRockToFred = new SingleServerQueue(RockFredRate);
    
//     SingleServerQueue[] queues = {queueEastToFred, queueFredToRock, queueRockToFred};

//     public testFirst(double EastFredRate, double FredRockRate, double RockFredRate){

//     }

//     public void run(int sim_time) { 
//         currentTime = 0;       
//         testMap1.addNode("toFrederick");
//         testMap1.addNode("toRockville");
//         testMap1.addNode("fromFrederick");
//         testMap1.addNode("fromEast");
        
//         while (currentTime < sim_time) {
//             // Get the next arrival time and next end service time
// 			double nextArrivalTime = arrival.nextJob().getFirstArrival();
//             double nextEndServiceTime = getNextEndServiceTime();

//             // Check if the next event is an arrival or completion
//             if (nextArrivalTime < nextEndServiceTime) {
//                 currentTime = nextArrivalTime; 
//                 Car arrivingJob = new Car(); 
//                 handleArrival(arrivingJob);
//             } else {
//                 currentTime = nextEndServiceTime;
//                 handleServiceCompletion();
//             }
//         }

//     }
//      // Get next end service time
//     private double getNextEndServiceTime() {
//         double nextEventEnd = Double.MAX_VALUE;
//         //iterate through queues to find out which finishes next
//         for (SingleServerQueue queue : queues) {
//             double endTime = queue.getEndServiceTime();
//             if (endTime < nextEventEnd) {
//                 nextEventEnd = endTime;
//             }
//         }
//         return nextEventEnd;
//     }
//     // Handle arrival of a new car
//     private void handleArrival(Car arrivingCar) {
//         switch (arrivingCar.getOrigin()) {
//             case "Frederick":
                
//             case "East of Frederick":
//         }
//     }

//     // Handle completion of service
//     private void handleServiceCompletion() {
//         for (int i = 0; i < queues.length; i++) {
//             if (queues[i].getEndServiceTime() == currentTime) {
//                 Job completedJob = queues[i].complete(currentTime);
//                 //Move to another station, else complete if at checkout
//                 if (i < queues.length - 1) {
//                     routeToNextQueue(completedJob, i);
//                 } 
//                 else {
//                     completedJobs.enqueue(completedJob);
//                 }
//             }
//         }
//     }
//     private Car spawnCar() {
//         int roll20 = (int)Math.random() * 20;
//         String source;
//         String destination;
//         if (roll20 < 8) {
//             source = "East of Frederick";
//         }
//         else {
//             source = "Frederick";
//         }
//         if 
//         Car newCar = new Car(source, destination, currentTime);
//     }
// }
