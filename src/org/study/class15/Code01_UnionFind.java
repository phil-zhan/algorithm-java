package org.study.class15;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * https://leetcode.com/problems/largest-component-size-by-common-factor/
 * @author Phil
 */
public class Code01_UnionFind {

	public static class Node<V> {
		V value;

		public Node(V v) {
			value = v;
		}
	}

	public static class UnionFind<V> {

		// 利用map映射实现指针的关系


		// 样本元素和封装体之间的映射
		public HashMap<V, Node<V>> nodes;

		// 当前节点元素和父节点元素的对应关系
		public HashMap<Node<V>, Node<V>> parents;

		// 当前节点所代表的结合与其大小的映射
		public HashMap<Node<V>, Integer> sizeMap;

		public UnionFind(List<V> values) {
			nodes = new HashMap<>();
			parents = new HashMap<>();
			sizeMap = new HashMap<>();
			for (V cur : values) {
				Node<V> node = new Node<>(cur);
				nodes.put(cur, node);

				// 初始的时候，自己的父元素就是自己
				parents.put(node, node);

				sizeMap.put(node, 1);
			}
		}

		// 给你一个节点，请你往上到不能再往上，把代表返回【也就是把头返回】
		public Node<V> findFather(Node<V> cur) {

			// * 3 往上找也是一个优化，在找的过程中，将所有子节点都连到最大的祖先节点上去，让整个链扁平化，减少下次查找的时间
			Stack<Node<V>> path = new Stack<>();

			// 父节点不是自己，就是还没到顶
			while (cur != parents.get(cur)) {
				path.push(cur);
				cur = parents.get(cur);
			}
			while (!path.isEmpty()) {
				parents.put(path.pop(), cur);
			}
			return cur;
		}

		public boolean isSameSet(V a, V b) {
			return findFather(nodes.get(a)) == findFather(nodes.get(b));
		}

		public void union(V a, V b) {
			Node<V> aHead = findFather(nodes.get(a));
			Node<V> bHead = findFather(nodes.get(b));
			if (aHead != bHead) {
				int aSetSize = sizeMap.get(aHead);
				int bSetSize = sizeMap.get(bHead);
				Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
				Node<V> small = big == aHead ? bHead : aHead;
				parents.put(small, big);
				sizeMap.put(big, aSetSize + bSetSize);
				sizeMap.remove(small);
			}
		}

		public int sets() {
			return sizeMap.size();
		}

	}
}
