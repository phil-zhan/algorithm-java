package org.study.coding.class10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * 本题测试链接：https://www.lintcode.com/problem/top-k-frequent-words-ii/
 * 以上的代码不要粘贴, 把以下的代码粘贴进java环境编辑器
 * 把类名和构造方法名改成TopK, 可以直接通过
 *
 * 题意：源源不断的吐出str字符串
 * 随时返回排名最高的前几个字符串【按照出现次数排名】
 *
 * 当一个字符串来的时候，得看看之前有没有对应的字符串节点。
 * 有的话，让该节点的出现次数加1
 * 没有的话，加一个新的节点进去
 *
 * 维护小跟堆【按照出现次数】【方便剔除的时候，直接剔除堆顶】
 * 维护反向索引表。有利于实现加强堆。可以随时修改出现的次数
 *
 * 堆里面只维护需要的k个数【也就是前几名。会以参数的形式给出】
 * 当堆满的时候，将小的踢出去
 *
 *
 * @since 2022-03-11 07:42:53
 */
public class Code02_TopK {


    public static void main(String[] args) {
        Code02_TopK code02_topK = new Code02_TopK(2);
        code02_topK.add("a");
        code02_topK.add("b");
        code02_topK.add("c");
        code02_topK.add("a");
        code02_topK.add("c");
        System.out.println(code02_topK.topk());
    }




    private Node[] heap;
    private int heapSize;
    /**
     * 词频表   key  abc   value  (abc,7)
     * 要的只是前 k 个。堆里只维护前 k 个
     * strNodeMap 保留所有的词频。方便检查
     */
    private HashMap<String, Node> strNodeMap;
    private HashMap<Node, Integer> nodeIndexMap;
    private NodeHeapComp comp;
    private TreeSet<Node> treeSet;

    public Code02_TopK(int K) {
        heap = new Node[K];
        heapSize = 0;
        strNodeMap = new HashMap<>();
        nodeIndexMap = new HashMap<>();
        comp = new NodeHeapComp();
        treeSet = new TreeSet<>(new NodeTreeSetComp());
    }

    public static class Node {
        public String str;
        public int times;

        public Node(String s, int t) {
            str = s;
            times = t;
        }
    }

    public static class NodeHeapComp implements Comparator<Node> {

        // 保留频次高的。频次一样的，保留字典序低的【每次都会一处堆顶】【将频次低的或字典序高的放在前面】
        @Override
        public int compare(Node o1, Node o2) {
            return o1.times != o2.times ? (o1.times - o2.times) : (o2.str.compareTo(o1.str));
        }

    }

    public static class NodeTreeSetComp implements Comparator<Node> {

        // 输出返回的时候，先按照频次从低到高。频次一样的时候，按照字典序从低到高
        @Override
        public int compare(Node o1, Node o2) {
            return o1.times != o2.times ? (o2.times - o1.times) : (o1.str.compareTo(o2.str));
        }

    }

    public void add(String str) {
        if (heap.length == 0) {
            return;
        }
        // str   找到对应节点  curNode
        Node curNode = null;
        // 对应节点  curNode  在堆上的位置
        int preIndex = -1;
        if (!strNodeMap.containsKey(str)) {
            curNode = new Node(str, 1);
            strNodeMap.put(str, curNode);
            nodeIndexMap.put(curNode, -1);
        } else {
            curNode = strNodeMap.get(str);
            // 要在time++之前，先在treeSet中删掉
            // 原因是因为一但times++，curNode在treeSet中的排序就失效了
            // 这种失效会导致整棵treeSet出现问题
            if (treeSet.contains(curNode)) {
                treeSet.remove(curNode);
            }
            curNode.times++;
            preIndex = nodeIndexMap.get(curNode);
        }
        if (preIndex == -1) {
            if (heapSize == heap.length) {
                if (comp.compare(heap[0], curNode) < 0) {
                    treeSet.remove(heap[0]);
                    treeSet.add(curNode);
                    nodeIndexMap.put(heap[0], -1);
                    nodeIndexMap.put(curNode, 0);
                    heap[0] = curNode;
                    heapify(0, heapSize);
                }
            } else {
                treeSet.add(curNode);
                nodeIndexMap.put(curNode, heapSize);
                heap[heapSize] = curNode;
                heapInsert(heapSize++);
            }
        } else {
            treeSet.add(curNode);
            heapify(preIndex, heapSize);
        }
    }

    public List<String> topk() {
        ArrayList<String> ans = new ArrayList<>();
        for (Node node : treeSet) {
            ans.add(node.str);
        }
        return ans;
    }

    private void heapInsert(int index) {
        while (index != 0) {
            int parent = (index - 1) / 2;
            if (comp.compare(heap[index], heap[parent]) < 0) {
                swap(parent, index);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void heapify(int index, int heapSize) {
        int l = index * 2 + 1;
        int r = index * 2 + 2;
        int smallest = index;
        while (l < heapSize) {
            if (comp.compare(heap[l], heap[index]) < 0) {
                smallest = l;
            }
            if (r < heapSize && comp.compare(heap[r], heap[smallest]) < 0) {
                smallest = r;
            }
            if (smallest != index) {
                swap(smallest, index);
            } else {
                break;
            }
            index = smallest;
            l = index * 2 + 1;
            r = index * 2 + 2;
        }
    }

    private void swap(int index1, int index2) {
        nodeIndexMap.put(heap[index1], index2);
        nodeIndexMap.put(heap[index2], index1);
        Node tmp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = tmp;
    }

}