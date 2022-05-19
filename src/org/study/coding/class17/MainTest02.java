package org.study.coding.class17;

import java.util.Arrays;

public class MainTest02 {

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
        int num = 11;
        System.out.println(Arrays.toString(lessEqualNum(matrix, num)));

        System.out.println(kthSmallest2(matrix, 4));
    }

    public static int kthSmallest2(int[][] matrix, int kth) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return -1;
        }
        int rowNum = matrix.length;
        int colNum = matrix[0].length;

        int left = matrix[0][0];
        int right = matrix[rowNum - 1][colNum - 1];
        int ans = right;

        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            int[] ints = lessEqualNum(matrix, mid);
            if (ints[0] < kth) {
                left = mid + 1;
            } else {
                right = mid - 1;
                ans = ints[1];
            }

        }
        return ans;
    }

    public static int[] lessEqualNum(int[][] matrix, int num) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return new int[]{};
        }


        int row = 0;
        int col = matrix[0].length - 1;
        int lately = 0;
        int count = 0;
        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] > num) {
                col--;
            } else if (matrix[row][col] <= num) {
                count += col + 1;
                lately = matrix[row][col];
                row++;
            }
        }
        return new int[]{count, lately};
    }
}
