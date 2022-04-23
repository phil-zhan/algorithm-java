package org.study.coding.class34;

/**
 * 这是应该付费题
 * 给定一个字符串，求一个子串，子串的字符种类不能超过k，求这个子串的最大长度是多少
 *
 * 解法：
 * 利用窗口【】
 * 维持一个L...R 的窗口
 * 窗口的范围变大，字符的种类只会变多或不变。不可能变少。【窗口的大小和字符的种类存在单调性】
 * 开始的时候，窗口在0位置，往下扩
 * 当窗口里面的字符种类刚刚超过k时，抓一下这个窗口长度
 * 抓完只会，窗口的左边界往右扩。直到窗口内的字符种类小于k，
 * 再去考虑扩右边界
 *
 *
 *
 * 在这个过程中，维持一个map。
 * key：是窗口内的字符
 * value：是该字符对应的词频
 *
 * 右边界扩的时候，记得加加
 * 左边界扩的时候，记得减减
 *
 *
 * @author Dell
 * @since 2022-04-23 11:09:55
 */
public class Problem_0340_LongestSubstringWithAtMostKDistinctCharacters {

	public static int lengthOfLongestSubstringKDistinct(String s, int k) {
		if (s == null || s.length() == 0 || k < 1) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		int[] count = new int[256];

		// 窗口内的字符种类
		int diff = 0;
		int R = 0;
		int ans = 0;
		for (int i = 0; i < N; i++) {
			// R 窗口的右边界
			// diff < k 字符种类还不足k种
			// diff == k && count[str[R]] > 0  这个字符在窗口内出现过
			while (R < N && (diff < k || (diff == k && count[str[R]] > 0))) {
				diff += count[str[R]] == 0 ? 1 : 0;
				count[str[R++]]++;
			}

			// R 来到违规的第一个位置
			ans = Math.max(ans, R - i);
			diff -= count[str[i]] == 1 ? 1 : 0;

			// 既然是左边界的字符，词频就不会等于0。左边界在窗口内，之前遍历的时候，已经将其加入到词频表了
			count[str[i]]--;
		}
		return ans;
	}

}
