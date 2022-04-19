package org.study.coding.class32;

import java.util.HashMap;

/**
 * 166. 分数到小数
 * 给定两个整数，分别表示分数的分子 numerator 和分母 denominator，以 字符串形式返回小数 。
 * 如果小数部分为循环小数，则将循环的部分括在括号内。
 * 如果存在多个答案，只需返回 任意一个 。
 * 对于所有给定的输入，保证 答案字符串的长度小于 104 。
 *
 *
 *
 * 示例 1：
 * 输入：numerator = 1, denominator = 2
 * 输出："0.5"
 *
 *
 * 示例 2：
 * 输入：numerator = 2, denominator = 1
 * 输出："2"
 * 示例 3：
 *
 * 输入：numerator = 4, denominator = 333
 * 输出："0.(012)"
 *
 *
 * 解法：
 * 先取模。看看是不是整除。如果是整除，就能直接返回了
 * 如果不是整除
 *
 *
 *
 * @since 2022-04-18 22:57:27
 */
public class Problem_0166_FractionToRecurringDecimal {

	public static String fractionToDecimal(int numerator, int denominator) {
		if (numerator == 0) {
			return "0";
		}
		StringBuilder res = new StringBuilder();

		// 先考虑正负数的符号
		// "+" or "-"
		res.append(((numerator > 0) ^ (denominator > 0)) ? "-" : "");

		// 取绝对值
		long num = Math.abs((long) numerator);
		long den = Math.abs((long) denominator);


		// integral part
		res.append(num / den);

		// 取出余数
		num %= den;
		// 整除，直接返回
		if (num == 0) {
			return res.toString();
		}

		// 有小数
		// fractional part
		res.append(".");

		// 小数部分怎么求？？
		// 当前的余数乘以10，之后再去除以被除数，就是当前位的数。除完之后，将余数更新
		// 再当前的余数乘以10、之后再去除以被除数，就是当前位的数。除完之后，将余数更新
		// 再当前的余数乘以10、之后再去除以被除数，就是当前位的数。除完之后，将余数更新
		// ......
		// 一直循环下去
		// key：是某个小数
		// value：该小数出现的位置
		// 整数和整数除，不可能除出来无理数【无限不循环】。也就是结果要么是整数。要么是循环小数

		HashMap<Long, Integer> map = new HashMap<>();
		map.put(num, res.length());
		while (num != 0) {
			num *= 10;
			res.append(num / den);
			num %= den;

			// 该数之前出现过了。说明出现了循环小数
			if (map.containsKey(num)) {
				int index = map.get(num);
				res.insert(index, "(");
				res.append(")");
				break;
			} else {
				map.put(num, res.length());
			}
		}
		return res.toString();
	}

	public static void main(String[] args) {
		System.out.println(fractionToDecimal(127, 999));
	}

}
