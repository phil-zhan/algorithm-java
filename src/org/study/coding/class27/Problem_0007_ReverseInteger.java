package org.study.coding.class27;

/**
 * 4. 给你一个32位的有符号整数x，返回将x中的数字部分反转后的结果，如果反转后整数超过 32 位的有符号整数的范围，就返回0
 *    假设环境不允许存储 64 位整数（有符号或无符号）
 *    Leetcode题目：https://leetcode.com/problems/reverse-integer/
 *
 * 解法：不要先转成string，再转回来，这样就没分了
 * 可能存在一些不能转的。比如系统最大，转换后就超出int的数据范围了。对于这些数，直接返回0
 *
 * 常见思路：
 * 将需要转换的数，先弄成负数。因为系统最小值的绝对值比系统最大值的绝对值要多一个，表示的范围会大一点
 * 当然，对本题的影响不大
 *
 * @since 2022-04-11 07:42:16
 */
public class Problem_0007_ReverseInteger {

	/**
	 * 每一次都取模，拿到最低位，
	 * 最低位乘以10就可以做到最高位的位置
	 * 1、保证不要越界
	 * 2、都转换成负数去操作
	 *
	 * @since 2022-04-11 21:45:48
	 */
	public static int reverse(int x) {
		boolean neg = ((x >>> 31) & 1) == 1;

		// 如果x是负数，就不用管。如果是正数，将其转换为负数去做
		x = neg ? x : -x;
		int m = Integer.MIN_VALUE / 10;
		int o = Integer.MIN_VALUE % 10;
		int res = 0;
		while (x != 0) {

			// res 马上要操作了。不要让它越界
			if (res < m || (res == m && x % 10 < o)) {
				return 0;
			}
			res = res * 10 + x % 10;
			x /= 10;
		}
		return neg ? res : Math.abs(res);
	}

}
