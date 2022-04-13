package org.study.coding.class29;

/**
 * 66. 加一
 * 
 * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 *
 * 示例1：
 *
 * 输入：digits = [1,2,3]
 * 输出：[1,2,4]
 * 解释：输入数组表示数字 123。
 * 示例2：
 *
 * 输入：digits = [4,3,2,1]
 * 输出：[4,3,2,2]
 * 解释：输入数组表示数字 4321。
 * 示例 3：
 *
 * 输入：digits = [0]
 * 输出：[1]
 *
 *
 *
 *
 *
 * @since 2022-04-13 23:04:08
 */
public class Problem_0066_PlusOne {

	public static int[] plusOne(int[] digits) {
		int n = digits.length;
		for (int i = n - 1; i >= 0; i--) {

			// 当前位不是9
			if (digits[i] < 9) {
				digits[i]++;
				return digits;
			}

			// 当前位等于9
			digits[i] = 0;
		}

		// 原数组全是9。返回 [1000...000]
		int[] ans = new int[n + 1];

		ans[0] = 1;
		return ans;
	}

}
