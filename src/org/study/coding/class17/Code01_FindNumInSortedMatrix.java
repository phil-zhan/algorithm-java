package org.study.coding.class17;

/**
 * 给定一个每一行有序、每一列也有序，整体可能无序的二维数组，
 * 再给定一个数num，返回二维数组中有没有num这个数
 * <p>
 * <p>
 * 解法：
 * 从右上位置开始考虑
 * 如果当前位置的数字比要找的数字大。就向下左动指针 【当前位置下面的数字不可能】
 * 如果当前位置的数字比要找的数字小。就向下下动指针 【当前位置左边的数字不可能】
 * 在数组越界之前，能找到目标数，就直接返回true。否则返回false
 * <p>
 * 时间复杂度 O(n+m)
 *
 *
 * 当然，也可以考虑从左下角开始走
 *
 * @since 2022-03-18 03:13:46
 */
public class Code01_FindNumInSortedMatrix {

    public static boolean isContains(int[][] matrix, int K) {
        int row = 0;
        int col = matrix[0].length - 1;
        while (row < matrix.length && col > -1) {
            if (matrix[row][col] == K) {
                return true;
            } else if (matrix[row][col] > K) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {0, 1, 2, 3, 4, 5, 6},
                {10, 12, 13, 15, 16, 17, 18},
                {23, 24, 25, 26, 27, 28, 29},
                {44, 45, 46, 47, 48, 49, 50},
                {65, 66, 67, 68, 69, 70, 71},
                {96, 97, 98, 99, 100, 111, 122},
                {166, 176, 186, 187, 190, 195, 200},
                {233, 243, 321, 341, 356, 370, 380}
        };
        int K = 233;
        System.out.println(isContains(matrix, K));
    }

}
