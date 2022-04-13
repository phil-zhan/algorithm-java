package org.study.coding.class29;

import java.util.Arrays;

/**
 * 56. 合并区间
 *
 * @since 2022-04-13 22:10:37
 */
public class Problem_0056_MergeIntervals {

	public static int[][] merge(int[][] intervals) {
		if (intervals.length == 0) {
			return new int[0][0];
		}

		// 根据开始位置排序
		Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

		// 开始位置
		int s = intervals[0][0];

		// 结束位置
		int e = intervals[0][1];

		// 有效的区间个数
		int size = 0;

		// 遍历
		for (int i = 1; i < intervals.length; i++) {

			// 合并
			if (intervals[i][0] > e) {
				intervals[size][0] = s;
				intervals[size++][1] = e;
				s = intervals[i][0];
				e = intervals[i][1];
			} else {

				// 当前的最大的结束位置
				e = Math.max(e, intervals[i][1]);
			}
		}

		// 将最后一个区间放进去
		intervals[size][0] = s;
		intervals[size++][1] = e;

		return Arrays.copyOf(intervals, size);
	}

}
