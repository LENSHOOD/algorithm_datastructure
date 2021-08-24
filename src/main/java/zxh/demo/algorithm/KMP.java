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

    public int match() {
        if (matcher.length() == 0) {
            return 0;
        }

        int[] next = buildNext();
        int pt = 0, pm = -1;
        while (pt < text.length()) {
            // if not match, go back to last substring's longest prefix
            // then test if it's match, if not, keep go back until get to
            // beginning of the matcher
            while (pm >= 0 && text.charAt(pt) != matcher.charAt(pm + 1)) {
                pm = next[pm];
            }

            // if current char matched, then extend to test next char
            if (text.charAt(pt) == matcher.charAt(pm + 1)) {
                pm++;
            }

            if (pm == matcher.length() - 1) {
                return pt - matcher.length() + 1;
            }

            pt++;
        }

        return -1;
    }
}
