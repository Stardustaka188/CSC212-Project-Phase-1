/** The cursor-based linked list used in the course. */
public class LinkedList<T> implements List<T> {
    private Node<T> head;
    private Node<T> current;

    public LinkedList() { head = current = null; }
    public void findFirst() { current = head; }
    public void findNext() { current = current.next; }
    public T retrieve() { return current.data; }
    public void update(T e) { current.data = e; }

    public void insert(T e) {
        Node<T> node = new Node<>(e);
        if (empty()) {
            head = current = node;
        } else {
            node.next = current.next;
            current.next = node;
            current = node;
        }
    }

    public void remove() {
        if (current == head) {
            head = head.next;
        } else {
            Node<T> previous = head;
            while (previous.next != current) previous = previous.next;
            previous.next = current.next;
        }
        if (current.next == null) current = head;
        else current = current.next;
    }

    public boolean full() { return false; }
    public boolean empty() { return head == null; }
    public boolean last() { return current.next == null; }
}