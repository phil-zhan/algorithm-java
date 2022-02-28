package org.study.system.class25;

import java.util.Stack;

/**
 * @author phil
 * @date 2021/8/27 16:25
 */
public class MainTest02 {

    public static int max2(int[] arr){

        int size = arr.length;
        int[] sums = new int[size];
        sums[0] = arr[0];
        for (int i = 1; i < size; i++) {
            sums[i] = sums[i-1] + arr[i];
        }

        int max = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < size; i++) {
            while (!stack.isEmpty() && arr[stack.peek()]>arr[i]){




            }
        }




        return max;

    }

}
