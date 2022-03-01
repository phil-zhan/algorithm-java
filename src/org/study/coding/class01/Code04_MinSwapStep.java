package org.study.coding.class01;

/**
 *  // 一个数组中只有两种字符'G'和'B'，
 * 	// 可以让所有的G都放在左侧，所有的B都放在右侧
 *  // 或者可以让所有的G都放在右侧，所有的B都放在左侧
 * 	// 但是只能在相邻字符之间进行交换操作，请问请问至少需要交换几次，
 *
 * @since 2022-02-28 10:08:50
 */
public class Code04_MinSwapStep {


	/**
	 * 解法1：
	 * 准备一个step1指针。表示下一个G应该放的位置
	 * 从左往右遍历
	 * 遇到的如果是B，直接跳过
	 * 遇到的如果是G。累加当前位置的G一定到`gi`的位置需要多少步（ans += index-gi）.  【在考虑下一个元素之前，不要忘记gi++】
	 * 两个标准。所有G在左，也可以所有B在左。
	 * 考虑这两种情况下的最下值
	 * 时间复杂度 O(N)
	 * @since 2022-02-28 10:09:06
	 */
	public static int minSteps1(String s) {
		if (s == null || s.equals("")) {
			return 0;
		}
		char[] str = s.toCharArray();
		int step1 = 0;
		int gi = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == 'G') {
				step1 += i - (gi++);
			}
		}
		int step2 = 0;
		int bi = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == 'B') {
				step2 += i - (bi++);
			}
		}
		return Math.min(step1, step2);
	}

	// 简化版
	// 可以让G在左，或者在右
	public static int minSteps2(String s) {
		if (s == null || s.equals("")) {
			return 0;
		}
		char[] str = s.toCharArray();
		int step1 = 0;
		int step2 = 0;
		int gi = 0;
		int bi = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == 'G') { // 当前的G，去左边   方案1
				step1 += i - (gi++);
			} else {// 当前的B，去左边   方案2
				step2 += i - (bi++);
			}
		}
		return Math.min(step1, step2);
	}

	// 为了测试
	public static String randomString(int maxLen) {
		char[] str = new char[(int) (Math.random() * maxLen)];
		for (int i = 0; i < str.length; i++) {
			str[i] = Math.random() < 0.5 ? 'G' : 'B';
		}
		return String.valueOf(str);
	}

	public static void main(String[] args) {
		int maxLen = 100;
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			String str = randomString(maxLen);
			int ans1 = minSteps1(str);
			int ans2 = minSteps2(str);
			if (ans1 != ans2) {
				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束");
	}
}