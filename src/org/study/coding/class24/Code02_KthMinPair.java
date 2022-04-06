package org.study.coding.class24;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 2. 长度为N的数组arr，一定可以组成N^2个数字对。例如arr = [3,1,2]，数字对有(3,3) (3,1) (3,2) (1,3) (1,1) (1,2) (2,3) (2,1) (2,2)
 * 也就是任意两个数都可以，而且自己和自己也算数字对。数字对怎么排序？第一维数据从小到大；第一维数据一样的，第二维数组也从小到大
 * 所以上面的数值对排序的结果为：(1,1)(1,2)(1,3)(2,1)(2,2)(2,3)(3,1)(3,2)(3,3)。给定一个数组arr，和整数k，返回第k小的数值对
 * <p>
 * 字节原题
 * <p>
 * <p>
 * <p>
 * <p>
 * 暴力解
 * 两个for循环，枚举所有的数字对。总共 N^2 对
 * 放到一个数组里面去，将数组排序
 * 然后遍历拿到第k小的对
 * 时间复杂度： N^2 * log(N^2)
 * <p>
 * 最优解：
 * 时间复杂度 O(N)
 * 原始数组是无序的。假设他是有序的【在无序数组中取到第i位置的数，不需要排序】
 * 假设原数组为[1,1,1,2,2,3,3,3,3,5,5]。要求第70小的数字对。假设为(a,b).我们只需要确定a和b就好啦
 * 将第一维数字是1的划为第一组
 * 将第一维数字是2的划为第二组
 * 将第一维数字是3的划为第三组
 * 将第一维数字是5的划为第四组
 * <p>
 * 0位置的1为第一维的有11个【每个数都能和数组中的任何一个数组成数字对（包含自己）】
 * 1位置的1为第一维的有11个
 * 2位置的1为第一维的有11个
 * 3位置的2为第一维的有11个
 * 4位置的2为第一维的有11个
 * 5位置的3为第一维的有11个
 * 6位置的3为第一维的有11个
 * 。。。
 * a肯定是6位置的3
 * <p>
 * 第三组之前的占据了55个数。第三组又占据了33个数。那么要的肯定是第三大组中的第15个
 * 将第三大组再划分。按照第二维去划分
 * 在当前组中，必须以0位置的1做第二维的有多少对【原数组中有多少个3就会有多少对】，全部划分在3-1小组
 * 在当前组中，必须以1位置的1做第二维的有多少对【原数组中有多少个3就会有多少对】，全部划分在3-2小组
 * 在当前组中，必须以2位置的1做第二维的有多少对【原数组中有多少个3就会有多少对】，全部划分在3-3小组
 * 在当前组中，必须以3位置的2做第二维的有多少对【原数组中有多少个3就会有多少对】，全部划分在3-4小组
 * ...
 * 看看3-1小组占据了多少对
 * 看看3-2小组占据了多少对
 * 看看3-3小组占据了多少对
 * 以此类推，哪个组占据了第15的位置。就是我们要的答案
 * <p>
 * 在同一个小组内，数字对肯定是一样的。
 * 【第一维是按照每个不同数字划分的，在同一个小组内，第一维数字肯定是一样的】
 * 第二维：考虑的是以某个数字做第二维来分小组。所以第二维也是一样的
 * <p>
 * <p>
 * ====================================== 抽象化 ========================================
 * 假设一共有n个数，要求第k小的数字对
 * (k-1)/n :就是第一维数据的下标【前面的数占据了多少的位置】【k是从1开始的，要减去1】。【每个数字能搞定n个】【可以举例子看，更直观】
 * 假设这个下标是i. 【在无序数组中取到第i位置的数，不需要排序,bfptr算法，改进快排的算法】【O(N)】
 *
 * @since 2022-04-04 14:12:29
 */
public class Code02_KthMinPair {

    public static class Pair {
        public int x;
        public int y;

        Pair(int a, int b) {
            x = a;
            y = b;
        }
    }

    public static class PairComparator implements Comparator<Pair> {

        @Override
        public int compare(Pair arg0, Pair arg1) {
            return arg0.x != arg1.x ? arg0.x - arg1.x : arg0.y - arg1.y;
        }

    }

