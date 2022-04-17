package org.study.coding.class31;

import java.util.Stack;

/**
 * 150. 逆波兰表达式求值
 * 根据 逆波兰表示法，求表达式的值。
 * 有效的算符包括+、-、*、/。每个运算对象可以是整数，也可以是另一个逆波兰表达式。
 * 注意两个整数之间的除法只保留整数部分。
 * 可以保证给定的逆波兰表达式总是有效的。换句话说，表达式总会得出有效数值且不存在除数为 0 的情况。
 *
 * 
 *
 * 示例1：
 * 输入：tokens = ["2","1","+","3","*"]
 * 输出：9
 * 解释：该算式转化为常见的中缀算术表达式为：((2 + 1) * 3) = 9
 *
 * 示例2：
 * 输入：tokens = ["4","13","5","/","+"]
 * 输出：6
 * 解释：该算式转化为常见的中缀算术表达式为：(4 + (13 / 5)) = 6
 *
 * 示例3：
 * 输入：tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
 * 输出：22
 * 解释：该算式转化为常见的中缀算术表达式为：
 *   ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
 * = ((10 * (6 / (12 * -11))) + 17) + 5
 * = ((10 * (6 / -132)) + 17) + 5
 * = ((10 * 0) + 17) + 5
 * = (0 + 17) + 5
 * = 17 + 5
 * = 22
 *
 * 题意：
 * 遇到一个符号之后，将该符号之前的两个数，用该符号将其结合。
 * 结合后的结果放在这三个字符的位置。不要和这三个字符的前面及后面改变相对次序
 *
 * 解法:
 * 采用栈来完成。
 * 遇到数字就如栈。遇到符号就将栈顶的两个元素拉出来，用当前符号将其结合。结合后的结果再入栈。
 * 最后栈里肯定只会实现一个数【逆波兰试是绝对有效的】
 * 将最后的数返回就ok了。
 * 
 * 
 * @since 2022-04-17 16:02:11
 */
public class Problem_0150_EvaluateReversePolishNotation {

	public static int evalRPN(String[] tokens) {
		Stack<Integer> stack = new Stack<>();
		for (String str : tokens) {
			if ("+".equals(str) || "-".equals(str) || "*".equals(str) || "/".equals(str)) {
				compute(stack, str);
			} else {
				stack.push(Integer.valueOf(str));
			}
		}

		return stack.peek();
	}

	public static void compute(Stack<Integer> stack, String op) {
		int num2 = stack.pop();
		int num1 = stack.pop();
		int ans = 0;
		switch (op) {
		case "+":
			ans = num1 + num2;
			break;
		case "-":
			ans = num1 - num2;
			break;
		case "*":
			ans = num1 * num2;
			break;
		case "/":
			ans = num1 / num2;
			break;
		default:
			// 啥也不干。逆波兰试是绝对有效的
		}
		stack.push(ans);
	}

}
