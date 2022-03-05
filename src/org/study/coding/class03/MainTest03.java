package org.study.coding.class03;

/**
 * @author phil
 * @since 2022-0305 10:38:32
 */
public class MainTest03 {

    public int largest1BorderedSquare(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }
        int[][] right = new int[grid.length][grid[0].length];
        int[][] down = new int[grid.length][grid[0].length];
        setBorder(grid, right, down);
        for (int size = Math.min(grid.length, grid[0].length); size != 0; size--) {
            if (hasSizeOfBorder(size, right, down)) {
                return size * size;
            }
        }

        return 0;
    }


    public void setBorder(int[][] grid, int[][] right, int[][] down) {
        int rowNum = grid.length;
        int colNum = grid[0].length;

        if (grid[rowNum - 1][colNum - 1] == 1) {
            right[rowNum - 1][colNum - 1] = 1;
            down[rowNum - 1][colNum - 1] = 1;
        }

        for (int row = rowNum - 2; row >= 0; row--) {
            if (grid[row][colNum - 1] == 1) {
                right[row][colNum - 1] = 1;
                down[row][colNum - 1] = down[row + 1][colNum - 1] + 1;
            }
        }


        for (int col = colNum - 2; col >= 0; col--) {
            if (grid[rowNum - 1][col] == 1) {
                right[rowNum - 1][col] = right[rowNum - 1][col + 1] + 1;
                down[rowNum - 1][col] = 1;
            }
        }

        for (int row = rowNum - 2; row >= 0; row--) {
            for (int col = colNum - 2; col >= 0; col--) {
                if (grid[row][col] == 1) {
                    right[row][col] = right[row][col + 1] + 1;
                    down[row][col] = down[row + 1][col] + 1;
                }
            }
        }

    }

    public boolean hasSizeOfBorder(int size, int[][] right, int[][] down) {
        for (int row = 0; row != right.length - size + 1; row++) {
            for (int col = 0; col != right[0].length - size + 1; col++) {
                if (right[row][col] >= size && down[row][col] >= size && right[row + size - 1][col] >= size
                        && down[row][col + size - 1] >= size) {
                    return true;
                }
            }
        }
        return false;
    }
}
