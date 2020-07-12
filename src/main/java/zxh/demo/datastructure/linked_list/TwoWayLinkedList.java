package zxh.demo.datastructure.linked_list;

import static java.util.Objects.nonNull;

/**
 * OneWayLinkedList:
 * @author zhangxuhai
 * @date 2020/7/11
*/
public class TwoWayLinkedList<E> extends AbstractLinkedList<E> {
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
    protected void addNode(int index, E element) {
        Node<E> originalNode = getNode(index);
        originalNode.previous.next = new Node<>(originalNode.previous, originalNode, element);
    }

    @Override
    public E get(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        return getNode(index).data;
    }

     @Override
     protected Node<E> getNode(int index) {
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
    protected void removeNode(int index) {
        Node<E> originalNode = getNode(index);
        originalNode.previous.next = originalNode.next;
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
