package zxh.demo.algorithm;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class KMPTest {
    @Test
    void should_get_next_array() {
        // given
        String matcher = "abcdabcdcd";
        KMP kmp = new KMP("", matcher);

        // when
        int[] next = kmp.buildNext();

        // then
        assertArrayEquals(new int[]{-1, -1, -1, -1, 0, 1, 2, 3, -1, -1}, next);
    }

    @Test
    void should_match_first_occur() {
        // given
        KMP kmp = new KMP("mississippi", "issip");

        // when
        int first = kmp.match();

        // then
        assertEquals(4, first);
    }

    @Test
    void should_get_minus_one_if_not_match() {
        // given
        KMP kmp = new KMP("abcd", "issip");

        // when
        int notMatch = kmp.match();

        // then
        assertEquals(-1, notMatch);
    }

    @Test
    void should_get_zero_if_matcher_is_empty() {
        // given
        KMP kmp = new KMP("abcd", "");

        // when
        int zero = kmp.match();

        // then
        assertEquals(0, zero);
    }
}