package org.study.coding.class33;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 这是应该付费题
 * 给出很多字符串【字符串数组的形式给出】
 * 认为这些字符串是出版社的人员名字
 * 现在怀疑出版社内部，有一个自己的字典序【正常的是 a<b<c<d<e....】【出版社可能自己规定了其他的顺序】
 * 现在这份名单，可能是按照自定义的字典序排列的。也可能是随便排列的
 * <p>
 * 如果这份名单后面 有 自定义的字典序。将整个字典序返回。从小到大
 * 如果这份名单后面 没有 自定义的字典序。返回空字符串
 * <p>
 * <p>
 * <p>
 * <p>
 * 解法：
 * 考虑拓扑排序
 * <p>
 * <p>
 * <p>
 * 考虑i位置的字符串和 i+1 的字符串
 * 从他们的第一个字符开始考虑。找到第一次不一样的字符。就能得到一个关系。i位置的字符串的字符小于 i+1 位置的字符串的字符
 * 【如果没分出大小，也就是两个字符串的对应字符是一样的。那就考虑这两个字符串的下一个位置。】
 * 【一旦分出大小。后面的字符就不用看了。直接去考虑 i+1 位置的字符串和 i+2 位置的字符串】
 * <p>
 * <p>
 * <p>
 * 将这里面的字符的大小关系用箭头表示。从小到大，就能形成一个图。生成图之后。去考虑拓扑排序。如果能生成拓扑排序，那么拓扑排序的结果就是答案。如果不能生成，直接返回空字符串
 *
 * @since 2022-04-20 23:28:22
 */
public class Problem_0269_AlienDictionary {

    public static String alienOrder(String[] words) {
        if (words == null || words.length == 0) {
            return "";
        }
        int N = words.length;

        // 字符的入度表【所有字符的入度，都初始化为0】
        HashMap<Character, Integer> indegree = new HashMap<>();
        for (int i = 0; i < N; i++) {
            for (char c : words[i].toCharArray()) {
                indegree.put(c, 0);
            }
        }

        // 邻接表
        HashMap<Character, HashSet<Character>> graph = new HashMap<>();
        for (int i = 0; i < N - 1; i++) {
            char[] cur = words[i].toCharArray();
            char[] nex = words[i + 1].toCharArray();

            // 要对其了比
            int len = Math.min(cur.length, nex.length);
            int j = 0;
            for (; j < len; j++) {

                // 找到第一个不等的位置。搞定就break
                if (cur[j] != nex[j]) {

                    // 邻接表没有，就生成
                    if (!graph.containsKey(cur[j])) {
                        graph.put(cur[j], new HashSet<>());
                    }

                    // 当前字符的邻接表里面没有下一个字符。将下一个字符加入当前字符的邻接表。调整下一个字符的入度
                    // 如果当前字符的邻接表里面已经有下一个字符了。如果当前的这个大小关系之前就有过了。直接舍弃
                    if (!graph.get(cur[j]).contains(nex[j])) {
                        graph.get(cur[j]).add(nex[j]);
                        indegree.put(nex[j], indegree.get(nex[j]) + 1);
                    }
                    break;
                }
            }

            // 如果i位置的字符比 i+1 位置的字符长。也就是next先结束【应该是长的排在后面。这里长的排在前面（有字符的字典序大于0的字典序），直接违反规则。返回false】
            if (j < cur.length && j == nex.length) {
                return "";
            }
        }

        // collect ans
        StringBuilder ans = new StringBuilder();

        // 队列实现拓扑排序
        Queue<Character> q = new LinkedList<>();

        // 找到入度为0的点
        for (Character key : indegree.keySet()) {
            if (indegree.get(key) == 0) {
                q.offer(key);
            }
        }

        while (!q.isEmpty()) {
            char cur = q.poll();
            ans.append(cur);
            if (graph.containsKey(cur)) {
                for (char next : graph.get(cur)) {
                    indegree.put(next, indegree.get(next) - 1);
                    if (indegree.get(next) == 0) {
                        q.offer(next);
                    }
                }
            }
        }

        // 能生成拓扑排序【拓扑排序的结果长度等于原图的节点数】
        return ans.length() == indegree.size() ? ans.toString() : "";
    }

}
