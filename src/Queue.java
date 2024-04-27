public class Queue {
    private QueueNode first;
    private QueueNode last;

    public Queue() {
    }

    // TODO: Change Object references to class representing vehicles
    public void enqueue(Object t) {
        final QueueNode newNode = new QueueNode(t, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
    }

    public Object dequeue() {
        final QueueNode temp = first;
        if (temp == null) {
            return null;
        }
        first = first.next;
        temp.next = null;
        return temp.item;
    }

    public Object peek() {
        if (first == null) {
            return null;
        }
        return first;
    }

//    public boolean hasEntries() {
//        return (head != null);
//    }

    public void printQueue() {
        if (first == null) {
            System.out.println("Queue is empty.");
            return;
        }
        System.out.println("Queue Start");
        System.out.println("--------------------------------------------------");
        first.printNext();
        System.out.println("Queue End");
    }

    private static class QueueNode {
        private Object item;
        private QueueNode next;

        public QueueNode(Object type, QueueNode next) {
            this.item = type;
            this.next = next;
        }

        public void printNext() {
            System.out.println("==> "+ item + "\n--------------------------------------------------");
            if (next != null) {
                next.printNext();
            }
        }
    }








    // Unit Testing (old)
//    public static void unitTest() {
//        Queue testQueue = new Queue();
//        System.out.println("Adding 1 Record.");
//        testQueue.enqueue(new Job());
//        testQueue.printQueue();
//
//        System.out.println("\n");
//
//        System.out.println("Adding 4 Records.");
//        testQueue.enqueue(new Job());
//        testQueue.enqueue(new Job());
//        testQueue.enqueue(new Job());
//        testQueue.enqueue(new Job());
//        testQueue.printQueue();
//
//        System.out.println("\n");
//
//        System.out.println("Removing 2 Records.");
//        System.out.println("Dequeued: " + testQueue.dequeue());
//        System.out.println("Dequeued: " + testQueue.dequeue());
//        testQueue.printQueue();
//
//        System.out.println("\n");
//
//        System.out.println("Removing 3 Records.");
//        System.out.println("Dequeued: " + testQueue.dequeue());
//        System.out.println("Dequeued: " + testQueue.dequeue());
//        System.out.println("Dequeued: " + testQueue.dequeue());
//        testQueue.printQueue();
//
//        System.out.println("\n");
//
//        System.out.println("Adding 2 Records");
//        testQueue.enqueue(new Job());
//        testQueue.enqueue(new Job());
//        testQueue.printQueue();
//    }
}
