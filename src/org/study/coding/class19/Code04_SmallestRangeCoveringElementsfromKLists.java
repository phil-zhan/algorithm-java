package org.study.coding.class19;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * 谷歌原题
 * <p>
 * 你有k个非递减排列的整数列表。找到一个最小区间，使得k个列表中的每个列表至少有一个数包含在其中
 * 我们定义如果b-a < d-c或者在b-a == d-c时a < c，则区间 [a,b] 比 [c,d] 小。
 * 也就是右多个最小区间的时候，选开始值最小的那个区间返回
 * Leetcode题目：https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/
 * <p>
 * 解法：
 * 利用有序表【有序表：对于放进来的数，可以很方便的查到其最小值和最大值】
 * 将每个数组的第一个数都加入有序表。
 * 此时得到一个最小值和最大值。其所组成的区间，能保证每个数组中，至少有一个值落在这个区间上
 * <p>
 * 将最小的值弹出来。看其来自哪一个数组，将该数组的下一个数放进去。就会得到一个新的区间。
 * 看能不能把之前的区间推小。每次操作的时候，都抓一下区间大小。
 * 重复执行。直到弹出来的数所在的数组没有下一个数为止。
 * 也就是只要有一个数组耗尽了。就结束
 *
 * @since 2022-03-20 03:12:34
 */
public class Code04_SmallestRangeCoveringElementsfromKLists {

    public static class Node {
        public int value;
        public int arrid;
        public int index;

        public Node(int v, int ai, int i) {
            value = v;
            arrid = ai;
            index = i;
        }
    }

    public static class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            return o1.value != o2.value ? o1.value - o2.value : o1.arrid - o2.arrid;
        }

    }

    public static int[] smallestRange(List<List<Integer>> nums) {
        int N = nums.size();
        TreeSet<Node> orderSet = new TreeSet<>(new NodeComparator());
        for (int i = 0; i < N; i++) {
            orderSet.add(new Node(nums.get(i).get(0), i, 0));
        }
        boolean set = false;
        int a = 0;
        int b = 0;
        while (orderSet.size() == N) {
            Node min = orderSet.first();
            Node max = orderSet.last();
            if (!set || (max.value - min.value < b - a)) {
                set = true;
                a = min.value;
                b = max.value;
            }
            min = orderSet.pollFirst();
            int arrid = min.arrid;
            int index = min.index + 1;
            if (index != nums.get(arrid).size()) {
                orderSet.add(new Node(nums.get(arrid).get(index), arrid, index));
            }
        }
        return new int[]{a, b};
    }

}
