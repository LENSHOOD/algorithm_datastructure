package zxh.demo.algorithm.sort;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

class InsertionSortTest {
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
        new InsertionSort<>(sortCollection).sort();

        // then
        Assertions.assertArrayEquals(original, expected);
    }
}