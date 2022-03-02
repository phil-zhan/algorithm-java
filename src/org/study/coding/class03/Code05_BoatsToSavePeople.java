package org.study.coding.class03;

import java.util.Arrays;

// 给定一个正数数组arr，代表若干人的体重
// 再给定一个正数limit，表示所有船共同拥有的载重量【每艘船的最大载重量都是 limit】
// 每艘船最多坐两人，且不能超过载重
// 想让所有的人同时过河，并且用最好的分配方法让船尽量少
// 返回最少的船数
// 测试链接 : https://leetcode.com/problems/boats-to-save-people/
public class Code05_BoatsToSavePeople {

	/**
	 * 先排序
	 * 设置两个指针 L 和 R
	 * L指向小于等于 arr[N/2]的最后一个数
	 * R指向大于等于 arr[N/2]的第一个一个数
	 * 考察 L和R能否凑一船
	 * 不可以：L指针左移
	 * 可以：考察R指针，R指针右移，找到不能和当前L组成一船的位置的前一个位置（也就是找到能和当前L位置组成一船的最后一个位置）
	 * 此时，R滑动的长度假设为L个数。要消化掉这L个数。用从此时的L开始，往左推L个数是最省的【L往右，搞不定这L个数。若果往左太多，又会浪费】【见图】
	 * 这样，就可以一次性搞定L个。达到加速的目的
	 * 【匹配过程中，将匹配走的都做个标记】
	 * 若果右边先耗尽。【剩下没有被匹配走的都是左边的，那么剩下的数除以二就是额外需要的船数】【在对称轴以左，肯定不会超载】
	 * 若左侧先耗尽。【剩下没有被匹配走的（有左边没有被标记的，也有右边没有被匹配的）】
	 * 【在右边的，每个人就是一个船，在左边没有被标记的，除以二，向上取整就是需要的船数】【在对称轴以右，任何两个肯定都会超载】
	 *
	 * @since 2022-03-02 09:10:46
	 */
	public static int numRescueBoats1(int[] arr, int limit) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		Arrays.sort(arr);
		if (arr[N - 1] > limit) {
			return -1;
		}
		int lessR = -1;
		for (int i = N - 1; i >= 0; i--) {
			if (arr[i] <= (limit / 2)) {
				lessR = i;
				break;
			}
		}
		if (lessR == -1) {
			return N;
		}
		int L = lessR;
		int R = lessR + 1;
		int noUsed = 0;
		while (L >= 0) {
			int solved = 0;
			while (R < N && arr[L] + arr[R] <= limit) {
				R++;
				solved++;
			}
			if (solved == 0) {
				noUsed++;
				L--;
			} else {
				L = Math.max(-1, L - solved);
			}
		}
		int all = lessR + 1;
		int used = all - noUsed;
		int moreUnsolved = (N - all) - used;
		return used + ((noUsed + 1) >> 1) + moreUnsolved;
	}

	// 首尾双指针的解法
	public static int numRescueBoats2(int[] people, int limit) {
		Arrays.sort(people);
		int ans = 0;
		int l = 0;
		int r = people.length - 1;
		int sum = 0;
		while (l <= r) {
			sum = l == r ? people[l] : people[l] + people[r];
			if (sum > limit) {
				r--;
			} else {
				l++;
				r--;
			}
			ans++;
		}
		return ans;
	}

}
