package org.study.coding.class38;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * https://leetcode.cn/problems/find-all-anagrams-in-a-string/
 *
 * 窗口加欠债表
 * 1、保证窗口长度等于目标长度
 * 2、注意欠债和还款是否有效
 * 		右边界向右扩时。如果对应字符欠债个数大于0，就是有效的还债。否则不是有效的还债
 * 		左边界向右扩时，如果对应字符大于等于0，就是有效的欠债。否则不是有效的欠债。
 *
 * 当总欠债为0时，就找到了一个，收集答案
 *
 * @since 2022-06-02 20:25:15
 */
public class Problem_0438_FindAllAnagramsInAString {

	public static List<Integer> findAnagrams(String s, String p) {
		List<Integer> ans = new ArrayList<>();
		if (s == null || p == null || s.length() < p.length()) {
			return ans;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		char[] pst = p.toCharArray();
		int M = pst.length;

		// 欠债表
		HashMap<Character, Integer> map = new HashMap<>();
		for (char cha : pst) {
			if (!map.containsKey(cha)) {
				map.put(cha, 1);
			} else {
				map.put(cha, map.get(cha) + 1);
			}
		}

		// 形成初始窗口
		int all = M;
		for (int end = 0; end < M - 1; end++) {
			if (map.containsKey(str[end])) {
				int count = map.get(str[end]);
				if (count > 0) {
					all--;
				}
				map.put(str[end], count - 1);
			}
		}

		// 加一个吐一个
		for (int end = M - 1, start = 0; end < N; end++, start++) {
			if (map.containsKey(str[end])) {
				int count = map.get(str[end]);
				if (count > 0) {
					all--;
				}
				map.put(str[end], count - 1);
			}
			if (all == 0) {
				ans.add(start);
			}
			if (map.containsKey(str[start])) {
				int count = map.get(str[start]);
				if (count >= 0) {
					all++;
				}
				map.put(str[start], count + 1);
			}
		}
		return ans;
	}

}
