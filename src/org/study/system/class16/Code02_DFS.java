package org.study.system.class16;

import java.util.HashSet;
import java.util.Stack;
/**
 * 深度优先遍历图
 * @date 2021-07-12 16:24:00
 */
public class Code02_DFS {

	public static void dfs(Node node) {
		if (node == null) {
			return;
		}

		// 栈里面放的永远是当前正在遍历的路径
		Stack<Node> stack = new Stack<>();
		HashSet<Node> set = new HashSet<>();
		stack.add(node);
		set.add(node);
		System.out.println(node.value);
		while (!stack.isEmpty()) {
			Node cur = stack.pop();
			for (Node next : cur.nexts) {

				// 只要发现一个没遍历过的下一跳节点，就将当前节点和下一跳节点都压入栈，然后退出当前for循环。进入下一次while循环
				// 当前节点不存在没有遍历的下一跳节点，当前节点也不压入，直接进入下一层while循环。弹出栈顶元素
				// 栈里面放的永远是当前正在遍历的路径
				if (!set.contains(next)) {
					stack.push(cur);
					stack.push(next);
					set.add(next);
					System.out.println(next.value);
					break;
				}
			}
		}
	}
	
	
	

}
