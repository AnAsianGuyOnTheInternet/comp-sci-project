public class Driver {
  public static void main(String[] args) {
    RoadGridModel roadGrid = new RoadGridModel(0.1, "src/locationdata.txt"); 

    /*
     * First simulation, 100 cars
     */

     roadGrid.simulationStep(100, 0);
  }
}
