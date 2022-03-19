package org.study.coding.class17;

import java.util.HashMap;

/**
 * 给定一个字符串str，返回str的所有子序列中有多少不同的字面值
 * Leetcode题目：https://leetcode.com/problems/distinct-subsequences-ii/
 * <p>
 * 先考虑无重复数字的情况：
 * 1）：空字符算一种
 * 2）：假设之前有x种字符。来到 i 位置的时候。当前位置的字符，加到之后的任意一个集合里面，都能形成一种新的字符
 * 那么当前位置形成的字面值就是 1 + x + x 【空集加自己一种，之前的右x种，当前字符和之前的合并也能形成x种】
 * <p>
 * 假设有重复字符
 * 1）：空字符算一种
 * 2）：假设之前有x种字符。来到 i 位置的时候。当前位置的字符，加到之后的任意一个集合里面，都能形成一种新的字符
 * 那么当前位置形成的字面值就是 1 + x + x 【空集加自己一种，之前的右x种，当前字符和之前的合并也能形成x种】
 * 需要减掉一个补偿机制。也就是减掉该字符上一次出现时的以该字符结尾的集合数量.【也就是上一次该字符出现时，新加出来的这一部分的数量】
 * <p>
 * <p>
 * 因为该字符前一次出现的时候，它之前的集合就加过该字符，形成了新一倍的集合数量，它之前的集合一直都保留到了现在。如果
 * 此时再加一个该字符，就会与上一次该字符出现的时候形成的集合重复
 *
 *
 * 原题意是不算空集的。需要减掉一个1
 *
 * @since 2022-03-18 09:26:23
 */
public class Code05_DistinctSubseqValue {

    public static int distinctSubseqII(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int m = 1000000007;
        char[] str = s.toCharArray();
        int[] count = new int[26];

        // 算空集
        int all = 1;
        for (char x : str) {
            int add = (all - count[x - 'a'] + m) % m;
            all = (all + add) % m;
            count[x - 'a'] = (count[x - 'a'] + add) % m;
        }

        // 原题意是不算空集的。需要减掉一个1
        return all - 1;
    }

    public static int zuo(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int m = 1000000007;
        char[] str = s.toCharArray();
        HashMap<Character, Integer> map = new HashMap<>();

        // 一个字符也没遍历的时候，有空集
        int all = 1;
        for (char x : str) {
            int newAdd = all;
            // int curAll = all + newAdd - (map.containsKey(x) ? map.get(x) : 0);
            int curAll = all;
            curAll = (curAll + newAdd) % m;

            // 减的时候，要对m取余，需要先加一个m。避免减之后出现一个负数
            curAll = (curAll - (map.getOrDefault(x, 0)) + m) % m;
            all = curAll;
            map.put(x, newAdd);
        }
        // 原题意是不算空集的。需要减掉一个1
        return all-1;
    }

    public static void main(String[] args) {
        // 这里测试的时候用26个小写字母
        String s = "aa" ;


        System.out.println(distinctSubseqII(s) );
        System.out.println(zuo(s));
    }

}
