package org.study.coding.class33;

/**
 * 【常考题】
 * 242. 有效的字母异位词
 * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
 * 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
 *
 *
 *
 * 示例 1:
 * 输入: s = "anagram", t = "nagaram"
 * 输出: true
 *
 * 示例 2:
 * 输入: s = "rat", t = "car"
 * 输出: false
 *
 * 解法：
 * 欠债表
 *
 *
 * @since 2022-04-20 22:07:07
 */
public class Problem_0242_ValidAnagram {

	public static boolean isAnagram(String s, String t) {
		if (s.length() != t.length()) {
			return false;
		}
		char[] str1 = s.toCharArray();
		char[] str2 = t.toCharArray();
		int[] count = new int[256];
		for (char cha : str1) {
			count[cha]++;
		}
		for (char cha : str2) {

			// 当前字符在str1中没有出现过，一上来就会减到0
			// 当前字符比str1的字符个数少，就会在当前字符的某个位置减到0
			// 当前字符出现的个数比str1多。长度又是相等的。那么除了当前字符外，总会有一个字符会减到小于0的时候
			if (--count[cha] < 0) {
				return false;
			}
		}
		return true;
	}

}
