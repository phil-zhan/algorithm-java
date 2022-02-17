package org.study.class48;

/**
 * @author phil
 * @date 2022/2/17 10:29
 */
public class Code18_907_SumSubarrayMins {


    public static void main(String[] args) {
        int sum = new Code18_907_SumSubarrayMins().sumSubarrayMins(new int[]{});
        System.out.println(sum);
    }

    public int sumSubarrayMins(int[] arr) {
        if (null == arr || arr.length == 0) {
            return 0;
        }

        int len = arr.length;

        int[] stack = new int[len];

        int si = -1;

        long ans = 0;

        for (int right = 0; right < len; right++) {
            while (si != -1 && arr[right] <= arr[stack[si]]) {
                // process
                long left = si == 0 ? -1 : stack[si - 1];
                // [left+1, right-1]
                // int cur = stack[si];
                ans += (stack[si] - left) * (long)(right - stack[si]) * (long)arr[stack[si]];
                ans %= 1000000007;
                si--;
            }

            stack[++si] = right;
        }

        // clear
        while (si != -1) {
            int left = si == 0 ? -1 : stack[si - 1];
            //int cur = stack[si];
            ans += (stack[si] - left) * (len - stack[si]) * arr[stack[si]];
            ans %= 1000000007;

            si--;
        }

        return (int)ans;
    }
}
