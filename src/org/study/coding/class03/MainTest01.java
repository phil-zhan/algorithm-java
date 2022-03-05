package org.study.coding.class03;

/**
 * @author phil
 * @since 2022-0305 09:41:37
 */
public class MainTest01 {

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] str = s.toCharArray();

        // 准备256，防止出现一些特殊字符
        int[] map = new int[256];

        for (int i = 0; i < 256; i++) {
            map[i] = -1;
        }

        map[str[0]] = 0;
        int pre = 1;
        int ans = 1;
        for (int i = 1; i < str.length; i++) {

            // cur ans
            pre = Math.min(i - map[str[i]], pre + 1);
            ans = Math.max(ans, pre);
            map[str[i]] = i;

        }

        return ans;
    }
}
