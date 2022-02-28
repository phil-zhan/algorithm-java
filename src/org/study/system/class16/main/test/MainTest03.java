package org.study.system.class16.main.test;

import java.util.*;

/**
 * 拓扑排序001
 *
 * @author phil
 * @date 2021/7/13 14:34
 */
public class MainTest03 {

    private static List<Graph.Node> sortedTopology(Graph graph){

        List<Graph.Node> results = new ArrayList<>();
        Queue<Graph.Node> zeroQueue = new LinkedList<>();
        Map<Graph.Node,Integer> inMap = new HashMap<>();


        for (Graph.Node node:graph.nodeMap.values()) {
            inMap.put(node, node.in);
            if(node.in == 0){
                zeroQueue.add(node);
            }
        }

        while (!zeroQueue.isEmpty()){
            Graph.Node node = zeroQueue.poll();
            results.add(node);
            for (Graph.Node next: node.nodes) {
                inMap.put(next,inMap.get(next)-1);
                if (inMap.get(next) == 0) {
                    zeroQueue.add(next);
                }
            }
        }

        return results;
    }
}
