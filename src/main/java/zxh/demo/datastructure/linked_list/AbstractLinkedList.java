package zxh.demo.datastructure.linked_list;

import static java.util.Objects.isNull;

import lombok.AllArgsConstructor;

/**
 * AbstractLinkedList:
 * @author zhangxuhai
 * @date 2020/7/12
*/
public abstract class AbstractLinkedList<E> implements LinkedList<E> {
    @AllArgsConstructor
    protected static class Node<E> {
        Node<E> previous;
        Node<E> next;
        E data;

        Node(Node<E> next, E data) {
            this.next = next;
            this.data = data;
        }

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

    protected Node<E> head = null;
    protected Node<E> tail = null;

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

        addNode(index, element);
    }

    protected abstract void addNode(int index, E element);

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

        removeNode(index);
    }

    protected abstract void removeNode(int index);

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

    protected abstract Node<E> getNode(int index);

    @Override
    public boolean isEmpty() {
        return isNull(head) && isNull(tail);
    }
}
