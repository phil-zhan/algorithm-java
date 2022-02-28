package org.study.system.class16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 返回拓扑排序的列表
 * @date 2021-07-12 16:53:45
 */
public class Code03_TopologySort {

	// directed graph and no loop
	public static List<Node> sortedTopology(Graph graph) {
		// key 某个节点   value 剩余的入度
		HashMap<Node, Integer> inMap = new HashMap<>();
		// 只有剩余入度为0的点，才进入这个队列
		Queue<Node> zeroInQueue = new LinkedList<>();

		// 遍历所有的图节点。维护节点与入度的关系表
		for (Node node : graph.nodes.values()) {
			inMap.put(node, node.in);
			if (node.in == 0) {
				zeroInQueue.add(node);
			}
		}
		List<Node> result = new ArrayList<>();
		while (!zeroInQueue.isEmpty()) {
			Node cur = zeroInQueue.poll();
			result.add(cur);

			// 遍历当前点的所有邻居点。所有入度都减一
			for (Node next : cur.nexts) {
				inMap.put(next, inMap.get(next) - 1);
				if (inMap.get(next) == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		return result;
	}
}
