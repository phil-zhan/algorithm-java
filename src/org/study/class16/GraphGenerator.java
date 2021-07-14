package org.study.class16;

/**
 * 图转换器。也是图的适配器。将不熟悉的图转换熟悉的经典图
 * @date 2021-07-12 14:12:18
 */
public class GraphGenerator {

	// matrix 所有的边
	// N*3 的矩阵。【也就是N行3列的矩阵】【第一列是边的权重，第二列是边的起点序号，第三列是边的终点序号】
	// [weight, from节点上面的值，to节点上面的值]
	// 
	// [ 5 , 0 , 7]  0------>7 [权重是5]
	// [ 3 , 0,  1]	 0------>1 [权重是3]
	// 
	public static Graph createGraph(int[][] matrix) {
		Graph graph = new Graph();

		// 拿到每一条边
		for (int i = 0; i < matrix.length; i++) {
			 // 拿到每一条边， matrix[i]
			int weight = matrix[i][0];
			int from = matrix[i][1];
			int to = matrix[i][2];

			// 起点不存在，就新建起点
			if (!graph.nodes.containsKey(from)) {
				graph.nodes.put(from, new Node(from));
			}

			// 终点不存在，就新建终点
			if (!graph.nodes.containsKey(to)) {
				graph.nodes.put(to, new Node(to));
			}
			Node fromNode = graph.nodes.get(from);
			Node toNode = graph.nodes.get(to);

			// 新建一条边
			Edge newEdge = new Edge(weight, fromNode, toNode);

			// 维护起点出发的邻居点
			fromNode.nexts.add(toNode);

			// 维护起点的出度
			fromNode.out++;

			// 维护终点的入度
			toNode.in++;

			// 维护起点出发的边
			fromNode.edges.add(newEdge);

			// 维护图
			graph.edges.add(newEdge);
		}
		return graph;
	}

}
