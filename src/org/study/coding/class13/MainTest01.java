package org.study.coding.class13;

import java.util.HashMap;
import java.util.Map;

/**
 * @author phil
 * @since 2022-04-29 07:27:04
 */
public class MainTest01 {

    public double func1() {
        return process(0);
    }


    public double process(int cur) {
        if (cur >= 17 && cur < 21) {
            return 1D;
        }
        if (cur >= 21) {
            return 0D;
        }

        double w = 0.0;
        for (int i = 1; i <= 10; i++) {
            w += process(cur + i);
        }

        return w / 10;
    }

    public double func2(int n, int a, int b) {
        return process2(0, n, a, b);
    }

    public double process2(int cur, int n, int a, int b) {

        if (cur >= a && cur < b) {
            return 1.0;
        }

        if (cur >= b) {
            return 0.0;
        }

        double w = 0.0;
        for (int i = 1; i <= n; i++) {
            w += process2(cur + i, n, a, b);
        }

        return w / n;

    }

    public double process3(int cur, int n, int a, int b, Map<Integer, Double> map) {
        if (map.containsKey(cur)) {
            return map.get(cur);
        }

        double ans = 0.0;
        if (cur >= a && cur < b) {
            ans = 1.0;
        } else if (cur >= b) {
            ans = 0.0;
        } else {
            double w = 0.0;
            for (int i = 1; i <= n; i++) {
                w += process2(cur + i, n, a, b);
            }

            ans = w / n;
        }

        map.put(cur, ans);
        return ans;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(new MainTest01().func1());
        System.out.println(System.currentTimeMillis() -start);


        start = System.currentTimeMillis();
        System.out.println(new MainTest01().func2(10, 17, 21));
        System.out.println(System.currentTimeMillis() -start);
        start = System.currentTimeMillis();
        System.out.println(new MainTest01().process3(0, 10, 17, 21, new HashMap<>()));
        System.out.println(System.currentTimeMillis() -start);
    }


}
