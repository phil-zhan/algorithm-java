package org.study.coding.class07;

import java.util.Collections;
import java.util.HashSet;

/**
 * @author phil
 * @date 2022-03-24 17:35:36
 */
public class MainTest05 {

    public static void main(String[] args) {
        char[] candidates = {'a', 'b'};
        int num = 20;
        int len = 4;
        int joint = 5;
        int testTimes = 30000;
        boolean testResult = true;
        for (int i = 0; i < testTimes; i++) {
            Code05_WorldBreak.RandomSample sample = Code05_WorldBreak.generateRandomSample(candidates, num, len, joint);
            int ans1 = ways1(sample.str, sample.arr);
            int ans2 = ways2(sample.str, sample.arr);
            int ans3 = ways3(sample.str, sample.arr);
            int ans4 = ways4(sample.str, sample.arr);
            if (ans1 != ans2 || ans3 != ans4 || ans2 != ans4) {
                testResult = false;
            }
        }
        System.out.println(testTimes + "次随机测试是否通过：" + testResult);
    }

    public static int ways1(String str, String[] arr) {
        HashSet<String> hashSet = new HashSet<>();
        Collections.addAll(hashSet, arr);
        return process(str, 0, hashSet);
    }

    public static int process(String str, int index, HashSet<String> hashSet) {
        if (index == str.length()) {
            return 1;
        }
        int ways = 0;
        for (int end = index; end < str.length(); end++) {
            String pre = str.substring(index, end + 1);
            if (hashSet.contains(pre)) {
                ways += process(str, end + 1, hashSet);
            }
        }
        return ways;
    }

    public static int ways2(String str, String[] dictionary) {
        if (str == null || str.length() == 0 || dictionary == null || dictionary.length == 0) {
            return 0;
        }
        int len = str.length();

        HashSet<String> hashSet = new HashSet<>();
        Collections.addAll(hashSet, dictionary);
        int[] dp = new int[len + 1];
        dp[len] = 1;
        for (int index = len - 1; index >= 0; index--) {
            int ways = 0;
            for (int end = index; end < str.length(); end++) {
                String pre = str.substring(index, end + 1);
                if (hashSet.contains(pre)) {
                    ways += dp[end + 1];
                }
            }
            dp[index] = ways;
        }
        return dp[0];
    }

    public static class Node {
        public boolean end;
        public Node[] next;

        public Node() {
            this.end = false;
            this.next = new Node[26];
        }
    }

    public static int ways3(String str, String[] dictionary) {

        if (str == null || str.length() == 0 || dictionary == null || dictionary.length == 0) {
            return 0;
        }

        Node root = new Node();

        for (String dict : dictionary) {
            Node cur = root;
            char[] ches = dict.toCharArray();
            int path;
            for (char ch : ches) {
                path = ch - 'a';
                if (cur.next[path] == null) {
                    cur.next[path] = new Node();
                }
                cur = cur.next[path];
            }
            cur.end = true;
        }

        return process3(str.toCharArray(), root, 0);

    }

    /**
     * index之前的字符都被匹配了，考虑将 index...n-1 搞定，能有多少种方法
     * 枚举所有能搞定的前缀
     *
     * @since 2022-03-25 10:40:23
     */
    public static int process3(char[] str, Node root, int index) {
        if (index == str.length) {
            return 1;
        }

        int ways = 0;
        Node cur = root;
        for (int end = index; end < str.length; end++) {
            int path = str[end] - 'a';
            if (cur.next[path] == null) {
                break;
            }
            cur = cur.next[path];
            if (cur.end) {
                ways += process3(str, root, end + 1);
            }
        }

        return ways;
    }


    public static int ways4(String s, String[] dictionary) {
        Node root = new Node();
        for (String dict : dictionary) {
            char[] ches = dict.toCharArray();
            Node cur = root;
            int path;
            for (char ch : ches) {
                path = ch - 'a';
                if (cur.next[path] == null) {
                    cur.next[path] = new Node();
                }
                cur = cur.next[path];
            }
            cur.end = true;
        }
        char[] str = s.toCharArray();
        int len = str.length;
        int[] dp = new int[len + 1];

        dp[len] = 1;
        for (int index = len - 1; index >= 0; index--) {
            Node cur = root;
            for (int end = index; end < len; end++) {
                int path = str[end] - 'a';
                if (cur.next[path] == null) {
                    break;
                }
                cur = cur.next[path];
                if (cur.end) {
                    dp[index] += dp[index + 1];
                }
            }
        }
        return dp[0];
    }
}
