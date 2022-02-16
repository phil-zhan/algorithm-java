package org.study.class48;

/**
 * @author phil
 * @date 2022/2/16 16:12
 */
public class Code14_84_LargestRectangleArea {
    public int largestRectangleArea(int[] heights) {
        int len = heights.length;

        int[] stack = new int[len];
        int si = -1;
        int maxArea = 0;
        for (int i = 0; i < len; i++) {
            while (si != -1 && heights[i] <= heights[stack[si]]) {

                //处理栈顶元素
                int left = si == 0 ? -1 : stack[si - 1];
                maxArea = Math.max(maxArea, heights[stack[si]] * (i - left - 1));
                si--;
            }

            stack[++si] = i;
        }

        // 清理
        while (si != -1) {
            int right = heights.length;
            int left = si == 0 ? -1 : stack[si - 1];
            maxArea = Math.max(maxArea, heights[stack[si]] * (right - left - 1));

            si--;
        }

        return maxArea;
    }
}
