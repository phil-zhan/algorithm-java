package org.study.system.class08;

import java.util.Arrays;

/**
 * 基数排序
 * 基数排序要求，样本是10进制的正整数
 * 一组正整数，将其都化为与最大值位数一样，不够的在前面补0
 * 准备10个辅助数组
 * 第一步：按照个位进桶，个位是多少，就进多少号桶。完事后再倒出来【从0号桶开始，先进的先出】
 * 第二步：按照十位进桶，十位是多少，就进多少号桶。完事后再倒出来【从0号桶开始，先进的先出】
 * 第三步：按照百位进桶，百位是多少，就进多少号桶。完事后再倒出来【从0号桶开始，先进的先出】
 * 。。。
 * 以此类推，直到最高位都进了桶。再倒出来的数据就是有序的
 *
 *
 *
 *
 * 如果是有负数。先找到最小值。将所有数都加上该最小值，再按照前面的基数排序的步骤去执行。排完之后，再统一减去7
 * @author phil
 * @date 2021/6/15 17:15
 */
public class Code04_RadixSort {

    /**
     * // only for no-negative value 【只能是非负数】
     * @date 2021-06-16 10:56:47
     */
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        radixSort(arr, 0, arr.length - 1, maxbits(arr));
    }

    public static int maxbits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        int res = 0;
        while (max != 0) {
            res++;
            max /= 10;
        }
        return res;
    }

    /**
     * // arr[L..R]排序  ,  最大值的十进制的位数 digit
     * 这里没有利用桶，而是利用前缀和的形式来实现
     *
     * 时间复杂度是 O（N*log max）
     * @date 2021-06-16 10:57:09
     *
     */
    public static void radixSort(int[] arr, int L, int R, int digit) {
        final int radix = 10;
        int i = 0, j = 0;
        // 有多少个数准备多少个辅助空间
        int[] help = new int[R - L + 1];
        for (int d = 1; d <= digit; d++) { // 有多少位就进出几次
            // 针对每一位（个十百...）,生成一个统计数组。
            // 【数组中的元素是，需要排序的数组中，有多少元素的当前位（个十百...）是以该元素的下标结尾的】
            // 也就是统计以该位为x的数有多少
            // 再转化为前缀和
            // 最后从右到左遍历。实现出桶效果【】



            // 10个空间
            // count[0] 当前位(d位)是0的数字有多少个
            // count[1] 当前位(d位)是(0和1)的数字有多少个
            // count[2] 当前位(d位)是(0、1和2)的数字有多少个
            // count[i] 当前位(d位)是(0~i)的数字有多少个
            int[] count = new int[radix]; // count[0..9]
            for (i = L; i <= R; i++) {
                // 103  1   3
                // 209  1   9
                j = getDigit(arr[i], d);
                count[j]++;
            }
            for (i = 1; i < radix; i++) {
                count[i] = count[i] + count[i - 1];
            }
            for (i = R; i >= L; i--) {
                j = getDigit(arr[i], d);
                help[count[j] - 1] = arr[i];
                count[j]--;
            }
            for (i = L, j = 0; i <= R; i++, j++) {
                arr[i] = help[j];
            }
        }
    }

    public static int getDigit(int x, int d) {
        return ((x / ((int) Math.pow(10, d - 1))) % 10);
    }

    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        radixSort(arr);
        printArray(arr);

    }

}
