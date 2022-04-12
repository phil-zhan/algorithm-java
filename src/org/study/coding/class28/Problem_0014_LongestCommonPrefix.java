package org.study.coding.class28;

/**
 * 最长前缀【是所有字符串的最长公共前缀返回】
 * 方法1：用前缀树的pass去解
 * <p>
 * 方法2，遍历字符串数组。来到 0 位置的时候。认为0位置的字符串和自己的最长公共前缀是0位置字符串的长度【抓一下最长公共前缀】
 * 然后去到下一个字符串，看看前缀缩到多短了
 * ...
 * 一直往下，最长公共前缀只减不增。
 * 验完最后一个字符串的时候，直接返回抓到的最长公共前缀
 *
 * @since 2022-04-11 23:04:44
 */
public class Problem_0014_LongestCommonPrefix {

    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        char[] chs = strs[0].toCharArray();
        int min = Integer.MAX_VALUE;
        for (String str : strs) {
            char[] tmp = str.toCharArray();
            int index = 0;
            while (index < tmp.length && index < chs.length) {
                if (chs[index] != tmp[index]) {
                    break;
                }
                index++;
            }
            min = Math.min(index, min);
            if (min == 0) {
                return "";
            }
        }
        return strs[0].substring(0, min);
    }

}
