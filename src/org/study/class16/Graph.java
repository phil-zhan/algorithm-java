package org.study.class16;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 图的经典表示方法
 * @author Phil
 * @date 2021-07-12 14:08:56
 */
public class Graph {

	/**
	 * // key:是节点序号
	 * // value: 是对应节点的信息
	 * @date 2021-07-12 14:09:19
	 */
	public HashMap<Integer, Node> nodes;

	/**
	 * 图中所有的边
	 * @date 2021-07-12 14:10:03
	 */
	public HashSet<Edge> edges;
	
	public Graph() {
		nodes = new HashMap<>();
		edges = new HashSet<>();
	}
}
