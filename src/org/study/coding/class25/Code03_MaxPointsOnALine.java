package org.study.coding.class25;

import java.util.HashMap;
import java.util.Map;

/**
 * 本题测试链接: https://leetcode.com/problems/max-points-on-a-line/
 * 3. 给你一个数组 points ，其中 points[i] = [xi, yi] 表示 X-Y 平面上的一个点。求最多有多少个点在同一条直线上。
 * <p>
 * 题意：经典的共线问题
 * <p>
 * 解法：
 * 假设有点集 a,b,c,d,e,f,g
 * 任何两个点都会有一个斜率。斜率相等就会共线
 * 针对每个点。都去做一个map
 * key：当期点与剩下的点的斜率 【不需要考虑前面的点。前面的点之前已经考虑过了】
 * value：表示当前斜率下的共线点数
 * <p>
 * 抓一个最大值，就是必过当前点的最多共线点数
 *
 * 怎么表示斜率？？？
 * 1）共斜线
 * 2）共横线
 * 3）共竖线
 * 4）共点
 *
 * 表示斜率不能用double。会有精度问题。我们用分数来表示。相比较的时候，考虑约分
 * @since 2022-04-05 22:13:50
 */
public class Code03_MaxPointsOnALine {


    /**
     * [[1,3]
     * [4,9]
     * [5,7]]
     *
     * @since 2022-04-06 07:34:16
     */
    public static int maxPoints(int[][] points) {
        if (points == null) {
            return 0;
        }
        if (points.length <= 2) {
            return points.length;
        }
        // key是分子，value是一个map【里层map的key是分母，value是当前斜率对应的共线点】
        // key = 3
        // value = {7 , 10}  -> 斜率为3/7的点 有10个
        //         {5,  15}  -> 斜率为3/5的点 有15个
        Map<Integer, Map<Integer, Integer>> map = new HashMap<Integer, Map<Integer, Integer>>();
        int result = 0;

        // 第一层是考虑可能的初始点
        for (int i = 0; i < points.length; i++) {
            // 换了一个出发点，之前的斜率都失效了
            map.clear();

            // 起始点先记录一下
            int samePosition = 1;

            int sameX = 0;
            int sameY = 0;
            int line = 0;

            // i号点，和j号点，的斜率关系 【每个分支都是互斥的。不能算重】
            for (int j = i + 1; j < points.length; j++) {
                int x = points[j][0] - points[i][0];
                int y = points[j][1] - points[i][1];


                if (x == 0 && y == 0) {
                    // 共点
                    samePosition++;
                } else if (x == 0) {
                    // 共x轴
                    sameX++;
                } else if (y == 0) {

                    // 共y轴
                    sameY++;
                } else {
                    // 普通斜率【共斜线】
                    int gcd = gcd(x, y);
                    x /= gcd;
                    y /= gcd;
                    // x / y
                    if (!map.containsKey(x)) {
                        map.put(x, new HashMap<>());
                    }
                    if (!map.get(x).containsKey(y)) {
                        map.get(x).put(y, 0);
                    }
                    map.get(x).put(y, map.get(x).get(y) + 1);
                    line = Math.max(line, map.get(x).get(y));
                }
            }

            // 抓一个最多点数【共点的情况，可以和其他任何情况共线。所以加上后再PK】
            result = Math.max(result, Math.max(Math.max(sameX, sameY), line) + samePosition);
        }
        return result;
    }

    /**
     * 保证初始调用的时候，a和b不等于0
     * O(1)
     * 一个分数 a/b 。要约分成最精简的形式，就需要求他们的最大公约数
     * 该方法就是求他们的最大公约数
     *
     *
     * @since 2022-04-06 07:54:43
     */
    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

}
