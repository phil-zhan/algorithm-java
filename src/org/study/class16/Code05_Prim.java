package org.study.class16;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

// undirected graph only
public class Code05_Prim {

	public static class EdgeComparator implements Comparator<Edge> {

		@Override
		public int compare(Edge o1, Edge o2) {
			return o1.weight - o2.weight;
		}

	}

	/**
	 * 点去找边，边再去找点。。。。
	 * @date 2021-07-13 10:22:36
	 */
	public static Set<Edge> primMST(Graph graph) {
		// 解锁的边进入小根堆
		PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());

		// 哪些点被解锁出来了
		HashSet<Node> nodeSet = new HashSet<>();


		// 依次挑选的的边在result里
		Set<Edge> result = new HashSet<>();

		// 随便挑了一个点
		// 这个外层的 for 是为了防止非森林，也就是出现不相连的点
		for (Node node : graph.nodes.values()) {
			// node 是开始点
			if (!nodeSet.contains(node)) {
				nodeSet.add(node);
				// 由一个点，解锁所有相连的边
				// 这里可能会重复放边。但是也不会影响结果。因为在考虑的时候，其所指的节点都考虑过了，会直接跳过
				// 也可以再做一个set集合，去重
				for (Edge edge : node.edges) {
					priorityQueue.add(edge);
				}
				while (!priorityQueue.isEmpty()) {
					// 弹出解锁的边中，最小的边【由小跟堆弹出的，就是最小的边】
					Edge edge = priorityQueue.poll();

					// 可能的一个新的点
					Node toNode = edge.to;

					// 不含有的时候，就是新的点
					if (!nodeSet.contains(toNode)) {
						nodeSet.add(toNode);
						result.add(edge);
						for (Edge nextEdge : toNode.edges) {
							priorityQueue.add(nextEdge);
						}
					}
				}
			}

			// 如果确定所有点都是相连的，这里可以直接 break，节省时间
			// break;
		}
		return result;
	}

	// 请保证graph是连通图
	// graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
	// 返回值是最小连通图的路径之和
	public static int prim(int[][] graph) {
		int size = graph.length;
		int[] distances = new int[size];
		boolean[] visit = new boolean[size];
		visit[0] = true;
		for (int i = 0; i < size; i++) {
			distances[i] = graph[0][i];
		}
		int sum = 0;
		for (int i = 1; i < size; i++) {
			int minPath = Integer.MAX_VALUE;
			int minIndex = -1;
			for (int j = 0; j < size; j++) {
				if (!visit[j] && distances[j] < minPath) {
					minPath = distances[j];
					minIndex = j;
				}
			}
			if (minIndex == -1) {
				return sum;
			}
			visit[minIndex] = true;
			sum += minPath;
			for (int j = 0; j < size; j++) {
				if (!visit[j] && distances[j] > graph[minIndex][j]) {
					distances[j] = graph[minIndex][j];
				}
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		System.out.println("hello world!");
	}

}
