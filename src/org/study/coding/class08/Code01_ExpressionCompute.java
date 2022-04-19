package org.study.coding.class08;

import java.util.LinkedList;

/**
 * 本题测试链接 : https://leetcode.com/problems/basic-calculator-iii/
 *
 * 1. 给定一个字符串str，str表示一个公式，公式里可能有整数、加减乘除符号和左右括号。返回公式的计算结果
 *    难点在于括号可能嵌套很多层，str="48*((70-65)-43)+8*1"，返回-1816。str="3+1*4"，返回7。str="3+(1*4)"，返回7。
 *    1）. 可以认为给定的字符串一定是正确的公式，即不需要对str做公式有效性检查
 *    2）. 如果是负数，就需要用括号括起来，比如"4*(-3)"但如果负数作为公式的开头或括号部分的开头，则可以没有括号，比如"-3*4"和"(-3*4)"都是合法的
 *    3）. 不用考虑计算过程中会发生溢出的情况。
 *
 * 解法：
 * 考察从str[i...N]往下算，遇到 字符串终止位置 或者 右括号 ，就停止
 * 返回两个值，长度为2的数组
 * 0) 负责的这一段的结果是多少
 * 1) 负责的这一段计算到了哪个位置
 * 从i位置到N（或者右括号）遍历
 *
 * cur = 0;
 * str[i] 是数字：往后，拿到完整的数字。cur = cur * 10 + str[i++] - '0';
 * str[i] 是运算符号：遇到的是运算符号。将当前数cur和遇到的符号压入栈，当前cur入栈之前，检查是否需要合并栈顶（栈顶是乘除需要合并，加减不需要）
 * str[i] 是左括号：遇到左括号了.去左括号的下一个位置调用递归
 * str[i] 是右括号：结束当前递归方法
 * str[i] 到了字符串的终止位置：也结束当前递归方法
 * 在结束方法之前，记得将最后一个数字压入栈之后，再清理完成当前栈，给上游返回结果
 *
 *
 *
 *
 * 前序：
 * 计算只包含加减乘除的表达式【没有括号】
 * 设置一个cur变量。表示当前数的大小
 * 如果str[i]是数字. 往后，拿到完整的数字
 * 如果str[i]是算术符
 * 看一下栈顶的元素，如果是 乘或除，就将栈顶元素（乘或除）及其栈顶元素下面的一个数出栈
 * 将当前的cur和出栈的两个元素进行计算
 * 计算之后，将计算后的结果和str[i]位置的算术符压入栈
 *
 * 压栈之前，都检查一下，栈顶元素是否是乘除
 *
 * 到str的结束位置时，栈里面肯定就数字和加减。
 * 此时还没有入栈的就是最后一个cur数字。
 * 每次都从栈里面弹出一个算术符合一个数字，将其和当前的cur进行计算。计算后的结果还是用cur接住
 * 直到栈为空。此时的cur就是表达式的结果
 *
 * 入栈之后，cur归0
 *
 * @since 2022-03-08 10:09:49
 */
public class Code01_ExpressionCompute {

	public static void main(String[] args) {
		System.out.println(calculate(" 3/2 "));
	}

	public static int calculate(String str) {
		return process(str.toCharArray(), 0)[0];
	}


	/**
	 * 请从str[i...N]往下算，遇到字符串终止位置或者右括号，就停止
	 * 返回两个值，长度为2的数组
	 * 0) 负责的这一段的结果是多少
	 * 1) 负责的这一段计算到了哪个位置
	 *
	 * @since 2022-03-08 10:46:14
	 */
	public static int[] process(char[] str, int i) {
		LinkedList<String> que = new LinkedList<String>();
		int cur = 0;
		int[] bra;
		// 从i出发，开始撸串
		// 遇到右括号或者遇到字符串的终止
		while (i < str.length && str[i] != ')') {
			if (str[i] == ' '){
				i++;
				continue;
			}
			if (str[i] >= '0' && str[i] <= '9') {
				// 数字
				// 往后，拿到完整的数字
				cur = cur * 10 + str[i++] - '0';
			} else if (str[i] != '(') {
				// 遇到的是运算符号
				// 如果是负数，会一上来就遇到 负号。此时 cur = 0.就会在栈中加入 "0" "-"
				addNum(que, cur);

				// 将当前运算符压入【压入运算符之前，检查栈顶】
				que.addLast(String.valueOf(str[i++]));

				// 压入之后，当前值归 0
				cur = 0;
			} else {
				// 遇到左括号了【就交给递归去搞定】
				// 去左括号的下一位开始，调递归
				bra = process(str, i + 1);
				cur = bra[0];
				// 指针
				i = bra[1] + 1;
			}
		}

		// 遇到右括号或者遇到字符串的终止.计算当前的栈
		// 跳出while的时候，记得把最后一个cur放进去
		addNum(que, cur);

		// getNum 清理栈
		// 将计算结果和当前的结束位置返回给上游，方便上游继续往后算
		return new int[] { getNum(que), i };
	}

	/**
	 * num要进展，至于要不要和当前的栈顶元素结合，也在这个method里面做逻辑处理
	 * @since 2022-03-08 11:40:52
	 */
	public static void addNum(LinkedList<String> que, int num) {

		if (!que.isEmpty()) {
			int cur = 0;

			// 栈顶元素是加减，当前
			String top = que.pollLast();
			if ("+".equals(top) || "-".equals(top)) {
				// 如果是加减，不动
				que.addLast(top);
			} else {
				// 乘除，拿出来计算
				cur = Integer.valueOf(que.pollLast());
				num = "*".equals(top) ? (cur * num) : (cur / num);
			}
		}

		// 将当前数或计算结果压入
		que.addLast(String.valueOf(num));
	}

	public static int getNum(LinkedList<String> que) {
		int res = 0;
		boolean add = true;
		String cur = null;
		int num = 0;
		while (!que.isEmpty()) {
			cur = que.pollFirst();
			if ("+".equals(cur)) {
				add = true;
			} else if ("-".equals(cur)) {
				add = false;
			} else {
				num = Integer.parseInt(cur);
				res += add ? num : (-num);
			}
		}
		return res;
	}

}
