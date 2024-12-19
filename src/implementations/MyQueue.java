package implementations;

import exceptions.EmptyQueueException;
import utilities.QueueADT;
import utilities.Iterator;

import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * A generic implementation of a Queue using a singly linked list.
 * The queue supports standard operations like enqueue, dequeue, and peek, 
 * and includes utility methods for checking size and converting to arrays.
 *
 * @param <E> The type of elements in the queue.
 */
public class MyQueue<E> implements QueueADT<E>, Serializable {

    private static final long serialVersionUID = 2033529128621524653L;

    /**
     * The front (head) of the queue.
     */
    private Node<E> head;

    /**
     * The rear (tail) of the queue.
     */
    private Node<E> tail;

    /**
     * The number of elements in the queue.
     */
    private int size;

    /**
     * Constructs an empty queue.
     */
    public MyQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void enqueue(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null element to the queue.");
        }
        Node<E> newNode = new Node<>(toAdd);
        if (tail == null) { // If the queue is empty
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public E dequeue() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Cannot dequeue from an empty queue.");
        }
        E data = head.data;
        head = head.next;
        if (head == null) { // If the queue is now empty
            tail = null;
        }
        size--;
        return data;
    }

    @Override
    public E peek() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Cannot peek into an empty queue.");
        }
        return head.data;
    }

    @Override
    public void dequeueAll() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null in the queue.");
        }
        Node<E> current = head;
        while (current != null) {
            if (current.data.equals(toFind)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int search(E toFind) {
        if (toFind == null) {
            return -1;
        }
        Node<E> current = head;
        int position = 1;
        while (current != null) {
            if (current.data.equals(toFind)) {
                return position;
            }
            current = current.next;
            position++;
        }
        return -1;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node<E> current = head;
        int index = 0;
        while (current != null) {
            array[index++] = current.data;
            current = current.next;
        }
        return array;
    }

    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        if (holder == null) {
            throw new NullPointerException("Provided array is null.");
        }
        if (holder.length < size) {
            @SuppressWarnings("unchecked")
            E[] newArray = (E[]) new Object[size];
            Node<E> current = head;
            int index = 0;
            while (current != null) {
                newArray[index++] = current.data;
                current = current.next;
            }
            return newArray;
        }
        Node<E> current = head;
        int index = 0;
        while (current != null) {
            holder[index++] = current.data;
            current = current.next;
        }
        if (holder.length > size) {
            holder[size] = null;
        }
        return holder;
    }

    @Override
    public boolean equals(QueueADT<E> that) {
        if (that == null || that.size() != this.size) {
            return false;
        }
        Iterator<E> thisIt = this.iterator();
        Iterator<E> thatIt = that.iterator();
        while (thisIt.hasNext() && thatIt.hasNext()) {
            if (!thisIt.next().equals(thatIt.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    @Override
    public boolean isFull() {
        return false; // This queue is dynamically sized, so it is never full.
    }

    /**
     * A node in the queue.
     *
     * @param <E> The type of data stored in the node.
     */
    private static class Node<E> implements Serializable {
        private static final long serialVersionUID = 6529685098267757693L;

        /**
         * The data stored in the node.
         */
        E data;

        /**
         * The reference to the next node in the queue.
         */
        Node<E> next;

        /**
         * Creates a new node with the given data.
         *
         * @param data The data to store in the node.
         */
        Node(E data) {
            this.data = data;
            this.next = null;
        }
    }
}
