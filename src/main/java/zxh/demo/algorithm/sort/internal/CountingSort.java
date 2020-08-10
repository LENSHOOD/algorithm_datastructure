package zxh.demo.algorithm.sort.internal;

import zxh.demo.algorithm.sort.CollectionForSort;
import zxh.demo.algorithm.sort.IntIdObject;
import zxh.demo.algorithm.sort.Sortable;

/**
 * BucketSort:
 * @author zhangxuhai
 * @date 2020/8/9
*/
public class CountingSort<T extends IntIdObject<T>> implements Sortable {
    private final CollectionForSort<T> collection;
    private int[] buckets;
    private int biggestId = 0;
    private int smallestId = Integer.MAX_VALUE;

    public CountingSort(CollectionForSort<T> collection) {
        this.collection = collection;
    }

    @Override
    public void sort() {
        detectBound();

        counting();

        int[] accumulationBuckets = getAccumulationBuckets();

        Object[] sortedArray = sortByAccumulationBuckets(accumulationBuckets);

        rebuildCollection(sortedArray);
    }

    void detectBound() {
        for (int i = 0; i < collection.size(); i++) {
            int currId = collection.get(i).getId();
            if (currId > biggestId) {
                biggestId = currId;
            }

            if (currId < smallestId) {
                smallestId = currId;
            }
        }
    }

    void counting() {
        buckets = new int[biggestId - smallestId + 1];
        for (int i = 0; i < collection.size(); i++) {
            T curr = collection.get(i);
            int index = curr.getId() - smallestId;
            if (buckets[index] == 0) {
                buckets[index] = 1;
            } else {
                buckets[index] += 1;
            }
        }
    }

    int[] getAccumulationBuckets() {
        int[] accumulationBuckets = new int[buckets.length];
        for (int i = 0; i < buckets.length; i++) {
            if (i == 0) {
                accumulationBuckets[i] = buckets[i];
                continue;
            }
            accumulationBuckets[i] = accumulationBuckets[i - 1] + buckets[i];
        }
        return accumulationBuckets;
    }

    Object[] sortByAccumulationBuckets(int[] accumulationBuckets) {
        Object[] sortedArray = new Object[collection.size()];
        for (int i = collection.size() - 1; i >= 0; i--) {
            T curr = collection.get(i);
            int pos = accumulationBuckets[curr.getId() - smallestId];
            sortedArray[pos - 1] = curr;
            accumulationBuckets[curr.getId() - smallestId] = pos - 1;
        }
        return sortedArray;
    }

    void rebuildCollection(Object[] sortedArray) {
        for (int i = 0; i < collection.size(); i++) {
            collection.set(i, (T) sortedArray[i]);
        }
    }
}
