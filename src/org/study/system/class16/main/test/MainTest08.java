package org.study.system.class16.main.test;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 最小生成树【Prim算法】
 *
 * @author phil
 * @date 2021/7/13 16:44
 */
public class MainTest08 {

    private static class EdgeComparator implements Comparator<Graph.Edge>{
        @Override
        public int compare(Graph.Edge o1, Graph.Edge o2) {
            return o1.weight - o2.weight;
        }
    }
    /**
     * 点去找边，边再去找点。。。。
     * @date 2021-07-13 10:22:36
     */
    public static Set<Graph.Edge> primMST(Graph graph) {

        Set<Graph.Edge> edgeSet = new HashSet<>();

        PriorityQueue<Graph.Edge> queue = new PriorityQueue<>(new EdgeComparator());

        // 哪些点被解锁出来了
        HashSet<Graph.Node> nodeSet = new HashSet<>();


        // 随便挑了一个点
        // 这个外层的 for 是为了防止非森林，也就是出现不相连的点
        for (Graph.Node node : graph.nodeMap.values()) {
            if(!nodeSet.contains(node)){
                nodeSet.add(node);
                queue.addAll(node.edges);
                while (!queue.isEmpty()) {
                    Graph.Edge edge = queue.poll();
                    Graph.Node toNode = edge.to;
                    if(nodeSet.contains(toNode)){
                        nodeSet.add(toNode);
                        edgeSet.add(edge);
                        queue.addAll(toNode.edges);
                    }
                }
            }
        }


        return edgeSet;
    }
}
