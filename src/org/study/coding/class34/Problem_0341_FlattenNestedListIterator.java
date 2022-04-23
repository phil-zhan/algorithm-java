package org.study.coding.class34;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;


/**
 * 341. 扁平化嵌套列表迭代器
 * 给你一个嵌套的整数列表 nestedList 。每个元素要么是一个整数，要么是一个列表；该列表的元素也可能是整数或者是其他列表。请你实现一个迭代器将其扁平化，使之能够遍历这个列表中的所有整数。
 *
 * 实现扁平迭代器类 NestedIterator ：
 *
 * NestedIterator(List<NestedInteger> nestedList) 用嵌套列表 nestedList 初始化迭代器。
 * int next() 返回嵌套列表的下一个整数。
 * boolean hasNext() 如果仍然存在待迭代的整数，返回 true ；否则，返回 false 。
 * 你的代码将会用下述伪代码检测：
 *
 * initialize iterator with nestedList
 * res = []
 * while iterator.hasNext()
 *     append iterator.next() to the end of res
 * return res
 * 如果 res 与预期的扁平化列表匹配，那么你的代码将会被判为正确。
 *
 *
 *
 * 示例 1：
 *
 * 输入：nestedList = [[1,1],2,[1,1]]
 * 输出：[1,1,2,1,1]
 * 解释：通过重复调用 next 直到 hasNext 返回 false，next 返回的元素的顺序应该是: [1,1,2,1,1]。
 * 示例 2：
 *
 * 输入：nestedList = [1,[4,[6]]]
 * 输出：[1,4,6]
 * 解释：通过重复调用 next 直到 hasNext 返回 false，next 返回的元素的顺序应该是: [1,4,6]。
 *
 *
 * 解法
 * 考虑栈结构
 * 栈里面记录每一个深度，下标推到了哪里。
 * 如果栈里面的数是[3,4,2,9,6] 表示最外层拿3位置，第二层嵌套拿4位置，第三次嵌套拿2位置... 以此类推，最后一层拿6位置的数
 * 栈里面只记录下一个数该怎么拿。如果栈里面记录的数被使用过了，就将used改为true。
 *
 *
 * @since 2022-04-23 11:23:52
 */
public class Problem_0341_FlattenNestedListIterator {

	/**
	 * 不要提交这个接口类
	 * @since 2022-04-23 11:24:01
	 */
	public interface NestedInteger {

		// @return true if this NestedInteger holds a single integer, rather than a
		// nested list.
		public boolean isInteger();

		// @return the single integer that this NestedInteger holds, if it holds a
		// single integer
		// Return null if this NestedInteger holds a nested list
		public Integer getInteger();

		// @return the nested list that this NestedInteger holds, if it holds a nested
		// list
		// Return null if this NestedInteger holds a single integer
		public List<NestedInteger> getList();
	}

	public class NestedIterator implements Iterator<Integer> {

		private List<NestedInteger> list;
		private Stack<Integer> stack;
		private boolean used;

		public NestedIterator(List<NestedInteger> nestedList) {
			list = nestedList;
			stack = new Stack<>();
			stack.push(-1);
			used = true;
			hasNext();
		}

		/**
		 * 栈里面记录每一个深度，下标推到了哪里。
		 * 如果栈里面的数是[3,4,2,9,6] 表示最外层拿3位置，第二层嵌套拿4位置，第三次嵌套拿2位置... 以此类推，最后一层拿6位置的数
		 * 栈里面只记录下一个数该怎么拿。如果栈里面记录的数被使用过了，就将used改为true。
		 *
		 *
		 * @since 2022-04-23 15:28:48
		 */
		@Override
		public Integer next() {
			Integer ans = null;
			if (!used) {

				// 通过栈里面记录的位置，去深度拿数据
				ans = get(list, stack);

				// 修改标志
				used = true;

				// 准备下一个数据
				hasNext();
			}
			return ans;
		}

		@Override
		public boolean hasNext() {

			// 最外层已经没了。所有数都没了
			if (stack.isEmpty()) {
				return false;
			}

			// used 在true和false 直接切换
			if (!used) {
				return true;
			}

			// 深度准备下一个数据
			if (findNext(list, stack)) {

				// 该数未被使用过
				used = false;
			}

			// 还有数未被使用，返回true
			return !used;
		}

		private Integer get(List<NestedInteger> nestedList, Stack<Integer> stack) {
			int index = stack.pop();
			Integer ans = null;

			// 栈不为空，还没到底
			if (!stack.isEmpty()) {
				ans = get(nestedList.get(index).getList(), stack);
			} else {
				// 到底了，拿到对应的数
				ans = nestedList.get(index).getInteger();
			}
			// 将沿途的路径再压回去
			stack.push(index);
			return ans;
		}

		private boolean findNext(List<NestedInteger> nestedList, Stack<Integer> stack) {
			int index = stack.pop();
			if (!stack.isEmpty() && findNext(nestedList.get(index).getList(), stack)) {
				stack.push(index);
				return true;
			}
			for (int i = index + 1; i < nestedList.size(); i++) {
				if (pickFirst(nestedList.get(i), i, stack)) {
					return true;
				}
			}
			return false;
		}

		private boolean pickFirst(NestedInteger nested, int position, Stack<Integer> stack) {
			if (nested.isInteger()) {
				stack.add(position);
				return true;
			} else {
				List<NestedInteger> actualList = nested.getList();
				for (int i = 0; i < actualList.size(); i++) {
					if (pickFirst(actualList.get(i), i, stack)) {
						stack.add(position);
						return true;
					}
				}
			}
			return false;
		}

	}

}
