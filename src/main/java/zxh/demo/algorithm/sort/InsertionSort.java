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
            int j = i - 1;
            for (; j >= 0; j--) {
                if (valueToBeCompared.compareTo(collection.get(j)) >= 0) {
                    break;
                }

                collection.set(j + 1, collection.get(j));
            }
            collection.set(j + 1, valueToBeCompared);
        }
    }
}
