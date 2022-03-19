package org.study.coding.class18;

/**
 * 2. 在给定的二维二进制数组A中，存在两座岛。（岛是由四面相连的 1 形成的一个最大组。）现在，我们可以将0变为1，以使两座岛连接起来，变成一座岛。
 * 返回必须翻转的0 的最小数目。（可以保证答案至少是1）
 * Leetcode题目：https://leetcode.com/problems/shortest-bridge/
 * <p>
 * <p>
 * 解法。
 * 准备一个辅助空间
 * 只把原数组中的一片“1”挪下来
 * 像宽度优先那样，从这一片“1”开始，去广播周围。
 * 也就是往外扩散，认为自己到自己的距离是1
 * 到下一层的距离是上一层的距离加1
 * 直到把整个矩阵都扩散满【只能上下左右扩，不能斜着扩】
 * <p>
 * 第二片1，也经历同样的扩散过程。也得到一个扩散矩阵
 * <p>
 * <p>
 * 两个辅助扩散矩阵，对应的位置，相加之后，和越小的，就是越近的桥连点【也就是通道经过改点的话，至少要打通多少距离，才能让两片指定位置连接起来】
 * 具体的通道距离。是两个对应位置的和再减去3。就是需要挖的通道的距离【对应位置肯定在通道上】
 * <p>
 * 这中间的层数就是需要打通的最短距离
 *
 * @since 2022-03-19 03:25:50
 */
public class Code02_ShortestBridge {

    public static int shortestBridge(int[][] m) {
        int N = m.length;
        int M = m[0].length;
        int all = N * M;

        // 当前是第几偏1的区域【总的也就两片区域】
        int island = 0;

        // curs里面的点【当前队列】
        // nexts【下一级队列】
        int[] curs = new int[all];
        int[] nexts = new int[all];

        // 存储两片1的辅助结构
        int[][] records = new int[2][all];

        // 遍历二维数组
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (m[i][j] == 1) {
                    // 当前位置发现了1！

                    // 把这一片的1，都变成2，同时，抓上来了，这一片1组成的初始队列
                    // curs, 把这一片的1到自己的距离，都设置成1了，records
                    int queueSize = infect(m, i, j, N, M, curs, 0, records[island]);

                    // 当前是第几层
                    int V = 1;

                    // 队列不为空，就继续广播
                    while (queueSize != 0) {

                        V++;

                        // curs里面的点，上下左右，records[点]==0，【没有算过距离】
                        // nexts【下一级队列】
                        // queueSize 下一级队列的大小
                        // all :
                        queueSize = bfs(N, M, all, V, curs, queueSize, nexts, records[island]);

                        // 让nexts队列直接赋值给当前队列
                        // 复用队列空间
                        int[] tmp = curs;
                        curs = nexts;
                        nexts = tmp;

                    }
                    // 接下来再遇到1就是下一片区域了
                    island++;
                }
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < all; i++) {
            min = Math.min(min, records[0][i] + records[1][i]);
        }
        return min - 3;
    }


    /**
     * 当前来到m[i][j] , 总行数是N，总列数是M
     * m[i][j]感染出去(找到这一片岛所有的1),把每一个1的坐标，放入到int[] curs队列！【二维变一维】
     * <p>
     * 如：
     * 1 (a,b) -> curs[index++] = (a * M + b)
     * 1 (c,d) -> curs[index++] = (c * M + d)
     * <p>
     * 二维已经变成一维了， 1 (a,b) -> a * M + b
     * <p>
     * 设置距离record[a * M +b ] = 1
     * <p>
     * <p>
     * curs：存的是所有1的坐标是什么【转化成1维的】。curs中下标没意义
     * record：存的是具体的坐标对应的距离 。record[i] = k .表示 i 坐标所对应的点距离题目所给 1 的那一片区域的距离是 k
     *
     *
     * 返回的是队列的长度。也及时这一片中，有多少个1
     *
     * @since 2022-03-19 04:16:33
     */
    public static int infect(int[][] m, int i, int j, int N, int M, int[] curs, int index, int[] record) {
        if (i < 0 || i == N || j < 0 || j == M || m[i][j] != 1) {
            return index;
        }
        // m[i][j] 不越界，且m[i][j] == 1
        // 保证不走回头路
        m[i][j] = 2;

        // 二维坐标转化一维坐标
        int p = i * M + j;
        record[p] = 1;

        // 收集到不同的1
        curs[index++] = p;

        // 感染上下左右
        index = infect(m, i - 1, j, N, M, curs, index, record);
        index = infect(m, i + 1, j, N, M, curs, index, record);
        index = infect(m, i, j - 1, N, M, curs, index, record);
        index = infect(m, i, j + 1, N, M, curs, index, record);

        return index;
    }


    /**
     * // 二维原始矩阵中，N总行数，M总列数
     * // all 总 all = N * M
     * // V 要生成的是第几层 curs V-1 nexts V
     * // record里面拿距离
     *
     * @since 2022-03-19 04:16:50
     */
    public static int bfs(int N, int M, int all, int V,
                          int[] curs, int size, int[] nexts, int[] record) {

        // 我要生成的下一层队列成长到哪了？
        int nexti = 0;
        for (int i = 0; i < size; i++) {

            // 通过一维坐标，直接推出上下左右

            // curs[i] -> 一个位置
            int up = curs[i] < M ? -1 : curs[i] - M;
            int down = curs[i] + M >= all ? -1 : curs[i] + M;

            // 摸完M。等于0.说明当前位置没有左【可以举例推边界】
            int left = curs[i] % M == 0 ? -1 : curs[i] - 1;
            int right = curs[i] % M == M - 1 ? -1 : curs[i] + 1;


            if (up != -1 && record[up] == 0) {
                record[up] = V;
                nexts[nexti++] = up;
            }
            if (down != -1 && record[down] == 0) {
                record[down] = V;
                nexts[nexti++] = down;
            }
            if (left != -1 && record[left] == 0) {
                record[left] = V;
                nexts[nexti++] = left;
            }
            if (right != -1 && record[right] == 0) {
                record[right] = V;
                nexts[nexti++] = right;
            }
        }
        return nexti;
    }

}
