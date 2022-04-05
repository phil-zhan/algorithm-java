package org.study.coding.class24;

/**
 * 6. 给你一个字符串 s ，请你去除字符串中重复的字母，使得每个字母只出现一次。需保证 返回结果的字典序最小（要求不能打乱其他字符的相对位置）
 * Leetcode题目：https://leetcode.com/problems/remove-duplicate-letters/
 * Input: s = "bcabc"
 * Output: "abc"
 * <p>
 * Input: s = "cbacdcbc"
 * Output: "acdb"
 * <p>
 * 解法：
 * 整体的思路是 [0...i]范围上选一个字符。选中之后，去[i+1...n-1] 选剩下的字符
 * <p>
 * 建立一个统计表map
 * key：是输入字符串的字符
 * value：是对应字符出现的次数
 * <p>
 * 从左往右遍历字符串。每来到一个字符，就将其对应的次数减减【表示从当前位置到最后，还有多少个该字符】
 * 如果某个字符的次数减到0的时候,假设对应的位置是i，后面再没有这个字符了.
 * 【这个字符必须在i位置之前选出来】【但是选的时候，尽可能在候选的这个范围上，先选ASCII最小的】
 * 表示现在必须选该字符了，【0...i】范围上选一个字符，【这个字符尽可能选能选的字符中，ASCII最小的】。假设选的位置是k。 0<= k <= i
 * 选完之后【假设选的是 'a' 】,将[k+1...n-1]范围上的所有 'a' 全部删掉
 * 删掉之后，再从[k+1...最后]范围上去考虑剩余的字符。
 * 再从剩下的字符里面去选剩下的
 * <p>
 * 在选的时候，如果有多个 ASCII 最小的时候。选哪个都行
 *
 * @since 2022-04-05 21:40:14
 */
public class Code06_RemoveDuplicateLettersLessLexi {

    /**
     * 最优解
     * 在str中，每种字符都要保留一个，让最后的结果，字典序最小 ，并返回
     * 时间复杂度 O(K*N) 【K是字符的种数。这个种数肯定不多】【也就可以看成O(N)的复杂度】
     *
     * @since 2022-04-05 21:40:25
     */
    public static String removeDuplicateLetters1(String str) {
        if (str == null || str.length() < 2) {
            return str;
        }
        int[] map = new int[256];
        for (int i = 0; i < str.length(); i++) {
            map[str.charAt(i)]++;
        }
        int minACSIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            // 记录最小的 ASCII 的位置
            minACSIndex = str.charAt(minACSIndex) > str.charAt(i) ? i : minACSIndex;
            if (--map[str.charAt(i)] == 0) {
                break;
            }
        }
        // 0...break(之前) minACSIndex 【包含break的那个位置】
        // str[minACSIndex] 剩下的字符串str[minACSIndex+1...] -> 去掉str[minACSIndex]字符 -> s'
        // s'...
        return str.charAt(minACSIndex) + removeDuplicateLetters1(
                str.substring(minACSIndex + 1).replaceAll(String.valueOf(str.charAt(minACSIndex)), ""));
    }

    /**
     * 非递归的版本。
     * 思路是一样的
     *
     * @since 2022-04-05 22:07:27
     */
    public static String removeDuplicateLetters2(String s) {
        char[] str = s.toCharArray();
        // 小写字母ascii码值范围[97~122]，所以用长度为26的数组做次数统计
        // 如果map[i] > -1，则代表ascii码值为i的字符的出现次数
        // 如果map[i] == -1，则代表ascii码值为i的字符不再考虑
        int[] map = new int[26];
        for (int i = 0; i < str.length; i++) {
            map[str[i] - 'a']++;
        }
        char[] res = new char[26];
        int index = 0;
        int L = 0;
        int R = 0;
        while (R != str.length) {
            // 如果当前字符是不再考虑的，直接跳过
            // 如果当前字符的出现次数减1之后，后面还能出现，直接跳过
            if (map[str[R] - 'a'] == -1 || --map[str[R] - 'a'] > 0) {
                R++;
            } else { // 当前字符需要考虑并且之后不会再出现了
                // 在str[L..R]上所有需要考虑的字符中，找到ascii码最小字符的位置
                int pick = -1;
                for (int i = L; i <= R; i++) {
                    if (map[str[i] - 'a'] != -1 && (pick == -1 || str[i] < str[pick])) {
                        pick = i;
                    }
                }
                // 把ascii码最小的字符放到挑选结果中
                res[index++] = str[pick];
                // 在上一个的for循环中，str[L..R]范围上每种字符的出现次数都减少了
                // 需要把str[pick + 1..R]上每种字符的出现次数加回来
                for (int i = pick + 1; i <= R; i++) {

                    // 只增加以后需要考虑字符的次数
                    if (map[str[i] - 'a'] != -1) {
                        map[str[i] - 'a']++;
                    }
                }
                // 选出的ascii码最小的字符，以后不再考虑了
                map[str[pick] - 'a'] = -1;
                // 继续在str[pick + 1......]上重复这个过程
                L = pick + 1;
                R = L;
            }
        }
        return String.valueOf(res, 0, index);
    }

}
