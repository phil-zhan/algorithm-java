package org.study.system.class26;

public class Code02_FibonacciProblem {

	public static int f1(int n) {
		if (n < 1) {
			return 0;
		}
		if (n == 1 || n == 2) {
			return 1;
		}
		return f1(n - 1) + f1(n - 2);
	}

	public static int f2(int n) {
		if (n < 1) {
			return 0;
		}
		if (n == 1 || n == 2) {
			return 1;
		}
		int res = 1;
		int pre = 1;
		int tmp = 0;
		for (int i = 3; i <= n; i++) {
			tmp = res;
			res = res + pre;
			pre = tmp;
		}
		return res;
	}

	// O(logN)
	public static int f3(int n) {
		if (n < 1) {
			return 0;
		}
		if (n == 1 || n == 2) {
			return 1;
		}

		// 这是那个对应的基矩阵。
		// [ 1 ,1 ]
		// [ 1, 0 ]
		int[][] base = { 
				{ 1, 1 }, 
				{ 1, 0 } 
				};

		// 求基矩阵的 (n-2)次方
		int[][] res = matrixPower(base, n - 2);


		return res[0][0] + res[1][0];
	}

	public static int[][] matrixPower(int[][] m, int p) {
		int[][] res = new int[m.length][m[0].length];

		// 做出单位矩阵【左上到右下的对角线上都是1的矩阵】
		for (int i = 0; i < res.length; i++) {
			res[i][i] = 1;
		}

		// res = 矩阵中的1
		int[][] t = m;// 矩阵1次方

		// 每循环一次，将二进制都右移一位。然后再每次都取最后的一位。直到其值小于1停止
		for (; p != 0; p >>= 1) {

			// 不管目标要不要，每次都翻倍的乘。也就是平方【任何一个数都可以换算成二进制。也就是1、2、4、8、16... 的和】【每次都翻倍，能更快的到大目标次方】
			// 如果每次都乘对应数的一次方，得到N次才能到大目标次方。但是每次都翻倍，最多 LogN 就能到大目标次方

			// (p & 1) 判断其二进制的最后一位是否是1
			if ((p & 1) != 0) {
				// 对应的二进制位上是1，将对应的结果乘到目标上
				res = muliMatrix(res, t);
			}
			t = muliMatrix(t, t);
		}
		return res;
	}

	// 两个矩阵乘完之后的结果返回
	public static int[][] muliMatrix(int[][] m1, int[][] m2) {
		int[][] res = new int[m1.length][m2[0].length];
		for (int i = 0; i < m1.length; i++) {
			for (int j = 0; j < m2[0].length; j++) {
				for (int k = 0; k < m2.length; k++) {
					res[i][j] += m1[i][k] * m2[k][j];
				}
			}
		}
		return res;
	}

	public static int s1(int n) {
		if (n < 1) {
			return 0;
		}
		if (n == 1 || n == 2) {
			return n;
		}
		return s1(n - 1) + s1(n - 2);
	}

	public static int s2(int n) {
		if (n < 1) {
			return 0;
		}
		if (n == 1 || n == 2) {
			return n;
		}
		int res = 2;
		int pre = 1;
		int tmp = 0;
		for (int i = 3; i <= n; i++) {
			tmp = res;
			res = res + pre;
			pre = tmp;
		}
		return res;
	}

	public static int s3(int n) {
		if (n < 1) {
			return 0;
		}
		if (n == 1 || n == 2) {
			return n;
		}
		int[][] base = { { 1, 1 }, { 1, 0 } };
		int[][] res = matrixPower(base, n - 2);
		return 2 * res[0][0] + res[1][0];
	}

	/**
	 *
	 * 第一年农场有1只成熟的母牛A，往后的每年：
	 *
	 * 1）每一只成熟的母牛都会生一只母牛
	 *
	 * 2）每一只新出生的母牛都在出生的第三年成熟
	 *
	 * 3）每一只母牛永远不会死
	 *
	 * 返回N年后牛的数量
	 */
	public static int c1(int n) {
		if (n < 1) {
			return 0;
		}
		if (n == 1 || n == 2 || n == 3) {
			return n;
		}
		return c1(n - 1) + c1(n - 3);
	}

	public static int c2(int n) {
		if (n < 1) {
			return 0;
		}
		if (n == 1 || n == 2 || n == 3) {
			return n;
		}
		int res = 3;
		int pre = 2;
		int prepre = 1;
		int tmp1 = 0;
		int tmp2 = 0;
		for (int i = 4; i <= n; i++) {
			tmp1 = res;
			tmp2 = pre;
			res = res + prepre;
			pre = tmp1;
			prepre = tmp2;
		}
		return res;
	}

	public static int c3(int n) {
		if (n < 1) {
			return 0;
		}
		if (n == 1 || n == 2 || n == 3) {
			return n;
		}
		int[][] base = { 
				{ 1, 1, 0 }, 
				{ 0, 0, 1 }, 
				{ 1, 0, 0 } };
		int[][] res = matrixPower(base, n - 3);
		return 3 * res[0][0] + 2 * res[1][0] + res[2][0];
	}

	public static void main(String[] args) {
		int n = 19;
		System.out.println(f1(n));
		System.out.println(f2(n));
		System.out.println(f3(n));
		System.out.println("===");

		System.out.println(s1(n));
		System.out.println(s2(n));
		System.out.println(s3(n));
		System.out.println("===");

		System.out.println(c1(n));
		System.out.println(c2(n));
		System.out.println(c3(n));
		System.out.println("===");

	}

}
