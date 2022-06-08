package org.study.coding.class38;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/partition-labels/
 *
 * 做一张表
 * key：是字符
 * value：该字符在字符串中最靠右的位置
 *
 * 遍历字符串。
 * 用一个变量，记录当前块的最右边界。也就是当前块在哪切2
 *
 * 遇到每一个字符，都看看当前字符的最右边界与当前块的最右边界做比较。
 * 如果字符的左右边界大于块的右边界。就更新块的右边界
 *
 * 如果遍历的位置超过了当前块的右边界。那么就切出一块。准备进入下一块。 下一块的右边界为刚超过的那个字符的右边界
 *
 * @since 2022-06-02 23:01:20
 */
public class Problem_0763_PartitionLabels {

	public static List<Integer> partitionLabels(String S) {
		char[] str = S.toCharArray();
		int[] far = new int[26];
		for (int i = 0; i < str.length; i++) {
			far[str[i] - 'a'] = i;
		}
		List<Integer> ans = new ArrayList<>();
		int left = 0;
		int right = far[str[0] - 'a'];
		for (int i = 1; i < str.length; i++) {
			if (i > right) {
				ans.add(right - left + 1);
				left = i;
			}
			right = Math.max(right, far[str[i] - 'a']);
		}
		ans.add(right - left + 1);
		return ans;
	}

}
