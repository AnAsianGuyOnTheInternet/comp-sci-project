import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Clock {
    private double currentTime;
    private final List<Queue> queueList;

    public Clock() {
        currentTime = 0.0;
        queueList = new LinkedList<>();
    }

    public Clock(Queue[] q) {
        this();
        addAll(q);
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
}
