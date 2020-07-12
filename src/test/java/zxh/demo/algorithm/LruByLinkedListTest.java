package zxh.demo.algorithm;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.jupiter.api.Test;

class LruByLinkedListTest {
    @Test
    void should_put_key_to_head_when_access_new_key() {
        // given
        LruByLinkedList lru = new LruByLinkedList();

        // when
        lru.access("firstKey");

        // then
        assertThat(lru.getHead(), is("firstKey"));
    }

    @Test
    void should_put_key_to_head_when_access_exist_key() {
        // given
        LruByLinkedList lru = new LruByLinkedList();
        lru.access("firstKey");
        lru.access("secondKey");
        lru.access("thirdKey");

        // when
        lru.access("secondKey");

        // then
        assertThat(lru.getHead(), is("secondKey"));
    }

    @Test
    void should_remove_tail_key_when_retire_key() {
        // given
        LruByLinkedList lru = new LruByLinkedList();
        lru.access("firstKey");
        lru.access("secondKey");
        lru.access("thirdKey");

        // when
        String retiredKey = lru.retire();

        // then
        assertThat(retiredKey, is("firstKey"));
    }

    @Test
    void should_remove_nothing_when_retire_key_to_empty_lru() {
        // given
        LruByLinkedList lru = new LruByLinkedList();

        // when
        String retiredKey = lru.retire();

        // then
        assertThat(retiredKey, is(nullValue()));
    }

}