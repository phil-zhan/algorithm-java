package org.study.coding.class09;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author phil
 * @date 2022-04-06 15:43:38
 */
public class MainTest04 {

    public static void main(String[] args) {
        int testTimes = 10000;
        int maxLen = 1000;
        int maxWeight = 500;
        int maxHeight = 500;
        for (int times = 0; times < testTimes; times++) {
            int[][] matrix = randomMatrix(maxLen, maxWeight, maxHeight);
            int ans1 = maxEnvelopes(matrix);
            int ans2 = Code04_EnvelopesProblem.maxEnvelopes(matrix);
            if (ans1 != ans2) {
                System.out.println("!!!!!!!!!!Oops!!!!!!!!!!");
                System.out.println(Arrays.toString(matrix));
                System.out.println("ans1:" + ans1);
                System.out.println("ans2:" + ans2);
                System.out.println("!!!!!!!!!!Oops!!!!!!!!!!");
            }
        }
        System.out.println("!!!!!!!!!!Nice!!!!!!!!!!");
    }

    public static int maxEnvelopes(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        Envelope[] envelopes = sort(matrix);
        int[] end = new int[envelopes.length];
        end[0] = envelopes[0].height;

        int endRight = 0;
        int left = 0;
        int right = 0;
        int mid = 0;

        int max = 1;
        for (int index = 1; index < envelopes.length; index++) {
            left = 0;
            right = endRight;
            while (left <= right) {
                mid = left + ((right - left) >> 1);
                if (envelopes[index].height > end[mid]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            endRight = Math.max(endRight, left);
            end[left] = envelopes[index].height;
            max = Math.max(max, left + 1);

        }
        return max;
    }

    public static Envelope[] sort(int[][] matrix) {
        Envelope[] envelopes = new Envelope[matrix.length];
        int ei = 0;
        for (int[] ints : matrix) {
            envelopes[ei++] = new Envelope(ints[0], ints[1]);
        }
        Arrays.sort(envelopes, new EnvelopsComparator());
        return envelopes;
    }

    public static class Envelope {
        public int weight;
        public int height;

        public Envelope(int weight, int height) {
            this.weight = weight;
            this.height = height;
        }
    }

    public static class EnvelopsComparator implements Comparator<Envelope> {
        @Override
        public int compare(Envelope o1, Envelope o2) {
            return o1.weight != o2.weight ? o1.weight - o2.weight : o2.height - o1.height;
        }
    }

    public static int[][] randomMatrix(int maxLen, int maxWeight, int maxHeight) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[][] matrix = new int[len][2];
        for (int index = 0; index < len; index++) {
            matrix[index][0] = (int) (Math.random() * (maxWeight + 1));
            matrix[index][1] = (int) (Math.random() * (maxHeight + 1));
        }
        return matrix;
    }
}
