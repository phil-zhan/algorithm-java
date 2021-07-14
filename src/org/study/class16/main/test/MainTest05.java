package org.study.class16.main.test;

import org.study.class16.Code03_TopologicalOrderDFS1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * lintCode拓扑排序题02
 *
 * @author phil
 * @date 2021/7/13 15:16
 */
public class MainTest05 {

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
        public int deep;


        public Record(DirectedGraphNode n, int o) {
            node = n;
            deep = o;
        }
    }

    public static class MyComparator implements Comparator<Record> {

        @Override
        public int compare(Record o1, Record o2) {
            return o2.deep - o1.deep;
        }
    }


    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        ArrayList<DirectedGraphNode> answer = new ArrayList<>();
        Map<DirectedGraphNode,Record> order = new HashMap<>();

        for (DirectedGraphNode cur :graph) {
            computeNum(cur,order);
        }

        ArrayList<Record> arrayList = new ArrayList<>(order.values());

        arrayList.sort(new MyComparator());


        for (Record record :arrayList) {
            answer.add(record.node);
        }

        return answer;
    }

    private static Record computeNum(DirectedGraphNode cur, Map<DirectedGraphNode, Record> order) {
        if (order.containsKey(cur)) {
            return order.get(cur);
        }
        int curNum=0;
        for (DirectedGraphNode next :cur.neighbors) {
            curNum = Math.max(computeNum(next,order).deep,curNum);
        }
        Record record = new Record(cur,curNum+1);
        order.put(cur,record);
        return record;
    }

}
