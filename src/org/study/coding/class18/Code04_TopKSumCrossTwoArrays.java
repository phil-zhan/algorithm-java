package org.study.coding.class18;

// 牛客的测试链接：


import java.util.Scanner;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * // https://www.nowcoder.com/practice/7201cacf73e7495aa5f88b223bbbf6d1
 * // 不要提交包信息，把import底下的类名改成Main，提交下面的代码可以直接通过
 * // 因为测试平台会卡空间，所以把set换成了动态加和减的结构
 * <p>
 * <p>
 * 描述
 * 给定两个有序数组arr1和arr2，再给定一个整数k，返回来自arr1和arr2的两个数相加和最大的前k个，两个数必须分别来自两个数组
 * arr1选一个。arr2选一个，两数相加。共有 N*M 中组合
 * 在这些组合中。返回最大的前k个
 * 按照降序输出
 * [要求]
 * 时间复杂度为O(k \log k)O(klogk)
 * 输入描述：
 * 第一行三个整数N, K分别表示数组arr1, arr2的大小，以及需要询问的数
 * 接下来一行N个整数，表示arr1内的元素
 * 再接下来一行N个整数，表示arr2内的元素
 * 输出描述：
 * 输出K个整数表示答案
 * <p>
 * <p>
 * 解法：
 * 大根堆
 * <p>
 * 做一个样本对应模型dp【因为两个数组都是有序的】
 * dp[i][j] = arr1[i]+arr2[j]
 * 最大值，毫无疑问是右下角
 * <p>
 * 先将最大值放进大根堆。
 * 开始弹出堆顶，收集答案。
 * 每次弹出的时候。将弹出的位置的值，其左边和上边拽进大根堆。
 * 然后再次弹出堆顶。重复操作
 * 直到收集到K个数位置
 *
 * @since 2022-03-19 09:03:21
 */
public class Code04_TopKSumCrossTwoArrays {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        int[] arr1 = new int[N];
        int[] arr2 = new int[N];
        for (int i = 0; i < N; i++) {
            arr1[i] = sc.nextInt();
        }
        for (int i = 0; i < N; i++) {
            arr2[i] = sc.nextInt();
        }
        int[] topK = topKSum(arr1, arr2, K);
        for (int i = 0; i < K; i++) {
            System.out.print(topK[i] + " ");
        }
        System.out.println();
        sc.close();
    }

    /**
     * 放入大根堆中的结构
     *
     * @since 2022-03-19 09:38:28
     */
    public static class Node {

        // 也就是行列。可以在二维表中，定位到弹出的位置
        // arr1中的位置
        public int index1;

        // arr2中的位置
        public int index2;

        // arr1[index1] + arr2[index2]的值
        public int sum;

        public Node(int i1, int i2, int s) {
            index1 = i1;
            index2 = i2;
            sum = s;
        }
    }

    /**
     * 生成大根堆的比较器
     *
     * @since 2022-03-19 09:38:39
     */
    public static class MaxHeapComp implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o2.sum - o1.sum;
        }
    }

    /**
     * 利用大根堆完成
     *
     * 解法：
     * 大根堆
     * <p>
     * 做一个样本对应模型dp【因为两个数组都是有序的】
     * dp[i][j] = arr1[i]+arr2[j]
     * 最大值，毫无疑问是右下角
     * <p>
     * 先将最大值放进大根堆。
     * 开始弹出堆顶，收集答案。
     * 每次弹出的时候。将弹出的位置的值，其左边和上边拽进大根堆。
     * 然后再次弹出堆顶。重复操作
     * 直到收集到K个数位置
     *
     *
     * @since 2022-03-19 09:52:32
     */
    public static int[] topKSum(int[] arr1, int[] arr2, int topK) {
        if (arr1 == null || arr2 == null || topK < 1) {
            return null;
        }
        int N = arr1.length;
        int M = arr2.length;
        topK = Math.min(topK, N * M);

        // 结果数组
        int[] res = new int[topK];
        int resIndex = 0;

        // 堆
        PriorityQueue<Node> maxHeap = new PriorityQueue<>(new MaxHeapComp());

        // 二维变一维。可以表示一个位置是否加入过大根堆。将二维坐标，转化成一维坐标。放在set里。表示是否进入过大根堆
        HashSet<Long> set = new HashSet<>();

        // 两个数组的指针
        int i1 = N - 1;
        int i2 = M - 1;

        // 最大值先放进大根堆
        maxHeap.add(new Node(i1, i2, arr1[i1] + arr2[i2]));

        // 右下角加入过大根堆了
        set.add(x(i1, i2, M));

        // 直到收集到K个答案位置
        while (resIndex != topK) {

            Node curNode = maxHeap.poll();
            res[resIndex++] = curNode.sum;

            i1 = curNode.index1;
            i2 = curNode.index2;

            // 出大根堆了。不会再有上或左，能让它再一次加入大跟堆。直接在set将其删掉。省空间
            // 从大根堆出来了，不会再遇到了
            // 不删也行，只是会浪费空间。可能过不了
            set.remove(x(i1, i2, M));

            // 上
            if (i1 - 1 >= 0 && !set.contains(x(i1 - 1, i2, M))) {
                set.add(x(i1 - 1, i2, M));
                maxHeap.add(new Node(i1 - 1, i2, arr1[i1 - 1] + arr2[i2]));
            }

            // 左
            if (i2 - 1 >= 0 && !set.contains(x(i1, i2 - 1, M))) {
                set.add(x(i1, i2 - 1, M));
                maxHeap.add(new Node(i1, i2 - 1, arr1[i1] + arr2[i2 - 1]));
            }
        }
        return res;
    }

    public static long x(int i1, int i2, int M) {
        return (long) i1 * (long) M + (long) i2;
    }

}
