package org.study.coding.class32;

import java.util.HashSet;
import java.util.TreeSet;

/**
 * 202. 快乐数
 * 编写一个算法来判断一个数 n 是不是快乐数。
 *
 * 「快乐数」 定义为：
 *
 * 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
 * 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
 * 如果这个过程 结果为 1，那么这个数就是快乐数。
 * 如果 n 是 快乐数 就返回 true ；不是，则返回 false 。
 *
 *
 *
 * 示例 1：
 *
 * 输入：n = 19
 * 输出：true
 * 解释：
 * 12 + 92 = 82
 * 82 + 22 = 68
 * 62 + 82 = 100
 * 12 + 02 + 02 = 1
 * 示例 2：
 *
 * 输入：n = 2
 * 输出：false
 *
 * 解法：
 *
 *
 *
 *
 * @since 2022-04-19 21:07:32
 */
public class Problem_0202_HappyNumber {

	/**
	 * 推荐硬记
	 * 将每个中间结果都加set。类似于缓存
	 * 如果发现后面来的数在set里面。就说明成环了，就直接结束循环
	 *
	 * 结论：
	 * 如果在算的过程中。出现了4，那么以后就不会再出现1 了，也就是当前数不是快乐数
	 * 也就是不管任何数字。如果不是快乐数，肯定会遇到4 。如果是快乐数，肯定会遇到1
	 *
	 * 当前方法是暴力方法。最优解在下面
	 *
	 * @since 2022-04-19 21:07:51
	 */
	public static boolean isHappy1(int n) {
		HashSet<Integer> set = new HashSet<>();
		while (n != 1) {
			int sum = 0;
			while (n != 0) {
				int r = n % 10;
				sum += r * r;
				n /= 10;
			}
			n = sum;
			if (set.contains(n)) {
				break;
			}
			set.add(n);
		}
		return n == 1;
	}

	// 实验代码
	public static TreeSet<Integer> sum(int n) {
		TreeSet<Integer> set = new TreeSet<>();
		while (!set.contains(n)) {
			set.add(n);
			int sum = 0;
			while (n != 0) {
				sum += (n % 10) * (n % 10);
				n /= 10;
			}
			n = sum;
		}
		return set;
	}

	/**
	 * 最优解
	 * @since 2022-04-19 21:14:46
	 */
	public static boolean isHappy2(int n) {
		while (n != 1 && n != 4) {
			int sum = 0;
			while (n != 0) {
				// 将每一位都算平方。在将所有的平方都累加起来
				sum += (n % 10) * (n % 10);

				// 考虑下一位
				n /= 10;
			}
			n = sum;
		}
		return n == 1;
	}

	public static void main(String[] args) {
		for (int i = 1; i < 1000; i++) {
			System.out.println(sum(i));
		}
	}

}
