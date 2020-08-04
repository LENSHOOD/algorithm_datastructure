package zxh.demo.algorithm.sort;

/**
 * CollectionForSort:
 * @author zhangxuhai
 * @date 2020/7/25
*/
public interface CollectionForSort<T extends Comparable<T>> {
    /**
     * compare then swap from and to index
     *
     * @param fromIndex from index
     * @param toIndex to index
     * @return is swapped
     */
    boolean compareAndSwap(int fromIndex, int toIndex);

    /**
     * swap from and to index
     *
     * @param fromIndex from index
     * @param toIndex to index
     */
    void swap(int fromIndex, int toIndex);

    /**
     * move value
     *
     * @param startIndex index start
     * @param endIndex index end
     * @param steps move steps
     * @return is moved
     */
    boolean move(int startIndex, int endIndex, int steps);

    /**
     * get size
     *
     * @return size
     */
    int size();

    /**
     * get value by index
     *
     * @param index index
     * @return value
     */
    T get(int index);

    /**
     * set value to index
     *
     * @param index index
     * @param value value
     */
    void set(int index, T value);

    /**
     * return array
     *
     * @param array the array into which the elements of the list are to be stored
     * @return array
     */
    T[] toArray(T[] array);
}
