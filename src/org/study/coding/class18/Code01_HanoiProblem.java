package org.study.coding.class18;


/**
 * 1. 给定一个数组arr，长度为N，arr中的值只有1，2，3三种
 * arr[i] == 1，代表汉诺塔问题中，从上往下第i个圆盘目前在左
 * arr[i] == 2，代表汉诺塔问题中，从上往下第i个圆盘目前在中
 * arr[i] == 3，代表汉诺塔问题中，从上往下第i个圆盘目前在右
 * 那么arr整体就代表汉诺塔游戏过程中的一个状况，如果这个状况不是汉诺塔最优解运动过程中的状况，返回-1
 * 如果这个状况是汉诺塔最优解运动过程中的状态，返回它是第几个状态
 * <p>
 * 题意：
 * 下标代表汉罗塔的层数。也就是当前的轮盘属于第几层
 * 数组的取值是1、2、3  代表左中右
 * 判断当前数组所给的状态，是汉罗塔移动过程中的第几步。如果完全就不是汉罗塔移动过程的中间步骤。就返回-1
 * <p>
 * 解法：
 * 目标是: 把0~i的圆盘，从from全部挪到to上
 * 返回，根据arr中的状态arr[0..i]，它是最优解的第几步？
 * f(i, 3 , 2, 1) f(i, 1, 3, 2) f(i, 3, 1, 2)
 *
 * @since 2022-03-19 09:12:00
 */
public class Code01_HanoiProblem {

    /**
     * 递归简化版
     *
     *
     * @since 2022-03-19 03:14:52
     */
    public static int step1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        return process(arr, arr.length - 1, 1, 2, 3);
    }

    /**
     * 目标是: 把0~i的圆盘，从from全部挪到to上
     * 返回，根据arr中的状态arr[0..i]，它是最优解的第几步？
     * f(i, 3 , 2, 1) f(i, 1, 3, 2) f(i, 3, 1, 2)
     *
     * @since 2022-03-19 09:08:24
     */
    public static int process(int[] arr, int i, int from, int other, int to) {
        if (i == -1) {
            return 0;
        }
        if (arr[i] != from && arr[i] != to) {
            return -1;
        }
        if (arr[i] == from) {
            // 第一大步没走完
            return process(arr, i - 1, from, to, other);
        } else {
            // arr[i] == to
            // 已经走完1，2两步了，
            // 第三大步完成的程度
            int rest = process(arr, i - 1, other, from, to);
            if (rest == -1) {
                return -1;
            }
            return (1 << i) + rest;
        }
    }

    /**
     * 迭代版
     *
     * @since 2022-03-19 03:16:32
     */
    public static int step2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int from = 1;
        int mid = 2;
        int to = 3;
        int i = arr.length - 1;
        int res = 0;
        int tmp = 0;
        while (i >= 0) {
            if (arr[i] != from && arr[i] != to) {
                return -1;
            }
            if (arr[i] == to) {
                res += 1 << i;
                tmp = from;
                from = mid;
            } else {
                tmp = to;
                to = mid;
            }
            mid = tmp;
            i--;
        }
        return res;
    }

    /**
     * 递归版本原解
     *
     * @since 2022-03-19 03:14:16
     */
    public static int kth(int[] arr) {
        int N = arr.length;
        return step(arr, N - 1, 1, 3, 2);
    }

    /**
     * 0...index这些圆盘，arr[0..index] index+1层塔
     * 在哪？from 去哪？to 另一个是啥？other
     * arr[0..index]这些状态，是index+1层汉诺塔问题的，最优解第几步
     * <p>
     * 将index层的圆盘从 from 挪到 to
     *
     * @since 2022-03-19 09:08:43
     */
    public static int step(int[] arr, int index, int from, int to, int other) {
        if (index == -1) {

            // 没有圆盘了
            return 0;
        }
        if (arr[index] == other) {

            // index 的圆盘不可能会在 other上，如果在 other 上，说明，这不是过程之一
            return -1;
        }
        // arr[index] == from   或者 arr[index] == to;
        if (arr[index] == from) {
            // 整体的过程是
            // 1） 1...i-1  from--> other
            // 2） i  		from--> to
            // 3） 1...i-1  other--> from


            // index 的圆盘还在 from上。那么 1...i-1 的移动步数就是整体的移动步数
            // 在数组的层面。就是 index 位置的数对上了。只要看看index前面的数对上需要几步。就是当前的整体的步数
            return step(arr, index - 1, from, other, to);
        } else {
            // arr[index] == to

            // n 层汉诺塔的总步数是 2^n-1步
            // index 的圆盘已经在 to 上了。
            // 说明前面的 0...i-1 位置的汉诺塔已经从from移到了other上 用了 2^(i) -1  【前面有i层】
            // 第 i 层用了 1 步
            // 只需要看看 1...i-1 从other移到to需要多少步。然后都加起来。就是当前的步数

            // 在数组的层面。就是 index 位置的数已经在to上了。只要看看index前面的数对上需要几步

            int p1 = (1 << index) - 1;
            int p2 = 1;
            int p3 = step(arr, index - 1, other, to, from);

            // 前面的也可能存在不是最优解的某一步
            if (p3 == -1) {
                return -1;
            }
            return p1 + p2 + p3;
        }
    }

    public static void main(String[] args) {
        int[] arr = {3, 3, 2, 1};
        System.out.println(step1(arr));
        System.out.println(step2(arr));
        System.out.println(kth(arr));
    }
}
