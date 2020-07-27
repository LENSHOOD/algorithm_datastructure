package zxh.demo.algorithm.sort;

/**
 * SelectionSort:
 * @author zhangxuhai
 * @date 2020/7/27
*/
public class SelectionSort<T extends Comparable<T>> implements Sortable {
    private final CollectionForSort<T> collection;

    public SelectionSort(CollectionForSort<T> collection) {
        this.collection = collection;
    }

    @Override
    public void sort() {
        for (int i = 0; i < collection.size() - 1; i++) {
            collection.compareAndSwap(i, findSmallestIndex(i + 1, collection.size() - 1));
        }
    }

    private int findSmallestIndex(int indexStart, int indexEnd) {
        int smallestIndex = indexStart;
        for (int i = indexStart + 1; i <= indexEnd; i++) {
            if (collection.get(smallestIndex).compareTo(collection.get(i)) > 0) {
                smallestIndex = i;
            }
        }
        return smallestIndex;
    }
}
