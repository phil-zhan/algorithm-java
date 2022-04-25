package org.study.coding.class35;

/**
 * 来自网易
 * 给定一个正数数组arr，表示每个小朋友的得分
 * 任何两个相邻的小朋友，如果得分一样，怎么分糖果无所谓，但如果得分不一样，分数大的一定要比分数少的多拿一些糖果
 * 假设所有的小朋友坐成一个环形，返回在不破坏上一条规则的情况下，需要的最少糖果数
 *
 * 解法：
 * 找到一个坡顶。考察其左右两边的坡度。
 * 当前坡顶的糖数根据坡度大的决定。
 * 坡度大的一边。从坡底到坡顶，分别是1、2、3、4、、、 依次递增到坡顶。
 * 坡度较低的一边，从坡底到坡顶，1、2、3、4、、、、、 。不要到坡顶。到坡顶的前一个位置
 *
 *
 *
 * 遍历数组。
 * 生成左坡度
 * 如果当前位置大于前面一个位置。当前位置的糖数就要在前面的位置的糖数上加1.
 * 如果当前位置小于前面一个位置。当前位置的糖数就归1.
 * 如果当前位置等于前面一个位置。当前位置的糖数就等于前面一个位置的糖数.
 *
 * 生成右坡度
 * 如果当前位置大于右边一个位置。当前位置的糖数就要在右边的位置的糖数上加1.
 * 如果当前位置小于右边一个位置。当前位置的糖数就归1.
 * 如果当前位置等于右边一个位置。当前位置的糖数就等于右边一个位置的糖数.
 *
 *
 * 然后再去遍历。来到i位置的时候，当前位置的糖数，就是左坡和右坡取一个最大值。【最后再累加】
 *
 *
 *
 * 本题是一个进阶问题；
 * 数组是一个环形
 *
 *
 * 就算是环形数组，也一定存在价值洼地。
 * 找到这个价值洼地。从价值洼地切开。切开后，首尾都是洼地这个数。
 * 也就是之前的n个数。切开后就变成了 n+1 个数
 *
 * 用切开后的数组去做就行。【价值洼地是不会影响其他位置的。价值洼地的糖数肯定是1】
 *
 *
 * 需要注意的是，最后一个位置的糖数不要累加上来。会重复
 *
 *
 *
 *
 * @since 2022-04-25 22:04:03
 */
public class Code05_CircleCandy {

	public static int minCandy(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return 1;
		}
		int n = arr.length;
		int minIndex = 0;
		for (int i = 0; i < n; i++) {
			if (arr[i] <= arr[lastIndex(i, n)] && arr[i] <= arr[nextIndex(i, n)]) {
				minIndex = i;
				break;
			}
		}
		int[] nums = new int[n + 1];
		for (int i = 0; i <= n; i++, minIndex = nextIndex(minIndex, n)) {
			nums[i] = arr[minIndex];
		}
		int[] left = new int[n + 1];
		left[0] = 1;
		for (int i = 1; i <= n; i++) {
			left[i] = nums[i] > nums[i - 1] ? (left[i - 1] + 1) : 1;
		}
		int[] right = new int[n + 1];
		right[n] = 1;
		for (int i = n - 1; i >= 0; i--) {
			right[i] = nums[i] > nums[i + 1] ? (right[i + 1] + 1) : 1;
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			ans += Math.max(left[i], right[i]);
		}
		return ans;
	}

	public static int nextIndex(int i, int n) {
		return i == n - 1 ? 0 : (i + 1);
	}

	public static int lastIndex(int i, int n) {
		return i == 0 ? (n - 1) : (i - 1);
	}

	public static void main(String[] args) {
		int[] arr = { 3, 4, 2, 3, 2 };
		System.out.println(minCandy(arr));
	}

}
