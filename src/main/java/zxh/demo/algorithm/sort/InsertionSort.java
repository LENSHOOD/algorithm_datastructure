package zxh.demo.algorithm.sort;

/**
 * InsertionSort:
 * @author zhangxuhai
 * @date 2020/7/26
*/
public class InsertionSort<T extends Comparable<T>> implements Sortable {
    private final CollectionForSort<T> collection;

    public InsertionSort(CollectionForSort<T> collection) {
        this.collection = collection;
    }

    @Override
    public void sort() {
        for (int i = 1; i < collection.size(); i++) {
            T valueToBeCompared = collection.get(i);
            for (int j = i - 1; j >= -1; j--) {
                if (j == -1 || valueToBeCompared.compareTo(collection.get(j)) >= 0) {
                    collection.move(j + 1, i, 1);
                    break;
                }
            }
        }
    }
}
