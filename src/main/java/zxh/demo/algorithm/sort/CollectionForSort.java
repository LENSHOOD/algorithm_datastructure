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
     * compare value
     *
     * @param v1 v1
     * @param v2 v2
     * @param <T> comparable type
     * @return  v1 < v2 : -1;
     *          v1 == v2 : 0;
     *          v1 > v2 : 1
     */
    default int compare(T v1, T v2) {
        return v1.compareTo(v2);
    }

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
