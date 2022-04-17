package org.study.coding.class31;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 127. 单词接龙
 *
 * 字典wordList 中从单词 beginWord和 endWord 的 转换序列 是一个按下述规格形成的序列beginWord -> s1-> s2-> ... -> sk：
 * 每一对相邻的单词只差一个字母。
 * 对于1 <= i <= k时，每个si都在wordList中。注意， beginWord不需要在wordList中。
 * sk== endWord
 * 给你两个单词 beginWord和 endWord 和一个字典 wordList ，返回 从beginWord 到endWord 的 最短转换序列 中的 单词数目 。如果不存在这样的转换序列，返回 0 。
 *
 *
 * 示例 1：
 * 输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
 * 输出：5
 * 解释：一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog", 返回它的长度 5。
 *
 * 示例 2：
 * 输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
 * 输出：0
 * 解释：endWord "cog" 不在字典中，所以无法进行转换。
 *
 * 解法：
 *    本题即涉及到宽度优先遍历，也涉及到深度优先遍历。还有递归函数的设计。
 * <p>
 * 1、做出所给字符串列表的邻接表hashmap。【可以想想成一张图】
 *    key：所给列表里面的字符串
 *    value：是一个list列表。表示所给的字符串列表里面，有哪些是当前key，只改变一个字符就能到达的
 *    该过程就牵涉到优化。当然可以在每个字符串的时候都去遍历。但是太慢了
 *    本题有一个限制。就是所有的字符都只是小写字母（26种）
 * 优化：
 *    在来到某个字符串的时候。考虑当前字符串。将第一个字符变换成其他25种字符，看看谁在表里有，有的就是自己的邻居。
 *    接着去考虑第二个字符、第三个字符，以此类推【穷举邻居的所有可能】【O(26*N*k^2)】
 *    【字符串查hash表示O(K)的】
 * <p>
 * 2、生成距离表
 *    从start出发【如果所给的表中没有这个start，将start加进去。】，宽度优先遍历
 *    距离表统一对start负责
 *    本题也可以考虑是对end负责。就看start和end谁的邻居更少。
 *    从end开始的话，搜到start的时候就停止
 *
 *
 * 3、当遍历到end的时候，就能将当前的距离返回了
 *
 *
 * @since 2022-04-16 14:40:34
 */
public class Problem_0127_WordLadder {


    /**
     * start，出发的单词
     * to, 目标单位
     * list, 列表
     * to 一定属于list
     * start未必
     * 返回变幻的最短路径长度
     *
     *
     * @since 2022-04-16 15:06:12
     */
	public static int ladderLength1(String start, String to, List<String> list) {

		// 先将start放在list里面去。有它没他都一样
		list.add(start);

		// 邻接表
		// key : 列表中的单词，每一个单词都会有记录！
		// value : key这个单词，有哪些邻居！
		HashMap<String, ArrayList<String>> nexts = getNexts(list);

		// 距离表
		// abc  出发     abc  -> abc  0
		//
		// bbc  1
		HashMap<String, Integer> distanceMap = new HashMap<>();

		// 本题要求的是返回变化的路径上有多少个字符串。而不是需要多少步转化。
		distanceMap.put(start, 1);

		// 不要重复遍历
		HashSet<String> set = new HashSet<>();
		set.add(start);

		// 实现宽度优先遍历
		Queue<String> queue = new LinkedList<>();

		queue.add(start);
		while (!queue.isEmpty()) {
			String cur = queue.poll();
			Integer distance = distanceMap.get(cur);
			for (String next : nexts.get(cur)) {
				if (next.equals(to)) {
					return distance + 1;
				}
				if (!set.contains(next)) {
					set.add(next);
					queue.add(next);
					distanceMap.put(next, distance + 1);
				}
			}

		}
		return 0;
	}

	/**
	 * 到每个词的时候，都可以将剩下的单词都遍历一遍。看看和当前的单词是不是只相差一个字符。【复杂度会更高】
	 * 这里做一个优化
	 * 优化：
	 *    在来到某个字符串的时候。考虑当前字符串。将第一个字符变换成其他25种字符，看看谁在表里有，有的就是自己的邻居。
	 *    接着去考虑第二个字符、第三个字符，以此类推【穷举邻居的所有可能】【O(26*N*k^2)】
	 *    【字符串查hash表示O(K)的】
	 *
	 *
	 * @since 2022-04-17 10:40:27
	 */
	public static HashMap<String, ArrayList<String>> getNexts(List<String> words) {
		HashSet<String> dict = new HashSet<>(words);
		HashMap<String, ArrayList<String>> nexts = new HashMap<>();
		for (int i = 0; i < words.size(); i++) {
			nexts.put(words.get(i), getNext(words.get(i), dict));
		}
		return nexts;
	}


