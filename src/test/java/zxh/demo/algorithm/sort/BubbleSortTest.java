package zxh.demo.algorithm.sort;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

class BubbleSortTest {
    private static final int MAX_SIZE = 10;
    private static Random random = new Random();

    @Test
    public void should_sort_array() {
        // given
        Integer[] original = random.ints(random.nextInt(MAX_SIZE)).boxed().toArray(Integer[]::new);
        Integer[] expected = Arrays.copyOf(original, original.length);
        Arrays.sort(expected);
        ArrayListForSort<Integer> sortCollection = new ArrayListForSort<>(original);

        // when
        new BubbleSort<>(sortCollection).sort();

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
        new BubbleSort<>(sortCollection).sort();

        // then
        Assertions.assertArrayEquals(sortCollection.toArray(new Integer[]{}), expected);
    }

}