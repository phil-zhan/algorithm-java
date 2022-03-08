package org.study.coding.class04;

public class MainTest05 {

    public static void main(String[] args) {


        // 1 2 1 1
        // 1 3 2 1
        int[] test1 = { 3, 5, 4, 0 };
        System.out.println(candy1(test1));
        System.out.println(Code05_CandyProblem.candy1(test1));



        // 进阶
        int[] test2 = { 3, 0, 5, 5, 4, 4, 0 };
        System.out.println(candy2(test2));


        int[] test3 = { 3, 0, 5, 5, 4, 4, 0 };
        System.out.println(Code05_CandyProblem.candy3(test3));
    }
    public static int candy1(int[] ratings) {
        if (ratings == null || ratings.length == 0) {
            return 0;
        }

        int len = ratings.length;
        int[] left = new int[len];
        for (int i = 1; i < len; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            }
        }

        int[] right = new int[len];
        for (int i = len - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                right[i] = right[i + 1] + 1;
            }
        }

        int ans = 0;
        for (int i = 0; i < len; i++) {
            ans+=Math.max(left[i],right[i]);
        }

        return ans+len;
    }


    /**
     * 进阶
     * @since 2022-03-07 17:49:50
     */
    public static int candy2(int[] ratings) {
        if (ratings == null || ratings.length == 0) {
            return 0;
        }

        int len = ratings.length;
        int[] left = new int[len];
        for (int i = 1; i < len; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            }
            if (ratings[i] == ratings[i - 1]) {
                left[i] = left[i - 1];
            }
        }

        int[] right = new int[len];
        for (int i = len - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                right[i] = right[i + 1] + 1;
            }
            if (ratings[i] == ratings[i + 1] ){
                right[i] = right[i + 1];
            }
        }

        int ans = 0;
        for (int i = 0; i < len; i++) {
            ans+=Math.max(left[i],right[i]);
        }

        return ans+len;
    }
}
