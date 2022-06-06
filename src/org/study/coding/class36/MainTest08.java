package org.study.coding.class36;

import java.util.Arrays;

public class MainTest08 {

    public static void main(String[] args) {
        System.out.println(new MainTest08().minBoat(new int[]{1, 2, 3, 4}, 4));
        System.out.println(Code08_MinBoatEvenNumbers.minBoat(new int[]{1, 2, 3, 4}, 4));
    }

    public int minBoat(int[] weights, int limit) {
        int oddNum = 0;
        int evenNum = 0;


        // odd + odd = even
        // odd + even = odd
        // even + even = even
        for (int i = 0; i < weights.length; i++) {
            if ((weights[i] & 1) == 0) {
                evenNum++;
            } else {
                oddNum++;
            }
        }

        int[] odds = new int[oddNum];
        int[] evens = new int[evenNum];


        for (int i = weights.length - 1; i >= 0; i--) {
            if ((weights[i] & 1) == 0) {
                evens[--evenNum] = weights[i];
            } else {
                odds[--oddNum] = weights[i];
            }
        }

        // 注意处理返回-1【也就是搞不定的情况】
        int oddAns = numRescueBoats(odds, limit);
        int evenAns = numRescueBoats(evens, limit);
        return  oddAns == -1 || evenAns == -1 ? -1 : oddAns+evenAns;
    }


    public int numRescueBoats(int[] weights, int limit) {
        if (weights == null || weights.length == 0) {
            return 0;
        }

        Arrays.sort(weights);

        int size = weights.length;
        if (weights[size - 1] > limit) {
            return -1;
        }

        int less = -1;
        for (int i = size - 1; i >= 0; i--) {
            if (weights[i] <= (limit >> 1)) {
                less = i;
                break;
            }
        }

        int left = less;
        int right = less + 1;
        int noSolved = 0;
        while (left >= 0) {
            int solved = 0;
            while (right < size && weights[left] + weights[right] <= limit) {
                right++;
                solved++;
            }

            if (solved == 0) {
                noSolved++;
                left--;
            } else {
                left = Math.max(-1, left - solved);
            }
        }
        int used = (less + 1) - noSolved;

        // 两人一船。向上取整
        int leftRest = (noSolved + 1) >> 1;

        int rightRest = size - (less + 1) - used;

        return used + leftRest + rightRest;
    }
}
