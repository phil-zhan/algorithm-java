package org.study.coding.class35;

import java.util.PriorityQueue;


/**
 * 来自网易
 * map[i][j] == 0，代表(i,j)是海洋，渡过的话代价是2
 * map[i][j] == 1，代表(i,j)是陆地，渡过的话代价是1
 * map[i][j] == 2，代表(i,j)是障碍，无法渡过
 * 每一步上、下、左、右都能走，返回从左上角走到右下角最小代价是多少，如果无法到达返回-1
 * <p>
 * <p>
 * <p>
 * 解法：
 * 因为可以上下左右，不能用动态规划
 * <p>
 * 优先级队列结合的遍历
 * 如果左上角或右下角是2，那么直接返回 -1
 * <p>
 * <p>
 * <p>
 * 准备一个优先级队列【也就是小跟堆】
 * 来到 i、j 位置。
 * 考虑到当前位置的上下左右【包含上下左右】。需要的代价。
 * 将这些点和对应代价封装成节点，放到小跟堆里面去。【优先级队列按照代价组织】
 * <p>
 * 每一次都从小跟堆里面弹出一个最小代价的节点去考虑。
 * <p>
 * <p>
 * 当然，还需要一个一个boolean 类型的表来配合。走过的路就不要再进入小跟堆了
 * 防止一直都走不完
 *
 * @since 2022-04-25 21:52:26
 */
public class Code04_WalkToEnd {

    public static int minCost(int[][] map) {
        if (map[0][0] == 2) {
            return -1;
        }
        int n = map.length;
        int m = map[0].length;
        PriorityQueue<Node> heap = new PriorityQueue<>((a, b) -> a.cost - b.cost);
        boolean[][] visited = new boolean[n][m];
        add(map, 0, 0, 0, heap, visited);
        while (!heap.isEmpty()) {
            Node cur = heap.poll();
            if (cur.row == n - 1 && cur.col == m - 1) {
                return cur.cost;
            }
            add(map, cur.row - 1, cur.col, cur.cost, heap, visited);
            add(map, cur.row + 1, cur.col, cur.cost, heap, visited);
            add(map, cur.row, cur.col - 1, cur.cost, heap, visited);
            add(map, cur.row, cur.col + 1, cur.cost, heap, visited);
        }
        return -1;
    }

    public static void add(int[][] m, int i, int j, int pre, PriorityQueue<Node> heap, boolean[][] visited) {
        if (i >= 0 && i < m.length && j >= 0 && j < m[0].length && m[i][j] != 2 && !visited[i][j]) {
            heap.add(new Node(i, j, pre + (m[i][j] == 0 ? 2 : 1)));
            visited[i][j] = true;
        }
    }

    public static class Node {
        public int row;
        public int col;
        public int cost;

        public Node(int a, int b, int c) {
            row = a;
            col = b;
            cost = c;
        }
    }

}
