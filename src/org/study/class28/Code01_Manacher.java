package org.study.class28;

public class Code01_Manacher {

	/**
	 * 暴力解
	 * 在每个字符的前后都加一个虚字符（任意字符）去统计。
	 * 统计完成后，再去掉虚字符的结果。将原始字符的结果的答案都除以二并向下取整
	 *
	 * 加虚字符是为了处理回文子串长度是偶数的情况【加完虚数字符之后，奇数串的长度是奇数。偶数串的长度还是奇数】
	 *
	 *
	 * 概念：
	 * 1、回文半径【包扩中心的位置】
	 * 2、回文直径
	 * 3、最大回文右边界
	 * 4、取得最大回文右边界的中心位置
	 * @date 2021-09-28 16:22:09
	 */
	public static int manacher(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		// 拿到处理串
		// "12132" -> "#1#2#1#3#2#"
		// 由于虚字符和原始字符是不相遇的，所以虚字符可选任意字符
		char[] str = manacherString(s);

		// 回文半径的大小
		int[] pArr = new int[str.length];

		// C 取得最右回文右边界的时候，的中心轴的位置【R更新的时候，C再更新】
		int C = -1;
		// 讲述中：R代表最右的扩成功的位置
		// coding：最右的扩成功位置的，再下一个位置
		int R = -1;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < str.length; i++) { // 0 1 2
			// R第一个违规的位置，i>= R
			// i位置扩出来的答案，i位置扩的区域，

			// 至少是多大。

			// R > i :表示 i在 R 内
			// pArr 是回文半径数组
			// [2 * C - i] 就是 i' 。i关于C的对称点
			pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;

			// 到这里是 i 的最小回文半径。再考虑其左右是否还能扩
			// 只要不越界，就继续向下对比
			while (i + pArr[i] < str.length && i - pArr[i] > -1) {
				if (str[i + pArr[i]] == str[i - pArr[i]]) {
					pArr[i]++;
				} else {
					break;
				}
			}

			// 考虑是否要更新右边界
			if (i + pArr[i] > R) {
				R = i + pArr[i];
				C = i;
			}

			// 考虑是否更新最大回文半径
			max = Math.max(max, pArr[i]);
		}

		// 处理串的半径减一之后，也就是原来串的直径了【奇偶回文都一样】
		return max - 1;
	}

	public static char[] manacherString(String str) {
		char[] charArr = str.toCharArray();
		char[] res = new char[str.length() * 2 + 1];
		int index = 0;
		for (int i = 0; i != res.length; i++) {
			res[i] = (i & 1) == 0 ? '#' : charArr[index++];
		}
		return res;
	}

	// for test
	public static int right(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = manacherString(s);
		int max = 0;
		for (int i = 0; i < str.length; i++) {
			int L = i - 1;
			int R = i + 1;
			while (L >= 0 && R < str.length && str[L] == str[R]) {
				L--;
				R++;
			}
			max = Math.max(max, R - L - 1);
		}
		return max / 2;
	}

	// for test
	public static String getRandomString(int possibilities, int size) {
		char[] ans = new char[(int) (Math.random() * size) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
		}
		return String.valueOf(ans);
	}

	public static void main(String[] args) {
		int possibilities = 5;
		int strSize = 20;
		int testTimes = 5000000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			String str = getRandomString(possibilities, strSize);
			if (manacher(str) != right(str)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish");
	}

}
