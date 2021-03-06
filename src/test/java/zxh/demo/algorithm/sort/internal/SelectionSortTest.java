package zxh.demo.algorithm.sort.internal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import zxh.demo.algorithm.sort.ArrayListForSort;
import zxh.demo.algorithm.sort.LinkedListForSort;
import zxh.demo.algorithm.sort.internal.SelectionSort;
import java.util.Arrays;
import java.util.Random;

class SelectionSortTest {
    private static final int MAX_SIZE = 1000;
    private static Random random = new Random();

    @Test
    public void should_sort_array() {
        // given
        Integer[] original = random.ints(random.nextInt(MAX_SIZE)).boxed().toArray(Integer[]::new);
        Integer[] expected = Arrays.copyOf(original, original.length);
        Arrays.sort(expected);
        ArrayListForSort<Integer> sortCollection = new ArrayListForSort<>(original);

        // when
        new SelectionSort<>(sortCollection).sort();

        // then
        Assertions.assertArrayEquals(sortCollection.toArray(new Integer[]{}), expected);
    }

    @Test
    public void should_sort_linked_list() {
        // given
        Integer[] original = random.ints(random.nextInt(MAX_SIZE)).boxed().toArray(Integer[]::new);
        Integer[] expected = Arrays.copyOf(original, original.length);
        Arrays.sort(expected);
        LinkedListForSort<Integer> sortCollection = new LinkedListForSort<>(original);

        // when
        new SelectionSort<>(sortCollection).sort();

        // then
        Assertions.assertArrayEquals(sortCollection.toArray(new Integer[]{}), expected);
    }
}