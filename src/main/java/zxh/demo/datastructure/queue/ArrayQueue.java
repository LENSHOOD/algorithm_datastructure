package zxh.demo.datastructure.queue;

/**
 * ArrayQueue:
 * @author zhangxuhai
 * @date 2020/7/20
*/
public class ArrayQueue<E> implements Queue<E> {
    private static final int DEFAULT_CAPACITY = 16;
    private Object[] elementArray;
    private int headIndex = 0;
    private int tailIndex = 1;
    private int size = 0;

    public ArrayQueue() {
        this.elementArray = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void enqueue(E element) {
        if (isFull()) {
            throw new UnsupportedOperationException("Array queue is full.");
        }

        elementArray[tailIndex] = element;
        if (tailIndex == elementArray.length - 1) {
            tailIndex = 0;
        } else {
            tailIndex++;
        }
        size++;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("Array queue is empty.");
        }

        if (headIndex == elementArray.length - 1) {
            headIndex = 0;
        } else {
            headIndex++;
        }
        E result = (E) elementArray[headIndex];
        size--;
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return headIndex == tailIndex;
    }
}
