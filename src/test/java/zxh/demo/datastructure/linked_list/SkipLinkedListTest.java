package zxh.demo.datastructure.linked_list;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import zxh.demo.datastructure.sorted_list.SkipLinkedList;

class SkipLinkedListTest {
    @Test
    void should_add_head_to_empty_list() {
        // given
        SkipLinkedList<String> list = new SkipLinkedList<>();

        // when
        list.add("1-head");

        // then
        assertThat(list.size(), is(1));
        assertTrue(list.contains("1-head"));
    }

    @Test
    void should_add_element_to_list() {
        // given
        SkipLinkedList<String> list = new SkipLinkedList<>();
        list.add("1-head");
        list.add("9-tail");

        // when
        list.add("2-element");
        String[] array = list.toArray(new String[]{});

        // then
        assertThat(array.length, is(3));
        assertThat(array[0], is("1-head"));
        assertThat(array[1], is("2-element"));
        assertThat(array[2], is("9-tail"));
    }

    @Test
    void should_add_element_to_list_with_alphabet_order() {
        // given
        SkipLinkedList<String> list = new SkipLinkedList<>();
        list.add("1-head");
        list.add("9-tail");

        // when
        list.add("8-element");
        list.add("4-element");
        list.add("3-element");
        list.add("9-element");
        String[] array = list.toArray(new String[]{});

        // then
        assertThat(array.length, is(6));
        assertThat(array[0], is("1-head"));
        assertThat(array[1], is("3-element"));
        assertThat(array[2], is("4-element"));
        assertThat(array[3], is("8-element"));
        assertThat(array[4], is("9-element"));
        assertThat(array[5], is("9-tail"));
    }

    @Test
    void should_contains_true_from_list() {
        // given
        SkipLinkedList<String> list = new SkipLinkedList<>();
        list.add("1-head");
        list.add("2-element");
        list.add("9-tail");

        // when
        boolean contains = list.contains("2-element");

        // then
        assertTrue(contains);
    }

    @Test
    void should_fail_when_remove_from_empty_list() {
        SkipLinkedList<String> list = new SkipLinkedList<>();

        assertThrows(IllegalStateException.class, () -> list.remove(""));
    }

    @Test
    void should_remove_element_from_list() {
        // given
        SkipLinkedList<String> list = new SkipLinkedList<>();
        list.add("1-head");
        list.add("2-element");
        list.add("9-tail");

        // when
        list.remove("2-element");
        String[] array = list.toArray(new String[]{});

        // then
        assertThat(array.length, is(2));
        assertFalse(list.contains("2-element"));
    }
}