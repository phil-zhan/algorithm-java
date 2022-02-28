package org.study.system.class16.main.test;

import java.util.*;

/**
 * lintCode拓扑排序题
 *
 * @author phil
 * @date 2021/7/13 14:49
 */
public class MainTest04 {

    private static class DirectedGraphNode{
        public int label;
        List<DirectedGraphNode> neighbors;
        public DirectedGraphNode(int label){
            this.label = label;
            neighbors = new ArrayList<>();
        }
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graphNodes) {

        Map<DirectedGraphNode,Integer> integerMap = new HashMap<>();
        // 需要有这个初始化过程，不然初始入度为0的点就不会在  integerMap 表中
        for (DirectedGraphNode graphNode : graphNodes) {
            integerMap.put(graphNode, 0);
        }
        for (DirectedGraphNode node:graphNodes) {
            for (DirectedGraphNode neighbor:node.neighbors) {
                integerMap.put(neighbor,integerMap.get(neighbor) +1);
            }
        }
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        Queue<DirectedGraphNode> zeroQueue = new LinkedList<>();
        for (DirectedGraphNode node:integerMap.keySet()) {
            if (integerMap.get(node) == 0) {
                zeroQueue.add(node);
            }
        }
        while (!zeroQueue.isEmpty()) {
            DirectedGraphNode node = zeroQueue.poll();
            ans.add(node);
            for (DirectedGraphNode next :node.neighbors) {
                integerMap.put(next,integerMap.get(next) - 1);
                if(integerMap.get(next) == 0){
                    zeroQueue.add(next);
                }
            }
        }
        return ans;
    }
}
