package org.study.coding.class35;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
 *
 *
 * 示例 1:
 *
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 * 示例 2:
 *
 * 输入: nums = [1], k = 1
 * 输出: [1]
 *
 * 题意：给出的参数一定没有并列的
 *
 * 解法：
 * 利用门槛堆
 * 做出一个词频表
 * 再准备一个堆【按照词频组织的小跟堆】。
 * 遍历词频表
 * 堆里面始终只维护k个元素。
 * 当新来的元素的词频高于堆顶元素的词频时。就将新来的元素加入堆
 * 【堆里面的元素不足k个时，也直接入堆】
 * 同时注意检查。堆里面只维护k个元素。如果加入之后，导致堆里面的元素个数多余k个。就将对顶元素移除
 *
 * 
 * @since 2022-04-24 07:43:26
 */
public class Problem_0347_TopKFrequentElements {

	public static class Node {
		public int num;
		public int count;

		public Node(int k) {
			num = k;
			count = 1;
		}
	}

	public static class CountComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.count - o2.count;
		}

	}

	public static int[] topKFrequent(int[] nums, int k) {
		HashMap<Integer, Node> map = new HashMap<>();
		for (int num : nums) {
			if (!map.containsKey(num)) {
				map.put(num, new Node(num));
			} else {
				map.get(num).count++;
			}
		}
		PriorityQueue<Node> heap = new PriorityQueue<>(new CountComparator());
		for (Node node : map.values()) {
			if (heap.size() < k || (heap.size() == k && node.count > heap.peek().count)) {
				heap.add(node);
			}
			if (heap.size() > k) {
				heap.poll();
			}
		}
		int[] ans = new int[k];
		int index = 0;
		while (!heap.isEmpty()) {
			ans[index++] = heap.poll().num;
		}
		return ans;
	}

}
