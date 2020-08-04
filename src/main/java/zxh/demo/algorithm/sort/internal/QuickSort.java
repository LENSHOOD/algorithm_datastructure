package zxh.demo.algorithm.sort.internal;

import zxh.demo.algorithm.sort.CollectionForSort;
import zxh.demo.algorithm.sort.Sortable;

/**
 * QuickSort:
 * @author zhangxuhai
 * @date 2020/8/4
*/
public class QuickSort<T extends Comparable<T>> implements Sortable {
    private final CollectionForSort<T> collection;

    public QuickSort(CollectionForSort<T> collection) {
        this.collection = collection;
    }

    @Override
    public void sort() {
        if (collection.size() == 0) {
            return;
        }

        partitionAndSplit(collection, 0, collection.size() - 1);
    }

    private void partitionAndSplit(CollectionForSort<T> collection, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }

        T pivot = collection.get(endIndex);
        int i = 0;
        for (int j = 0; j < endIndex; j++) {
            if (collection.get(j).compareTo(pivot) < 0) {
                collection.compareAndSwap(i, j);
                i++;
            }
        }
        collection.compareAndSwap(i, endIndex);

        partitionAndSplit(collection, startIndex, i - 1);
        partitionAndSplit(collection, i + 1, endIndex);
    }
}