    /**
     * O(N^2 * log (N^2))的复杂度，你肯定过不了
     * 返回的int[] 长度是2，{3,1} int[2] = [3,1]
     *
     * @since 2022-04-04 15:25:45
     */
    public static int[] kthMinPair1(int[] arr, int k) {
        int N = arr.length;
        if (k > N * N) {
            return null;
        }
        Pair[] pairs = new Pair[N * N];
        int index = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                pairs[index++] = new Pair(arr[i], arr[j]);
            }
        }
        Arrays.sort(pairs, new PairComparator());
        return new int[]{pairs[k - 1].x, pairs[k - 1].y};
    }

    /**
     * O(N*logN)的复杂度，你肯定过了
     *
     * @since 2022-04-04 15:25:37
     */
    public static int[] kthMinPair2(int[] arr, int k) {
        int N = arr.length;
        if (k > N * N) {
            return null;
        }
        // O(N*logN)
        Arrays.sort(arr);
        // 第K小的数值对，第一维数字，是什么 是arr中
        int fristNum = arr[(k - 1) / N];

        // 数出比fristNum小的数有几个
        int lessFristNumSize = 0;

        // 数出==fristNum的数有几个
        int fristNumSize = 0;
        // <= fristNum
        for (int i = 0; i < N && arr[i] <= fristNum; i++) {
            if (arr[i] < fristNum) {
                lessFristNumSize++;
            } else {
                fristNumSize++;
            }
        }
        int rest = k - (lessFristNumSize * N);
        return new int[]{fristNum, arr[(rest - 1) / fristNumSize]};
    }

    /**
     * O(N)的复杂度，你肯定蒙了
     *
     * @since 2022-04-04 15:26:01
     */
    public static int[] kthMinPair3(int[] arr, int k) {
        int N = arr.length;
        if (k > N * N) {
            return null;
        }
        // 在无序数组中，找到第K小的数，返回值【也就是答案的第一维数字】
        // 第K小，以1作为开始
        int firstNum = getMinKth(arr, (k - 1) / N);

        // 第1维数字
        int lessFirstNumSize = 0;
        int firstNumSize = 0;
        for (int i = 0; i < N; i++) {

            // 数一下原数组中，小于 firstNum 的有多少个【看看前面的占据了多少个位置，才能知道在 firstNum 内部的组中，需要找第几个】
            if (arr[i] < firstNum) {
                lessFirstNumSize++;
            }

            // 原数组中，第一维数字等于 firstNum 的有多少。也就是原数组中可能出现重复值，看看有多少个
            if (arr[i] == firstNum) {
                firstNumSize++;
            }
        }
        int rest = k - (lessFirstNumSize * N);

        // 在这里，第一维数字已经确定了，要在内部组中找第 rest 个
        // 原数组的每一个数字都能作为第二维。我们要找到第 rest 小的那一个。就是我们要的第二维
        return new int[]{firstNum, getMinKth(arr, (rest - 1) / firstNumSize)};
    }

    /**
     * 改写快排，时间复杂度O(N)
     * 在无序数组arr中，找到，如果排序的话，arr[index]的数是什么？
     *
     * @since 2022-04-04 15:26:07
     */
    public static int getMinKth(int[] arr, int index) {
        int L = 0;
        int R = arr.length - 1;
        int pivot = 0;
        int[] range = null;
        while (L < R) {
            pivot = arr[L + (int) (Math.random() * (R - L + 1))];
            range = partition(arr, L, R, pivot);
            if (index < range[0]) {
                R = range[0] - 1;
            } else if (index > range[1]) {
                L = range[1] + 1;
            } else {
                return pivot;
            }
        }
        return arr[L];
    }

    public static int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
        int more = R + 1;
        int cur = L;
        while (cur < more) {
            if (arr[cur] < pivot) {
                swap(arr, ++less, cur++);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[]{less + 1, more - 1};
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 为了测试，生成值也随机，长度也随机的随机数组
    public static int[] getRandomArray(int max, int len) {
        int[] arr = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    // 为了测试
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

    // 随机测试了百万组，保证三种方法都是对的
    public static void main(String[] args) {
        int max = 100;
        int len = 30;
        int testTimes = 100000;
        System.out.println("test bagin, test times : " + testTimes);
        for (int i = 0; i < testTimes; i++) {
            int[] arr = getRandomArray(max, len);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);
            int N = arr.length * arr.length;
            int k = (int) (Math.random() * N) + 1;
            int[] ans1 = kthMinPair1(arr1, k);
            int[] ans2 = kthMinPair2(arr2, k);
            int[] ans3 = kthMinPair3(arr3, k);
            if (ans1[0] != ans2[0] || ans2[0] != ans3[0] || ans1[1] != ans2[1] || ans2[1] != ans3[1]) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

}
