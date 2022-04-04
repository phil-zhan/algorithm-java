package org.study.coding.class23;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 3. 定义什么是可整合数组：一个数组排完序之后，除了最左侧的数外，有arr[i] = arr[i-1]+1
 * 则称这个数组为可整合数组比如{5,1,2,4,3}、{6,2,3,1,5,4}都是可整合数组，返回arr中最长可整合子数组的长度
 * <p>
 * 解法：
 * 不能将整个数组排序
 * 不能改变原数组的相对次序
 * <p>
 * 也不能用窗口。窗口需要有单调性
 * <p>
 * 暴力解：【O( N^3 *logN)】
 * 枚举所有的子数组。然后将子数组拷贝出来，将拷贝出来的子数组排序，然后再去考察其是否满足题目要求
 * <p>
 * 可整合：
 * 1）数组中不能有重复数字
 * 2）数组的最大值减去最小值就等于个数减1
 *
 * @since 2022-03-28 20:43:13
 */
public class Code03_LongestIntegratedLength {

    /**
     * 最优解
     * @since 2022-03-28 21:08:52
     */
    public static int maxLen(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        HashSet<Integer> set = new HashSet<>();
        int ans = 1;
        for (int L = 0; L < N; L++) {

            // 换了开头，就要清空set集合
            set.clear();

            // 当前的子数组最值
            int min = arr[L];
            int max = arr[L];
            set.add(arr[L]);
            // L..R
            for (int R = L + 1; R < N; R++) {
                // L....R
                if (set.contains(arr[R])) {

                    // 存在重复值，这个L开头的就都不行了
                    break;
                }
                // 新来的R位置。将其添加到当前的set中
                set.add(arr[R]);

                // 更新最值
                min = Math.min(min, arr[R]);
                max = Math.max(max, arr[R]);

                // 满足条件，就抓一个答案。不满足直接去下一个位置
                if (max - min == R - L) {
                    ans = Math.max(ans, R - L + 1);
                }
            }
        }
        return ans;

    }

    public static int getLIL1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int len = 0;
        // O(N^3 * log N)
        // 子数组所有可能的开头
        for (int start = 0; start < arr.length; start++) {

            // 开头为start的情况下，所有可能的结尾
            for (int end = start; end < arr.length; end++) {
                // arr[s ... e]
                // O(N * logN)
                if (isIntegrated(arr, start, end)) {
                    len = Math.max(len, end - start + 1);
                }
            }
        }
        return len;
    }

    public static boolean isIntegrated(int[] arr, int left, int right) {

        // O(N)
        int[] newArr = Arrays.copyOfRange(arr, left, right + 1);

        // O(N*logN)
        Arrays.sort(newArr);
        for (int i = 1; i < newArr.length; i++) {
            if (newArr[i - 1] != newArr[i] - 1) {
                return false;
            }
        }
        return true;
    }

    public static int getLIL2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int len = 0;
        int max = 0;
        int min = 0;
        HashSet<Integer> set = new HashSet<>();

        // 两个for循环，枚举所有的子数组
        // L 左边界
        for (int L = 0; L < arr.length; L++) {
            // L .......
            set.clear();
            max = Integer.MIN_VALUE;
            min = Integer.MAX_VALUE;

            // R 右边界
            for (int R = L; R < arr.length; R++) {
                // arr[L..R]这个子数组在验证 l...R L...r+1 l...r+2
                if (set.contains(arr[R])) {
                    // arr[L..R]上开始 出现重复值了，arr[L..R往后]不需要验证了，
                    // 一定不是可整合的
                    break;
                }
                // arr[L..R]上无重复值
                set.add(arr[R]);
                max = Math.max(max, arr[R]);
                min = Math.min(min, arr[R]);

                // L..R 是可整合的
                if (max - min == R - L) {
                    len = Math.max(len, R - L + 1);
                }
            }
        }
        return len;
    }

    public static void main(String[] args) {
        int[] arr = {5, 5, 3, 2, 6, 4, 3};
        System.out.println(getLIL1(arr));
        System.out.println(getLIL2(arr));

    }

}
