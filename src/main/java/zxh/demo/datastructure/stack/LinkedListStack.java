package zxh.demo.datastructure.stack;

import zxh.demo.datastructure.linked_list.LinkedList;
import zxh.demo.datastructure.linked_list.TwoWayLinkedList;

/**
 * LinkedListStack:
 * @author zhangxuhai
 * @date 2020/7/15
*/
public class LinkedListStack<E> implements Stack<E> {
    private LinkedList<E> linkedList = new TwoWayLinkedList<>();

    @Override
    public void push(E element) {
        linkedList.addHead(element);
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            return null;
        }

        E element = linkedList.getHead();
        linkedList.removeHead();
        return element;
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
