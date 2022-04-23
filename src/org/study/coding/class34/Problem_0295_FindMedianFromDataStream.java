package org.study.coding.class34;

import java.util.PriorityQueue;

/**
 * 295. 数据流的中位数
 * 中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
 *
 * 例如，
 *
 * [2,3,4] 的中位数是 3
 *
 * [2,3] 的中位数是 (2 + 3) / 2 = 2.5
 *
 * 设计一个支持以下两种操作的数据结构：
 *
 * void addNum(int num) - 从数据流中添加一个整数到数据结构中。
 * double findMedian() - 返回目前所有元素的中位数。
 * 示例：
 *
 * addNum(1)
 * addNum(2)
 * findMedian() -> 1.5
 * addNum(3)
 * findMedian() -> 2
 * 进阶:
 *
 * 如果数据流中所有整数都在 0 到 100 范围内，你将如何优化你的算法？
 * 如果数据流中 99% 的整数都在 0 到 100 范围内，你将如何优化你的算法？
 *
 *
 * 解法：
 * 如果数字不重复的话，就利用有序表来完成
 *
 * 这题是有重复数字的
 * 利用两个堆来完成。一个大根堆，一个小跟堆 【加入和弹出堆顶都是logN】【大跟堆承载小的一半数，小跟堆承载大的一半数】
 *
 * 每次有数字进来的时候
 * 如果两个堆都是空的。直接进大跟堆。
 * 如果当前数小于等于大跟堆的堆顶，就入大跟堆。
 * 如果当前数大于大跟堆的堆顶，就入小跟堆。
 *
 * 每次放完之后，检查大小【堆内元素的个数】
 * 如果大根堆和小跟堆的个数差值等于2，就让个数多的那个堆弹出堆顶，进入另外一个堆。
 *
 * 差值没有到达2，就不调整。
 *
 *
 *
 * 相当于从中间分开，较大的一半在大跟堆，较小的一半在小跟堆。【这里的大小跟堆，不带表示大的一半数和小的一半数。只代表其数字的排列方式】
 * 【添加完数字之后。小跟对里面装的是大的一半数字。大跟堆里面装的是小的一半数字】
 * 【两个堆的堆顶搞好就是中间的数】
 * 【两个堆的个数不一样，就去取个数多的那个堆的堆顶】
 * 【两个堆的个数一样，就去取两个堆的堆顶，求平均值】
 *
 *
 *
 * @since 2022-04-21 22:07:09
 */
public class Problem_0295_FindMedianFromDataStream {

	class MedianFinder {

		private PriorityQueue<Integer> maxh;
		private PriorityQueue<Integer> minh;

		public MedianFinder() {

			// 大小跟堆
			maxh = new PriorityQueue<>((a, b) -> b - a);
			minh = new PriorityQueue<>((a, b) -> a - b);
		}

		public void addNum(int num) {
			if (maxh.isEmpty() || maxh.peek() >= num) {
				maxh.add(num);
			} else {
				minh.add(num);
			}
			balance();
		}

		public double findMedian() {
			if (maxh.size() == minh.size()) {
				return (double) (maxh.peek() + minh.peek()) / 2;
			} else {
				return maxh.size() > minh.size() ? maxh.peek() : minh.peek();
			}
		}

		/**
		 * 调整平衡
		 *
		 * @since 2022-04-21 22:28:22
		 */
		private void balance() {
			if (Math.abs(maxh.size() - minh.size()) == 2) {
				if (maxh.size() > minh.size()) {
					minh.add(maxh.poll());
				} else {
					maxh.add(minh.poll());
				}
			}
		}

	}

}