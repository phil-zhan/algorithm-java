package org.study.coding.class13;

/**
 * 本题测试链接 : <a href="https://leetcode.com/problems/bricks-falling-when-hit/">https://leetcode.com/problems/bricks-falling-when-hit/</a>
 * 有一个 m x n 的二元网格，其中 1 表示砖块，0 表示空白。砖块 稳定（不会掉落）的前提是：
 * 一块砖直接连接到网格的顶部，或者至少有一块相邻（4个方向之一）砖块稳定不会掉落时
 * 给你一个数组 hits ，这是需要依次消除砖块的位置。每当消除hits[i] = (rowi, coli) 位置上的砖块时，对应位置的砖块（若存在）会消失
 * 然后其他的砖块可能因为这一消除操作而掉落。一旦砖块掉落，它会立即从网格中消失（即，它不会落在其他稳定的砖块上）
 * 返回一个数组 result ，其中 result[i] 表示第 i 次消除操作对应掉落的砖块数目
 * 注意，消除可能指向是没有砖块的空白位置，如果发生这种情况，则没有砖块掉落。
 * <p>
 * 解法：
 * 考虑还有多少砖连在天花板上。用天花板上的砖的数量变化，来收集答案
 * 每次消除之后，看连接到天花板上的砖的数量减少了多少【假设减少了 x 块】。那么当前消除就会掉下了 x-1 。被消除的快不算在内
 * <p>
 * 只关注那个炮击位置有砖的【也就是炮击位置等于1的】。炮击位置没有1的直接忽略。
 * 根据炮击的位置。去将原数组中，对应位置是1的改为2.如果对应位置是0的。直接收集答案。【答案为0】
 * <p>
 * 然后逆序考察炮击位置。将炮击位置是2的，恢复为1.看连接到天花板的砖头多了多少。将增加的数量，减去自己。就是当前炮击，会掉下来的砖块数量
 * <p>
 * 其中原理：
 * 为什么要逆着来？
 * 因为如果顺着来的话，前面的打掉后。下面的砖块就会直接掉下来。后面的炮击，就很难收集答案。【炮击是从上到下，从左到右的】
 * <p>
 * <p>
 * 采用并查集来实现
 * 先将要炮轰的位置改为2
 * 初始化并查集。该合并的合并【只将是1的位置合并】
 * 额外设置一个结构。能根据并查集集合的父节点，查询该集合是否是天花板集合
 * 逆序检查炮轰的位置。将对应的位置改为1.然后去检查对应位置的上下左右，能否合成一个更大的集合
 * 合并完成后。看所有天花板集合的板砖数量增加了多少。将增加的量，减去1【炮轰的位置本身】。就是当前的答案【炮轰该位置会掉下去多少个砖头】
 *
 * @since 2022-03-15 09:12:54
 */
public class Code04_BricksFallingWhenHit {

    public static int[] hitBricks(int[][] grid, int[][] hits) {

        // 标记炮击位置
        for (int i = 0; i < hits.length; i++) {
            if (grid[hits[i][0]][hits[i][1]] == 1) {
                grid[hits[i][0]][hits[i][1]] = 2;
            }
        }

        // 并查集
        UnionFind unionFind = new UnionFind(grid);
        int[] ans = new int[hits.length];
        for (int i = hits.length - 1; i >= 0; i--) {

            // 炮击
            if (grid[hits[i][0]][hits[i][1]] == 2) {
                ans[i] = unionFind.finger(hits[i][0], hits[i][1]);
            }
        }
        return ans;
    }

    /**
     * 并查集
     * @since 2022-03-16 07:41:36
     */
    public static class UnionFind {
        private int N;
        private int M;

        // 有多少块砖，连到了天花板上
        private int cellingAll;

        // 原始矩阵，因为炮弹的影响，1 -> 2
        private int[][] grid;

        // cellingSet[i] = true; i 是头节点，所在的集合是天花板集合
        private boolean[] cellingSet;
        private int[] fatherMap;
        private int[] sizeMap;
        private int[] stack;

        public UnionFind(int[][] matrix) {
            initSpace(matrix);
            initConnect();
        }

        private void initSpace(int[][] matrix) {
            grid = matrix;
            N = grid.length;
            M = grid[0].length;
            int all = N * M;
            cellingAll = 0;
            cellingSet = new boolean[all];
            fatherMap = new int[all];
            sizeMap = new int[all];
            stack = new int[all];
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < M; col++) {
                    if (grid[row][col] == 1) {
                        int index = row * M + col;
                        fatherMap[index] = index;
                        sizeMap[index] = 1;
                        if (row == 0) {
                            cellingSet[index] = true;
                            cellingAll++;
                        }
                    }
                }
            }
        }

        private void initConnect() {
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < M; col++) {
                    union(row, col, row - 1, col);
                    union(row, col, row + 1, col);
                    union(row, col, row, col - 1);
                    union(row, col, row, col + 1);
                }
            }
        }

        private int find(int row, int col) {
            int stackSize = 0;
            int index = row * M + col;
            while (index != fatherMap[index]) {
                stack[stackSize++] = index;
                index = fatherMap[index];
            }
            while (stackSize != 0) {
                fatherMap[stack[--stackSize]] = index;
            }
            return index;
        }

        private void union(int r1, int c1, int r2, int c2) {

            // 验证。只有两个都是1的位置才能连接
            if (valid(r1, c1) && valid(r2, c2)) {

                int father1 = find(r1, c1);
                int father2 = find(r2, c2);

                if (father1 != father2) {
                    int size1 = sizeMap[father1];
                    int size2 = sizeMap[father2];

                    boolean status1 = cellingSet[father1];
                    boolean status2 = cellingSet[father2];
                    if (size1 <= size2) {
                        fatherMap[father1] = father2;
                        sizeMap[father2] = size1 + size2;

                        // 两个集合。如果都是天花板集合或都不是天花板集合。都不需要改变
                        if (status1 ^ status2) {
                            // 设置天花板集合
                            cellingSet[father2] = true;

                            // 不是天花板集合的那个加进来
                            cellingAll += status1 ? size2 : size1;
                        }
                    } else {
                        fatherMap[father2] = father1;
                        sizeMap[father1] = size1 + size2;

                        if (status1 ^ status2) {
                            cellingSet[father1] = true;
                            cellingAll += status1 ? size2 : size1;
                        }
                    }
                }
            }
        }

        private boolean valid(int row, int col) {
            return row >= 0 && row < N && col >= 0 && col < M && grid[row][col] == 1;
        }

        public int cellingNum() {
            return cellingAll;
        }

        public int finger(int row, int col) {
            grid[row][col] = 1;
            int cur = row * M + col;

            // 直接打到第一行
            if (row == 0) {
                cellingSet[cur] = true;
                cellingAll++;
            }

            fatherMap[cur] = cur;
            sizeMap[cur] = 1;
            // 记录当前天花板集合的数量
            int pre = cellingAll;
            union(row, col, row - 1, col);
            union(row, col, row + 1, col);
            union(row, col, row, col - 1);
            union(row, col, row, col + 1);

            // 连接完成之后。检查天花板上砖头数量的变化
            int now = cellingAll;

            // 如果是在第 0 行。【可以画图看】
            if (row == 0) {
                return now - pre;
            } else {

                // 如果不是天花板集合。还需要减去自己
                return now == pre ? 0 : now - pre - 1;
            }
        }
    }

}
