package zxh.demo.algorithm;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

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
}