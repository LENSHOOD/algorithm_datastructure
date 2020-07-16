package zxh.demo.datastructure.queue;

import zxh.demo.datastructure.linked_list.LinkedList;
import zxh.demo.datastructure.linked_list.TwoWayLinkedList;

/**
 * LinkedListQueue:
 * @author zhangxuhai
 * @date 2020/7/16
*/
public class LinkedListQueue<E> implements Queue<E> {
    private LinkedList<E> linkedList = new TwoWayLinkedList<>();

    @Override
    public void enqueue(E element) {
        if (size() == Integer.MAX_VALUE) {
            throw new IllegalStateException("Queue is full.");
        }

        linkedList.addHead(element);
    }

    @Override
    public E dequeue() {
        try {
            E tail = linkedList.getTail();
            linkedList.removeTail();
            return tail;
        } catch (UnsupportedOperationException e) {
            throw new IllegalStateException("Queue is empty");
        }
    }

    @Override
    public int size() {
        return linkedList.size();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }
}
