package implementations;

import java.io.Serializable;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * A doubly linked list implementation.
 * This class provides basic list functionalities such as adding, removing, and searching elements.
 *
 * @param <E> The type of elements stored in the list.
 */
public class MyDLL<E> implements ListADT<E>, Serializable {
    private static final long serialVersionUID = 2410843404827905565L;
	private MyDLLNode<E> head;
    private MyDLLNode<E> tail;
    private int size;

    /**
     * Constructs an empty doubly linked list.
     */
    public MyDLL() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean add(int index, E element) {
        if (element == null) throw new NullPointerException("Cannot add null element.");
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index out of bounds.");
        MyDLLNode<E> newNode = new MyDLLNode<>(element);

        if (index == 0) { // Add at the beginning
            if (head == null) {
                head = tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
        } else if (index == size) { // Add at the end
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else { // Add in the middle
            MyDLLNode<E> current = getNodeAt(index);
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
        }

        size++;
        return true;
    }

    @Override
    public boolean add(E element) {
        return add(size, element);
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds.");
        MyDLLNode<E> toRemove;

        if (index == 0) { // Remove the first element
            toRemove = head;
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (index == size - 1) { // Remove the last element
            toRemove = tail;
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
            }
        } else { // Remove from the middle
            toRemove = getNodeAt(index);
            toRemove.prev.next = toRemove.next;
            toRemove.next.prev = toRemove.prev;
        }

        size--;
        return toRemove.data;
    }

    @Override
    public E remove(E element) {
        if (element == null) throw new NullPointerException("Cannot remove null element.");
        MyDLLNode<E> current = head;
        while (current != null) {
            if (current.data.equals(element)) {
                if (current == head) return remove(0);
                if (current == tail) return remove(size - 1);
                current.prev.next = current.next;
                current.next.prev = current.prev;
                size--;
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds.");
        return getNodeAt(index).data;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index out of bounds.");
        if (element == null) throw new NullPointerException("Cannot set null element.");
        MyDLLNode<E> current = getNodeAt(index);
        E oldValue = current.data;
        current.data = element;
        return oldValue;
    }

    @Override
    public boolean contains(E element) {
        if (element == null) throw new NullPointerException("Cannot search for null element.");
        MyDLLNode<E> current = head;
        while (current != null) {
            if (current.data.equals(element)) return true;
            current = current.next;
        }
        return false;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E[] toArray(E[] holder) {
        if (holder == null) throw new NullPointerException("Holder array cannot be null.");
        if (holder.length < size) {
            @SuppressWarnings("unchecked")
            E[] newArray = (E[]) new Object[size];
            holder = newArray;
        }
        MyDLLNode<E> current = head;
        int i = 0;
        while (current != null) {
            holder[i++] = current.data;
            current = current.next;
        }
        if (holder.length > size) holder[size] = null;
        return holder;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        MyDLLNode<E> current = head;
        int i = 0;
        while (current != null) {
            array[i++] = current.data;
            current = current.next;
        }
        return array;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private MyDLLNode<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                E data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    @Override
    public boolean addAll(ListADT<? extends E> toAdd) {
        if (toAdd == null) throw new NullPointerException("Cannot add elements from a null list.");
        for (Object obj : toAdd.toArray()) {
            add((E) obj);
        }
        return true;
    }

    private MyDLLNode<E> getNodeAt(int index) {
        MyDLLNode<E> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) current = current.next;
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) current = current.prev;
        }
        return current;
    }

    private static class MyDLLNode<E> {
        E data;
        MyDLLNode<E> next;
        MyDLLNode<E> prev;

        MyDLLNode(E data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }
}
