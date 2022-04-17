package org.study.coding.class31;

/**
 * 125. 验证回文串
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 *
 * @since 2022-04-16 14:33:25
 */
public class Problem_0125_ValidPalindrome {

    /**
     * 忽略空格、忽略大小写 -> 是不是回文
     * 数字不在忽略大小写的范围内
     *
     * @since 2022-04-16 14:34:14
     */
    public static boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        char[] str = s.toCharArray();
        int L = 0;
        int R = str.length - 1;
        while (L < R) {
            // 英文（大小写） + 数字
            if (validChar(str[L]) && validChar(str[R])) {
                if (!equal(str[L], str[R])) {
                    return false;
                }
                L++;
                R--;
            } else {

                // 不是有效的字符。就右移(或左移)
                L += validChar(str[L]) ? 0 : 1;
                R -= validChar(str[R]) ? 0 : 1;
            }
        }
        return true;
    }

    public static boolean validChar(char c) {
        return isLetter(c) || isNumber(c);
    }

    public static boolean equal(char c1, char c2) {
        if (isNumber(c1) || isNumber(c2)) {
            return c1 == c2;
        }

        // 左右两边都不是数字【大小写不一样的情况：较大的ASCII-较小的ASCII = 32】
        // a  A   32
        // b  B   32
        // c  C   32
        return (c1 == c2) || (Math.max(c1, c2) - Math.min(c1, c2) == 32);
    }

    public static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static boolean isNumber(char c) {
        return (c >= '0' && c <= '9');
    }

}
