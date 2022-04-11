package org.study.coding.class26;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * 本题测试链接 : https://leetcode.com/problems/word-search-ii/
 * 2. 给定一个m x n 二维字符网格board和一个单词（字符串）列表 words，找出所有同时在二维网格和字典中出现的单词
 *    单词必须按照字母顺序，通过 相邻的单元格 内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格
 *    同一个单元格内的字母在一个单词中不允许被重复使用。
 * 题意：
 * 在字符矩阵中，看看哪些单词能走出来，能走出来的收集答案并返回。
 *
 * 解法：
 * 大思路是考虑从 i、j 位置出发，能走出哪些单词来。每个点都走一遍
 *
 * 将所有的单词建成一个前缀树。
 * 遍历二维矩阵，在来到某个位置时，以该位置为起点，去检查前缀树。如果前缀树的头压根不等于当前字符的字符。直接跳过。
 * 之后是考虑当前位置的上下左右。如果前缀树上有对应的路，就去递归处理。没有对应的路，直接放弃
 *
 * 本题的亮点在于剪枝，优化常数时间
 *
 * @since 2022-04-06 22:33:39
 */
public class Code02_WordSearchII {



	/**
	 * 最优解
	 * @since 2022-04-07 21:22:44
	 */
	public static List<String> findWords(char[][] board, String[] words) {

		// 前缀树最顶端的头【前缀树记得去重】
		TrieNode head = new TrieNode();
		HashSet<String> set = new HashSet<>();
		for (String word : words) {
			if (!set.contains(word)) {
				fillWord(head, word);
				set.add(word);
			}
		}

		// 答案
		List<String> ans = new ArrayList<>();
		// 沿途走过的字符，收集起来，存在path里
		LinkedList<Character> path = new LinkedList<>();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				// 枚举在board中的所有位置
				// 每一个位置出发的情况下，答案都收集
				process(board, row, col, path, head, ans);
			}
		}
		return ans;
	}


	/**
	 * 从board[row][col]位置的字符出发，
	 * 之前的路径上，走过的字符，记录在path里
	 * cur还没有登上，有待检查能不能登上去的前缀树的节点
	 * 如果找到words中的某个str，就记录在 res里
	 * 返回值，从row,col 出发，一共找到了多少个str
	 *
	 * @since 2022-04-06 22:34:11
	 */
	public static int process(
			char[][] board, int row, int col, 
			LinkedList<Character> path, TrieNode cur,
			List<String> res) {
		char cha = board[row][col];

		// 这个row col位置是之前走过的位置【本题的设定是不能邹回头路。也就是一次深度遍历，不能形成环。】
		if (cha == 0) {
			return 0;
		}
		// (row,col) 不是回头路 cha 有效

		int index = cha - 'a';
		// 如果没路，或者这条路上最终的字符串之前加入过结果里
		// pass 是用来剪枝的、加速
		// 表示在前缀树中，当前节点有多少个字符串穿过自己。
		// 每次收集到答案之后，将沿途的pass减减。下次再来到当前节点的时候，如果对应的pass为0.表示该条支路的所有答案都收集完了
		if (cur.nexts[index] == null || cur.nexts[index].pass == 0) {
			return 0;
		}
		// 没有走回头路且能登上去
		cur = cur.nexts[index];

		// 当前位置的字符加到路径里去
		path.addLast(cha);

		// 从row和col位置出发，后续一共搞定了多少答案
		int fix = 0;
		// 当我来到row col位置，如果决定不往后走了。是不是已经搞定了某个字符串了
		if (cur.end) {
			res.add(generatePath(path));

			// 收集过之后，就将对应的end标志改为false。下次不要重复收集
			cur.end = false;
			fix++;
		}
		// 往上、下、左、右，四个方向尝试
		board[row][col] = 0;
		if (row > 0) {
			fix += process(board, row - 1, col, path, cur, res);
		}
		if (row < board.length - 1) {
			fix += process(board, row + 1, col, path, cur, res);
		}
		if (col > 0) {
			fix += process(board, row, col - 1, path, cur, res);
		}
		if (col < board[0].length - 1) {
			fix += process(board, row, col + 1, path, cur, res);
		}
		// 恢复现场。不影响上层的遍历
		board[row][col] = cha;
		path.pollLast();

		cur.pass -= fix;
		return fix;
	}


	public static void fillWord(TrieNode head, String word) {
		head.pass++;
		char[] chs = word.toCharArray();
		int index = 0;
		TrieNode node = head;
		for (int i = 0; i < chs.length; i++) {
			index = chs[i] - 'a';
			if (node.nexts[index] == null) {
				node.nexts[index] = new TrieNode();
			}
			node = node.nexts[index];
			node.pass++;
		}
		node.end = true;
	}

	public static String generatePath(LinkedList<Character> path) {
		char[] str = new char[path.size()];
		int index = 0;
		for (Character cha : path) {
			str[index++] = cha;
		}
		return String.valueOf(str);
	}


	public static class TrieNode {
		public TrieNode[] nexts;
		public int pass;
		public boolean end;

		public TrieNode() {
			nexts = new TrieNode[26];
			pass = 0;
			end = false;
		}

	}
}
