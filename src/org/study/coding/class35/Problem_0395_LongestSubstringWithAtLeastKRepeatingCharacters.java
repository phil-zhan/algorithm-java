package org.study.coding.class35;

/**
 * 395. 至少有 K 个重复字符的最长子串
 * 给你一个字符串 s 和一个整数 k ，请你找出 s 中的最长子串， 要求该子串中的每一字符出现次数都不少于 k 。返回这一子串的长度。
 *
 *
 *
 * 示例 1：
 *
 * 输入：s = "aaabb", k = 3
 * 输出：3
 * 解释：最长子串为 "aaa" ，其中 'a' 重复了 3 次。
 * 示例 2：
 *
 * 输入：s = "ababbc", k = 2
 * 输出：5
 * 解释：最长子串为 "ababb" ，其中 'a' 重复了 2 次， 'b' 重复了 3 次。
 *
 *
 * 提示：
 *
 * 1 <= s.length <= 104
 * s 仅由小写英文字母组成
 * 1 <= k <= 105
 *
 *
 * 解法：
 * 字符串只包含26种英文字符
 * 我们考虑子串必须只包含1种字符，且每种字符至少出现k次的有多长
 * 我们考虑子串必须只包含2种字符，且每种字符至少出现k次的有多长
 * 我们考虑子串必须只包含3种字符，且每种字符至少出现k次的有多长
 * .....
 * 我们考虑子串必须只包含26种字符，且每种字符至少出现k次的有多长
 *
 * 所有答案中，抓一个最长的，就是答案。
 *
 *
 * @since 2022-04-24 07:57:48
 */
public class Problem_0395_LongestSubstringWithAtLeastKRepeatingCharacters {

	public static int longestSubstring1(String s, int k) {
		char[] str = s.toCharArray();
		int N = str.length;
		int max = 0;
		for (int i = 0; i < N; i++) {
			int[] count = new int[256];
			int collect = 0;
			int satisfy = 0;
			for (int j = i; j < N; j++) {
				if (count[str[j]] == 0) {
					collect++;
				}
				if (count[str[j]] == k - 1) {
					satisfy++;
				}
				count[str[j]]++;
				if (collect == satisfy) {
					max = Math.max(max, j - i + 1);
				}
			}
		}
		return max;
	}

	public static int longestSubstring2(String s, int k) {
		char[] str = s.toCharArray();
		int N = str.length;
		int max = 0;
		for (int require = 1; require <= 26; require++) {
			// 3种
			// a~z 出现次数
			int[] count = new int[26];
			// 目前窗口内收集了几种字符了
			int collect = 0;
			// 目前窗口内出现次数>=k次的字符，满足了几种
			int satisfy = 0;

			// 窗口右边界
			int R = -1;

			// L要尝试每一个窗口的最左位置
			for (int L = 0; L < N; L++) {
				// [L..R] R+1
				// 可以向右扩。当前字符没要达到要求的字符种数，就一直往右扩
				while (R + 1 < N && !(collect == require && count[str[R + 1] - 'a'] == 0)) {
					R++;
					if (count[str[R] - 'a'] == 0) {
						collect++;
					}
					if (count[str[R] - 'a'] == k - 1) {
						satisfy++;
					}
					count[str[R] - 'a']++;
				}


				// [L...R]
				if (satisfy == require) {
					max = Math.max(max, R - L + 1);
				}


				// L++
				if (count[str[L] - 'a'] == 1) {
					collect--;
				}
				if (count[str[L] - 'a'] == k) {
					satisfy--;
				}

				// 左边界字符出去了
				count[str[L] - 'a']--;
			}
		}
		return max;
	}

	// 会超时，但是思路的确是正确的
	public static int longestSubstring3(String s, int k) {
		return process(s.toCharArray(), 0, s.length() - 1, k);
	}

	public static int process(char[] str, int L, int R, int k) {
		if (L > R) {
			return 0;
		}
		int[] counts = new int[26];
		for (int i = L; i <= R; i++) {
			counts[str[i] - 'a']++;
		}
		char few = 0;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < 26; i++) {
			if (counts[i] != 0 && min > counts[i]) {
				few = (char) (i + 'a');
				min = counts[i];
			}
		}
		if (min >= k) {
			return R - L + 1;
		}
		int pre = 0;
		int max = Integer.MIN_VALUE;
		for (int i = L; i <= R; i++) {
			if (str[i] == few) {
				max = Math.max(max, process(str, pre, i - 1, k));
				pre = i + 1;
			}
		}
		if (pre != R + 1) {
			max = Math.max(max, process(str, pre, R, k));
		}
		return max;
	}

}
