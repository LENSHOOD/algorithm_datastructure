package zxh.demo.algorithm;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

class JosephusProblemTest {
    @Test
    void should_return_num_16_remain_last() {
        // given
        JosephusProblem josephusProblem = new JosephusProblem(41, 3);

        // when
        int[] suicideSequence = josephusProblem.getSuicideSequence();

        // then
        assertThat(suicideSequence[suicideSequence.length - 1], is(31));
        assertThat(suicideSequence[suicideSequence.length - 2], is(16));
    }
}