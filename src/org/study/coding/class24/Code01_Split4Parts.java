package org.study.coding.class24;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 1. 给定一个正数数组arr，长度一定大于6（>=7），一定要选3个数字做分割点，从而分出4个部分，并且每部分都有数
 * 分割点的数字直接删除，不属于任何4个部分中的任何一个。返回有没有可能分出的4个部分累加和一样大
 * 如：{3,2,3,7,4,4,3,1,1,6,7,1,5,2}。可以分成{3,2,3}、{4,4}、{1,1,6}、{1,5,2}。分割点是不算的！
 * <p>
 * 解法：
 * 做一个前缀和的辅助数组
 * 来到 i 位置。让 i 位置作为第一刀。有没有可能切出四个累加和一样的部分，
 * 来到i位置。如果 i-1 位置的前缀和是 100 。arr[i] = 9 。
 * 那么要使得第二个部分和第一个部分相等，就从i往后推，必须要找到一个前缀和等于209的位置。假设为j，那么j+1位置就是第二刀的位置
 * 因为都是正数数组。越往后，前缀和越大。。找到了第一个209，后面就不会再有209了
 * <p>
 * 假设arr[j+1] = 6
 * 从j+1位置往后推，必须要找到一个前缀和等于315的位置[100+9+100+6+100]。假设为x，那么x+1位置就是第三刀的位置
 * 切完第三刀之后，看看剩余的累加和是不是100。
 * <p>
 * 在这个过程中，只要第一个209或第二个315，亦或者是第三刀之后，剩下的累加和不等于100.那么当前的第一刀的位置都是无效的
 * <p>
 * 为了方便找到某个前缀和是否存在。准备一个hash表
 * key：某个前缀和
 * value：该前缀和对应的出现的位置
 *
 * @since 2022-04-05 08:03:42
 */
public class Code01_Split4Parts {

    public static boolean canSplits1(int[] arr) {
        if (arr == null || arr.length < 7) {
            return false;
        }
        HashSet<String> set = new HashSet<>();
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        int leftSum = arr[0];
        for (int i = 1; i < arr.length - 1; i++) {
            set.add(leftSum + "_" + (sum - leftSum - arr[i]));
            leftSum += arr[i];
        }
        int l = 1;
        int lsum = arr[0];
        int r = arr.length - 2;
        int rsum = arr[arr.length - 1];
        while (l < r - 3) {
            if (lsum == rsum) {
                String lkey = String.valueOf(lsum * 2 + arr[l]);
                String rkey = String.valueOf(rsum * 2 + arr[r]);
                if (set.contains(lkey + "_" + rkey)) {
                    return true;
                }
                lsum += arr[l++];
            } else if (lsum < rsum) {
                lsum += arr[l++];
            } else {
                rsum += arr[r--];
            }
        }
        return false;
    }

    /**
     * 最优解
     * @since 2022-04-05 20:58:28
     */
    public static boolean canSplits2(int[] arr) {
        if (arr == null || arr.length < 7) {
            return false;
        }
        // key 某一个累加和， value出现的位置
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int sum = arr[0];
        for (int i = 1; i < arr.length; i++) {
            map.put(sum, i);
            sum += arr[i];
        }

        // 第一刀左侧的累加和
        int lsum = arr[0];

        // s1是第一刀的位置
        for (int s1 = 1; s1 < arr.length - 5; s1++) {

            // 100 x 100   100*2 + x
            int checkSum = lsum * 2 + arr[s1];
            if (map.containsKey(checkSum)) {

                // j -> y
                int s2 = map.get(checkSum);
                checkSum += lsum + arr[s2];

                // 100 * 3 + x + y
                if (map.containsKey(checkSum)) {

                    // k -> z
                    int s3 = map.get(checkSum);
                    if (checkSum + arr[s3] + lsum == sum) {
                        return true;
                    }
                }
            }
            lsum += arr[s1];
        }
        return false;
    }

    public static int[] generateRondomArray() {
        int[] res = new int[(int) (Math.random() * 10) + 7];
        for (int i = 0; i < res.length; i++) {
            res[i] = (int) (Math.random() * 10) + 1;
        }
        return res;
    }

    public static void main(String[] args) {
        int testTime = 3000000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRondomArray();
            if (canSplits1(arr) ^ canSplits2(arr)) {
                System.out.println("Error");
            }
        }
    }
}
