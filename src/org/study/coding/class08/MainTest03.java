package org.study.coding.class08;

/**
 * @author phil
 * @date 2022-04-05 09:37:20
 */
public class MainTest03 {
    public static boolean findWord11(char[][] matrix, String word) {
        // 条件过来
        if (word == null || word.length() == 0) {
            return true;
        }

        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return false;
        }

        char[] str = word.toCharArray();
        boolean ans = false;

        // 枚举所有可能的起点
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                ans = canLoop(matrix, row, col, 0, str);

                // 能搞定，就直接退出
                if (ans) {
                    break;
                }
            }
            // 能搞定，就直接退出
            if (ans) {
                break;
            }
        }

        return ans;
    }

    // 当前在 [row][col]
    // 能不能搞定 str[k...n-1]
    public static boolean canLoop(char[][] matrix, int row, int col, int k, char[] str) {
        if (k == str.length) {
            return true;
        }

        if (row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length || matrix[row][col] != str[k]) {
            return false;
        }

        return canLoop(matrix, row + 1, col, k + 1, str) || canLoop(matrix, row - 1, col, k + 1, str) ||
                canLoop(matrix, row, col + 1, k + 1, str) || canLoop(matrix, row, col - 1, k + 1, str);
    }


    public static boolean findWord12(char[][] matrix, String word) {

        if (word == null || word.length() == 0) {
            return true;
        }

        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return false;
        }

        char[] str = word.toCharArray();

        int rowNum = matrix.length;
        int colNum = matrix[0].length;
        int len = word.length();

        // dp[i][j][k]表示：必须以m[i][j]这个字符结尾的情况下，能不能找到w[0...k]这个前缀串
        // 也可以在递归中使用。当前在i、j位置。能不能搞定前面的[0...k]
        boolean[][][] dp = new boolean[rowNum][colNum][len + 1];


        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                dp[row][col][0] = matrix[row][col] == str[0];
            }
        }

        for (int k = 1; k < len; k++) {
            for (int row = 0; row < rowNum; row++) {
                for (int col = 0; col < colNum; col++) {

                    dp[row][col][k] = matrix[row][col] == str[k] && checkPrevious(dp, row, col, k);
                }
            }
        }

        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                if (dp[row][col][len - 1]) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean checkPrevious(boolean[][][] dp, int row, int col, int k) {

        boolean up = row > 0 && dp[row - 1][col][k - 1];
        boolean down = row < dp.length - 1 && dp[row + 1][col][k - 1];
        boolean left = col > 0 && dp[row][col - 1][k - 1];
        boolean right = col < dp[0].length - 1 && dp[row][col + 1][k - 1];


        return up || down || left || right;
    }

    public static boolean findWord21(char[][] matrix, String word) {
        // 条件过来
        if (word == null || word.length() == 0) {
            return true;
        }

        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return false;
        }

        char[] str = word.toCharArray();
        boolean ans = false;

        // 枚举所有可能的起点
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                ans = noLoop(matrix, row, col, 0, str);

                // 能搞定，就直接退出
                if (ans) {
                    break;
                }
            }
            // 能搞定，就直接退出
            if (ans) {
                break;
            }
        }

        return ans;
    }

    /**
     * 牵涉到改原始矩阵，这里做成缓存.不过会有点麻烦，需要用到第三个状态来标识当前有没有计算过。
     *
     * @since 2022-04-05 10:34:18
     */
    public static boolean noLoop(char[][] matrix, int row, int col, int k, char[] str) {
        if (k == str.length) {
            return true;
        }

        if (row == -1 || row == matrix.length || col == -1 || col == matrix[0].length || str[k] != matrix[row][col]) {
            return false;
        }

        matrix[row][col] = 0;

        boolean ans = noLoop(matrix, row - 1, col, k + 1, str)
                || noLoop(matrix, row + 1, col, k + 1, str)
                || noLoop(matrix, row, col - 1, k + 1, str)
                || noLoop(matrix, row, col + 1, k + 1, str);

        matrix[row][col] = str[k];

        return ans;
    }

    public static void main(String[] args) {
        char[][] m = {{'a', 'b', 'z'},
                {'c', 'd', 'o'},
                {'f', 'e', 'o'},};
        String word1 = "zoooz";
        String word2 = "zoo";
        // 可以走重复路的设定
        // 递归
        System.out.println(findWord11(m, word1));
        System.out.println(findWord11(m, word2));
        // dp
        System.out.println(findWord12(m, word1));
        System.out.println(findWord12(m, word2));

        // 不可以走重复路的设定
        System.out.println(findWord21(m, word1));
        System.out.println(findWord21(m, word2));

    }
}
