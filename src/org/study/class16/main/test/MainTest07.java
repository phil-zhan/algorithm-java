package org.study.class16.main.test;

import org.study.class16.Edge;
import org.study.class16.Node;

import java.util.*;

/**
 * Kruskal算法【最小生成树】
 *
 * @author phil
 * @date 2021/7/13 16:18
 */
public class MainTest07 {


    private static class UnionFind{

        private final Map<Graph.Node,Graph.Node> parents;
        private final Map<Graph.Node,Integer> sizeMap;

        public UnionFind(Collection<Graph.Node> list){
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (Graph.Node node:list) {
                parents.put(node,node);
                sizeMap.put(node,1);
            }
        }

        private Graph.Node findNode(Graph.Node node){
            Stack<Graph.Node> stack = new Stack<>();
            while (node != parents.get(node)) {
                stack.add(node);
                node = parents.get(node);
            }


            while (!stack.isEmpty()) {
                parents.put(stack.pop(),node);
            }

            return node;
        }
        private void union(Graph.Node node1,Graph.Node node2){

            if (null == node1 || null == node2) {
                return;
            }
            Graph.Node findNode1 = findNode(node1);
            Graph.Node findNode2 = findNode(node2);
            if(findNode1 != findNode2){
                int size1 = sizeMap.get(findNode1);
                int size2 = sizeMap.get(findNode2);
                if(size1 > size2){
                    parents.put(findNode2,findNode1);
                    sizeMap.put(findNode1,size1+size2);
                    sizeMap.remove(findNode2);
                }else{
                    parents.put(findNode1,findNode2);
                    sizeMap.put(findNode2,size1+size2);
                    sizeMap.remove(findNode1);
                }
            }
        }

        private boolean isSameSet(Graph.Node node1,Graph.Node node2){
            return findNode(node1) == findNode(node2);
        }
    }

    private static class EdgeComparator implements Comparator<Graph.Edge>{
        @Override
        public int compare(Graph.Edge o1, Graph.Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    /**
     * 主方法入口
     * @date 2021-07-13 16:44:05
     */
    public static Set<Graph.Edge> kruskalMST(Graph graph) {
        UnionFind unionFind = new UnionFind(graph.nodeMap.values());

        PriorityQueue<Graph.Edge> queue = new PriorityQueue<>();

        queue.addAll(graph.edgeSet);

        Set<Graph.Edge> answerSet = new HashSet<>();

        while (!queue.isEmpty()) {
            Graph.Edge edge = queue.poll();

            if(!unionFind.isSameSet(edge.from, edge.to)){
                answerSet.add(edge);
                unionFind.union(edge.from,edge.to);
            }
        }

        return answerSet;
    }
}
