package org.study.coding.class32;

import java.util.ArrayList;
import java.util.List;

public class Problem_0163_MissingRanges {

	/**
	 * 这是应该付费题
	 * 给定一个有序数组和一个范围。返回缺失的字符串【也就是不在指定范围内的数字组成的范范围】
	 * 这个范围可以用数字的String类型。也可以用区间表示
	 * 如
	 * nums=[0,1,3,50,75]
	 * lower = 0
	 * upper = 99
	 *
	 * 返回
	 * ["2","4->49","51->74","79->99"]
	 *
	 *
	 *
	 * nums=[]
	 * lower = 1
	 * upper = 1
	 *
	 * 返回
	 * ["2"]
	 *
	 * @since 2022-04-18 21:56:12
	 */
	public static List<String> findMissingRanges(int[] nums, int lower, int upper) {
		List<String> ans = new ArrayList<>();

		// 遍历数组
		for (int num : nums) {

			// 当前数大于左边界。那么从左边界到当前的范围就是当前缺的范围【因为数组是有序的】
			if (num > lower) {
				ans.add(miss(lower, num - 1));
			}

			// 来到了上边界。范围已经填满了
			if (num == upper) {
				return ans;
			}

			// 调整当前的左边界
			lower = num + 1;
		}

		// 遍历完数组。lower都还没被推到upper。将剩下的范围收集
		if (lower <= upper) {
			ans.add(miss(lower, upper));
		}
		return ans;
	}

	/**
	 * 生成"lower->upper"的字符串，如果lower==upper，只用生成"lower"
	 *
	 * @since 2022-04-18 22:08:43
	 */
	public static String miss(int lower, int upper) {
		String left = String.valueOf(lower);
		String right = "";
		if (upper > lower) {
			right = "->" + String.valueOf(upper);
		}
		return left + right;
	}

}
