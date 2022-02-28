package org.study.system.class16.main.test;



import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * 图的宽度遍历
 * @author phil
 * @date 2021/7/13 13:44
 */
public class MainTest01 {

    private static void bfs(Graph.Node start){

        if (null == start) {
            return;
        }
        Queue<Graph.Node> queue = new LinkedList<>();
        Set<Graph.Node> set = new HashSet<>();

        queue.add(start);
        while (!queue.isEmpty()){
            Graph.Node curNode = queue.poll();
            System.out.println(curNode.value);

            for (Graph.Node node: curNode.nodes) {
                if (!set.contains(node)) {
                    queue.add(node);
                    set.add(curNode);
                }
            }
        }
    }
}
