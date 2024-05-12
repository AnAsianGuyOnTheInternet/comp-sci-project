import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Clock {
    private double currentTime;
    private final List<Queue> queueList;
    private List<Job> activeCars;
    private int completedCars;
    private StringBuffer infoBuffer;

    public Clock() {
        currentTime = 0.0;
        queueList = new LinkedList<>();
        this.activeCars = new LinkedList<Job>();
        this.infoBuffer = new StringBuffer();
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
        Integer nextInLineIndex = null;
        double tCompletion;
        double lastSmallestCompletion = Integer.MAX_VALUE;
        for (int i = 0 ; i < activeCars.size(); i++) {
            tCompletion = activeCars.get(i).getCompletion();
            if (tCompletion < lastSmallestCompletion)
                lastSmallestCompletion = tCompletion;
            if (currentTime > tCompletion && tCompletion <= lastSmallestCompletion) {
                nextInLineIndex = i;
            }
        }
        if (nextInLineIndex == null)
            currentTime = lastSmallestCompletion;
        else {
            int carToRemoveIndex = nextInLineIndex;
            ++this.completedCars;
            this.infoBuffer.append("Car " 
                + this.activeCars.get(carToRemoveIndex).getID() + ": "
                + (this.activeCars.get(carToRemoveIndex).getArrival() - this.activeCars.get(carToRemoveIndex).getCompletion()) 
                + "\n");
            this.infoBuffer.append("Total time per car so far: " + (currentTime / this.completedCars) + "\n");

            this.activeCars.remove(carToRemoveIndex);
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

    public boolean hasActive() {
        if (activeCars.size() > 0)
            return true;
        return false;
    }

    public StringBuffer reportResults() {
        return this.infoBuffer;
    }
}
