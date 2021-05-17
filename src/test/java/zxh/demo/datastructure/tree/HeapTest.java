package zxh.demo.datastructure.tree;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

class HeapTest {
    @Test
    void should_heapify_when_create() {
        // given
        Integer[] originalArr = {1, 4, 2, 8, 6, 12, 2, 7, 7, 1};

        // when
        Heap<Integer> heap = new Heap<>(originalArr);

        // then
        Integer[] heapified = heap.toArray();
        assertThat(Arrays.asList(heapified), contains(12, 8, 2, 7, 6, 1, 2, 4, 7, 1));
    }
}