package org.study.coding.class03;

import java.util.Arrays;

/**
 * @author phil
 * @since 2022-0305 14:03:16
 */
public class MainTest05 {

    public static void main(String[] args) {
        System.out.println(new MainTest05().numRescueBoats1(new int[]{3, 5, 3, 4}, 5));
        System.out.println(new MainTest05().numRescueBoats2(new int[]{3, 5, 3, 4}, 5));
        System.out.println(new MainTest05().numRescueBoats3(new int[]{3, 5, 3, 4}, 5));
    }

    public int numRescueBoats1(int[] people, int limit) {
        if (people == null || people.length == 0) {
            return 0;
        }

        Arrays.sort(people);
        int len = people.length;
        if (people[len - 1] > limit) {
            return -1;
        }

        int less = -1;

        for (int i = len - 1; i >= 0; i--) {
            if (people[i] <= (limit >> 1)) {
                less = i;
                break;
            }
        }

        if (less == -1) {
            return len;
        }


        int left = less;
        int right = less + 1;

        int leftNoSolved = 0;

        while (left >= 0) {
            int solved = 0;
            while (right < len && people[left] + people[right] <= limit) {
                right++;
                solved++;
            }

            if (solved == 0) {
                left--;
                leftNoSolved++;
            } else {
                left = Math.max(-1, left - solved);

            }
        }

        // 左边参与匹配的数量
        int all = less + 1;

        // 左边匹配成功的数量
        int used = all - leftNoSolved;

        // 右边未参与匹配的数量
        int moveUnsolved = (len - all) - used;


        return used + ((leftNoSolved + 1) >> 1) + moveUnsolved;

    }

    public int numRescueBoats2(int[] people, int limit) {
        if (people == null || people.length == 0) {
            return 0;
        }

        int len = people.length;
        Arrays.sort(people);
        if (people[len - 1] > limit) {
            return -1;
        }

        int left = 0;
        int right = len - 1;
        int sum = 0;
        int ans = 0;
        while (left <= right) {
            sum = left == right ? people[left] : people[left] + people[right];

            if (sum > limit) {
                right--;
            } else {
                right--;
                left++;
            }

            ans++;
        }

        return ans;
    }


    public int numRescueBoats3(int[] weight, int limit) {
        if (weight == null || weight.length == 0) {
            return 0;
        }
        Arrays.sort(weight);
        int size = weight.length;

        // 搞不定
        if (weight[size - 1] > limit) {
            return -1;
        }

        int less = -1;
        for (int i = size - 1; i >= 0; i--) {
            if (weight[i] <= (limit >> 1)) {
                less = i;
                break;
            }
        }

        int left = less;
        int right = less + 1;

        int noUsed = 0;
        while (left >= 0) {
            int solved = 0;
            while (right < size && weight[left] + weight[right] <= (limit >> 1)) {
                right++;
                solved++;
            }

            if (solved == 0) {
                noUsed++;
                left--;
            } else {

                left = Math.max(-1, left - solved);
            }

        }

        int used = less + 1 - noUsed;
        int leftRest = (noUsed + 1) >> 1;
        int rightRest = size - (less + 1) - used;
        return used + leftRest + rightRest;
    }
}
