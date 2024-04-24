public class LinkedObjectList<T>{
    private int size = 0;
    private LinkedNode<T> firstNode;
    private LinkedNode<T> lastNode;

    public LinkedObjectList() {

    }

    private void linkFirst(T t) {
        final LinkedNode<T> f = firstNode;
        final LinkedNode<T> newNode = new LinkedNode<>(null, t, f);

    }

    private static class LinkedNode<T> {
        T item;
        LinkedNode<T> next;
        LinkedNode<T> prev;

        LinkedNode(LinkedNode<T> prev, T type, LinkedNode<T> next) {
            this.item = type;
            this.next = next;
            this.prev = prev;
        }
    }
}
