package org.study.coding.class34;

/**
 * 287. 寻找重复数
 * 给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。
 * 假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
 * 你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
 *
 *
 *
 * 示例 1：
 * 输入：nums = [1,3,4,2,2]
 * 输出：2
 *
 * 示例 2：
 * 输入：nums = [3,1,3,4,2]
 * 输出：3
 *
 * 解法：
 * 考虑快慢指针
 * 遍历数组
 * 来到i位置。如果i位置上面的数是num[i]=x。那么就去找x位置的数 num[x]。如果x位置就输就是x .即num[x] == x 。那么x就是重复的数
 * 如果x位置上的不是x。 即 num[x] != x
 * 那么当前指针 i 就跳到 num[i] 的位置。也就是当前位置上的数是啥，就去找对应的下标
 * 如果对应的下标上的数和当前的数一样，那么就找到了重复值
 * 如果不一样。就跳到下标为  当前位置表示的数 的下标。也就是将当前数作为下标，再跳一次
 *
 *
 * 考虑快慢指针
 * 相当于单链表中找到第一个如环节点。
 * 将数组的值看成是下一个节点的下标值
 *
 * @since 2022-04-21 21:12:49
 */
public class Problem_0287_FindTheDuplicateNumber {

	public static int findDuplicate(int[] nums) {
		if (nums == null || nums.length < 2) {
			return -1;
		}
		int slow = nums[0];
		int fast = nums[nums[0]];
		while (slow != fast) {
			// 慢指针一次跳一步
			slow = nums[slow];

			// 快指针一次跳两步
			fast = nums[nums[fast]];
		}

		// 相遇的时候，快指针归0.然后快慢都一次跳一步。他们再次相遇的时候。就是第一个入环节点。也就是数组中的重复值
		fast = 0;
		while (slow != fast) {
			fast = nums[fast];
			slow = nums[slow];
		}
		return slow;
	}

}
