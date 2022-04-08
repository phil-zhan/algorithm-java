package org.study.coding.class10;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author phil
 * @date 2022-04-07 13:29:01
 */
public class MainTest02 {
    public static void main(String[] args) {
        TopK topK = new TopK(2);

        topK.add("lint");
        topK.add("code");
        topK.add("code");
        System.out.println(topK.topK());
    }


    public static class TopK {

        // 堆
        private final Node[] heap;

        // 堆内的元素
        private int heapSize;

        // 某一时刻的答案
        private final TreeSet<Node> treeSet;

        // 比较器
        private final Comparator<Node> comparator;

        // 反向索引
        private final HashMap<Node, Integer> indexMap;

        // 频次表
        private final HashMap<String, Node> timesMap;


        public TopK(int k) {
            heap = new Node[k];
            treeSet = new TreeSet<>(new NodeTreeComparator());
            comparator = new NodeHeapComparator();
            indexMap = new HashMap<>();
            timesMap = new HashMap<>();
            heapSize = 0;
        }

        public void add(String word) {
            if (heap.length == 0) {
                return;
            }
            Node curNode = null;
            int preIndex = -1;
            if (!timesMap.containsKey(word)) {
                // 之前没出现过
                curNode = new Node(word, 1);
                timesMap.put(word, curNode);
                indexMap.put(curNode, -1);
            } else {
                curNode = timesMap.get(word);
                if (treeSet.contains(curNode)) {
                    treeSet.remove(curNode);
                }
                curNode.times++;
                preIndex = indexMap.get(curNode);
            }

            if (preIndex == -1) {
                // 之前不在堆上。考虑能不能上堆
                if (heapSize == heap.length) {
                    // 堆满了【堆顶比较小，就扔掉堆顶。否则就保持原样】
                    if (comparator.compare(heap[0], curNode) < 0) {
                        treeSet.remove(heap[0]);
                        treeSet.add(curNode);
                        indexMap.put(heap[0], -1);
                        indexMap.put(curNode, 0);
                        heap[0] = curNode;
                        heapify(0);
                    }
                } else {
                    treeSet.add(curNode);
                    indexMap.put(curNode, heapSize);
                    // 堆上还有空位
                    heap[heapSize] = curNode;
                    heapInsert(heapSize++);
                }
            } else {
                // 之前已经在堆上，根据times，调整
                treeSet.add(curNode);

                // 频次只会增大，这里向下调就好
                heapify(preIndex);
            }
        }

        public List<String> topK() {

            return treeSet.stream().map(node -> node.str).collect(Collectors.toList());
        }

        private void heapInsert(int index) {
            while (index != 0) {
                int parent = (index - 1) >> 1;
                if (comparator.compare(heap[index], heap[parent]) < 0) {
                    swap(index, parent);
                    index = parent;
                } else {
                    break;
                }
            }
        }

        private void heapify(int index) {
            int left = (index << 1) + 1;
            while (left < heapSize) {
                int smallest = comparator.compare(heap[index], heap[left]) < 0 ? index : left;
                smallest = (left + 1) < heapSize && comparator.compare(heap[left + 1], heap[smallest]) < 0 ? left + 1 : smallest;

                if (smallest != index) {
                    swap(index, smallest);
                    index = smallest;
                    left = (index << 1) + 1;
                } else {
                    break;
                }
            }
        }

        private void swap(int index1, int index2) {
            // 维护indexMap
            Node node1 = heap[index1];
            Node node2 = heap[index2];
            indexMap.put(node1, index2);
            indexMap.put(node2, index1);

            heap[index1] = node2;
            heap[index2] = node1;
        }

        private static class NodeHeapComparator implements Comparator<Node> {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.times != o2.times ? o1.times - o2.times : o2.str.compareTo(o1.str);
            }
        }

        private static class NodeTreeComparator implements Comparator<Node> {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.times != o2.times ? o2.times - o1.times : o1.str.compareTo(o2.str);
            }
        }

        private static class Node {
            public String str;
            public int times;

            public Node(String str, int times) {
                this.str = str;
                this.times = times;
            }
        }
    }
}
