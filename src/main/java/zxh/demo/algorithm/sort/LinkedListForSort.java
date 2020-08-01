package zxh.demo.algorithm.sort;

import zxh.demo.datastructure.linked_list.LinkedList;
import zxh.demo.datastructure.linked_list.TwoWayLinkedList;
import java.util.stream.Stream;

/**
 * LinkedListForSort:
 * @author zhangxuhai
 * @date 2020/7/31
*/
public class LinkedListForSort<T extends Comparable<T>> implements CollectionForSort<T> {
    private LinkedList<T> elements = new TwoWayLinkedList<>();

    public LinkedListForSort(T[] array) {
        Stream.of(array).forEach(e -> elements.addTail(e));
    }

    @Override
    public boolean compareAndSwap(int fromIndex, int toIndex) {
        T tmp = elements.get(fromIndex);
        if (tmp.compareTo(elements.get(toIndex)) <= 0) {
            return false;
        }

        elements.set(fromIndex, elements.get(toIndex));
        elements.set(toIndex, tmp);
        return true;
    }

    @Override
    public boolean move(int startIndex, int endIndex, int steps) {
        if (startIndex == endIndex) {
            return false;
        }

        T tmp = elements.get(endIndex);
        elements.remove(endIndex);
        elements.add(startIndex, tmp);
        return true;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public T get(int index) {
        return elements.get(index);
    }

    @Override
    public void set(int index, T value) {
        elements.set(index, value);
    }

    @Override
    public T[] toArray(T[] array) {
        return elements.toArray(array);
    }
}
