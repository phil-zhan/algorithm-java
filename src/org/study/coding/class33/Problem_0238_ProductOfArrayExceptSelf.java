package org.study.coding.class33;

/**
 * 238. 除自身以外数组的乘积
 * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
 * 题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
 * 请不要使用除法，且在 O(n) 时间复杂度内完成此题。
 *
 *
 * 示例 1:
 * 输入: nums = [1,2,3,4]
 * 输出: [24,12,8,6]
 *
 * 示例 2:
 * 输入: nums = [-1,1,0,-3,3]
 * 输出: [0,0,9,0,0]
 *
 * 题意：
 * 返回一个结果数组。结果数组中的每一个元素，都是原始数组中，除了自己这一位，其他的元素都乘起来的结果。要求不能用除法
 * 额外空间复杂度O(1) 【要返回的数组，不算是额外空间】
 *
 * 解法：
 * 借用一下ans空间。
 * 从右往左生成。ans[i] = ans[i]*ans[i+1]*...*ans[n-1]
 *
 * 再反过来。从左到右
 * 在从左往右的过程中，用一个变量x，记录 0...i-1 的乘积
 * ans[i] = x * ans[i+1]
 * 此时的 ans[i+1] 是 i+1 及其之后的乘积
 *
 *
 * @since 2022-04-20 21:38:30
 */
public class Problem_0238_ProductOfArrayExceptSelf {

	public int[] productExceptSelf(int[] nums) {
		int n = nums.length;
		int[] ans = new int[n];
		ans[0] = nums[0];
		for (int i = 1; i < n; i++) {
			ans[i] = ans[i - 1] * nums[i];
		}
		int right = 1;
		for (int i = n - 1; i > 0; i--) {
			ans[i] = ans[i - 1] * right;
			right *= nums[i];
		}
		ans[0] = right;
		return ans;
	}

	// 扩展 : 如果仅仅是不能用除号，把结果直接填在nums里呢？【也就是ans数组也不用】
	// 解法：数一共几个0；每一个位得到结果就是，a / b，位运算替代 /，之前的课讲过（新手班）【org.study.primary.class05】

	// 先遍历一遍数组。在遍历的过程中
	// 1)统计有多少个0
	// 2)所有不是0的数乘在一起。假设等于a

	// 情况一：没有0【假设0位置是b，那么0位置就应该是a除以b.】【题目只说不能用除号。没说不能用除法】【用位运算替代除法】
	// 情况二：有一个0【除了那个0的位置，是其余的数乘起来，其他位置全是0】【再遍历一遍，把数填完就了】
	// 情况三：有大于等于2个0【所有位置全是0】【再遍历一遍，把所有位置全变成0】

}
