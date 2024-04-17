public class HighwayTest {

    public static void main(String[] args) {
        testDequeueWhenBothLanesHaveCars();
        testDequeueWhenOneLaneIsEmpty();
        testDequeueWhenBothLanesAreEmpty();
    }

    private static void testDequeueWhenBothLanesHaveCars() {
        Car car1 = new Car("Car1", 10);
        Car car2 = new Car("Car2", 15);

        MultiQueueHighway multiQueueHighway = new MultiQueueHighway(car1);
        multiQueueHighway.enqueue(car2);

        Car dequeuedCar = multiQueueHighway.dequeue();

        System.out.println("Dequeued car: " + dequeuedCar.getName());
    }

    private static void testDequeueWhenOneLaneIsEmpty() {
        Car car1 = new Car("Car1", 10);

        MultiQueueHighway multiQueueHighway = new MultiQueueHighway(car1);

        Car dequeuedCar = multiQueueHighway.dequeue();

        System.out.println("Dequeued car: " + dequeuedCar.getName());
    }

    private static void testDequeueWhenBothLanesAreEmpty() {
        MultiQueueHighway multiQueueHighway = new MultiQueueHighway(null);

        try {
            Car dequeuedCar = multiQueueHighway.dequeue();
            System.out.println("Dequeued car: " + dequeuedCar.getName());
        } catch (IllegalStateException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
}
