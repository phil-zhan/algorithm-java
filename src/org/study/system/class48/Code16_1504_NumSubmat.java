package org.study.system.class48;

/**
 * @author phil
 * @date 2022/2/16 16:58
 */
public class Code16_1504_NumSubmat {

    public static void main(String[] args) {
        int ansNum = new Code16_1504_NumSubmat().numSubmat(new int[][]{
                {1, 0, 1},
                {1, 1, 0},
                {1, 1, 0}
        });
        System.out.println(ansNum);
    }

    public int numSubmat(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0] == null || mat[0].length == 0) {
            return 0;
        }
        int colNum = mat[0].length;

        int ansNum = 0;
        int[] heights = new int[colNum];

        for (int[] ints : mat) {
            for (int col = 0; col < colNum; col++) {
                heights[col] = ints[col] == 0 ? 0 : heights[col] + 1;
            }
            // collect
            ansNum += getAnsNumByHeights(heights);
        }

        // return
        return ansNum;
    }


    public int getAnsNumByHeights(int[] heights) {
        int len = heights.length;
        int ansNum = 0;

        // 辅助栈
        int[] stack = new int[len];
        int si = -1;

        for (int right = 0; right < len; right++) {
            while (si != -1 && heights[right] <= heights[stack[si]]) {
                if (heights[right] < heights[stack[si]]) {
                    int left = si == 0 ? -1 : stack[si - 1];

                    // 抓到左右两边不能达到的高度的最大值。用于计算差值
                    int down = Math.max(left == -1 ? 0 : heights[left], heights[right]);

                    // calculate
                    ansNum += (heights[stack[si]] - down) * calculate(right - left - 1);
                }

                // 相等的情况只出栈。由后面的来处理
                si--;
            }

            stack[++si] = right;
        }

        // 清理
        while (si != -1) {

            // left
            int left = si == 0 ? -1 : stack[si - 1];

            // 抓到左右两边不能达到的高度的最大值。用于计算差值
            int down = left == -1 ? 0 : heights[left];

            // calculate
            ansNum += (heights[stack[si]] - down) * (calculate(len - left - 1));

            si--;
        }

        return ansNum;
    }

    public int calculate(int width){
        // 计算宽度为 width 的矩形包含多少个矩形
        return ((width * (width + 1)) >> 1);
    }
}
