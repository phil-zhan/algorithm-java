package org.study.coding.class01;

import java.io.File;
import java.util.Stack;

public class Code02_CountFiles {

	// 考虑利用队列，宽度优先遍历【栈或递归也行】
	// 注意这个函数也会统计隐藏文件
	public static int getFileNumber(String folderPath) {
		File root = new File(folderPath);
		if (!root.isDirectory() && !root.isFile()) {
			return 0;
		}
		if (root.isFile()) {
			return 1;
		}
		Stack<File> stack = new Stack<>();
		stack.add(root);
		int files = 0;
		while (!stack.isEmpty()) {
			File folder = stack.pop();
			for (File next : folder.listFiles()) {
				if (next.isFile()) {
					files++;
				}
				if (next.isDirectory()) {
					stack.push(next);
				}
			}
		}
		return files;
	}

	public static void main(String[] args) {
		// 你可以自己更改目录
		String path = "C:";
		System.out.println(getFileNumber(path));
	}

}
