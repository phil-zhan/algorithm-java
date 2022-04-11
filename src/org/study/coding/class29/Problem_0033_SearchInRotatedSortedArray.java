package org.study.coding.class29;

public class Problem_0033_SearchInRotatedSortedArray {

	// nums，原本是有序数组，旋转过，而且左部分长度不知道
	// 找num
	// num所在的位置返回
	public static int search(int[] nums, int target) {
		int L = 0;
		int R = nums.length - 1;
		int M = 0;
		while (L <= R) {
			// M = L + ((R - L) >> 1)
			M = (L + R) / 2;
			if (nums[M] == target) {
				return M;
			}
			// nums[M] != target
			// [L] == [M] == [R] != target 无法二分
			if (nums[L] == nums[M] && nums[M] == nums[R]) {
				while (L != M && nums[L] == nums[M]) {
					L++;
				}
				// 1) L == M L...M 一路都相等
				// 2) 从L到M终于找到了一个不等的位置
				if (L == M) { // L...M 一路都相等
					L = M + 1;
					continue;
				}
			}
			// ...
			// nums[M] != target
			// [L] [M] [R] 不都一样的情况, 如何二分的逻辑
			if (nums[L] != nums[M]) {
				if (nums[M] > nums[L]) { // L...M 一定有序
					if (target >= nums[L] && target < nums[M]) { //  3  [L] == 1    [M]   = 5   L...M - 1
						R = M - 1;
					} else { // 9    [L] == 2    [M]   =  7   M... R
						L = M + 1;
					}
				} else { // [L] > [M]    L....M  存在断点
					if (target > nums[M] && target <= nums[R]) {
						L = M + 1;
					} else {
						R = M - 1;
					}
				}
			} else { /// [L] [M] [R] 不都一样，  [L] === [M] -> [M]!=[R]
				if (nums[M] < nums[R]) {
					if (target > nums[M] && target <= nums[R]) {
						L = M + 1;
					} else {
						R = M - 1;
					}
				} else {
					if (target >= nums[L] && target < nums[M]) {
						R = M - 1;
					} else {
						L = M + 1;
					}
				}
			}
		}
		return -1;
	}

}
