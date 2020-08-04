package zxh.demo.algorithm.sort.internal;

import zxh.demo.algorithm.sort.CollectionForSort;
import zxh.demo.algorithm.sort.Sortable;

/**
 * BubbleSort:
 * @author zhangxuhai
 * @date 2020/7/25
*/
public class BubbleSort<T extends Comparable<T>> implements Sortable {
    private final CollectionForSort<T> collection;

    public BubbleSort(CollectionForSort<T> collection) {
        this.collection = collection;
    }

    @Override
    public void sort() {
        for (int i = 0; i < collection.size(); i++) {
            boolean swapFlag = false;
            for (int j = 0; j < collection.size() - i - 1 ; j++) {
                swapFlag = collection.compareAndSwap(j, j + 1) || swapFlag;
            }
            if (!swapFlag) {
                break;
            }
        }
    }
}
