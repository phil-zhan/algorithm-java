package org.study.system.class40;

/**
 * @author phil
 * @date 2021/10/19 16:03
 */
public class MainTest05 {

    public static void spiralOrderPrint(int[][] matrix) {
        if (null == matrix || matrix.length == 0 || matrix[0].length == 0) {
            return;
        }

        int startRow = 0;
        int startCol = 0;
        int endRow = matrix.length - 1;
        int endCol = matrix[0].length - 1;


        while (startRow <= endRow && startCol <= endCol){

            printEdge(matrix,startRow++,startCol++,endRow--,endCol--);
        }
    }

    private static void printEdge(int[][] matrix,int startRow, int startCol, int endRow, int endCol) {
        if(startRow == endRow){
            for (int i = startCol; i <= endCol; i++) {
                System.out.print(matrix[startRow][i] + " ");
            }
        }else if(startCol == endCol){
            for (int i = startRow; i <=endRow ; i++) {
                System.out.print(matrix[i][startCol] + " ");
            }
        }else {
            int curRow = startRow;
            int curCol = startCol;
            while (curCol < endCol){
                System.out.print( matrix[curRow][curCol++]+ " ");
            }
            while (curRow < endRow){
                System.out.print( matrix[curRow++][curCol]+ " ");
            }

            while (curCol > startCol){
                System.out.print( matrix[curRow][curCol--]+ " ");
            }

            while (curRow > startRow){
                System.out.print( matrix[curRow--][curCol]+ " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                { 1, 2, 3, 4 },
                { 5, 6, 7, 8 },
                { 9, 10, 11, 12 },
                { 13, 14, 15, 16 } };
        spiralOrderPrint(matrix);

    }
}
