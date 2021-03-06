package org.study.coding.class32;

/**
 * 给定整数 n ，返回 所有小于非负整数n的质数的数量 。
 *
 * 
 *
 * 示例 1：
 *
 * 输入：n = 10
 * 输出：4
 * 解释：小于 10 的质数一共有 4 个, 它们是 2, 3, 5, 7 。
 * 示例 2：
 *
 * 输入：n = 0
 * 输出：0
 * 示例 3：
 *
 * 输入：n = 1
 * 输出：0
 *
 *
 * 解法：
 * 考虑插空法
 * 1...n 除了2，所有的偶数都不是质数
 * 去试的时候
 * 首先偶数不用考虑【直接画叉】
 * 考虑
 * 2*3  2*5  2*7  2*9  2*11 ...【这些碰上的数，直接画叉】
 * 3*3  3*5  3*7  3*9  3*11 ...【这些碰上的数，直接画叉】
 * 4* ...【4是偶数，不用试，直接跳过】
 * 5*5  5*7  5*9  5*11  5*13 ...【这些碰上的数，直接画叉】
 * 6* ...【6是偶数，不用试，直接跳过】
 * 7*7  7*9  7*11  7*13  7*15 ...【这些碰上的数，直接画叉】
 *
 * 从2开始往上试试。当来到x。
 * 如果x没有被画叉。那么它就是质数。如果被画叉了，它就不是质数
 * 考虑试的时候。将 x*x  x*(x+2)  x*(x+4)  ...  直到乘积的结果大于所给的数n 。结束当前数的试探
 * 偶数直接跳过。奇数才需要试
 *
 * 当来到 x 的时候。
 * 如果x是质数，就将质数数量加加
 * 不是质数就不加加
 * 如果明显不是质数，就直接跳过它。【除了2的偶数都可以跳过了】
 *
 *
 * 注意：只去抓住所有可能减减的时机。
 *
 *
 * @since 2022-04-19 21:17:59
 */
public class Problem_0204_CountPrimes {

	public static int countPrimes(int n) {
		if (n < 3) {
			return 0;
		}
		// j已经不是素数了，f[j] = true;
		boolean[] f = new boolean[n];

		// 所有偶数都不要，还剩几个数。【每遇到一个非质数，就将count减减。最后看看还剩下多少个】
		int count = n / 2;
		// 跳过了1、2    3、5、7、
		for (int i = 3; i * i < n; i += 2) {

			// 当前数已经不是质数了。那么它就没有再往后试的必要了【说明之前已经被减减过了】
			if (f[i]) {
				continue;
			}
			// 3 -> 3 * 3 = 9   3 * 5 = 15   3 * 7 = 21
			// 7 -> 7 * 7 = 49  7 * 9 = 63
			// 13 -> 13 * 13  13 * 15
			// j 就是乘积
			// x*x  x*(x+2)  x*(x+4) ==> x*x + x*y 【y的取值是2、4、6、8】
			// 也就是循环的时候，每次都乘以2
			for (int j = i * i; j < n; j += 2 * i) {

				// 该数之前没考虑过。将质数数量减减
				if (!f[j]) {
					--count;
					f[j] = true;
				}
			}
		}
		return count;
	}

}
