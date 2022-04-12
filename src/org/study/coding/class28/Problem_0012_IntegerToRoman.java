package org.study.coding.class28;

/**
 * 将一个数字转换成罗马数字的表达
 * I：表示1
 * V：表示5
 * X：表示10【XX:20  XXX:30】
 * L: 表示50
 * C：表示100 【CC：200】
 * D：表示500
 * M：表示1000
 * @since 2022-04-11 22:43:53
 */
public class Problem_0012_IntegerToRoman {

	public static String intToRoman(int num) {
		String[][] c = {
				// 1--9
				{ "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" },

				// 10--90
				{ "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" },

				// 100--900
				{ "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" },

				// 1000--3000【题目所给的数字不会超过4000，最大也就是399】
				{ "", "M", "MM", "MMM" } };
		StringBuilder roman = new StringBuilder();

		roman
		// 千位
		.append(c[3][num / 1000 % 10])
		// 百位
		.append(c[2][num / 100 % 10])
		.append(c[1][num / 10 % 10])
		.append(c[0][num % 10]);

		// 摸个位上是0，直接拼一个空字符串【罗马数字没有0】

		return roman.toString();
	}


}
