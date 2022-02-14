package org.study.class24;

import java.util.LinkedList;

/**
 * @author phil
 * @date 2021/8/20 10:18
 */
public class MainTest02 {

    public static int num(int[] arr, int sum) {
        int count = 0;
        int len = arr.length;
        LinkedList<Integer> maxQueue = new LinkedList<>();
        LinkedList<Integer> minQueue = new LinkedList<>();

        int right = 0;
        for (int left = 0; left < len; left++) {
            while (right < len) {
                while (!maxQueue.isEmpty() && arr[right] >= arr[maxQueue.peekLast()]) {
                    maxQueue.pollLast();
                }
                maxQueue.addLast(right);


                while (!minQueue.isEmpty() && arr[right] <= arr[minQueue.peekLast()]) {
                    minQueue.pollLast();
                }
                minQueue.addLast(right);


                if (arr[maxQueue.peekFirst()] - arr[minQueue.peekFirst()] > sum) {
                    break;
                }

                right++;
            }

            count += right - left;
            if (left == maxQueue.peekFirst()){
                maxQueue.pollFirst();
            }

            if (left == minQueue.peekFirst()){
                minQueue.pollFirst();
            }
        }


        return count;
    }

    public static void main(String[] args) {
        int count = num(new int[]{185, -89, 34, 87, -42, -171, -141, -99, -12, 74, 122, -174, -46, -122, 102, -12, 96, 66,
                162, 163, -64, 74, 118, 129, -42, -54, -174, -33, -143, -24, 2, 38, -23, -60, -113, -55, 164, -166, -8,
                26, 48, 35, -83, 54, -28, -12, 98, -92, -11, -30, 54, -100, -61}, 113);
        System.out.println(count);
    }
}
