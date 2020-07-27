package zxh.demo.algorithm.sort;

import org.junit.jupiter.api.Test;
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
    private static final int MAX_SIZE = 100000;
    private static Random random = new Random();

    @Test
    public void should_sort_array() {
        // given
        Integer[] original = random.ints(MAX_SIZE).boxed().toArray(Integer[]::new);
        Supplier<ArrayListForSort<Integer>> collectionBuilder =
                () -> new ArrayListForSort<>(Arrays.copyOf(original, original.length));
        ArrayListForSort<Integer> first = collectionBuilder.get();
        ArrayListForSort<Integer> second = collectionBuilder.get();
        ArrayListForSort<Integer> third = collectionBuilder.get();

        // when
        printTimeConsuming(SelectionSort.class, c -> new SelectionSort<>(c).sort(), third);
        printTimeConsuming(InsertionSort.class, c -> new InsertionSort<>(c).sort(), second);
        printTimeConsuming(BubbleSort.class, c -> new BubbleSort<>(c).sort(), first);
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
