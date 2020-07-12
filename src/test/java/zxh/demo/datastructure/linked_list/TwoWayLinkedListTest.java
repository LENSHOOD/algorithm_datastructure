package zxh.demo.datastructure.linked_list;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TwoWayLinkedListTest {
    @Test
    void should_add_head_to_empty_list() {
        // given
        TwoWayLinkedList<String> list = new TwoWayLinkedList<>();

        // when
        list.addHead("head");

        // then
        assertThat(list.getHead(), is("head"));
        assertThat(list.getTail(), is("head"));
        assertThat(list.indexOf("head"), is(0));
    }

    @Test
    void should_add_tail_to_empty_list() {
        // given
        TwoWayLinkedList<String> list = new TwoWayLinkedList<>();

        // when
        list.addTail("head");

        // then
        assertThat(list.getHead(), is("head"));
        assertThat(list.getTail(), is("head"));
        assertThat(list.indexOf("head"), is(0));
    }

    @Test
    void should_add_head_to_list() {
        // given
        TwoWayLinkedList<String> list = new TwoWayLinkedList<>();
        list.addHead("head");

        // when
        list.addHead("newHead");

        // then
        assertThat(list.getHead(), is("newHead"));
        assertThat(list.getTail(), is("head"));
        assertThat(list.indexOf("newHead"), is(0));
    }

    @Test
    void should_add_tail_to_list() {
        // given
        TwoWayLinkedList<String> list = new TwoWayLinkedList<>();
        list.addHead("head");

        // when
        list.addTail("tail");

        // then
        assertThat(list.getHead(), is("head"));
        assertThat(list.getTail(), is("tail"));
        assertThat(list.indexOf("tail"), is(1));
    }

    @Test
    void should_add_element_to_index_1() {
        // given
        TwoWayLinkedList<String> list = new TwoWayLinkedList<>();
        list.addHead("head");
        list.addTail("tail");

        // when
        list.add(1, "element");

        // then
        assertThat(list.getHead(), is("head"));
        assertThat(list.getTail(), is("tail"));
        assertThat(list.indexOf("element"), is(1));
    }

    @Test
    void should_get_from_list() {
        // given
        TwoWayLinkedList<String> list = new TwoWayLinkedList<>();
        list.addHead("head");
        list.add(1, "element");
        list.addTail("tail");

        // when
        String head = list.getHead();
        String element = list.get(1);
        String tail = list.getTail();

        // then
        assertThat(head, is("head"));
        assertThat(element, is("element"));
        assertThat(tail, is("tail"));
    }

    @Test
    void should_fail_when_get_from_empty_list() {
        TwoWayLinkedList<String> list = new TwoWayLinkedList<>();

        assertThrows(UnsupportedOperationException.class, list::getHead);
        assertThrows(UnsupportedOperationException.class, list::getTail);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
    }

    @Test
    void should_fail_when_remove_from_empty_list() {
        TwoWayLinkedList<String> list = new TwoWayLinkedList<>();

        assertThrows(UnsupportedOperationException.class, list::removeHead);
        assertThrows(UnsupportedOperationException.class, list::removeTail);
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }

    @Test
    void should_remove_head_from_list() {
        // given
        TwoWayLinkedList<String> list = new TwoWayLinkedList<>();
        list.addHead("head");

        // when
        list.removeHead();

        // then
        assertTrue(list.isEmpty());
    }

    @Test
    void should_remove_tail_from_list() {
        // given
        TwoWayLinkedList<String> list = new TwoWayLinkedList<>();
        list.addTail("tail");

        // when
        list.removeTail();

        // then
        assertTrue(list.isEmpty());
    }

    @Test
    void should_remove_element_from_index_1() {
        // given
        TwoWayLinkedList<String> list = new TwoWayLinkedList<>();
        list.addHead("head");
        list.add(1, "element");
        list.addTail("tail");

        // when
        list.remove(1);

        // then
        assertThat(list.getHead(), is("head"));
        assertThat(list.getTail(), is("tail"));
        assertThat(list.get(1), is("tail"));
    }

    @Test
    void should_return_minis_1_when_call_index_of_to_empty_list() {
        // given
        TwoWayLinkedList<String> list = new TwoWayLinkedList<>();

        // when
        int index = list.indexOf("anyString");

        // then
        assertThat(index, is(-1));
    }
}