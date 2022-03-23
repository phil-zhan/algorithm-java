package org.study.coding.class06;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 网易的原题
 * 数组中所有数都异或起来的结果，叫做异或和。
 * 给定一个数组arr，可以任意切分成若干个不相交的子数组。
 * 其中一定存在一种最优方案，使得切出异或和为0的子数组的数量最多，
 * 返回这个最多数量
 *
 * @since 2022-03-06 02:56:07
 */
public class Code04_MostXorZero {

    // 暴力方法
    // 时间复杂度 O(2^N)
    // 每个点都有两个分支
    public static int comparator(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[] eor = new int[N];
        eor[0] = arr[0];
        for (int i = 1; i < N; i++) {
            eor[i] = eor[i - 1] ^ arr[i];
        }
        return process(eor, 1, new ArrayList<>());
    }


    /**
     * index去决定：前一坨部分，结不结束！
     * 如果结束！就把index放入到parts里去
     * 如果不结束，就不放
     * 也就是在不在 index的后面切
     * <p>
     * parts 里面放的是切分的位置，也就是当前子数组到不了的位置
     * <p>
     * 枚举所有的切分可能，使得子数组异或为0的数量最多【所有元素都参与异或】，返回这个数量
     *
     * @since 2022-03-06 03:23:37
     */
    public static int process(int[] eor, int index, ArrayList<Integer> parts) {
        int ans;
        if (index == eor.length) {
            parts.add(eor.length);
            // 用一个辅助结构
            // 前缀异或和数组，有类似前缀和数组的功能
            // 能很快得出任意两个位置间的子数组的异或和结果
            ans = eorZeroParts(eor, parts);

            // 深度优先遍历清理现场
            // 每一次得到结果后，都将当前的信息清除掉，
            // 不然在下一次递归的时候，也就是返回到上游的时候，
            // 还保留之前的决策信息，就会导致list里面一直往后堆
            parts.remove(parts.size() - 1);
        } else {
            // 不切
            int p1 = process(eor, index + 1, parts);

            // 切
            parts.add(index);
            int p2 = process(eor, index + 1, parts);

            // 清理现场
            parts.remove(parts.size() - 1);

            ans = Math.max(p1, p2);
        }
        return ans;
    }

    public static int eorZeroParts(int[] eor, ArrayList<Integer> parts) {
        int L = 0;
        int ans = 0;
        for (Integer end : parts) {
            if ((eor[end - 1] ^ (L == 0 ? 0 : eor[L - 1]))  == 0) {
                ans++;
            }
            L = end;
        }
        return ans;
    }


    /**
     * 时间复杂度O(N)的方法
     * 假设答案法：【数组三联第三问】
     * 假设完答案后，去分析这种答案的特征和流程，在coding的时候，不要让这些特征错过
     * <p>
     * dp[i] ： arr[0...i]能够切出多少个 异或和 为0的部分来，
     * 从左往右的动态规划
     * 关注最优划分下的最后一个部分【不包含i位置的数】
     * 1)最后一个部分 异或和 不是0 。也就相当于最后一次以 i 为结尾划分的时候，有 i 和没 i 都没有改变 异或和为 0 的个数。dp[i] = dp[i-1]
     * 2)最后一个部分 异或和 是0 .
     * 满足一个性质，在最后一部分的所有位置中，不可能存在一个位置k，使得最后一部分能划分成两个 异或和 为0的数组【因为是最优划分】
     * 假设arr[0...i]的整体异或和是 a
     * 那么如果能找到i位置之前的前缀疑惑和 等于a的最晚出现的位置,假设为 j，再往后推一位j+1，就是最后一部分的划分位置。dp[i] = dp[j] + 1
     * 【因为最后的一部分自己搞定0，那么这个a肯定是前面的前缀异或和捣腾出来的】
     * <p>
     * 两种可能性抓一个最大值就是 dp[i]
     *
     * @since 2022-03-06 04:23:22
     */
    public static int mostXor(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;

        // dp[i] ： arr[0...i]能够切出多少个 异或和 为0的部分来，
        int[] dp = new int[N];

        // key 某一个前缀异或和
        // value 这个前缀异或和上次出现的位置(最晚！)
        HashMap<Integer, Integer> map = new HashMap<>();

        // 一定要有
        map.put(0, -1);


        // 0~i整体的异或和
        int xor = 0;

        for (int i = 0; i < N; i++) {
            xor ^= arr[i];

            // 可能性2
            // 之前已经出现过该异或和，那么最后一块肯定是 0
            if (map.containsKey(xor)) {
                int pre = map.get(xor);
                dp[i] = pre == -1 ? 1 : (dp[pre] + 1);
            }

            // 0位置的时候，无需考虑可能性1
            if (i > 0) {
                dp[i] = Math.max(dp[i - 1], dp[i]);
            }
            // 缓存一下当前 异或和 最晚出现的位置
            map.put(xor, i);
        }

        // arr[0...N - 1]能够切出多少个 异或和 为0的部分来，
        return dp[N - 1];
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
        int testTime = 150000;
        int maxSize = 12;
        int maxValue = 5;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int res = mostXor(arr);
            int comp = comparator(arr);
            if (res != comp) {
                succeed = false;
                printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

}
