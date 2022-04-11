package org.study.coding.class26;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 本题测试链接 : https://leetcode.com/problems/word-ladder-ii/
 * 4. 按字典wordList 完成从单词 beginWord 到单词 endWord 转化，一个表示此过程的 转换序列 是形式上像 beginWord -> s1 -> s2 -> ... -> sk 这样的单词序列，并满足：
 *    每对相邻的单词之间仅有单个字母不同。
 *    转换过程中的每个单词 si（1 <= i <= k）必须是字典wordList 中的单词。注意，beginWord 不必是字典 wordList 中的单词
 *    给你两个单词 beginWord 和 endWord ，以及一个字典 wordList
 *    请你找出并返回所有从 beginWord 到 endWord 的 最短转换序列 ，如果不存在这样的转换序列，返回一个空列表
 *    每个序列都应该以单词列表 [beginWord, s1, s2, ..., sk] 的形式返回
 * <p>
 * 题意：
 *    所给的所有字符串的长度都一样
 *    目的是将 start字符串 变成 end字符串。每次只能变一个字符。且变化的所有字符串都必须在所给定的表里。
 *    返回变化的最短路径。可能存在多条最短路径，所有最短路径都返回
 * <p>
 * 解法：
 *    本题即涉及到宽度优先遍历，也涉及到深度优先遍历。还有递归函数的设计。
 * <p>
 * 1、做出所给字符串列表的邻接表hashmap。【可以想想成一张图】
 *    key：所给列表里面的字符串
 *    value：是一个list列表。表示所给的字符串列表里面，有哪些是当前key，只改变一个字符就能到达的
 *    该过程就牵涉到优化。当然可以在每个字符串的时候都去遍历。但是太慢了
 *    本题有一个限制。就是所有的字符都只是小写字母（26种）
 * 优化：
 *    在来到某个字符串的时候。考虑当前字符串。将第一个字符变换成其他25中字符，看看谁在表里有，有的就是自己的邻居。
 *    接着去考虑第二个字符、第三个字符，以此类推【穷举邻居的所有可能】【O(26*N*k^2)】
 *    【字符串查hash表示O(K)的】
 * <p>
 * 2、生成距离表
 *    从start出发【如果所给的表中没有这个start，将start加进去。】，宽度优先遍历
 *    距离表统一对start负责
 *
 * 3、收集答案
 *    在收集之前。看看表中是否有end字符串，没有直接返回 null
 *    从距离表中，查一下，start到end的距离是多少。假设是5
 *    从start开始，看一下当前字符串的所有邻居当中，哪些的距离是当前字符串的距离加1.只走向比自己距离大1 的哪些邻居。
 *    每个满足条件（当前距离加1）的支路都去走一遍。在走的时候，沿途收集字符。
 *    这样到终点的时候，距离刚好等于end的距离
 *    距离严格加1。其他的支路都不要【深度优先】
 *    也就是遍历的时候，每次距离都加1，肯定能到大end的
 *
 * @since 2022-04-06 22:35:37
 */
public class Code04_WordLadderII {

    public static List<List<String>> findLadders(String start, String end, List<String> list) {
        list.add(start);
        HashMap<String, List<String>> nexts = getNexts(list);
        HashMap<String, Integer> distances = getDistances(start, nexts);
        LinkedList<String> pathList = new LinkedList<>();
        List<List<String>> res = new ArrayList<>();
        getShortestPaths(start, end, nexts, distances, pathList, res);
        return res;
    }

    /**
     * 生成所有字符串的邻居表
     *
     * @since 2022-04-10 15:37:36
     */
    public static HashMap<String, List<String>> getNexts(List<String> words) {
        HashSet<String> dict = new HashSet<>(words);
        HashMap<String, List<String>> nexts = new HashMap<>();
        for (int i = 0; i < words.size(); i++) {
            nexts.put(words.get(i), getNext(words.get(i), dict));
        }
        return nexts;
    }

    /**
     * word, 在表中，有哪些邻居，把邻居们，生成list返回
     *
     * @since 2022-04-06 22:36:10
     */
    public static List<String> getNext(String word, HashSet<String> dict) {
        ArrayList<String> res = new ArrayList<>();
        char[] chs = word.toCharArray();
        for (char cur = 'a'; cur <= 'z'; cur++) {
            for (int i = 0; i < chs.length; i++) {
                if (chs[i] != cur) {
                    char tmp = chs[i];
                    chs[i] = cur;
                    if (dict.contains(String.valueOf(chs))) {
                        res.add(String.valueOf(chs));
                    }
                    chs[i] = tmp;
                }
            }
        }
        return res;
    }

    /**
     * 生成距离表，从start开始，根据邻居表，宽度优先遍历，对于能够遇到的所有字符串，生成(字符串，距离)这条记录，放入距离表中
     *
     * @since 2022-04-06 22:35:48
     */
    public static HashMap<String, Integer> getDistances(String start, HashMap<String, List<String>> nexts) {
        HashMap<String, Integer> distances = new HashMap<>();
        distances.put(start, 0);
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        HashSet<String> set = new HashSet<>();
        set.add(start);
        while (!queue.isEmpty()) {
            String cur = queue.poll();
            for (String next : nexts.get(cur)) {
                if (!set.contains(next)) {
                    distances.put(next, distances.get(cur) + 1);
                    queue.add(next);
                    set.add(next);
                }
            }
        }
        return distances;
    }


    /**
     * cur 当前来到的字符串 可变
     * to 目标，固定参数
     * nexts 每一个字符串的邻居表
     * cur 到开头距离5 -> 到开头距离是6的支路 distances距离表
     * path : 来到cur之前，深度优先遍历之前的历史是什么
     * res : 当遇到cur，把历史，放入res，作为一个结果
     *
     * @since 2022-04-06 22:36:02
     */
    public static void getShortestPaths(String cur, String to, HashMap<String, List<String>> nexts,
                                        HashMap<String, Integer> distances, LinkedList<String> path, List<List<String>> res) {
        path.add(cur);
        if (to.equals(cur)) {
            res.add(new LinkedList<>(path));
        } else {
            for (String next : nexts.get(cur)) {
                if (distances.get(next) == distances.get(cur) + 1) {
                    getShortestPaths(next, to, nexts, distances, path, res);
                }
            }
        }

        // 清理现场
        path.pollLast();
    }

}
