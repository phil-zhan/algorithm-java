package org.study.class38;

public class Code02_EatGrass {

	/**
	 * 暴力解
	 *
	 * // 如果n份草，最终先手赢，返回"先手"
	 * // 如果n份草，最终后手赢，返回"后手"
	 *
	 * @date 2021-10-13 16:31:21
	 */
	public static String whoWin(int n) {
		if (n < 5) {
			return n == 0 || n == 2 ? "后手" : "先手";
		}
		// 进到这个过程里来，当前的先手，先选
		int want = 1;
		while (want <= n) {

			// 后序的调用过程中，当前这个先手赢了
			if ("后手".equals(whoWin(n - want))) {
				return "先手";
			}

			// 别越界了
			if (want <= (n / 4)) {
				want *= 4;
			} else {
				break;
			}


		}
		return "后手";
	}

	/**
	 * 暴力解之后，找到规律
	 *
	 * @date 2021-10-13 16:30:56
	 */
	public static String winner1(int n) {
		if (n < 5) {
			return (n == 0 || n == 2) ? "后手" : "先手";
		}
		int base = 1;
		while (base <= n) {
			if ("后手".equals(winner1(n - base))) {
				return "先手";
			}

			// 防止base*4之后溢出
			if (base > n / 4) {
				break;
			}
			base *= 4;
		}
		return "后手";
	}

	public static String winner2(int n) {
		if (n % 5 == 0 || n % 5 == 2) {
			return "后手";
		} else {
			return "先手";
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i <= 50; i++) {
			System.out.println(i + " : " + whoWin(i));
		}
	}

}
