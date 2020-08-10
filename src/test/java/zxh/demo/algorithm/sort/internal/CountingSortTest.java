package zxh.demo.algorithm.sort.internal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import zxh.demo.algorithm.sort.ArrayListForSort;
import zxh.demo.algorithm.sort.IntIdObject;
import zxh.demo.algorithm.sort.LinkedListForSort;
import java.util.Arrays;
import java.util.Random;

class CountingSortTest {
    private static final int MAX_SIZE = 10;
    private static final int ORIGIN = 1;
    private static final int BOUND = MAX_SIZE;
    private static Random random = new Random();

    class IntObject implements IntIdObject<IntObject>, Comparable<IntObject> {
        private final Integer value;

        IntObject(Integer value) {
            this.value = value;
        }

        @Override
        public int getId() {
            return value;
        }

        @Override
        public int compareTo(IntObject o) {
            return value.compareTo(o.value);
        }
    }

    @Test
    public void should_sort_array() {
        // given
        IntObject[] original = random.ints(random.nextInt(MAX_SIZE), ORIGIN, BOUND).mapToObj(IntObject::new).toArray(IntObject[]::new);
        IntObject[] expected = Arrays.copyOf(original, original.length);
        Arrays.sort(expected);
        ArrayListForSort<IntObject> sortCollection = new ArrayListForSort<>(original);

        // when
        new CountingSort<>(sortCollection).sort();

        // then
        Assertions.assertArrayEquals(sortCollection.toArray(new IntObject[]{}), expected);
    }

    @Test
    public void should_sort_linked_list() {
        // given
        IntObject[] original = random.ints(random.nextInt(MAX_SIZE), ORIGIN, BOUND).mapToObj(IntObject::new).toArray(IntObject[]::new);
        IntObject[] expected = Arrays.copyOf(original, original.length);
        Arrays.sort(expected);
        LinkedListForSort<IntObject> sortCollection = new LinkedListForSort<>(original);

        // when
        new CountingSort<>(sortCollection).sort();

        // then
        Assertions.assertArrayEquals(sortCollection.toArray(new IntObject[]{}), expected);
    }
}