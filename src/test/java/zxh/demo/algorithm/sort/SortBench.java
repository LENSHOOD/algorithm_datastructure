package zxh.demo.algorithm.sort;

import org.junit.jupiter.api.Test;
import zxh.demo.algorithm.sort.internal.*;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * SortBench:
 * @author zhangxuhai
 * @date 2020/7/27
*/
public class SortBench {
    private static final int MAX_SIZE = 1000;
    private static final Random random = new Random();

    @Test
    public void should_sort_array() {
        // given
        Integer[] original = random.ints(MAX_SIZE).boxed().toArray(Integer[]::new);
        Supplier<ArrayListForSort<Integer>> collectionBuilder =
                () -> new ArrayListForSort<>(Arrays.copyOf(original, original.length));

        // when
        printTimeConsuming(SelectionSort.class, c -> new SelectionSort<>(c).sort(), collectionBuilder.get());
        printTimeConsuming(InsertionSort.class, c -> new InsertionSort<>(c).sort(), collectionBuilder.get());
        printTimeConsuming(BubbleSort.class, c -> new BubbleSort<>(c).sort(), collectionBuilder.get());
        printTimeConsuming(MergeSort.class, c -> new MergeSort<>(c).sort(), collectionBuilder.get());
        printTimeConsuming(QuickSort.class, c -> new QuickSort<>(c).sort(), collectionBuilder.get());
        printTimeConsuming(HeapSort.class, c -> new HeapSort<>(c, Integer.class).sort(), collectionBuilder.get());
    }

    @Test
    public void should_sort_linked_list() {
        // given
        Integer[] original = random.ints(MAX_SIZE).boxed().toArray(Integer[]::new);
        Supplier<LinkedListForSort<Integer>> collectionBuilder =
                () -> new LinkedListForSort<>(Arrays.copyOf(original, original.length));

        // when
        printTimeConsuming(SelectionSort.class, c -> new SelectionSort<>(c).sort(), collectionBuilder.get());
        printTimeConsuming(InsertionSort.class, c -> new InsertionSort<>(c).sort(), collectionBuilder.get());
        printTimeConsuming(BubbleSort.class, c -> new BubbleSort<>(c).sort(), collectionBuilder.get());
        printTimeConsuming(MergeSort.class, c -> new MergeSort<>(c).sort(), collectionBuilder.get());
        printTimeConsuming(QuickSort.class, c -> new QuickSort<>(c).sort(), collectionBuilder.get());
        printTimeConsuming(HeapSort.class, c -> new HeapSort<>(c, Integer.class).sort(), collectionBuilder.get());
    }

    private <T extends Comparable<T>> void printTimeConsuming(
            Class<? extends Sortable> clazz, Consumer<CollectionForSort<T>> sortableConsumer, CollectionForSort<T> collection) {
        String name = clazz.getSimpleName();
        long start = System.currentTimeMillis();
        sortableConsumer.accept(collection);
        long end = System.currentTimeMillis();
        System.out.println(name + " time consuming: " + (end - start));
    }
}
