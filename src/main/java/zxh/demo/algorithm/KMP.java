package zxh.demo.algorithm;

/**
 * KMP:
 *
 * @author zhangxuhai
 * @date 2021/8/24
 */
public class KMP {
    private final String text;
    private final String matcher;

    public KMP(String text, String matcher) {
        this.text = text;
        this.matcher = matcher;
    }


    int[] buildNext() {
        int[] next = new int[matcher.length()];
        int left = -1, right = 1;
        next[0] = -1;
        while (right < matcher.length()) {
            // if new char not match, shorten the substring to the last's longest prefix
            // then test if it's prefix match, if not then keep shorten until left go back
            // to beginning
            while (left >= 0 && matcher.charAt(left + 1) != matcher.charAt(right)) {
                left = next[left];
            }

            // left prefix is equal to right prefix, extend both substring
            // to check if it has longer prefix
            if (matcher.charAt(left + 1) == matcher.charAt(right)) {
                left++;
            }

            // assign the longest prefix index as left
            next[right] = left;

            right++;
        }

        return next;
    }
}
