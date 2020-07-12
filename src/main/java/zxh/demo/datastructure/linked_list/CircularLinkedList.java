package zxh.demo.datastructure.linked_list;

import static java.util.Objects.isNull;

import lombok.AllArgsConstructor;

/**
 * CircularLinkedList:
 * @author zhangxuhai
 * @date 2020/7/12
*/
public class CircularLinkedList<E> implements LinkedList<E> {
    private Node<E> head = null;
    private Node<E> tail = null;

    @AllArgsConstructor
    private static class Node<E> {
        private Node<E> next;
        private E data;

        boolean dataEquals(E element) {
            if (data == element) {
                return true;
            }

            if (isNull(data)) {
                return false;
            }

            return data.equals(element);
        }
    }

    @Override
    public void addHead(E element) {
        if (isEmpty()) {
            tail = head = new Node<>(null, element);
            head.next = tail;
            return;
        }

        tail.next = new Node<>(head, element);
        head = tail.next;
    }

    @Override
    public void addTail(E element) {
        if (isEmpty()) {
            tail = head = new Node<>(null, element);
            head.next = tail;
            return;
        }

        tail.next = new Node<>(head, element);
        tail = tail.next;
    }

    @Override
    public void add(int index, E element) {
        if (index > size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (index == size()) {
            addTail(element);
            return;
        }

        if (index == 0) {
            addHead(element);
            return;
        }

        Node<E> previousNode = getNode(index - 1);
        Node<E> originalNode = getNode(index);
        previousNode.next = new Node<>(originalNode, element);
    }

    @Override
    public int indexOf(E element) {
        int index = -1;
        if (isEmpty()) {
            return index;
        }

        Node<E> currentNode = head;
        for(;;) {
            index++;
            if (index == size() || currentNode.dataEquals(element)) {
                break;
            }
            currentNode = currentNode.next;
        }

        if (index == size()) {
            return -1;
        }

        return index;
    }

    @Override
    public E getHead() {
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }

        return head.data;
    }

    @Override
    public E getTail() {
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }

        return tail.data;
    }

    @Override
    public E get(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        return getNode(index).data;
    }

    private Node<E> getNode(int index) {
        if (index < 0) {
            index = index + size();
        }

        Node<E> currentNode = head;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode;
    }

    @Override
    public void removeHead() {
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }

        if (head == tail) {
            head = null;
            tail = null;
            return;
        }

        tail.next = head.next;
        head = tail.next;
    }

    @Override
    public void removeTail() {
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }

        if (head == tail) {
            head = null;
            tail = null;
            return;
        }

        Node<E> nodeBeforeTail = getNode(size() - 2);
        nodeBeforeTail.next = head;
        tail = nodeBeforeTail;
    }

    @Override
    public void remove(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (index == size() - 1) {
            removeTail();
            return;
        }

        if (index == 0) {
            removeHead();
            return;
        }

        Node<E> previousNode = getNode(index - 1);
        Node<E> originalNode = getNode(index);
        previousNode.next = originalNode.next;
    }

    @Override
    public boolean isEmpty() {
        return isNull(head) && isNull(tail);
    }

    @Override
    public int size() {
        int size = 0;
        if (isEmpty()) {
            return size;
        }

        Node<E> currentNode = head;
        do {
            size++;
            currentNode = currentNode.next;
        } while (!currentNode.equals(head));

        return size;
    }
}
