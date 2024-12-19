package implementations;

/**
 * Represents a node in a doubly linked list.
 * Each node contains data and references to the next and previous nodes in the list.
 *
 * @param <E> The type of data stored in the node.
 */
public class MyDLLNode<E> {
    /**
     * The data stored in this node.
     */
    E data;

    /**
     * Reference to the next node in the list.
     */
    MyDLLNode<E> next;

    /**
     * Reference to the previous node in the list.
     */
    MyDLLNode<E> prev;

    /**
     * Creates a new node with the specified data.
     * Both next and prev references are initialized to null.
     *
     * @param data The data to store in this node.
     */
    public MyDLLNode(E data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
