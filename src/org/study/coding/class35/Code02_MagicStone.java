package org.study.coding.class35;

import java.util.Arrays;

/**
 * 来自小红书
 * [0,4,7] ： 0表示这里石头没有颜色，如果变红代价是4，如果变蓝代价是7
 * [1,X,X] ： 1表示这里石头已经是红，而且不能改颜色，所以后两个数X无意义
 * [2,X,X] ： 2表示这里石头已经是蓝，而且不能改颜色，所以后两个数X无意义
 *
 * 颜色只可能是0、1、2，代价一定>=0
 * 给你一批这样的小数组，要求最后必须所有石头都有颜色，且红色和蓝色一样多，返回最小代价
 * 如果怎么都无法做到所有石头都有颜色、且红色和蓝色一样多，返回-1
 *
 * 第二维度是红色
 * 第三维度是蓝色
 *
 * 解法：
 * 石头数量如果为奇数。直接返回-1
 * 如果所给的石头中，红或蓝之一大于了一半。直接返回-1
 *
 * 遍历一遍，看看红的缺多少。蓝的缺多少。假设红缺a个，蓝色缺b个
 * 将可分配的石头挑出来。【第一维度是0的】【总共可以分配的一定是a+b】
 *
 * 先考虑将所有的可分配石头都变成红色。
 * 再考虑其中的 b 个变成蓝色，哪 b 个变成蓝色最值得。
 * 第二维度减去第三维度，差值最大的 b 个。
 *
 * 【因为每改变一次，如果差值是正数，就能减少相应的代价。这个减少的代价当然是越大越好】
 * 当然，差值也可能是负数。这代表着，必须增加相应的代价，才能保持红蓝平衡。
 *
 *
 * 【coding04_codee02 与这个很相似】
 *
 *
 * @since 2022-04-25 06:57:46
 */
public class Code02_MagicStone {

	public static int minCost(int[][] stones) {
		int n = stones.length;
		if ((n & 1) != 0) {
			return -1;
		}
		Arrays.sort(stones, (a, b) -> a[0] == 0 && b[0] == 0 ? (b[1] - b[2] - a[1] + a[2]) : (a[0] - b[0]));
		int zero = 0;
		int red = 0;
		int blue = 0;
		int cost = 0;
		for (int i = 0; i < n; i++) {
			if (stones[i][0] == 0) {
				zero++;
				cost += stones[i][1];
			} else if (stones[i][0] == 1) {
				red++;
			} else {
				blue++;
			}
		}
		if (red > (n >> 1) || blue > (n >> 1)) {
			return -1;
		}
		blue = zero - ((n >> 1) - red);
		for (int i = 0; i < blue; i++) {
			cost += stones[i][2] - stones[i][1];
		}
		return cost;
	}

	public static void main(String[] args) {
		int[][] stones = { { 1, 5, 3 }, { 2, 7, 9 }, { 0, 6, 4 }, { 0, 7, 9 }, { 0, 2, 1 }, { 0, 5, 9 } };
		System.out.println(minCost(stones));
	}

}
