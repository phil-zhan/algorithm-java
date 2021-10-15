package org.study.class38;

/**
 * @author phil
 */
public class Code03_MSumToN {

	/**
	 * 1+2+3+... + 能否弄出目标数
	 * 2+3+4+... + 能否弄出目标数
	 * 3+4+5+... + 能否弄出目标数
	 * 4+5+6+... + 能否弄出目标数
	 * 。。。
	 * @date 2021-10-14 11:14:19
	 */
	public static boolean isMSum1(int num) {
		for (int start = 1; start <= num; start++) {
			int sum = start;
			for (int j = start + 1; j <= num; j++) {
				if (sum + j > num) {
					break;
				}
				if (sum + j == num) {
					return true;
				}
				sum += j;
			}
		}
		return false;
	}

	/**
	 * 暴力之后，找到规律。是2的某次方，就是false。不是2的某次方，就是true
	 * 判断一个数是2的某次方？【一个数的二进制形式，如果只有一个1，那么它就是2的某次方。否则就不是2的某次方】
	 * 将其最右侧的 1 提取出来。如果和原来的数一样大，那么它就是2的某次方
	 *
	 * @date 2021-10-14 11:16:08
	 */
	public static boolean isMSum2(int num) {

		// 前面两种要考虑去掉1（也就是2的0次方）的情况
//		
//		return num != (num & (~num + 1));
//		
		return num != (num & (-num));
//		
//		
//		return (num & (num - 1)) != 0;
	}

	public static void main(String[] args) {
		for (int num = 1; num < 200; num++) {
			System.out.println(num + " : " + isMSum1(num));
		}
		System.out.println("test begin");
		for (int num = 1; num < 5000; num++) {
			if (isMSum1(num) != isMSum2(num)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test end");

	}
}
