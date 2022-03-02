package org.study.coding.class03;

// 本题测试链接 : https://leetcode.com/problems/longest-substring-without-repeating-characters/
public class Code01_LongestSubstringWithoutRepeatingCharacters {

	/**
	 * 考虑以每个位置结尾的情况，前面能推多久能不重复
	 * 1)在从左往右的过程中，记录每个字符最后一次出现的位置。用于帮助后面的加速
	 *
	 * 2)记录前一个字符能往左推的最大距离。当前位置能推的最大距离，不能超过前一个字符能推的最大距离
	 *
	 * 考虑这两个因素能推出来的最大距离，取最小值就OK
	 *
	 * 动态规划
	 * @since 2022-03-02 07:58:12
	 */
	public static int lengthOfLongestSubstring(String s) {
		if (s == null || s.equals("")) {
			return 0;
		}
		char[] str = s.toCharArray();

		// ASCII 码只有0-255
		// map[i] = v   表示 i 这个字符上次出现在 v位置
		int[] map = new int[256];

		// 默认所有字符都在 -1 位置出现过
		for (int i = 0; i < 256; i++) {
			map[i] = -1;
		}


		map[str[0]] = 0;
		int N = str.length;
		int ans = 1;
		int pre = 1;
		for (int i = 1; i < N; i++) {
			pre = Math.min(i - map[str[i]], pre + 1);
			ans = Math.max(ans, pre);
			map[str[i]] = i;
		}
		return ans;
	}

}
