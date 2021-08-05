package org.study.class20;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * // 题目
 * // 数组arr代表每一个咖啡机冲一杯咖啡的时间，每个咖啡机只能串行的制造咖啡。
 * // 现在有n个人需要喝咖啡，只能用咖啡机来制造咖啡。
 * // 认为每个人喝咖啡的时间非常短，冲好的时间即是喝完的时间。
 * // 每个人喝完之后咖啡杯可以选择洗或者自然挥发干净，只有一台洗咖啡杯的机器，只能串行的洗咖啡杯。
 * // 洗杯子的机器洗完一个杯子时间为a，任何一个杯子自然挥发干净的时间为b。
 * // 四个参数：arr, n, a, b
 * // 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程结束后，至少来到什么时间点。
 *
 * @author phil
 * @date 2021/8/5 17:02
 */
public class MainTest03 {

    private static class CoffeeMacheine {
        public int startTime;
        public int workTime;

        public CoffeeMacheine(int startTime, int workTime) {
            this.startTime = startTime;
            this.workTime = workTime;
        }
    }

    private static class CoffeeMacheineComparator implements Comparator<CoffeeMacheine> {

        @Override
        public int compare(CoffeeMacheine o1, CoffeeMacheine o2) {
            return (o1.startTime + o1.workTime) - (o2.startTime + o2.workTime);
        }
    }

    /**
     * arr[i]: 第 i 台咖啡机制作咖啡的时间
     * n：表示有 n 个人
     * a：表示清洗机清洗一个杯子的时间
     * b：表示一个咖啡杯自然风干的时间
     *
     * @date 2021-08-05 17:37:58
     */
    public static int minTime(int[] arr, int n, int a, int b) {
        if (null == arr || arr.length == 0 || n <= 0 || a <= 0 || b <= 0) {
            return 0;
        }

        PriorityQueue<CoffeeMacheine> heap = new PriorityQueue<>(new CoffeeMacheineComparator());

        // 每个人喝完的时间点
        int[] drinks = new int[n];


        for (int workTime : arr) {
            heap.add(new CoffeeMacheine(0, workTime));
        }

        for (int i = 0; i < n; i++) {
            CoffeeMacheine coffeeMacheine = heap.poll();
            if (null == coffeeMacheine) {
                // 没有可用的咖啡机
                return 0;
            }
            coffeeMacheine.startTime += coffeeMacheine.workTime;
            drinks[i] = coffeeMacheine.startTime;
            heap.add(coffeeMacheine);
        }

        return bestCleanTime(drinks,a,b,0,0);
    }

    /**
     * drinks[i]:第 i 个人喝完咖啡的时间
     * a：表示清洗机清洗一个杯子的时间
     * b：表示一个咖啡杯自然风干的时间
     * index：当前考虑 index 个咖啡杯
     * free：清洁机可以开始的时间
     * @date 2021-08-05 17:59:37
     */
    private static int bestCleanTime(int[] drinks, int a, int b, int index, int free) {
        if (index == drinks.length) {
            return 0;
        }

        // index 号杯子决定用洗
        int selfClean1 = Math.max(drinks[index],free) + a;
        int restClean1 = bestCleanTime(drinks,a,b,index+1,selfClean1);
        int p1 = Math.max(selfClean1,restClean1);

        // index 号杯子决定用风干
        int selfClean2 = drinks[index] + b;
        int restClean2 = bestCleanTime(drinks,a,b,index+1,free);
        int p2 = Math.max(selfClean2,restClean2);


        return Math.min(p1,p2);

    }

    public static int minTime2(int[] arr,int n,int a,int b){
        if (null == arr || arr.length == 0 || n <= 0 || a <= 0 || b <= 0) {
            return 0;
        }

        PriorityQueue<CoffeeMacheine> heap = new PriorityQueue<>(new CoffeeMacheineComparator());

        // 每个人喝完的时间点
        int[] drinks = new int[n];


        for (int workTime : arr) {
            heap.add(new CoffeeMacheine(0, workTime));
        }

        for (int i = 0; i < n; i++) {
            CoffeeMacheine coffeeMacheine = heap.poll();
            if (null == coffeeMacheine) {
                // 没有可用的咖啡机
                return 0;
            }
            coffeeMacheine.startTime += coffeeMacheine.workTime;
            drinks[i] = coffeeMacheine.startTime;
            heap.add(coffeeMacheine);
        }

        return bestCleanTime2(drinks,a,b);
    }

    private static int bestCleanTime2(int[] drinks, int wash, int air) {

        int N = drinks.length;
        int maxFree = 0;
        for (int i = 0; i < drinks.length; i++) {
            maxFree = Math.max(maxFree, drinks[i]) + wash;
        }
        int[][] dp = new int[N + 1][maxFree + 1];
        for (int index = N - 1; index >= 0; index--) {
            for (int free = 0; free <= maxFree; free++) {
                int selfClean1 = Math.max(drinks[index], free) + wash;
                if (selfClean1 > maxFree) {
                    break; // 因为后面的也都不用填了
                }
                // index号杯子 决定洗
                int restClean1 = dp[index + 1][selfClean1];
                int p1 = Math.max(selfClean1, restClean1);
                // index号杯子 决定挥发
                int selfClean2 = drinks[index] + air;
                int restClean2 = dp[index + 1][free];
                int p2 = Math.max(selfClean2, restClean2);
                dp[index][free] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }


    // 验证的方法
    // 彻底的暴力
    // 很慢但是绝对正确
    public static int right(int[] arr, int n, int a, int b) {
        int[] times = new int[arr.length];
        int[] drink = new int[n];
        return forceMake(arr, times, 0, drink, n, a, b);
    }

    // 每个人暴力尝试用每一个咖啡机给自己做咖啡
    public static int forceMake(int[] arr, int[] times, int kth, int[] drink, int n, int a, int b) {
        if (kth == n) {
            int[] drinkSorted = Arrays.copyOf(drink, kth);
            Arrays.sort(drinkSorted);
            return forceWash(drinkSorted, a, b, 0, 0, 0);
        }
        int time = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int work = arr[i];
            int pre = times[i];
            drink[kth] = pre + work;
            times[i] = pre + work;
            time = Math.min(time, forceMake(arr, times, kth + 1, drink, n, a, b));
            drink[kth] = 0;
            times[i] = pre;
        }
        return time;
    }

    public static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
        if (index == drinks.length) {
            return time;
        }
        // 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;
        int ans1 = forceWash(drinks, a, b, index + 1, wash, Math.max(wash, time));

        // 选择二：当前index号咖啡杯，选择自然挥发
        int dry = drinks[index] + b;
        int ans2 = forceWash(drinks, a, b, index + 1, washLine, Math.max(dry, time));
        return Math.min(ans1, ans2);
    }


    // for test
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        System.out.print("arr : ");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + ", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 10;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            int n = (int) (Math.random() * 7) + 1;
            int a = (int) (Math.random() * 7) + 1;
            int b = (int) (Math.random() * 10) + 1;
            int ans1 = right(arr, n, a, b);
            int ans2 = minTime(arr, n, a, b);
            int ans3 = minTime2(arr, n, a, b);
            if (ans1 != ans2 || ans2 != ans3) {
                printArray(arr);
                System.out.println("n : " + n);
                System.out.println("a : " + a);
                System.out.println("b : " + b);
                System.out.println(ans1 + " , " + ans2 + " , " + ans3);
                System.out.println("===============");
                break;
            }
        }
        System.out.println("测试结束");

    }
}
