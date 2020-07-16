package zxh.demo.datastructure.queue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

class LinkedListQueueTest {
    @Test
    void should_enqueue_element_then_dequeue_it() {
        // given
        LinkedListQueue<Integer> queue = new LinkedListQueue<>();

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
}