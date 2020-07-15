package zxh.demo.datastructure.stack;

import java.util.Arrays;

/**
 * ArrayStack:
 * @author zhangxuhai
 * @date 2020/7/15
*/
public class ArrayStack<E> implements Stack<E>{
    private static final int DEFAULT_SIZE = 16;
    private Object[] array;
    private int count = 0;

    public ArrayStack() {
        array = new Object[DEFAULT_SIZE];
    }

    public ArrayStack(int size) {
        array = new Object[size];
    }

    @Override
    public void push(E element) {
        if (count == array.length - 1) {
            array = Arrays.copyOf(array, newCapacity());
        }

        array[count++] = element;
    }

    int newCapacity() {
        if (array.length == Integer.MAX_VALUE) {
            throw new UnsupportedOperationException("Max Size Exceeded.");
        }

        long newCapacity = (long) array.length * 2L;
        if (newCapacity > Integer.MAX_VALUE) {
            newCapacity = Integer.MAX_VALUE;
        }

        return (int) newCapacity;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            return null;
        }

        return (E) array[--count];
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    public void trimToSize() {
        array = Arrays.copyOf(array, count);
    }
}
