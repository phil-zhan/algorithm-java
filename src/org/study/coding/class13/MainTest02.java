package org.study.coding.class13;

public class MainTest02 {
    public static void main(String[] args) {
        MainTest02 mainTest02 = new MainTest02();
        System.out.println(mainTest02.findMinMoves(new int[]{1, 0, 5}));
        System.out.println(mainTest02.findMinMoves(new int[]{0, 3, 0}));
        System.out.println(mainTest02.findMinMoves(new int[]{0, 2, 0}));

    }

    public int findMinMoves(int[] machines) {
        if (machines == null || machines.length == 0) {
            return 0;
        }

        int sum = 0;
        for (int machine : machines) {
            sum += machine;
        }
        int size = machines.length;

        if (sum % size != 0) {
            return -1;
        }

        int avg = sum / size;
        int leftSum = 0;
        int ans = 0;
        for (int i = 0; i < size; i++) {
            int leftRest = leftSum - (i * avg);
            int rightRest = sum - leftSum - machines[i] - ((size - i - 1) * avg);
            if (leftRest < 0 && rightRest < 0) {
                ans = Math.max(ans, Math.abs(leftRest) + Math.abs(rightRest));
            } else {
                ans = Math.max(ans, Math.max(Math.abs(leftRest), Math.abs(rightRest)));
            }

            leftSum += machines[i];
        }
        return ans;
    }
}
