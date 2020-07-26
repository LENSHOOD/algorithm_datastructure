package zxh.demo.algorithm.sort;

/**
 * CollectionForSort:
 * @author zhangxuhai
 * @date 2020/7/25
*/
public interface CollectionForSort<T extends Comparable<T>> {
    /**
     * swap from and to index
     *
     * @param fromIndex from index
     * @param toIndex to index
     * @return is swapped
     */
    boolean compareAndSwap(int fromIndex, int toIndex);

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
}
