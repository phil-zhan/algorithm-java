package org.study.system.class48;

import org.study.coding.class07.Code05_WorldBreak;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author phil
 * @since 2022-0308 20:19:47
 */
public class Code28_139_WordBreak {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("leet");
        list.add("code");
        boolean ans = new Code28_139_WordBreak().wordBreak("leetcode", list);
        boolean ans2 = new Code28_139_WordBreak().wordBreak2("leetcode", list);
        System.out.println(ans);
        System.out.println(ans2);
    }

    public boolean wordBreak(String s, List<String> wordDict) {

        return process(s, 0, new HashSet<>(wordDict));
    }

    public boolean process(String str, int index, HashSet<String> wordDict) {
        if (index == str.length()) {
            return true;
        }
        for (int end = index; end < str.length(); end++) {
            if (wordDict.contains(str.substring(index, end + 1)) && process(str, end + 1, wordDict)) {
                return true;
            }
        }
        return false;
    }

    public boolean wordBreak2(String str, List<String> wordDict) {
        boolean[] dp = new boolean[str.length() + 1];

        dp[str.length()] = true;

        for (int index = str.length() - 1; index >= 0; index--) {
            for (int end = index; end < str.length(); end++) {
                if (wordDict.contains(str.substring(index, end + 1)) && dp[end + 1]) {
                    dp[index] = true;
                    break;
                }
            }
        }

        return dp[0];
    }

    public boolean wordBreak3(String s, List<String> wordDict) {
        Node root = new Node();

        for (String value : wordDict) {
            char[] ch = value.toCharArray();
            Node cur = root;
            int index;
            for (char c : ch) {
                index = c - 'a';
                if (cur.nexts[index] == null) {
                    cur.nexts[index] = new Node();
                }
                cur = cur.nexts[index];

            }
            cur.end = true;
        }

        char[] str = s.toCharArray();
        boolean[] dp = new boolean[s.length() + 1];
        dp[s.length()] = true;


        for (int index = s.length() - 1; index >= 0; index--) {
            Node cur = root;

            for (int end = index; end < str.length; end++) {

                int path = str[end] - 'a';
                if (cur.nexts[path] == null) {
                    break;
                }
                cur = cur.nexts[path];
                if (cur.end && dp[end + 1]) {
                    dp[index] = true;
                }
            }

        }
        return dp[0];
    }


    public boolean process3(char[] str, int index, Node root) {
        if (index == str.length) {
            return true;
        }

        Node cur = root;

        for (int end = index; end < str.length; end++) {
            int path = str[end] - 'a';

            if (cur.nexts[path] == null) {
                break;
            }
            cur = cur.nexts[path];
            if (cur.end && process3(str, end + 1, root)) { // i...end
                return true;
            }
        }

        return false;

    }

    public boolean wordBreak4(String s, List<String> wordDict) {
        Node root = new Node();

        for (String value : wordDict) {
            char[] ch = value.toCharArray();
            Node cur = root;
            int index;
            for (char c : ch) {
                index = c - 'a';
                if (cur.nexts[index] == null) {
                    cur.nexts[index] = new Node();
                }
                cur = cur.nexts[index];

            }
            cur.end = true;
        }

        return process3(s.toCharArray(), 0, root);
    }

    public static class Node {
        public boolean end;
        public Node[] nexts;

        public Node() {
            end = false;
            nexts = new Node[26];
        }
    }

}
