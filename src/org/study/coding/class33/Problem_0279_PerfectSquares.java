package org.study.coding.class33;

/**
 * 279. 完全平方数
 * 给你一个整数 n ，返回 和为 n 的完全平方数的最少数量 。
 *
 * 完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
 *
 *
 *
 * 示例 1：
 *
 * 输入：n = 12
 * 输出：3
 * 解释：12 = 4 + 4 + 4
 * 示例 2：
 *
 * 输入：n = 13
 * 输出：2
 * 解释：13 = 4 + 9

 *
 * @since 2022-04-21 07:27:38
 */
public class Problem_0279_PerfectSquares {

	/**
	 * 暴力解
	 * @since 2022-04-21 07:36:51
	 */
	public static int numSquares1(int n) {

		// 最多情况下是n
		// 去试，n是不是能被两个平方数搞出来。n是不是能被三个平方数搞出来。n是不是能被四个平方数搞出来。。。。
		// 最后发现，任何一个数，不会超过4个平方数就能搞出来
		int res = n;
		int num = 2;
		while (num * num <= n) {
			int a = n / (num * num);
			int b = n % (num * num);
			res = Math.min(res, a + numSquares1(b));
			num++;
		}
		return res;
	}


	/**
	 * 1 : 1, 4, 9, 16, 25, 36, ...
	 * 4 : 7, 15, 23, 28, 31, 39, 47, 55, 60, 63, 71, ...
	 * 规律解
	 * 规律一：个数不超过4【】
	 * 规律二：出现1个的时候，显而易见。【用Math下的开根号，然后向下取整。看看取整后的数的平方是不是当前数】
	 * 规律三：任何数 % 8 == 7，一定是4个【】
	 * 规律四：任何数消去4的因子之后，剩下rest，rest % 8 == 7，一定是4个
	 *
	 * @since 2022-04-21 07:27:59
	 */
	public static int numSquares2(int n) {
		int rest = n;
		while (rest % 4 == 0) {
			rest /= 4;
		}
		if (rest % 8 == 7) {
			return 4;
		}
		int f = (int) Math.sqrt(n);
		if (f * f == n) {
			return 1;
		}
		for (int first = 1; first * first <= n; first++) {
			int second = (int) Math.sqrt(n - first * first);
			if (first * first + second * second == n) {
				return 2;
			}
		}
		return 3;
	}


	/**
	 * 数学解
	 * 1）四平方和定理
	 * 2）任何数消掉4的因子，结论不变
	 *
	 * @since 2022-04-21 07:28:17
	 */
	public static int numSquares3(int n) {
		while (n % 4 == 0) {
			n /= 4;
		}
		if (n % 8 == 7) {
			return 4;
		}
		for (int a = 0; a * a <= n; ++a) {
			// a * a +  b * b = n  
			int b = (int) Math.sqrt(n - a * a);
			if (a * a + b * b == n) {
				// a和b 有一个是0的话。就返回1
				return (a > 0 && b > 0) ? 2 : 1;
			}
		}

		// 不是4  不是2  不是1   那肯定是拆成三份
		return 3;
	}

	public static void main(String[] args) {
		for (int i = 1; i < 1000; i++) {
			System.out.println(i + " , " + numSquares1(i));
		}
	}

}
