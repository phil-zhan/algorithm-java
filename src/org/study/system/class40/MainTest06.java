package org.study.system.class40;

/**
 * @author phil
 * @date 2021/10/19 16:03
 */
public class MainTest06 {

    public static void rotate(int[][] matrix) {
        if (null == matrix || matrix.length == 0) {
            return;
        }
        int startRow = 0;
        int startCol = 0;
        int endRow = matrix.length - 1;
        int endCol = matrix[0].length - 1;

        while (startRow <= endRow) {
            rotateEdge(matrix, startRow++, startCol++, endRow--, endCol--);
        }
    }

    private static void rotateEdge(int[][] matrix, int startRow, int startCol, int endRow, int endCol) {
        int temp;
        for (int i = 0; i < endCol - startCol; i++) {
            temp = matrix[startRow][startCol + i];
            matrix[startRow][startCol + i] = matrix[endRow - i][startCol];
            matrix[endRow - i][startCol] = matrix[endRow][endCol - i];
            matrix[endRow][endCol - i] = matrix[startRow + i][endCol];
            matrix[startRow + i][endCol] = temp;

        }
    }


    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        printMatrix(matrix);
        rotate(matrix);
        System.out.println("=========");
        printMatrix(matrix);

    }
}
