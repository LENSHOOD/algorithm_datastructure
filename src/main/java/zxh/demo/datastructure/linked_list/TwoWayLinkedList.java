package zxh.demo.datastructure.linked_list;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import lombok.AllArgsConstructor;

/**
 * OneWayLinkedList:
 * @author zhangxuhai
 * @date 2020/7/11
*/
public class TwoWayLinkedList<E> implements LinkedList<E> {
    private Node<E> head = null;
    private Node<E> tail = null;

    @AllArgsConstructor
    private static class Node<E> {
        private Node<E> previous;
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
            tail = head = new Node<>(null, null, element);
            return;
        }

        Node<E> newHead = new Node<>(null, head, element);
        head.previous = newHead;
        head = newHead;
    }

    @Override
    public void addTail(E element) {
        if (isEmpty()) {
            tail = head = new Node<>(null, null, element);
            return;
        }

        Node<E> newTail = new Node<>(tail, null, element);
        tail.next = newTail;
        tail = newTail;
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

        Node<E> originalNode = getNode(index);
        originalNode.previous.next = new Node<>(originalNode.previous, originalNode, element);
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

        head = head.next;
        head.previous = null;
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

        tail = tail.previous;
        tail.next = null;
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

        Node<E> originalNode = getNode(index);
        originalNode.previous.next = originalNode.next;
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
        } while (nonNull(currentNode));

        return size;
    }
}
