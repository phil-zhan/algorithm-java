package org.study.system.class17;

import java.util.Stack;

public class Code05_ReverseStackUsingRecursive {

	public static void reverse(Stack<Integer> stack) {
		if (stack.isEmpty()) {
			return;
		}
		// i是当前栈的最后一个
		int i = f(stack);

		// 多次递归之后，每次都把最后一个拿出来
		reverse(stack);

		// 反向插入
		stack.push(i);
	}

	// 栈底元素移除掉
	// 上面的元素盖下来
	// 返回移除掉的栈底元素
	// result 是从上到下存起来
	// last 是取到最后一个值后，从下到上传递，直到执行完成
	public static int f(Stack<Integer> stack) {
		int result = stack.pop();
		if (stack.isEmpty()) {
			return result;
		} else {
			// 这个last是下一步的result。也就是下一个栈顶
			int last = f(stack);

			// 这个result是倒数第二个栈顶
			stack.push(result);

			// 返回最后一个
			return last;
		}
	}

	public static void main(String[] args) {
		Stack<Integer> test = new Stack<Integer>();
		test.push(1);
		test.push(2);
		test.push(3);
		test.push(4);
		test.push(5);
		reverse(test);
		while (!test.isEmpty()) {
			System.out.println(test.pop());
		}

	}

}
