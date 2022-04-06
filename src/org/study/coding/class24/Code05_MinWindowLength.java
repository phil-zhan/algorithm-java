package org.study.coding.class24;

/**
 * // 测试链接 : https://leetcode.com/problems/minimum-window-substring/
 * 5. 给定两个字符串str1和str2，
 * 在str1中寻找一个最短子串，能包含str2的所有字符，【str2出现多个一样的字符，str1也必须要有对应的个数】
 * 字符顺序无所谓，str1的这个最短子串也可以包含多余的字符，返回这个最短包含子串
 * <p>
 * 经典的欠债模型。有单调性，考虑窗口
 * <p>
 * 解法：
 * 准备一个欠债表map
 * key：是str2的字符
 * value：是欠的字符个数
 * <p>
 * 准备一个变量 all = str2.length() 【总共前的字符个数】
 * <p>
 * 准备一个窗口【L=-1  ,R=-1】
 * 窗口在str1上滑动
 * 窗口R往右扩，扩进来的字符，就认为是str1还给str2了
 * 设划进来的字符是x，
 * 看看是不是有效的还款
 * 1）如果欠款的map表中存在x， 且欠债的数量大于0.就认为是有效的还款。all = all-1 。map[x]也减减
 * 2) 如果欠款的map表中不存在x， 或欠债的数量小于等于0。认为是无效的还款。只是map[x]减减 。无效还款 all不变
 * 当all=0的时候。表示子串必须以L位置开头的话，需要滑动到R位置才能刚刚好把欠债还完。记录一下此时的窗口长度
 * <p>
 * 开始滑动左窗口L。表示L位置的字符出窗口。认为str1是又开始欠str2的字符了
 * 设划进=出去的字符是y，
 * 看看是不是有效的欠款
 * 1）如果欠款的map表中存在x， 且欠债的数量大于等于0.就认为是有效的欠款。all = all+1 。map[x]也加加
 * 2) 如果欠款的map表中不存在x， 或欠债的数量小于0。认为是无效的欠款。只是map[x]加加 。无效欠款 all不变
 * 记录一下此时的窗口长度
 * <p>
 * all 只要不是0.就去扩R
 * all=0的时候就去扩L
 *
 * @since 2022-04-05 21:15:19
 */
public class Code05_MinWindowLength {

    /**
     * 最优解
     * @since 2022-04-05 21:37:33
     */
    public static int minLength(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() < s2.length()) {
            return Integer.MAX_VALUE;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        // map[37] = 4 37 4次
        int[] map = new int[256];
        for (int i = 0; i != str2.length; i++) {
            map[str2[i]]++;
        }
        int all = str2.length;

        // [L,R-1] R
        // [L,R) -> [0,0)
        int L = 0;
        int R = 0;
        int minLen = Integer.MAX_VALUE;
        while (R != str1.length) {
            map[str1[R]]--;
            if (map[str1[R]] >= 0) {
                all--;
            }

            // 还完了
            if (all == 0) {
                while (map[str1[L]] < 0) {
                    map[str1[L++]]++;
                }
                // [L..R]
                minLen = Math.min(minLen, R - L + 1);
                all++;
                map[str1[L++]]++;
            }
            R++;
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    /**
     * LeetCode题解。要求的返回值不一样。左一定的个性化处理
     * @since 2022-04-05 21:37:44
     */
    public static String minWindow(String s, String t) {
        if (s.length() < t.length()) {
            return "";
        }
        char[] str = s.toCharArray();
        char[] target = t.toCharArray();
        int[] map = new int[256];
        for (char cha : target) {
            map[cha]++;
        }
        int all = target.length;
        int L = 0;
        int R = 0;
        int minLen = Integer.MAX_VALUE;
        int ansl = -1;
        int ansr = -1;
        while (R != str.length) {
            map[str[R]]--;
            if (map[str[R]] >= 0) {
                all--;
            }
            if (all == 0) {
                while (map[str[L]] < 0) {
                    map[str[L++]]++;
                }
                if (minLen > R - L + 1) {
                    minLen = R - L + 1;
                    ansl = L;
                    ansr = R;
                }
                all++;
                map[str[L++]]++;
            }
            R++;
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(ansl, ansr + 1);
    }

}
