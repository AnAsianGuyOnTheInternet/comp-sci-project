/*
Michael Wenzel	
CMIS202
03/11/24
AssignmentTwo.java
Main method for running simulation and printing results in table format.
*/
public class AssignmentThree {
    public static void main(String[] args) {
        double increment = 0.25;
        int runCount = 5;
	 	double arrivalRate = increment;
	 	double serviceRate = increment;
        double simTime = 1000;

        int colCount = 7;
        String divider = ("+" + "-".repeat(17)).repeat(colCount) + "+%n";
        String headingFormat = "| %15s ".repeat(colCount) + "|%n";
        System.out.printf(divider);
        System.out.printf(headingFormat, "arrivalRate", "serviceRate", "sim_time", "jobsCompleted", "maxPassageTime", "avgPassageTime", "throughput");
        System.out.printf(divider);
        //simulate increasing arrival rate
		for (int i = 0; i < runCount; i++) {
			SimulationTest simmie = new SimulationTest(arrivalRate, serviceRate);
			simmie.run(simTime);
			arrivalRate += increment;
		}
		arrivalRate = increment;
        System.out.printf(divider);
		//simulate increase service rate
		for (int i = 0; i < runCount; i++) {
			SimulationTest simmie = new SimulationTest(arrivalRate, serviceRate);
			simmie.run(simTime);
			serviceRate += increment;
		}
        System.out.printf(divider);
		//simulate increasing arrival rate with increased service rate
		for (int i = 0; i < runCount; i++) {
			SimulationTest simmie = new SimulationTest(arrivalRate, serviceRate);
			simmie.run(simTime);
			arrivalRate += increment;
        }
        System.out.printf(divider);
    }
}
