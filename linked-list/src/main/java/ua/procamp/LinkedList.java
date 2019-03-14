package ua.procamp;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * {@link LinkedList} is a list implementation that is based on singly linked generic nodes. A node is implemented as
 * inner static class {@link Node<T>}. In order to keep track on nodes, {@link LinkedList} keeps a reference to a head node.
 *
 * @param <T> generic type parameter
 */
public class LinkedList<T> implements List<T> {

    private Node<T> root;
    private int listSize;

    /**
     * This method creates a list of provided elements
     *
     * @param elements elements to add
     * @param <T>      generic type
     * @return a new list of elements the were passed as method parameters
     */
    public static <T> List<T> of(T... elements) {
        LinkedList<T> list = new LinkedList<>();
        Arrays.stream(elements).forEach(list::add);
        return list;
    }

    /**
     * Adds an element to the end of the list
     *
     * @param element element to add
     */
    @Override
    public void add(T element) {
        add(listSize, element);
    }

    /**
     * Adds a new element to the specific position in the list. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index   an index of new element
     * @param element element to add
     */
    @Override
    public void add(int index, T element) {
        if (index > listSize || index < 0) throw new IndexOutOfBoundsException();
        Node<T> newNode = new Node<>(element);
        if (index == 0) {
            newNode.next = root == null ? newNode : root;
            root = newNode;
        } else {
            Node<T> node = getNodeAtIndex(index - 1);
            newNode.next = node.next;
            node.next = newNode;
        }
        listSize++;
    }

    /**
     * Changes the value of an list element at specific position. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index   an position of element to change
     * @param element a new element value
     */
    @Override
    public void set(int index, T element) {
        if (index >= listSize || index < 0) throw new IndexOutOfBoundsException();
        getNodeAtIndex(index).data = element;
    }

    /**
     * Retrieves an elements by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index element index
     * @return an element value
     */
    @Override
    public T get(int index) {
        if (listSize == 0) throw new IndexOutOfBoundsException();
        if (index >= listSize || index < 0) throw new IndexOutOfBoundsException();
        return getNodeAtIndex(index).data;
    }

    /**
     * Removes an elements by its position index. In case provided index in out of the list bounds it
     * throws {@link IndexOutOfBoundsException}
     *
     * @param index element index
     */
    @Override
    public void remove(int index) {
        if (index == 0) {
            root = root.next;
        } else {
            if (index > listSize || index < 0) throw new IndexOutOfBoundsException();
            Node node = getNodeAtIndex(index - 1);
            node.next = node.next.next;
        }
        listSize--;
    }


    /**
     * Checks if a specific exists in he list
     *
     * @return {@code true} if element exist, {@code false} otherwise
     */
    @Override
    public boolean contains(T element) {
        return root != null && Stream.iterate(root, node -> node.next)
                .takeWhile(node -> node.next != root)
                .anyMatch(node -> node.data == element);
    }

    /**
     * Checks if a list is empty
     *
     * @return {@code true} if list is empty, {@code false} otherwise
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns the number of elements in the list
     *
     * @return number of elements
     */
    @Override
    public int size() {
        return listSize;
    }

    /**
     * Removes all list elements
     */
    @Override
    public void clear() {
        root = null;
        listSize = 0;
    }

    private static class Node <T> {
        T data;
        Node <T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> getNodeAtIndex(int index) {
        return Stream.iterate(root, node -> node.next).skip(index).findFirst().orElse(null);
    }
}
