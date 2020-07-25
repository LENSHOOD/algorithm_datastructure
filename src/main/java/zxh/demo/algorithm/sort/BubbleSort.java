package zxh.demo.algorithm.sort;

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
            for (int j = i + 1; j < collection.size(); j++) {
                swapFlag = collection.compareAndSwap(i, j) || swapFlag;
            }
            if (!swapFlag) {
                break;
            }
        }
    }
}
