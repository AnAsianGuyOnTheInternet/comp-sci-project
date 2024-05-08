import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Clock {
    private double currentTime;
    private final List<Queue> queueList;
    private List<Job> activeCars;

    public Clock() {
        currentTime = 0.0;
        queueList = new LinkedList<>();
        this.activeCars = new LinkedList<Job>();
    }

    public Clock(Queue[] q) {
        this();
        addAll(q);
    }

    public void addEntranceEvent(Job car, double arrivalTime, double completionTime) {
        currentTime = arrivalTime;
        car.setArrival(arrivalTime);
        car.setCompletion(completionTime);
        this.activeCars.add(car);
        this.update();
    }
    public void update() {
        for (int i = 0 ; i < activeCars.size(); i++) {
            if (currentTime > activeCars.get(i).getCompletion()) {
                // TODO
                // add methods to return useful data about the cars trip
                // i.e. completion time - arrival time, delays from queue length..

                this.activeCars.remove(i);
            }
        }
    }

    public boolean addAll(Queue[] q) {
        return queueList.addAll(Arrays.asList(q));
    }

    public Queue advanceToNext() {
        int nextTime = Integer.MAX_VALUE;
        Queue nextQueue = null;
        // TODO: Uncomment below when Queue.java is updated with proper parameters
//        for (Queue q : queueList) {
//            if (q.peek().getStartTime() < nextTime) {
//                nextTime = q.peek().getStartTime();
//                nextQueue = q;
//            }
//        }
        currentTime = nextTime;
        return nextQueue;
    }

    public void setTime(double startTime) {
        this.currentTime = startTime;
    }
    public double getTime() {
        return this.currentTime;
    }
}
