package org.study.class23;

public class Code03_NQueens {

	public static int num1(int n) {
		if (n < 1) {
			return 0;
		}

		// 存放已经确定位置的皇后的位置 record[index] :表示第 index 行的皇后放在 record[index] 列上
		int[] record = new int[n];
		return process1(0, record, n);
	}

	// 当前来到i行，一共是0~N-1行
	// 在i行上放皇后，所有列都尝试
	// 必须要保证跟之前所有的皇后不打架
	// int[] record record[x] = y 之前的第x行的皇后，放在了y列上
	// 返回：不关心i以上发生了什么，i.... 后续有多少合法的方法数
	public static int process1(int i, int[] record, int n) {
		// 来到了越界位置，说明之前的摆放都是有效的
		if (i == n) {
			return 1;
		}
		int res = 0;
		// i行的皇后，放哪一列呢？j列，
		for (int j = 0; j < n; j++) {
			if (isValid(record, i, j)) {
				record[i] = j;
				res += process1(i + 1, record, n);
			}
		}
		return res;
	}

	public static boolean isValid(int[] record, int i, int j) {
		// 0..i-1
		// record 数组不用重置。因为考虑是否冲突的时候，只考虑当前行的前面是否冲突，不考虑当前行的后面的行
		// 就算是回溯到某行的时候，也只考虑当前行的前面行，后面的行就算有脏数据也不影响
		for (int k = 0; k < i; k++) {
			// 是从上到下考虑的，每行只放一个，所以不用考虑同行
			// j == record[k]  不在同列
			// Math.abs(record[k] - j) == Math.abs(i - k)  不在同一条斜线上

			if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)) {
				return false;
			}
		}
		return true;
	}

	// 请不要超过32皇后问题
	public static int num2(int n) {
		if (n < 1 || n > 32) {
			return 0;
		}
		// 如果你是13皇后问题，limit 最右13个1，其他都是0
		// -1:所有位上都是 1
		int limit = n == 32 ? -1 : (1 << n) - 1;
		return process2(limit, 0, 0, 0);
	}

	// 7皇后问题
	// limit : 0....0 1 1 1 1 1 1 1   【固定不变】
	// 之前皇后的列影响：colLim
	// 之前皇后的左下对角线影响：leftDiaLim
	// 之前皇后的右下对角线影响：rightDiaLim

	// 来到某个位置时。该位置能不能填，只需要考虑当前的左上、右上和直属列。 后面的行还没填呢
	public static int process2(int limit, int colLim, int leftDiaLim, int rightDiaLim) {

		// 所有的皇后都填满了【只有增加了皇后，才会增加列限制的位，其相等时，也就是填满了】
		if (colLim == limit) {
			return 1;
		}
		// pos中所有是1的位置，是你可以去尝试皇后的位置
		// ~ : 取反
		// (colLim | leftDiaLim | rightDiaLim)  得到所有不能放的位置
		// (~(colLim | leftDiaLim | rightDiaLim))  得到所有能放的位置

		// 和 limit 取与运算，防止当前摆放的位置在 limit 的范围之外  【limit：就是要求的 n 的位表示数组】
		// pos中所有是1的位置，是你可以去尝试皇后的位置
		int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
		int mostRightOne = 0;
		int res = 0;
		while (pos != 0) {
			// 类似于循环遍历所有位置的1，考虑能不能摆放
			// 将 pos 中最右侧的 1
			mostRightOne = pos & (~pos + 1);

			pos = pos - mostRightOne;
			res += process2(limit,
					// 调整列限制
					colLim | mostRightOne,
					// 调整左对角线限制
					(leftDiaLim | mostRightOne) << 1,
					// 调整右对角线限制
					(rightDiaLim | mostRightOne) >>> 1);
		}
		return res;
	}

	public static void main(String[] args) {
		int n = 16;

		long start = System.currentTimeMillis();
		System.out.println(num2(n));
		long end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + "ms");

		start = System.currentTimeMillis();
		System.out.println(num1(n));
		end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + "ms");

	}
}
