package org.study.coding.class07;

import java.util.HashMap;

/**
 * @author phil
 * @date 2022-03-25 13:23:34
 */
public class MainTest06 {

    public static void main(String[] args) {
        String str = "abcdefg";
        int K = 3;
        String[] parts = {"abc", "def", "g", "ab", "cd", "efg", "defg"};
        int[] record = {1, 1, 1, 3, 3, 3, 2};
        System.out.println(maxRecord1(str, K, parts, record));
        System.out.println(maxRecord2(str, K, parts, record));
        System.out.println(maxRecord3(str, K, parts, record));
    }

    public static int maxRecord1(String str, int k, String[] parts, int[] records) {
        if (str == null || str.length() == 0 || parts == null || records == null || parts.length != records.length) {
            return 0;
        }
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < parts.length; i++) {
            hashMap.put(parts[i], records[i]);
        }

        return process1(str, k, hashMap, 0);
    }

    public static int process1(String str, int rest, HashMap<String, Integer> hashMap, int index) {
        if (rest < 0) {
            return -1;
        }
        if (index == str.length()) {
            return rest == 0 ? 0 : -1;
        }

        int ans = -1;
        for (int end = index; end < str.length(); end++) {
            String first = str.substring(index, end + 1);
            int next = hashMap.containsKey(first) ? process1(str, rest - 1, hashMap, end + 1) : -1;
            if (next != -1) {
                ans = Math.max(ans, next + hashMap.get(first));
            }
        }

        return ans;
    }

    public static int maxRecord2(String str, int k, String[] parts, int[] records) {
        if (str == null || str.length() == 0 || parts == null || records == null || parts.length != records.length) {
            return 0;
        }
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < parts.length; i++) {
            hashMap.put(parts[i], records[i]);
        }
        int rowNum = str.length();
        int[][] dp = new int[str.length() + 1][k + 1];
        for (int col = 1; col <= k; col++) {
            dp[rowNum][col] = -1;
        }

        for (int index = rowNum - 1; index >= 0; index--) {
            for (int rest = 0; rest <= k; rest++) {

                int ans = -1;
                for (int end = index; end < rowNum; end++) {
                    String first = str.substring(index, end + 1);
                    int next = rest > 0 && hashMap.containsKey(first) ? dp[end + 1][rest - 1] : -1;
                    if (next != -1) {
                        ans = Math.max(ans, next + hashMap.get(first));
                    }
                }
                dp[index][rest] = ans;
            }
        }
        return dp[0][k];
    }

    public static int maxRecord3(String s, int k, String[] parts, int[] records) {
        if (s == null || s.length() == 0 ||
                k < 0 || parts == null || records == null ||
                parts.length == 0 || parts.length != records.length) {

            return 0;
        }
        char[] str = s.toCharArray();

        TreeNode root = generateTree(parts, records);
        int rowNum = s.length();
        int[][] dp = new int[s.length() + 1][k + 1];
        for (int col = 1; col <= k; col++) {
            dp[rowNum][col] = -1;
        }

        for (int index = rowNum - 1; index >= 0; index--) {
            for (int rest = 0; rest <= k; rest++) {

                TreeNode cur = root;
                int ans = -1;
                for (int end = index; end < rowNum; end++) {
                    int path = str[end] - 'a';
                    if (cur.next[path] == null) {
                        break;
                    }

                    cur = cur.next[path];
                    int next = rest > 0 && cur.value != -1 ? dp[end + 1][rest - 1] : -1;
                    if (next != -1) {
                        ans = Math.max(ans, cur.value + next);
                    }
                }
                dp[index][rest] = ans;
            }
        }
        return dp[0][k];
    }

    public static TreeNode generateTree(String[] parts, int[] records) {
        TreeNode root = new TreeNode();
        for (int i = 0; i < parts.length; i++) {
            char[] ches = parts[i].toCharArray();
            TreeNode cur = root;
            for (char ch : ches) {
                int path = ch - 'a';
                if (cur.next[path] == null) {
                    cur.next[path] = new TreeNode();
                }
                cur = cur.next[path];
            }
            cur.value = records[i];
        }
        return root;
    }


    public static class TreeNode {
        public int value;
        public TreeNode[] next;

        public TreeNode() {
            this.value = -1;
            this.next = new TreeNode[26];
        }
    }
}
