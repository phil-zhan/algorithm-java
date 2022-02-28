package org.study.system.class32;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Code04_AC2 {

	/**
	 * // 前缀树的节点
	 * @date 2021-10-05 16:43:16
	 */
	public static class Node {
		/**
		 * // 如果一个node，end为空，不是结尾
		 * // 如果end不为空，表示这个点是某个字符串的结尾，end的值就是这个字符串
		 *
		 * // 如果当前节点，确实是某个字符串的结尾，那么 end 就表示当前字符串
		 * // 如果当前节点，不是结尾节点，那么 end 就为空
		 * @date 2021-10-05 16:40:27
		 */
		public String end;


		// 只有在上面的end变量不为空的时候，endUse才有意义【也就是当前节点是尾结点的情况下，endUse才有意义】
		// 表示，之前 这个字符串 有没有加入过答案 【防止重复收集答案】
		public boolean endUse;

		/**
		 * fail 指针。详见见 readme.md
		 * @date 2021-10-05 16:43:05
		 */
		public Node fail;

		/**
		 * 从当前节点出去的节点结合【这里是利用字母的 ASCII 码来表示是第多少条路。如果是汉字的话。此法就不行了】
		 * 汉字可以考虑的结构： 【key:是该条路的字符。value：是该条路的下一个节点】
		 * HashMap<Character, Node> children=new HashMap<Character, Node>();
		 *
		 * @date 2021-10-05 16:43:27
		 */
		public Node[] nexts;

		public Node() {
			endUse = false;
			end = null;
			fail = null;
			nexts = new Node[26];
		}
	}

	public static class ACAutomation {
		private Node root;

		public ACAutomation() {
			root = new Node();
		}

		/**
		 * 构建前缀树
		 * @date 2021-10-05 16:46:58
		 */
		public void insert(String s) {
			char[] str = s.toCharArray();
			Node cur = root;
			int index = 0;
			for (int i = 0; i < str.length; i++) {

				// 利用字母的 ASCII 码值，表示是否存在第 a、b、c ... 条路径
				index = str[i] - 'a';
				if (cur.nexts[index] == null) {
					cur.nexts[index] = new Node();
				}
				cur = cur.nexts[index];
			}
			cur.end = s;
		}

		/**
		 * 构建 fail 指针体系
		 * @date 2021-10-05 16:46:34
		 */
		public void build() {

			// 考虑的是，遍历都某个节点的时候，去设置其所有子节点的 fail 指针。而不是遍历到设就去设置谁的 fail 指针
			// 因为子节点没有维护父节点的指针。子节点找不到父节点在哪
			Queue<Node> queue = new LinkedList<>();
			queue.add(root);
			Node cur = null;
			Node cfail = null;
			while (!queue.isEmpty()) {
				// 某个父亲，cur
				cur = queue.poll();
				for (int i = 0; i < 26; i++) { // 所有的路
					// cur -> 父亲  i号儿子，必须把i号儿子的fail指针设置好！
					if (cur.nexts[i] != null) { // 如果真的有i号儿子
						cur.nexts[i].fail = root; // 先将其设置为 root。找到有对应的fail，再更新
						cfail = cur.fail;
						while (cfail != null) {

							// 看看有没有 i 方向上的儿子
							if (cfail.nexts[i] != null) {
								cur.nexts[i].fail = cfail.nexts[i];
								break;
							}
							cfail = cfail.fail;
						}
						queue.add(cur.nexts[i]);
					}
				}
			}
		}

		// 大文章：content
		// 把大文章想象成一个长的链条，以每个字符开始去拧着这个链条，敏感字符以此往上靠，能匹配的都收集，避免重复收集，做一定的标识
		// 当前字符开头的收集完，大文章调整为下一个字符开始
		public List<String> containWords(String content) {
			char[] str = content.toCharArray();
			Node cur = root;
			Node follow = null;
			int index = 0;
			List<String> ans = new ArrayList<>();
			for (int i = 0; i < str.length; i++) {

				// 【某条路走不下去了。但是fail也回到了root。表示当前的字符开始已经收集完了，去下一个字符为起点收集】

				// 路
				index = str[i] - 'a';
				// 如果当前字符在这条路上没配出来。且 fail 指针又不是 root ，就随着fail方向走向下条路径
				while (cur.nexts[index] == null && cur != root) {
					cur = cur.fail;
				}

				// 执行到这里，意味着破坏了上面的 while 循环
				// 1) 现在来到的路径，是可以继续匹配的
				// 2) 现在来到的节点，就是前缀树的根节点
				cur = cur.nexts[index] != null ? cur.nexts[index] : root;

				// follow变量就是方便fail去收集答案
				follow = cur;
				while (follow != root) {
					if (follow.endUse) {
						break;
					}
					// 不同的需求，在这一段之间修改
					if (follow.end != null) {
						ans.add(follow.end);
						follow.endUse = true;
					}
					// 不同的需求，在这一段之间修改
					follow = follow.fail;
				}
			}
			return ans;
		}

	}

	public static void main(String[] args) {
		ACAutomation ac = new ACAutomation();
		ac.insert("dhe");
		ac.insert("he");
		ac.insert("abcdheks");
		ac.insert("sabcdhe");
		ac.insert("asldk");
		ac.insert("zxdfg");
		// 设置fail指针
		ac.build();

		List<String> contains = ac.containWords("abcdheksabcdheksabcdheksabcdheksabcdhekskdjfafhasldkflskdjhwqaeruv");
		for (String word : contains) {
			System.out.println(word);
		}
	}

}
