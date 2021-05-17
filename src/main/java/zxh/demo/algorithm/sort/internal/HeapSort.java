package zxh.demo.algorithm.sort.internal;

import zxh.demo.algorithm.sort.CollectionForSort;
import zxh.demo.algorithm.sort.Sortable;
import zxh.demo.datastructure.tree.Heap;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * HeapSort:
 * @author zhangxuhai
 * @date 2021/5/17
*/
public class HeapSort<T extends Comparable<T>> implements Sortable {
    private final CollectionForSort<T> collection;
    private final Class<T> type;

    public HeapSort(CollectionForSort<T> collection, Class<T> type) {
        this.collection = collection;
        this.type = type;
    }

    @Override
    public void sort() {
        T[] heapified = new Heap<>(collection.toArray((T[]) Array.newInstance(type, collection.size()))).toArray();
        for (int i = heapified.length-1; i >= 0 ; i--) {
            swap(heapified, i, 0);

            int currPos = 0;
            while (true) {
                int maxPos = currPos;
                int leftChildIndex = currPos * 2 + 1;
                int rightChildIndex = currPos * 2 + 2;

                if (leftChildIndex < i
                        && heapified[maxPos].compareTo(heapified[leftChildIndex]) < 0) {
                    maxPos = leftChildIndex;
                }

                if (rightChildIndex < i
                        && heapified[maxPos].compareTo(heapified[rightChildIndex]) < 0) {
                    maxPos = rightChildIndex;
                }

                if (maxPos == currPos) {
                    break;
                }

                swap(heapified, currPos, maxPos);
                currPos = maxPos;
            }
            collection.set(i, heapified[i]);
        }
    }

    private void swap(T[] arr, int fromIndex, int toIndex) {
        T tmp = arr[fromIndex];
        arr[fromIndex] = arr[toIndex];
        arr[toIndex] = tmp;
    }
}
