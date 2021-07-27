package org.study.class17;

import java.util.HashMap;
import java.util.Map;

/**
 * 加强堆的方式实现最短距离算法
 *
 * @author phil
 * @date 2021/7/14 16:58
 */
public class MainTest01 {


    public static class HeapNode{
        private final Map<Node,Integer> indexMap;

        private final Node[] nodes;

        private final Map<Node,Integer> distanceMap;

        private int size;


        public static class NodeRecord {
            public Node node;
            public int distance;

            public NodeRecord(Node node, int distance) {
                this.node = node;
                this.distance = distance;
            }
        }
        public HeapNode(int size){
            indexMap = new HashMap<>();
            nodes = new Node[size];
            distanceMap = new HashMap<>();
            size = 0;
        }


        // 有一个点叫node，现在发现了一个从源节点出发到达node的距离为distance
        // 判断要不要更新，如果需要的话，就更新
        public void addOrUpdateOrIgnore(Node node, int distance) {
            if (inHeap(node)) { // update
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                insertHeapify(node, indexMap.get(node));
            }
            if (!isEntered(node)) { // add
                nodes[size] = node;
                indexMap.put(node, size);
                distanceMap.put(node, distance);
                insertHeapify(node, size++);
            }
            // ignore 【进来过，但是又没在堆上】【说明该点已经被固定了】【直接跳过】
        }
        /**
         * 节点下沉
         * @date 2021-07-14 17:05:42
         */
        private void heapify(int size,int index) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
                        ? left + 1
                        : left;
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
                if (smallest == index) {
                    break;
                }
                swap(smallest, index);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        public NodeRecord pop() {
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            swap(0, size - 1); // 0 > size - 1    size - 1 > 0
            indexMap.put(nodes[size - 1], -1);
            distanceMap.remove(nodes[size - 1]);
            // free C++同学还要把原本堆顶节点析构，对java同学不必
            nodes[size - 1] = null;
            heapify(0, --size);
            return nodeRecord;
        }

        /**
         * 节点往堆顶方向飘
         * @date 2021-07-14 13:17:35
         */
        private void insertHeapify(Node node, int index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }


        public boolean isEmpty() {
            return size == 0;
        }


        private void swap(int index1, int index2) {
            indexMap.put(nodes[index1], index2);
            indexMap.put(nodes[index2], index1);
            Node tmp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
        }

        private boolean isEntered(Node node) {

            // 出堆的时候没有删反向索引表。只是将其在反向索引表中的值改为了 -1
            return indexMap.containsKey(node);
        }

        private boolean inHeap(Node node) {
            return isEntered(node) && indexMap.get(node) != -1;
        }


    }




    // 改进后的dijkstra算法
    // 从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
    public static HashMap<Node, Integer> dijkstra2(Node head, int size) {
        HeapNode nodeHeap = new HeapNode(size);
        nodeHeap.addOrUpdateOrIgnore(head, 0);
        HashMap<Node, Integer> result = new HashMap<>();
        while (!nodeHeap.isEmpty()) {
            HeapNode.NodeRecord record = nodeHeap.pop();
            Node cur = record.node;
            int distance = record.distance;
            for (Edge edge : cur.edges) {
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }
            result.put(cur, distance);
        }
        return result;
    }

}
