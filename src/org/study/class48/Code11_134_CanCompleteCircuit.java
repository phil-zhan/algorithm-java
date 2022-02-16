package org.study.class48;

import java.util.LinkedList;

/**
 * @author phil
 * @date 2022/2/15 17:07
 */
public class Code11_134_CanCompleteCircuit {
    public int canCompleteCircuit(int[] gas, int[] cost) {

        int length = gas.length;
        int M = length << 1;
        // 计算差值 gas[i] - cost[i]
        // 做出一个差值数组。在这个数组内，只要中途累加和小于0，就会熄火。
        int[] arr = new int[M];
        for (int i = 0; i < length; i++) {
            arr[i] = gas[i] - cost[i];
            arr[i + length] = gas[i] - cost[i];
        }

        for (int i = 1; i < M; i++) {
            arr[i] += arr[i - 1];
        }

        LinkedList<Integer> w = new LinkedList<>();

        // 第一个窗口
        for (int i = 0; i < length; i++) {
            while (!w.isEmpty() && arr[w.peekLast()] >= arr[i]) {
                w.pollLast();
            }
            w.addLast(i);
        }
        // boolean[] ans = new boolean[length];
        int ans = -1;

        // i ：左边界
        // j : 右边界
        // offset : 左边界的前一个值。用于处理累加和
        for (int offset = 0, i = 0, j = length; j < M;  j++) {
            if (arr[w.peekFirst()] - offset >= 0) {
                //ans[i] = true;
                ans = i;
                break;
            }

            if (w.peekFirst() == i) {
                w.pollFirst();
            }

            // 下一个元素入窗口
            while (!w.isEmpty() && arr[w.peekLast()] >= arr[j]) {
                w.pollLast();
            }
            w.addLast(j);

            // 左边别右移
            offset = arr[i++];
        }
        return ans;
    }
}