    /**
     * 应该根据具体数据状况决定用什么来找邻居
     * 1)如果字符串长度比较短，字符串数量比较多，以下方法适合
     * 2)如果字符串长度比较长，字符串数量比较少，以下方法不适合
     *
     *
     * @since 2022-04-16 15:06:31
     */
	public static ArrayList<String> getNext(String word, HashSet<String> dict) {
		ArrayList<String> res = new ArrayList<>();
		char[] chs = word.toCharArray();

		// 考虑每一个字符
		for (int i = 0; i < chs.length; i++) {

			// 当前字符的变化范围【除了当前字符自己外，其他的都可以考虑】
			for (char cur = 'a'; cur <= 'z'; cur++) {
				if (chs[i] != cur) {
					char tmp = chs[i];
					chs[i] = cur;
					if (dict.contains(String.valueOf(chs))) {
						res.add(String.valueOf(chs));
					}

					// 恢复现场
					chs[i] = tmp;
				}
			}
		}
		return res;
	}

	/**
	 * 这是两头往中间考的写法
	 *
	 * @since 2022-04-17 10:54:03
	 */
	public static int ladderLength2(String beginWord, String endWord, List<String> wordList) {
		HashSet<String> dict = new HashSet<>(wordList);
		if (!dict.contains(endWord)) {
			return 0;
		}
		// start这边所有发散出来的，都入 startSet
		HashSet<String> startSet = new HashSet<>();

		// end这边所有发散出来的，都入 endSet
		HashSet<String> endSet = new HashSet<>();

		// 左右两边，只要访问过的，都入 visit
		HashSet<String> visit = new HashSet<>();
		startSet.add(beginWord);
		endSet.add(endWord);

		for (int len = 2; !startSet.isEmpty(); len++) {
			// 默认startSet是较小的，endSet是较大的。方便好写程序

			// 这个 nextSet 是在当前 startSet和endSet中比较小的那个，所发散出来的所有结果
			HashSet<String> nextSet = new HashSet<>();

			// 遍历较小的那个集合。
			for (String w : startSet) {
				// 【集合中的每个字符串,又去遍历每一个字符的位置，再去考虑每个元素的变化范围a-z】
				// w -> a(nextSet)
				// a b c
				// 0		【0位置被改变的哪些】
				//   1		【1位置被改变的哪些】
				//     2	【2位置被改变的哪些】
				for (int j = 0; j < w.length(); j++) {
					char[] ch = w.toCharArray();

					// 再去考虑每个元素的变化范围a-z
					for (char c = 'a'; c <= 'z'; c++) {

						// 当前字符和自己一样，没有变化，直接跳过
						if (c != w.charAt(j)) {
							// char tmp = ch[j] ;

							ch[j] = c;
							String next = String.valueOf(ch);

							// 找到了endSet发散出来的。就直接返回
							if (endSet.contains(next)) {

								// 这里可以直接返回 len。因为每次都是从左右两边脚下的一边开始发散的。也就是不管是从左边还是右边，这个len都会加加
								return len;
							}

							// 没有找到，将其添加到nextSet中，方便下一轮开始的时候做重定位
							if (dict.contains(next) && !visit.contains(next)) {
								nextSet.add(next);
								visit.add(next);
							}
							// 恢复现场，这里可以没有【因为每次用的都是新的字符数组。当然，也可以改成每次都用一个数组。然后考虑恢复现场】
							// ch[j] = tmp;
						}
					}
				}
			}
			// 重定位【nextSet(是当前轮中，较小的那个发散出来的集合)】
			// 下一轮开始的时候，进行重新定位
			// startSet(小) -> nextSet(某个大小)   和 endSet大小来比
			startSet = (nextSet.size() < endSet.size()) ? nextSet : endSet;
			endSet = (startSet == nextSet) ? endSet : nextSet;
		}
		return 0;
	}

}
