package org.study.class16.main.test;


import org.study.class16.Code03_TopologicalOrderDFS2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * lintCode拓扑排序题03
 * @author phil
 * @date 2021/7/13 15:39
 */
public class MainTest06 {

    // 不要提交这个类
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    /**
     * // 提交下面的.封装这一层，是为了方便利用数组的排序。否则需要自己遍历map去排序
     * @date 2021-07-13 15:31:10
     */
    public static class Record {
        public DirectedGraphNode node;

        // nodes : 表示从当前节点开始，到图的最后，一个有多少个节点【其子节点的节点数之后。可能有些节点需要重复计算】
        public long nodes;


        public Record(DirectedGraphNode n, long o) {
            node = n;
            nodes = o;
        }
    }
    
    
    public  static  class MyComparator implements Comparator<Record>{

        @Override
        public int compare(Record o1, Record o2) {
            return Long.compare(o2.nodes,o1.nodes);
        }
    }


    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, Record> order = new HashMap<>();

        // 计算每个点为起点的点次
        for (DirectedGraphNode cur : graph) {
            countNum(cur, order);
        }
        ArrayList<Record> recordArr = new ArrayList<>();
        for (Record r : order.values()) {
            recordArr.add(r);
        }

        // 利用数组的比较器进行排序
        recordArr.sort(new MyComparator());
        ArrayList<DirectedGraphNode> ans = new ArrayList<DirectedGraphNode>();

        // 点次多的，拓扑序
        for (Record r : recordArr) {
            ans.add(r.node);
        }


        return ans;
        
        
    }

    private static Record countNum(DirectedGraphNode cur, Map<DirectedGraphNode, Record> order) {
        if (order.containsKey(cur)) {
            return order.get(cur);
        }
        // cur的点次之前没算过！
        long nodes = 0;
        for (DirectedGraphNode next : cur.neighbors) {
            nodes += countNum(next, order).nodes;
        }
        Record ans = new Record(cur, nodes + 1);

        // put到缓存里面去，方便下一次直接取。这样递归只走一次
        order.put(cur, ans);
        return ans;
    }
}
