package org.study.coding.class28;

/**
 * 删除有序数组中的重复项
 * 在数组的后面设置一个无效区
 * 遍历数组，如果当前数和自己的前一个一样，就将其和无效区的前一个做交换 index 不变
 * 如果当前数和自己的前一个不一样 index++
 * 最后返回有效区的长度
 *
 * 当然本题的思路是，设置有效区的index【因为对返回的无小区是什么都无所谓】
 * 从1开始遍历，和当前有效区的最右侧位置的数一样，就直接跳过，不一样就将其拷贝到有效区的下一个位置
 *
 * @since 2022-04-12 07:24:55
 */
public class Problem_0026_RemoveDuplicatesFromSortedArray {

	public static int removeDuplicates(int[] nums) {
		if (nums == null) {
			return 0;
		}
		if (nums.length < 2) {
			return nums.length;
		}
		int done = 0;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] != nums[done]) {
				nums[++done] = nums[i];
			}
		}
		return done + 1;
	}

}
