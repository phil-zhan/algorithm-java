package org.study.system.class48;

/**
 * @author phil
 * @date 2022/2/16 16:34
 */
public class Code15_85_MaximalRectangle {

    public static void main(String[] args) {
        int max = new Code15_85_MaximalRectangle().maximalRectangle(new char[][]{
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}});
        System.out.println(max);
    }

    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int colNum = matrix[0].length;

        int[] heights = new int[colNum];
        int maxArea = 0;
        for (char[] chars : matrix) {
            for (int col = 0; col < colNum; col++) {
                heights[col] = chars[col] == '0' ? 0 : heights[col] + 1;
            }

            // 当前行为底
            maxArea = Math.max(maxArea, getMaxAreaByHeights(heights));
        }

        return maxArea;
    }

    public int getMaxAreaByHeights(int[] heights) {
        int len = heights.length;
        int maxArea = 0;

        int[] stack = new int[len];
        int si = -1;
        for (int right = 0; right < len; right++) {
            while (si != -1 && heights[right] <= heights[stack[si]]) {
                int left = si == 0 ? -1 : stack[si - 1];
                maxArea = Math.max(maxArea, heights[stack[si]] * (right - left - 1));
                si--;
            }
            stack[++si] = right;
        }

        // 清理
        while (si != -1) {
            int left = si == 0 ? -1 : stack[si - 1];
            maxArea = Math.max(maxArea, heights[stack[si]] * (len - left - 1));
            si--;
        }

        return maxArea;
    }
}
