package org.study.coding.class17;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 给定一个每一行有序、每一列也有序，整体可能无序的二维数组，再给定一个正数k，返回二维数组中第k小的数
 * Leetcode题目：https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/
 * <p>
 * <p>
 * 解法：
 * 先解决一个原型
 * 考虑矩阵数组中，小于等于m的数有多少个？？
 * 还是从右上角开始。
 * 如果当前位置的数>m。说明当前位置的下边都不会有<=m的数。获得0个 <= m的数 .指针左移
 * 如果当前位置的数 <=m。说明当前位置的左边都会<=m。获得当前列加1个 <= m的数 .指针下移
 * 直到数组越界为止。
 * 在这个过程中，除了能知道 <= m 的有几个。还能知道 <= m 且离m最近的数是谁。【每次移动指针都抓一下】
 * <p>
 * 原问题：
 * 假设矩阵中的最大数为max【右下角的数】
 * 去考察小于等于 max/2 的数有多少个。
 * 如果 <=max/2 的个数小于要求的第k小。那么就去考察 小于等于 3max/4 的有多少
 * 如果 <=max/2 的个数大于要求的第k小。那么就去考察 小于等于 max/4 的有多少
 * <p>
 * 每次都去考察二分的位置。直到 <=x 的个数刚好等于k个。但是数组中可能没有x. 那么小于等于x且最接近x的那个数就是答案
 * <p>
 * 时间复杂度 O(n+m)*log(max-min)
 *
 * @since 2022-03-18 03:44:33
 */
public class Code02_KthSmallestElementInSortedMatrix {

    /**
     * 堆的方法
     *
     * @since 2022-03-18 05:02:01
     */
    public static int kthSmallest1(int[][] matrix, int k) {
        int N = matrix.length;
        int M = matrix[0].length;
        PriorityQueue<Node> heap = new PriorityQueue<>(new NodeComparator());
        boolean[][] set = new boolean[N][M];
        heap.add(new Node(matrix[0][0], 0, 0));
        set[0][0] = true;
        int count = 0;
        Node ans = null;
        while (!heap.isEmpty()) {
            ans = heap.poll();
            if (++count == k) {
                break;
            }
            int row = ans.row;
            int col = ans.col;
            if (row + 1 < N && !set[row + 1][col]) {
                heap.add(new Node(matrix[row + 1][col], row + 1, col));
                set[row + 1][col] = true;
            }
            if (col + 1 < M && !set[row][col + 1]) {
                heap.add(new Node(matrix[row][col + 1], row, col + 1));
                set[row][col + 1] = true;
            }
        }
        return ans.value;
    }

    public static class Node {
        public int value;
        public int row;
        public int col;

        public Node(int v, int r, int c) {
            value = v;
            row = r;
            col = c;
        }

    }

    public static class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            return o1.value - o2.value;
        }

    }

    /**
     * 二分的方法
     *
     * @since 2022-03-18 05:01:51
     */
    public static int kthSmallest2(int[][] matrix, int k) {
        int N = matrix.length;
        int M = matrix[0].length;
        int left = matrix[0][0];
        int right = matrix[N - 1][M - 1];
        int ans = 0;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            // <=mid 有几个 <= mid 在矩阵中真实出现的数，谁最接近mid
            Info info = noMoreNum(matrix, mid);
            if (info.num < k) {
                left = mid + 1;
            } else {
                ans = info.near;
                right = mid - 1;
            }
        }
        return ans;
    }

    public static class Info {
        public int near;
        public int num;

        public Info(int n1, int n2) {
            near = n1;
            num = n2;
        }
    }

    public static Info noMoreNum(int[][] matrix, int value) {
        int near = Integer.MIN_VALUE;
        int num = 0;
        int N = matrix.length;
        int M = matrix[0].length;
        int row = 0;
        int col = M - 1;
        while (row < N && col >= 0) {
            if (matrix[row][col] <= value) {
                near = Math.max(near, matrix[row][col]);
                num += col + 1;
                row++;
            } else {
                col--;
            }
        }
        return new Info(near, num);
    }

}
