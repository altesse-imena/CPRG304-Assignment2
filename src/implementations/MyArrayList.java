package implementations;

import utilities.ListADT;
import utilities.Iterator;
import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * A simple implementation of a list using an array. 
 * It supports basic list operations like adding, removing, and accessing elements.
 * The array grows automatically when needed.
 *
 * @param <E> The type of elements in the list.
 */
public class MyArrayList<E> implements ListADT<E>, Serializable {
    private static final long serialVersionUID = -7717442477139935929L;

    /**
     * The array that stores elements in the list.
     */
    private E[] elements;

    /**
     * The current number of elements in the list.
     */
    private int size;

    /**
     * The default initial capacity of the array.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Creates an empty list with an initial capacity of 10.
     */
    @SuppressWarnings("unchecked")
    public MyArrayList() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Adds an item to a specific position in the list.
     * Moves other items to make room.
     *
     * @param index   The position to add the item.
     * @param element The item to add.
     * @return True if the item was added successfully.
     * @throws NullPointerException      If the element is null.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    @Override
    public boolean add(int index, E element) {
        if (element == null) throw new NullPointerException("Cannot add null item.");
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Invalid position.");
        if (size == elements.length) resizeArray();
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = element;
        size++;
        return true;
    }

    /**
     * Adds an item to the end of the list.
     *
     * @param element The item to add.
     * @return True if the item was added successfully.
     * @throws NullPointerException If the element is null.
     */
    @Override
    public boolean add(E element) {
        if (element == null) throw new NullPointerException("Cannot add null item.");
        if (size == elements.length) resizeArray();
        elements[size] = element;
        size++;
        return true;
    }

    /**
     * Removes an item from a specific position.
     * Shifts other items to fill the gap.
     *
     * @param index The position of the item to remove.
     * @return The removed item.
     * @throws IndexOutOfBoundsException If the index is invalid.
     */
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Invalid position.");
        E removed = elements[index];
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[size - 1] = null;
        size--;
        return removed;
    }

    /**
     * Removes the first occurrence of a specific item.
     *
     * @param element The item to remove.
     * @return The removed item, or null if not found.
     * @throws NullPointerException If the element is null.
     */
    @Override
    public E remove(E element) {
        if (element == null) throw new NullPointerException("Cannot remove null item.");
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                return remove(i);
            }
        }
        return null;
    }

    /**
     * Gets the item at a specific position.
     *
     * @param index The position of the item.
     * @return The item at the specified position.
     * @throws IndexOutOfBoundsException If the index is invalid.
     */
    @Override
    public E get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Invalid position.");
        return elements[index];
    }

    /**
     * Updates the item at a specific position with a new value.
     *
     * @param index   The position to update.
     * @param element The new item.
     * @return The old item that was replaced.
     * @throws NullPointerException      If the new element is null.
     * @throws IndexOutOfBoundsException If the index is invalid.
     */
    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Invalid position.");
        if (element == null) throw new NullPointerException("Cannot set null item.");
        E old = elements[index];
        elements[index] = element;
        return old;
    }

    /**
     * Checks if the list contains a specific item.
     *
     * @param element The item to check for.
     * @return True if the item is in the list, false otherwise.
     * @throws NullPointerException If the element is null.
     */
    @Override
    public boolean contains(E element) {
        if (element == null) throw new NullPointerException("Cannot check for null item.");
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) return true;
        }
        return false;
    }

    /**
     * Clears all items from the list.
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * Gets the number of items in the list.
     *
     * @return The number of items in the list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if the list is empty.
     *
     * @return True if the list is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Converts the list to an array.
     *
     * @param holder The array to store the items.
     * @return The array containing the list items.
     * @throws NullPointerException If the provided array is null.
     */
    @Override
    public E[] toArray(E[] holder) {
        if (holder == null) throw new NullPointerException("Provided array cannot be null.");
        if (holder.length < size) {
            @SuppressWarnings("unchecked")
            E[] newArray = (E[]) new Object[size];
            System.arraycopy(elements, 0, newArray, 0, size);
            return newArray;
        }
        System.arraycopy(elements, 0, holder, 0, size);
        if (holder.length > size) holder[size] = null;
        return holder;
    }

    /**
     * Converts the list to an array.
     *
     * @return An array containing the list items.
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        System.arraycopy(elements, 0, array, 0, size);
        return array;
    }

    /**
     * Returns an iterator for the list.
     *
     * @return An iterator to traverse the list.
     */
    @Override
    public Iterator<E> iterator() {
        Object[] snapshot = toArray();
        return new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < snapshot.length;
            }

            @SuppressWarnings("unchecked")
            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                return (E) snapshot[currentIndex++];
            }
        };
    }

    /**
     * Adds all items from another list to this list.
     *
     * @param toAdd The list of items to add.
     * @return True if the items were added successfully.
     * @throws NullPointerException If the provided list is null.
     */
    @Override
    public boolean addAll(ListADT<? extends E> toAdd) {
        if (toAdd == null) throw new NullPointerException("Cannot add from a null list.");
        for (Object obj : toAdd.toArray()) {
            add((E) obj);
        }
        return true;
    }

    /**
     * Resizes the array when it becomes full.
     */
    private void resizeArray() {
        @SuppressWarnings("unchecked")
        E[] newArray = (E[]) new Object[elements.length * 2];
        System.arraycopy(elements, 0, newArray, 0, size);
        elements = newArray;
    }
}
