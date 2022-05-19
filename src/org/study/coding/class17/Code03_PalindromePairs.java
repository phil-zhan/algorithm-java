package org.study.coding.class17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 测试链接 : https://leetcode.com/problems/palindrome-pairs/
 * <p>
 * 给定一个String数组。数组中的任意两个字符串随机组合，返回能组成回文对的组合
 * 如：
 * 输入["aab","aa"]
 * 输出 [[0,1]]
 * <p>
 * 解法：
 * 先将所有字符串都放到一个hash表中
 * 遍历每个字符串，
 * 看当前字符串，是否有某些前缀是回文。如果有，就看看hash表里面是否存在除了当前前缀，剩下的后缀组成的字符串的逆序
 * 如果存在，就将该后缀的逆序 拼接在当前字符串的前面。就能构成回文
 * 如：["aaab","baa","aaa"]
 * 对于第一个字符串，前缀 "a" 是回文。后缀 "aab" 的逆序 "baa" 存在。就能组成 "baaaaab" 的回文。添加返回答案 [1,0]
 * 去到下一个前缀"aa"
 * <p>
 * 找完前缀之后，不要忘记找后缀。看当前字符串，是否有某些后缀是回文。如果有，就看看hash表里面是否存在除了当前后缀，剩下的前缀组成的字符串的 逆序
 * 如果存在，就将该前缀的逆序 拼接在当前字符串的后面。就能构成回文
 * 对于最后一个字符串，后缀 "b" 是回文。前缀 "aaa" 的逆序 "aaa" 存在。就能组成 "aaabaaa" 的回文。添加返回答案 [0,2]
 * 去到下一个后缀"ab"
 * <p>
 * 如果有重复字符串的时候.hash表的value放一个链表。生成答案的时候，和链表的每一个值都组成一个新的答案。
 * 如果字符串数组中存在空字符串。那么就要考虑某个字符串是否是整体成回文。如果是，那么空字符串加在它的前后都能组成回文
 *
 * @since 2022-03-19 10:16:10
 */
public class Code03_PalindromePairs {

    public static void main(String[] args) {
        System.out.println(palindromePairs(new String[]{"abcd", "dcba", "lls", "s", "sssll"}));
    }

    public static List<List<Integer>> palindromePairs(String[] words) {
        HashMap<String, Integer> wordset = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            wordset.put(words[i], i);
        }
        List<List<Integer>> res = new ArrayList<>();
        //{ [6,23] 、 [7,13] }
        for (int i = 0; i < words.length; i++) {
            // i words[i]
            // findAll(字符串，在i位置，wordset) 返回所有生成的结果返回
            res.addAll(findAll(words[i], i, wordset));
        }
        return res;
    }

    public static List<List<Integer>> findAll(String word, int index, HashMap<String, Integer> words) {
        List<List<Integer>> res = new ArrayList<>();
        String reverse = reverse(word);
        Integer rest = words.get("");
        // 存在空字符串，当前字符串又可以单独成回文
        if (rest != null && rest != index && word.equals(reverse)) {
            addRecord(res, rest, index);
            addRecord(res, index, rest);
        }

        // 拿到当前字符串的每个字符的回文半径
        int[] rs = manacherss(word);
        int mid = rs.length >> 1;

        // 查前缀
        for (int i = 1; i < mid; i++) {

            // 位置等于回文半径减1
            // 也就是当前
            // #a#b#b#a#
            if (i - rs[i] == -1) {
                rest = words.get(reverse.substring(0, mid - i));
                if (rest != null && rest != index) {
                    addRecord(res, rest, index);
                }
            }
        }

        // 查后缀
        for (int i = mid + 1; i < rs.length; i++) {
            if (i + rs[i] == rs.length) {
                rest = words.get(reverse.substring((mid << 1) - i));
                if (rest != null && rest != index) {
                    addRecord(res, index, rest);
                }
            }
        }
        return res;
    }

    public static void addRecord(List<List<Integer>> res, int left, int right) {
        List<Integer> newr = new ArrayList<>();
        newr.add(left);
        newr.add(right);
        res.add(newr);
    }

    public static int[] manacherss(String word) {
        char[] mchs = manachercs(word);
        int[] rs = new int[mchs.length];
        int center = -1;
        int pr = -1;
        for (int i = 0; i != mchs.length; i++) {
            rs[i] = pr > i ? Math.min(rs[(center << 1) - i], pr - i) : 1;
            while (i + rs[i] < mchs.length && i - rs[i] > -1) {
                if (mchs[i + rs[i]] != mchs[i - rs[i]]) {
                    break;
                }
                rs[i]++;
            }
            if (i + rs[i] > pr) {
                pr = i + rs[i];
                center = i;
            }
        }
        return rs;
    }

    public static char[] manachercs(String word) {
        char[] chs = word.toCharArray();
        char[] mchs = new char[chs.length * 2 + 1];
        int index = 0;
        for (int i = 0; i != mchs.length; i++) {
            mchs[i] = (i & 1) == 0 ? '#' : chs[index++];
        }
        return mchs;
    }

    public static String reverse(String str) {
        char[] chs = str.toCharArray();
        int l = 0;
        int r = chs.length - 1;
        while (l < r) {
            char tmp = chs[l];
            chs[l++] = chs[r];
            chs[r--] = tmp;
        }
        return String.valueOf(chs);
    }

}
