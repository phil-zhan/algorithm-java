package org.study.system.class16.main.test;

import java.util.*;

/**
 * 最短距离_Dijkstra算法
 *
 * @author phil
 * @date 2021/7/14 10:40
 */
public class MainTest09 {

    private static HashMap<Graph.Node,Integer> dijkstra1(Graph.Node from){

        HashMap<Graph.Node,Integer> answer = new HashMap<>(16);
        Set<Graph.Node> selectedNode = new HashSet<>();
        //PriorityQueue<Graph.Edge> selectableEdge = new PriorityQueue<>();
        answer.put(from,0);

        Graph.Node minNode = getMinDistanceAndUnselectedNode(answer,selectedNode);
        while (null != minNode) {
            //  原始点  ->  minNode(跳转点)   最小距离distance
            int distance = answer.get(minNode);
            for (Graph.Edge edge : minNode.edges) {
                Graph.Node toNode = edge.to;
                if (!answer.containsKey(toNode)) {
                    answer.put(toNode, distance + edge.weight);
                } else { // toNode
                    answer.put(edge.to, Math.min(answer.get(toNode), distance + edge.weight));
                }
            }
            selectedNode.add(minNode);
            minNode = getMinDistanceAndUnselectedNode(answer, selectedNode);
        }

        return answer;
    }

    private static Graph.Node getMinDistanceAndUnselectedNode(HashMap<Graph.Node, Integer> answer, Set<Graph.Node> selectedNode) {
        Graph.Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Graph.Node, Integer> entry :answer.entrySet()) {
            Graph.Node node= entry.getKey();
            int distance = entry.getValue();
            if(!selectedNode.contains(minNode) && distance < minDistance){
                minNode = node;
                minDistance = distance;

            }
        }

        return minNode;
    }
}
