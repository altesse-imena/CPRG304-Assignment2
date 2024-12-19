package implementations;

import utilities.StackADT;
import utilities.Iterator;

import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

/**
 * A generic stack implementation using an array.
 * This stack supports common operations like push, pop, and peek,
 * and includes utility methods for checking size, converting to arrays, and more.
 *
 * @param <E> The type of elements in the stack.
 */
public class MyStack<E> implements StackADT<E>, Serializable {

    private static final long serialVersionUID = 4468849289651320024L;

    /**
     * The array that stores the stack elements.
     */
    private Object[] stackArray;

    /**
     * The current size of the stack.
     */
    private int size;

    /**
     * The default capacity of the stack.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Creates an empty stack with the default capacity.
     */
    public MyStack() {
        stackArray = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null element to the stack.");
        }
        ensureCapacity();
        stackArray[size++] = toAdd;
    }

    @Override
    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        @SuppressWarnings("unchecked")
        E data = (E) stackArray[--size];
        stackArray[size] = null; // Clear reference to allow garbage collection
        return data;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (E) stackArray[size - 1];
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            stackArray[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null in the stack.");
        }
        for (int i = 0; i < size; i++) {
            if (stackArray[i].equals(toFind)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int search(E toFind) {
        if (toFind == null) {
            return -1;
        }
        for (int i = size - 1; i >= 0; i--) {
            if (stackArray[i].equals(toFind)) {
                return size - i; // 1-based index from the top
            }
        }
        return -1;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(stackArray, 0, result, 0, size);
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E[] toArray(E[] holder) throws NullPointerException {
        if (holder == null) {
            throw new NullPointerException("Provided array is null.");
        }
        if (holder.length < size) {
            holder = (E[]) new Object[size];
        }
        System.arraycopy(stackArray, 0, holder, 0, size);
        if (holder.length > size) {
            holder[size] = null;
        }
        return holder;
    }

    @Override
    public boolean equals(StackADT<E> that) {
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
    public boolean stackOverflow() {
        return size == stackArray.length;
    }

    @Override
    public Iterator<E> iterator() {
        Object[] snapshot = toArray(); // Create a snapshot of the stack's current state
        return new Iterator<E>() {
            private int currentIndex = snapshot.length - 1;

            @Override
            public boolean hasNext() {
                return currentIndex >= 0;
            }

            @Override
            @SuppressWarnings("unchecked")
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) snapshot[currentIndex--];
            }
        };
    }

    /**
     * Ensures that the stack has enough capacity to add a new element.
     * If the stack is full, it doubles the size of the underlying array.
     */
    private void ensureCapacity() {
        if (size == stackArray.length) {
            Object[] newArray = new Object[stackArray.length * 2];
            System.arraycopy(stackArray, 0, newArray, 0, stackArray.length);
            stackArray = newArray;
        }
    }
}
