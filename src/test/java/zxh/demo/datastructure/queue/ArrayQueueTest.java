package zxh.demo.datastructure.queue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.util.stream.IntStream;

class ArrayQueueTest {
    @Test
    void should_enqueue_element_then_dequeue_it() {
        // given
        ArrayQueue<Integer> queue = new ArrayQueue<>();

        // when
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);

        // then
        assertThat(queue.dequeue(), is(1));
        assertThat(queue.dequeue(), is(2));
        assertThat(queue.dequeue(), is(3));
        assertThat(queue.dequeue(), is(4));
    }

    @Test
    void should_enqueue_element_more_than_16() {
        // given
        ArrayQueue<Integer> queue = new ArrayQueue<>();

        // when
        IntStream.range(0, 15).forEach(queue::enqueue);

        // then
        assertTrue(queue.isFull());
        IntStream.range(0, 5).forEach(i -> assertThat(queue.dequeue(), is(i)));

        // when
        IntStream.range(0, 5).forEach(queue::enqueue);

        // then
        IntStream.range(5, 15).forEach(i -> assertThat(queue.dequeue(), is(i)));
        IntStream.range(0, 5).forEach(i -> assertThat(queue.dequeue(), is(i)));
    }
}