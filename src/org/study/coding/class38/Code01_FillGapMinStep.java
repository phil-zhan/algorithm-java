package org.study.coding.class38;

// 来自字节
// 给定两个数a和b
// 第1轮，把1选择给a或者b
// 第2轮，把2选择给a或者b
// ...
// 第i轮，把i选择给a或者b
// 想让a和b的值一样大，请问至少需要多少轮？
public class Code01_FillGapMinStep {

	// 暴力方法
	// 不要让a、b过大！
	public static int minStep0(int a, int b) {
		if (a == b) {
			return 0;
		}
		int limit = 15;
		return process(a, b, 1, limit);
	}

	public static int process(int a, int b, int i, int n) {
		if (i > n) {
			return Integer.MAX_VALUE;
		}
		if (a + i == b || a == b + i) {
			return i;
		}
		return Math.min(process(a + i, b, i + 1, n), process(a, b + i, i + 1, n));
	}

	/**
	 * 假设经过 i 轮之后。两个数能平衡
	 * ① a-b = s【是一个定值】
	 * 要想让给完之后两个数相等。那么就相当于把小的数给大的。打的数给小的 【a+b = b+a】
	 *
	 * 也就是总共需要给出 a+b 这么多的量，才能让两个数平衡。
	 * 那么
	 * ② a+b = 1+2+3+...+i = i*(i+1)/2; 【等差数列】【假设这个和是 sum】
	 *
	 * ②+① 得到 2a = s+sum ====> a = (sum+s)/2
	 * ②-① 得到 2b = sum-s ====> b = (sum-s)/2
	 *
	 * 又 a和b 都是正整数
	 * 故 只需要保证 s-sum 是一个大于等于0的偶数即可。
	 *
	 *
	 * O(N)
	 *
	 *
	 * @since 2022-06-04 10:33:56
	 */
	public static int minStep1(int a, int b) {
		if (a == b) {
			return 0;
		}
		int s = Math.abs(a - b);
		int num = 1;
		int sum = 0;
		// a+b=sum
		// a-b = s
		// b = (sum-s)/2
		// a = (sum+s)/2


		for (; !(sum >= s && (sum - s) % 2 == 0); num++) {
			sum += num;
		}
		return num - 1;
	}

	/**
	 * 对时间复杂度 O(N) 的优化。可以做到时间复杂度 O(logN)
	 * 要让 sum-s 是一个大于等于0的偶数 。
	 * 首先要让 sum >= s
	 * 让 i 等于1、 2、4、6、8...这样翻倍去追。
	 *
	 * 假设在 i=32 的时候都还没有追上 。但是在 i >= 64 的时候超过了s
	 * 那么最小的 i 肯定在 [32,64] 这个区间上。再在这个区间上去二分
	 *
	 *
	 * 假设最终找到 i = 47 的时候，sum刚刚大于等于s
	 * 那么就从 i = 47开始去试。 当 sum-s 是偶数时就停止。
	 * 【可以证明，这个试的过程不超过三次】 两个数相减的奇偶性。
	 *
	 *
	 * @since 2022-06-04 10:47:07
	 */
	public static int minStep2(int a, int b) {
		if (a == b) {
			return 0;
		}
		int s = Math.abs(a - b);
		// 找到sum >= s, 最小的i 【也就是找到 i*(i+1) >= 2s 的那个i】
		int begin = best(s << 1);
		while ((begin * (begin + 1) / 2 - s) % 2 != 0) {
			begin++;
		}
		return begin;
	}

	/**
	 * 找到sum >= s, 最小的i
	 * 先倍增，再二分
	 *
	 * @since 2022-06-04 10:55:58
	 */
	public static int best(int s2) {
		int L = 0;
		int R = 1;
		while (R * (R + 1) < s2) {
			L = R;
			R *= 2;
		}
		int ans = 0;
		while (L <= R) {
			int M = (L + R) / 2;
			if (M * (M + 1) >= s2) {
				ans = M;
				R = M - 1;
			} else {
				L = M + 1;
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		System.out.println("功能测试开始");
		for (int a = 1; a < 100; a++) {
			for (int b = 1; b < 100; b++) {
				int ans1 = minStep0(a, b);
				int ans2 = minStep1(a, b);
				int ans3 = minStep2(a, b);
				if (ans1 != ans2 || ans1 != ans3) {
					System.out.println("出错了！");
					System.out.println(a + " , " + b);
					break;
				}
			}
		}
		System.out.println("功能测试结束");

		int a = 19019;
		int b = 8439284;
		int ans2 = minStep1(a, b);
		int ans3 = minStep2(a, b);
		System.out.println(ans2);
		System.out.println(ans3);

	}

}
