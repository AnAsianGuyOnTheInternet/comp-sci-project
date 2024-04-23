import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class MultiQueueHighway {
    private Queue<Car> lane1;
    private Queue<Car> lane2;
    private Car car;
    //initialize
    public MultiQueueHighway(Car carA) {
        lane1 = new LinkedList<>();
        lane2 = new LinkedList<>();
        car = carA;

    }
    //adds
    public void enqueue(Car car) {
        int laneNumber;
        Random rand = new Random();
        laneNumber = rand.nextInt(2);
        if (laneNumber == 0) {
            lane1.add(car);
        } else if (laneNumber == 1) {
            lane2.add(car);
        }
    }


    public Car dequeue() {
        if (!lane1.isEmpty() && !lane2.isEmpty()) {
            Car car1 = lane1.peek();
            Car car2 = lane2.peek();

            if (car1.getNavigationTime() < car2.getNavigationTime()) {
                return lane1.poll();
            } else {
                return lane2.poll();
            }
        } else if (!lane1.isEmpty() && lane2.isEmpty()) {
            return lane1.poll();
        } else if (!lane2.isEmpty() && lane1.isEmpty()) {
            return lane2.poll();
        } else {
            throw new IllegalStateException("No cars in any lane");
        }
    }

}





