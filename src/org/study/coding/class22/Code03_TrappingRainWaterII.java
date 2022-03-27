package org.study.coding.class22;

import java.util.PriorityQueue;

/**
 * 本题测试链接 : https://leetcode.com/problems/trapping-rain-water-ii/
 * 给你一个 m x n 的矩阵，其中的值均为非负整数，代表二维高度图每个单元的高度，请计算图中形状最多能接多少体积的雨水。
 * <p>
 * 解法：
 * 考虑的还是某个位置的上面有多少水
 * <p>
 * <p>
 * 先将外围一圈的值都放到小跟堆里面去。不只是存值，还要存一下位置
 * 记一下 max 。初始值等于0
 * 从小跟堆里面弹出来的，就是此时的薄弱点。找到它是来自于哪里。看看它能不能推高max。
 * 接下来将该位置的上下左右没进入过堆的位置，都放到堆里面去，新进去的数，如果比max小，它的水量可以直接确定【就是max减去它的值】
 * 【因为此时的薄弱点是max。进去的值比max小，说明从max这个拼劲点能联通到这个最小点】
 * 再将最小值弹出，看看有没有推高max，如果没有推高max。说明还以之前的max做瓶颈。
 * 再将弹出位置的上下左右的值放到堆里面去。
 * <p>
 * 新进去的数，如果比max小，它的水量可以直接确定【就是max减去它的值】
 * 新进去的数，如果比max大，它的水量也可以直接确定【就是0。因为此时的这个位置是一个高点】
 * 新进去的数，如果和max一样发，它的水量也可以直接确定【就是0。因为此时的这个位置是一个等高点，无法盛水】
 *
 * @since 2022-03-24 22:33:22
 */
public class Code03_TrappingRainWaterII {

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

    public static int trapRainWater(int[][] heightMap) {
        if (heightMap == null || heightMap.length == 0 || heightMap[0] == null || heightMap[0].length == 0) {
            return 0;
        }
        int N = heightMap.length;
        int M = heightMap[0].length;

        // 记录某个位置是否加入过堆
        boolean[][] isEnter = new boolean[N][M];

        // 四个for循环，将最外围的一圈放进小跟堆。边界外置不可能盛水。所以也不需要对其进行计算
        PriorityQueue<Node> heap = new PriorityQueue<>((a, b) -> a.value - b.value);
        for (int col = 0; col < M - 1; col++) {
            isEnter[0][col] = true;
            heap.add(new Node(heightMap[0][col], 0, col));
        }
        for (int row = 0; row < N - 1; row++) {
            isEnter[row][M - 1] = true;
            heap.add(new Node(heightMap[row][M - 1], row, M - 1));
        }
        for (int col = M - 1; col > 0; col--) {
            isEnter[N - 1][col] = true;
            heap.add(new Node(heightMap[N - 1][col], N - 1, col));
        }
        for (int row = N - 1; row > 0; row--) {
            isEnter[row][0] = true;
            heap.add(new Node(heightMap[row][0], row, 0));
        }

        // 总水量
        int water = 0;

        // 此时的瓶颈点
        int max = 0;


        while (!heap.isEmpty()) {
            Node cur = heap.poll();

            // 能刷新max就刷新，不能刷新，就不刷
            max = Math.max(max, cur.value);
            int r = cur.row;
            int c = cur.col;

            // 上下左右，在加入堆的时候，计算水量
            // 4个if对应结算上下左右
            if (r > 0 && !isEnter[r - 1][c]) {
                water += Math.max(0, max - heightMap[r - 1][c]);
                isEnter[r - 1][c] = true;
                heap.add(new Node(heightMap[r - 1][c], r - 1, c));
            }
            if (r < N - 1 && !isEnter[r + 1][c]) {
                water += Math.max(0, max - heightMap[r + 1][c]);
                isEnter[r + 1][c] = true;
                heap.add(new Node(heightMap[r + 1][c], r + 1, c));
            }
            if (c > 0 && !isEnter[r][c - 1]) {
                water += Math.max(0, max - heightMap[r][c - 1]);
                isEnter[r][c - 1] = true;
                heap.add(new Node(heightMap[r][c - 1], r, c - 1));
            }
            if (c < M - 1 && !isEnter[r][c + 1]) {
                water += Math.max(0, max - heightMap[r][c + 1]);
                isEnter[r][c + 1] = true;
                heap.add(new Node(heightMap[r][c + 1], r, c + 1));
            }
        }
        return water;
    }

}
