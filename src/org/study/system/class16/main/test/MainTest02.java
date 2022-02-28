package org.study.system.class16.main.test;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 深度优先遍历
 * @author phil
 * @date 2021/7/13 14:19
 */
public class MainTest02 {

    private static void dfs(Graph.Node start){

        if (null == start) {
            return;
        }
        Stack<Graph.Node> stack = new Stack<>();
        Set<Graph.Node> nodeSet = new HashSet<>();

        stack.add(start);
        nodeSet.add(start);

        while (!stack.isEmpty()){
            Graph.Node curNode = stack.pop();
            for (Graph.Node nextNode:curNode.nodes) {
                if(!nodeSet.contains(nextNode)){
                    stack.add(curNode);
                    stack.add(nextNode);

                    System.out.println(nextNode.value);
                    break;
                }
            }
        }
    }
}
