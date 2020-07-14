package zxh.demo.datastructure.linked_list;

import static java.util.Objects.isNull;

import java.util.Iterator;

/**
 * CircularLinkedList:
 * @author zhangxuhai
 * @date 2020/7/12
*/
public class CircularLinkedList<E> extends AbstractLinkedList<E> implements Iterable<E> {
    public class CLLIterator implements Iterator<E> {
        private Node<E> next = tail;

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public E next() {
            next = next.next;
            return next.data;
        }

        @Override
        public void remove() {
            Node<E> tmp = next;
            CircularLinkedList.this.remove(indexOf(next.data));
            next = tmp.next;
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
    protected void addNode(int index, E element) {
        Node<E> previousNode = getNode(index - 1);
        Node<E> originalNode = getNode(index);
        previousNode.next = new Node<>(originalNode, element);
    }

    @Override
    protected Node<E> getNode(int index) {
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
    protected void removeNode(int index) {
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

    @Override
    public void reverse() {
        Node<E> pre = tail;
        Node<E> curr = head;
        int size = size();
        for (int i = 0; i < size; i++) {
            Node<E> container = curr.next;
            curr.next = pre;
            pre = curr;
            curr = container;
        }

        Node<E> tmpHead = head;
        head = tail;
        tail = tmpHead;
    }

    @Override
    public Iterator<E> iterator() {
        return new CLLIterator();
    }
}
