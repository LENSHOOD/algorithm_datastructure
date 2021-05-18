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
        Heap<T> heap = new Heap<>(collection.toArray((T[]) Array.newInstance(type, collection.size())));
        T[] sorted = heap.sort();
        for (int i = 0; i < collection.size(); i++) {
            collection.set(i, sorted[i]);
        }
    }
}
