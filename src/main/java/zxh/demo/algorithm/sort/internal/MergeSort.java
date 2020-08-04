package zxh.demo.algorithm.sort.internal;

import zxh.demo.algorithm.sort.CollectionForSort;
import zxh.demo.algorithm.sort.Sortable;

/**
 * MergeSort:
 * @author zhangxuhai
 * @date 2020/8/4
*/
public class MergeSort<T extends Comparable<T>> implements Sortable {
    private final CollectionForSort<T> collection;

    public MergeSort(CollectionForSort<T> collection) {
        this.collection = collection;
    }

    @Override
    public void sort() {
        if (collection.size() == 0) {
            return;
        }

        splitAndMerge(collection, 0, collection.size() - 1);
    }

    private void splitAndMerge(CollectionForSort<T> collection, int startIndex, int endIndex) {
        if (startIndex == endIndex) {
            return;
        }

        int splitIndex = (startIndex + endIndex) / 2;
        splitAndMerge(collection, startIndex, splitIndex);
        splitAndMerge(collection, splitIndex + 1, endIndex);

        Object[] tmpArray = new Object[endIndex - startIndex + 1];
        int i = startIndex;
        int j = splitIndex + 1;
        for (int k = 0; k < tmpArray.length; k++) {
            if (i <= splitIndex && (j > endIndex || collection.get(i).compareTo(collection.get(j)) < 0)) {
                tmpArray[k] = collection.get(i);
                i++;
            } else {
                tmpArray[k] = collection.get(j);
                j++;
            }
        }

        for (int k = 0; k < tmpArray.length; k++) {
            collection.set(startIndex + k, (T) tmpArray[k]);
        }
    }
}
