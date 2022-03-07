package org.study.coding.class01;

import java.util.Arrays;

public class MainTest06 {

    public int minAoe(int[] x, int[] hp, int range) {
        int ans = 0;
        for (int index = 0; index < x.length; index++) {
            if (hp[index] > 0) {
                int triggerPost = index;
                while (triggerPost < x.length && x[triggerPost] - x[index] <= range) {
                    triggerPost++;
                }
                ans += hp[index];
                aoe(x, hp, index, triggerPost - 1, range);
            }
        }

        return ans;
    }

    public static void aoe(int[] x, int[] hp, int L, int trigger, int range) {
        int N = x.length;
        int RPost = trigger;
        while (RPost < N && x[RPost] - x[trigger] <= range) {
            RPost++;
        }
        int minus = hp[L];
        for (int i = L; i < RPost; i++) {
            hp[i] = Math.max(0, hp[i] - minus);
        }
    }

    public static void main(String[] args) {
        int N = 500;
        int X = 10000;
        int H = 50;
        int R = 10;
        int time = 5000;
        System.out.println("test begin");
        for (int i = 0; i < time; i++) {
            int len = (int) (Math.random() * N) + 1;
            int[] x = Code06_AOE.randomArray(len, X);
            Arrays.sort(x);
            int[] hp = Code06_AOE.randomArray(len, H);
            int range = (int) (Math.random() * R) + 1;
            int[] x2 = Code06_AOE.copyArray(x);
            int[] hp2 = Code06_AOE.copyArray(hp);
            int ans2 = new MainTest06().minAoe(x2, hp2, range);
            // 已经测过下面注释掉的内容，注意minAoe1非常慢，
            // 所以想加入对比需要把数据量(N, X, H, R, time)改小
//			int[] x1 = copyArray(x);
//			int[] hp1 = copyArray(hp);
//			int ans1 = minAoe1(x1, hp1, range);
//			if (ans1 != ans2) {
//				System.out.println("Oops!");
//				System.out.println(ans1 + "," + ans2);
//			}
            int[] x3 = Code06_AOE.copyArray(x);
            int[] hp3 = Code06_AOE.copyArray(hp);
            int ans3 = Code06_AOE.minAoe3(x3, hp3, range);
            if (ans2 != ans3) {
                System.out.println("Oops!");
                System.out.println(ans2 + "," + ans3);
            }
        }
        System.out.println("test end");
    }
}
