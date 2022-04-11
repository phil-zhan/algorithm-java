package org.study.coding.class26;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * 本题为求最小包含区间
 * https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/
 * 有三个有序数组，分别在三个数组中挑出3个数，x、y、z。返回 |x-y| + |y-z| + |z-x|最小是多少？
 * <p>
 * 解法：
 * |x-y| + |y-z| + |z-x|  这个指标其实就是X到Z距离的2倍
 * <p>
 * 本题就变成了，三个数组中，选择一个最小区间【要的是绝对值相加最小】【尽可能的让最大值和最小值的差值缩小】，让这个区间里面的最小值和最大值的距离尽量大。
 * 每个数组都必须有一个数在所选的区间【class19_Code04_SmallestRangeCoveringElementsfromKLists】
 * <p>
 * 最窄区间问题，前面提过的。
 *
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
 * @since 2022-04-06 22:32:55
 */
public class Code01_MinRange {

    public static class Node {
        public int val;
        public int arr;
        public int idx;

        public Node(int value, int arrIndex, int index) {
            val = value;

            // 当前数来自哪个数组
            arr = arrIndex;

            // 当前值在对应数组中的index下标
            idx = index;
        }
    }

    public static class NodeComparator implements Comparator<Node> {

        // 有序表里面不能有重复的，不然会出现覆盖问题。在值一样的情况下，让arrIndex也参与排序
        // 在对应的有序表中，每个数组只会有一个在里面。
        @Override
        public int compare(Node a, Node b) {
            return a.val != b.val ? (a.val - b.val) : (a.arr - b.arr);
        }

    }

    public static int[] smallestRange(List<List<Integer>> nums) {
        int N = nums.size();
        TreeSet<Node> set = new TreeSet<>(new NodeComparator());

        // 初始化有序表
        for (int i = 0; i < N; i++) {
            set.add(new Node(nums.get(i).get(0), i, 0));
        }
        int r = Integer.MAX_VALUE;
        int a = 0;
        int b = 0;

        // set.size() != N 的时候，说明某个数组耗尽了。直接结束
        // 我们需要保证每个数组都要有数在有序表中
        while (set.size() == N) {
            Node max = set.last();
            Node min = set.pollFirst();
            if (max.val - min.val < r) {
                r = max.val - min.val;
                a = min.val;
                b = max.val;
            }
            if (min.idx < nums.get(min.arr).size() - 1) {
                set.add(new Node(nums.get(min.arr).get(min.idx + 1), min.arr, min.idx + 1));
            }
        }
        return new int[]{a, b};
    }

    /**
     * 以下为课堂题目，在main函数里有对数器
     *
     * @since 2022-04-06 22:33:23
     */
    public static int minRange1(int[][] m) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < m[0].length; i++) {
            for (int j = 0; j < m[1].length; j++) {
                for (int k = 0; k < m[2].length; k++) {
                    min = Math.min(min, Math.abs(m[0][i] - m[1][j]) + Math.abs(m[1][j] - m[2][k]) + Math.abs(m[2][k] - m[0][i]));
                }
            }
        }
        return min;
    }

    public static int minRange2(int[][] matrix) {
        int N = matrix.length;
        TreeSet<Node> set = new TreeSet<>(new NodeComparator());
        for (int i = 0; i < N; i++) {
            set.add(new Node(matrix[i][0], i, 0));
        }
        int min = Integer.MAX_VALUE;
        while (set.size() == N) {
            min = Math.min(min, set.last().val - set.first().val);
            Node cur = set.pollFirst();
            if (cur.idx < matrix[cur.arr].length - 1) {
                set.add(new Node(matrix[cur.arr][cur.idx + 1], cur.arr, cur.idx + 1));
            }
        }
        return min << 1;
    }

    public static int[][] generateRandomMatrix(int n, int v) {
        int[][] m = new int[3][];
        int s = 0;
        for (int i = 0; i < 3; i++) {
            s = (int) (Math.random() * n) + 1;
            m[i] = new int[s];
            for (int j = 0; j < s; j++) {
                m[i][j] = (int) (Math.random() * v);
            }
            Arrays.sort(m[i]);
        }
        return m;
    }

    public static void main(String[] args) {
        int n = 20;
        int v = 200;
        int t = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < t; i++) {
            int[][] m = generateRandomMatrix(n, v);
            int ans1 = minRange1(m);
            int ans2 = minRange2(m);
            if (ans1 != ans2) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }

}
