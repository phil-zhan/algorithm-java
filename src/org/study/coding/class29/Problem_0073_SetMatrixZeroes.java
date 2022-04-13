package org.study.coding.class29;

/**
 * 73. 矩阵置零
 * 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。
 * <p>
 * 解法：
 * 不能边遍历边更新，会都变成0的
 * <p>
 * 正确的做法1
 * 准备一张行表row[].boolean类型
 * row[i] ： 表示第i行要不要都变0
 * <p>
 * 再准备一张列表col[].boolean类型
 * col[i] ： 表示第i列要不要都变0
 * <p>
 * 先遍历数组，
 * 将这两张表填好
 * 最后再根据这两张表，去更新原表
 * <p>
 * 该方法的缺点是空间使用太大了。
 * <p>
 * 先忽略掉第0行和第0列的值，就用它来标记对应的列和对应的行是否要全变0【也就是替换掉之前的boolean类型的辅助数组】
 * 遍历数组，从第一行第一列开始。如果某个位置是0，假设 matrix[2][3] = 0
 * 就将 matrix[2][0]=0 matrix[0][3]=0 【也就是将当前0对应到第0行和第0列的位置设置为0，表示当前列和当前行都要全变成0】
 * <p>
 * 但是这个前提是在第0行和第0列没有0的情况下
 * <p>
 * 遍历完成后，根据第0行和第0列的数据，将对应的列和对应的行刷成0.
 * <p>
 * <p>
 * 如果第0行或第0列有0的情况下，在刷的时候，不知道要不要把第0行或第0列也全部刷成0。
 * 此时我们需要增加两个boolean的变量，表示第0行或第0列有没有0.
 * 在刷的时候，先不要刷第0行或第0列，最后再根据这两个boolean类型的变量，去刷第0行或第0列的
 * <p>
 * perfect!!!!!!!!
 *
 * @since 2022-04-13 23:38:50
 */
public class Problem_0073_SetMatrixZeroes {

    public static void setZeroes1(int[][] matrix) {

        // 两个boolean的变量，表示第0行和第0列有没有0
        boolean row0Zero = false;
        boolean col0Zero = false;
        int i = 0;
        int j = 0;
        for (i = 0; i < matrix[0].length; i++) {
            if (matrix[0][i] == 0) {
                row0Zero = true;
                break;
            }
        }
        for (i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                col0Zero = true;
                break;
            }
        }

        // 遍历数组，将第0行或第0列的辅助数据填充好
        for (i = 1; i < matrix.length; i++) {
            for (j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        // 将除第0行和第0列之外的位置，根据0行0列 的0的位置，将对应的列和对应的行刷成0
        for (i = 1; i < matrix.length; i++) {
            for (j = 1; j < matrix[0].length; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // 最后单独考虑0行和0列的值要不要全变0
        if (row0Zero) {
            for (i = 0; i < matrix[0].length; i++) {
                matrix[0][i] = 0;
            }
        }
        if (col0Zero) {
            for (i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    /**
     * 更省的方法
     *
     * @since 2022-04-14 07:34:40
     */
    public static void setZeroes2(int[][] matrix) {

        // 只用一个变量表示0列要不要全变0，另外一个行变量用 matrix[0][0] 的位置来代替
        boolean col0 = false;

        // i、j指针复用
        int i = 0;
        int j = 0;
        for (i = 0; i < matrix.length; i++) {

            // j 从0开始
            for (j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    if (j == 0) {
                        col0 = true;
                    } else {
                        matrix[0][j] = 0;
                    }
                }
            }
        }
        for (i = matrix.length - 1; i >= 0; i--) {

            // 更新的时候，第0列要不要变0，单独考虑
            for (j = 1; j < matrix[0].length; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }


        // 单独考虑0列
        if (col0) {
            for (i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }

}
