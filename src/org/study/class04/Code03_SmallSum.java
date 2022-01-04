package org.study.class04;

/**
 * @author phil
 * @date 2021/3/16 15:50
 * 小和问题
 * <p>
 * 给定一个数组，将当前位置之前的所有比自己小的数都累加起来 作为当前位置的小和。最后再求和
 * <p>
 * 例如
 * [6,3,2,1,6,7]
 * [0,0,0,0,6,18]
 * 求和为 24
 * <p>
 * <p>
 * 要求时间复杂度为 O(N*logN)
 * <p>
 * 实现思路如下：【实现过程就是一个归并排序，只是附加了几行 不影响时间复杂度的代码】
 * 设一个总共的小和：answer = 0;
 * <p>
 * 以步长来实现划分
 * 首先步长为2，将原数组划分为有个步长为二的数组，每相邻两个数组都去就行合并【也就是谁小拷贝谁】
 * 然后步长逐步增加，直到步长等于数组长度的一半。
 * <p>
 * 改动之后，
 * 在拷贝过程中，
 * 如果拷贝的是左组，那么久将被拷贝的数累加到 answer上
 * 【累加的次数为 也就是右组的当前指针位置的后面还有多少个数大于左组当前指针的数，就加几遍】
 * <p>
 * 如果拷贝的是右组，就不用管
 * <p>
 * 如果左右两个数组的指针所指的值是一样的，就拷贝右组。
 */
public class Code03_SmallSum {

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 2, 4, 6, 8, 10};
        int answer = myselfSmallSum(arr);
        int answer2 = smallSum(arr);
        System.out.println(answer);
    }

    private static int myselfSmallSum(int[] arr) {
        if (null == arr || arr.length < 2) {
            return 0;
        }

        return myselfProcess(arr, 0, arr.length - 1);
    }

    private static int myselfProcess(int[] arr, int left, int right) {

        if (left == right) {
            return 0;
        }

        int mid = left + ((right - left) >> 1);

        return myselfProcess(arr, left, mid) + myselfProcess(arr, mid + 1, right) + myselfMerge(arr, left, mid, right);
    }

    private static int myselfMerge(int[] arr, int left, int mid, int right) {
        int[] help = new int[right - left + 1];

        int newArrIndex = 0;
        int leftArrIndex = left;
        int rightArrIndex = mid + 1;
        int res = 0;

        while (leftArrIndex <= mid && rightArrIndex <= right) {
            res += arr[leftArrIndex] < arr[rightArrIndex] ? (right - rightArrIndex + 1) * arr[leftArrIndex] : 0;
            // 谁小拷贝谁，拷贝之后，移动对应的指针
            help[newArrIndex++] = arr[leftArrIndex] < arr[rightArrIndex] ? arr[leftArrIndex++] : arr[rightArrIndex++];
        }

        // 要么p1越界了，要么p2越界了【这两个循环只会执行一个】
        while (leftArrIndex <= mid) {
            help[newArrIndex++] = arr[leftArrIndex++];
        }
        while (rightArrIndex <= right) {
            help[newArrIndex++] = arr[rightArrIndex++];
        }

        return res;
    }


    /**
     * arr既要排好序，也要求出小和
     * 所有merge时，产生的小和相加
     *
     * @date 2021-03-16 17:23:41
     */
    public static int smallSum(int[] arr) {

        if (null == arr || arr.length < 2) {
            return 0;
        }

        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }

        int mid = left + ((right - left) >> 1);

        return process(arr, left, mid) + process(arr, mid + 1, right) + merge(arr, left, mid, right);
    }


    /**
     * 将左右两个有序的数组合并为一个 有序的大数组
     * <p>
     * 实现思路大概是
     * 定义一个新数组 arr，数组长度为左右两个数组的长度之和
     * 两个指针分别从左右两个数组的开始位置开始。
     * 比较左右两个指针所指的位置的数，谁小就拷贝谁到新数组，拷贝之后，被拷贝的指针向后移动，再次和另外一个数组的指针对应的数做比较
     * <p>
     * 也就是谁小拷贝谁，拷贝之后对应的指针后移。当一个数组的指针移到最后，也就是一个数组的数被拷贝完了，而另外一个数组还有数
     * 就讲另外一个数组的数全部拷贝到新数组的最后。
     *
     * @date 2021-03-15 14:16:05
     */
    public static int merge(int[] arr, int left, int mid, int right) {
        int[] help = new int[right - left + 1];

        int i = 0;
        int p1 = left;
        int p2 = mid + 1;
        int res = 0;

        while (p1 <= mid && p2 <= right) {
            res += arr[p1] < arr[p2] ? (right - p2 + 1) * arr[p1] : 0;
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }

        // 要么p1越界了，要么p2越界了【这两个循环只会执行一个】
        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }
        while (p2 <= right) {
            help[i++] = arr[p2++];
        }

        if (help.length >= 0) {
            System.arraycopy(help, 0, arr, left, help.length);
        }
        return res;
    }
}
