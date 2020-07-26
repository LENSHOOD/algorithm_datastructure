package zxh.demo.algorithm.sort;

/**
 * ArrayCollection:
 * @author zhangxuhai
 * @date 2020/7/25
*/
public class ArrayListForSort<T extends Comparable<T>> implements CollectionForSort<T> {
    private final T[] array;

    public ArrayListForSort(T[] array) {
        this.array = array;
    }

    @Override
    public boolean compareAndSwap(int fromIndex, int toIndex) {
        if (array[fromIndex].compareTo(array[toIndex]) <= 0) {
            return false;
        }

        T tmp = array[fromIndex];
        array[fromIndex] = array[toIndex];
        array[toIndex] = tmp;

        return true;
    }

    @Override
    public boolean move(int startIndex, int endIndex, int steps) {
        if (startIndex == endIndex) {
            return compareAndSwap(startIndex - 1, endIndex);
        }

        T tmpToBeMoved = array[endIndex];
        boolean moveFlag = false;
        for (int i = endIndex - 1; i >= startIndex; i--) {
            array[i + steps] = array[i];
            moveFlag = true;
        }
        array[startIndex] = tmpToBeMoved;

        return moveFlag;
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public T get(int index) {
        return array[index];
    }
}
