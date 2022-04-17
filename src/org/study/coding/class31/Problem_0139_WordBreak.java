package org.study.coding.class31;

import java.util.List;

/**
 * lintcode也有测试，数据量比leetcode大很多 : <a href="https://www.lintcode.com/problem/107/">https://www.lintcode.com/problem/107/</a>
 *
 *
 * 139. 单词拆分
 * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。请你判断是否可以利用字典中出现的单词拼接出 s 。
 * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
 *
 *
 *
 * 示例 1：
 *
 * 输入: s = "leetcode", wordDict = ["leet", "code"]
 * 输出: true
 * 解释: 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。
 * 示例 2：
 *
 * 输入: s = "applepenapple", wordDict = ["apple", "pen"]
 * 输出: true
 * 解释: 返回 true 因为 "applepenapple" 可以由 "apple" "pen" "apple" 拼接成。
 *     注意，你可以重复使用字典中的单词。
 * 示例 3：
 *
 * 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * 输出: false
 *
 * 解法：
 * 将字典建成前缀树。
 * 遍历原字符串。
 * 来到 index 位置的时候，看看有没有
 *
 *
 * 从左往右的尝试模型
 * word字符串从 index 出发，到 n-1 。能不能被set字典分解掉
 * process(int index,String word,Set<String> set)
 *
 * 暴力的方法时来到index 位置的时候，去考虑index 能往后推多远，假设到 j 位置，才能在set中找到 word.substring(index,j).【这个j位置，不论找到找不到，都要推到n-1位置】
 * 然后index跳到 j 位置，去考虑后面的子串
 *
 * 优化方法是利用前缀树。【加速枚举】
 * 来到index位置的时候，将从index位置出发，去贴字典的前缀树。能贴多长，也就是j能推多远。
 * 如果有路的时候。就考虑有没有以当前 j 字符结尾的单词，有的话去考虑后面的子串。这个 j 的路也是会一直往下推，直到推到没路。或者推到和原字符串一样长
 * 如果没路，就是当前 j 位置没有必要再往后推了。都没路了，从index 出发，最多能推到当前 j 之前，再往后就不会有对应的路了
 *
 * 当然。可以改成动态规划
 *
 * @since 2022-04-17 11:45:51
 */
public class Problem_0139_WordBreak {

    public static class Node {
        public boolean end;
        public Node[] nexts;

        public Node() {
            end = false;
            nexts = new Node[26];
        }
    }


    /**
     * 返回能不能搞定
     *
     * @since 2022-04-17 12:08:36
     */
    public static boolean wordBreak1(String s, List<String> wordDict) {
        Node root = new Node();

        // 建立字典的前缀树
        for (String str : wordDict) {
            char[] chs = str.toCharArray();
            Node node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new Node();
                }
                node = node.nexts[index];
            }
            node.end = true;
        }


        char[] str = s.toCharArray();
        int N = str.length;
        boolean[] dp = new boolean[N + 1];

        // dp[i]  word[i.....] 能不能被分解
        // 没有字符串了。
        dp[N] = true;

        // dp[N] word[N...]  -> ""  能不能够被分解
        // dp[i] ... dp[i+1....]
        for (int i = N - 1; i >= 0; i--) {
            // i
            // word[i....] 能不能够被分解
            // i..i    i+1....
            // i..i+1  i+2...
            // 依赖 i+1 到后面的值
            Node cur = root;

            // 枚举可能的前缀
            for (int end = i; end < N; end++) {
                cur = cur.nexts[str[end] - 'a'];
                if (cur == null) {
                    break;
                }
                // 有路！
                if (cur.end) {
                    // i...end 真的是一个有效的前缀串  end+1....  能不能被分解
                    // 后面的能搞定，那么当前位置就能搞定。
                    // 后面的搞不定，那就是两个 false 求或逻辑
                    dp[i] |= dp[end + 1];
                }

                // 这里要的只是能不能搞定。当前 i...n-1 能搞定，就不要再去枚举其他的了。
                // 如果要求方法数或者所有的分解结果的话，还是要去继续枚举的
                if (dp[i]) {
                    break;
                }
            }
        }

        // 表示原字符串从 0 出发，能不能被单词表分解
        return dp[0];
    }

    /**
     * 返回方法数
     *
     * @since 2022-04-17 12:08:24
     */
    public static int wordBreak2(String s, List<String> wordDict) {
        Node root = new Node();
        for (String str : wordDict) {
            char[] chs = str.toCharArray();
            Node node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new Node();
                }
                node = node.nexts[index];
            }
            node.end = true;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[] dp = new int[N + 1];

        // 已经来到最后了。之前的分解有效。找到一种有效的
        dp[N] = 1;
        for (int i = N - 1; i >= 0; i--) {
            Node cur = root;
            for (int end = i; end < N; end++) {
                cur = cur.nexts[str[end] - 'a'];
                if (cur == null) {
                    break;
                }
                if (cur.end) {
                    dp[i] += dp[end + 1];
                }
            }
        }
        return dp[0];
    }

}
