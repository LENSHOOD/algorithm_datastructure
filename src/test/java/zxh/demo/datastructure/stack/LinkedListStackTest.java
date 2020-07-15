package zxh.demo.datastructure.stack;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.jupiter.api.Test;

class LinkedListStackTest {
    @Test
    void should_pop_54321_when_push_12345() {
        // given
        LinkedListStack<Integer> stack = new LinkedListStack<>();

        // when
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        // then
        assertThat(stack.pop(), is(5));
        assertThat(stack.pop(), is(4));
        assertThat(stack.pop(), is(3));
        assertThat(stack.pop(), is(2));
        assertThat(stack.pop(), is(1));
        assertThat(stack.pop(), is(nullValue()));
        assertThat(stack.pop(), is(nullValue()));
    }
}