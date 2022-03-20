package org.study.coding.class19;

import java.util.LinkedList;

/**
 * 一张扑克有3个属性，每种属性有3种值（A、B、C）
 * 比如"AAA"，第一个属性值A，第二个属性值A，第三个属性值A
 * 比如"BCA"，第一个属性值B，第二个属性值C，第三个属性值A
 * 给定一个字符串类型的数组cards[]，每一个字符串代表一张扑克
 * 从中挑选三张扑克，一个属性达标的条件是：这个属性在三张扑克中全一样，或全不一样
 * 挑选的三张扑克达标的要求是：每种属性都满足上面的条件
 * 比如："ABC"、"CBC"、"BBC"
 * 第一张第一个属性为"A"、第二张第一个属性为"C"、第三张第一个属性为"B"，全不一样
 * 第一张第二个属性为"B"、第二张第二个属性为"B"、第三张第二个属性为"B"，全一样
 * 第一张第三个属性为"C"、第二张第三个属性为"C"、第三张第三个属性为"C"，全一样
 * 每种属性都满足在三张扑克中全一样，或全不一样，所以这三张扑克达标
 * 返回在cards[]中任意挑选三张扑克，达标的方法数
 *
 * 解法：
 * 每张牌只有三种属性，每种属性只有三个取值。所以每张牌的牌面值只会有27种组合【牌面值：就是把A看成0，B看成1，C看成2】
 * 【AAA,AAB.AAC , ABA,ABB.ABC , ACA,ACB.ACC , BAA,BAB.BAC , BBA,BBB.BBC , BCA,BCB.BCC , CAA,CAB.CAC , CBA,CBB.CBC , CCA,CCB.CCC】
 * 也就相当于每张牌有27中可能的组合
 *
 * 统计一下，看看所有的牌中：
 * AAA有多少张, 在这AAA中，随意挑选3张，肯定都达标
 * AAB有多少张, 在这AAB中，随意挑选3张，肯定都达标
 * AAC有多少张, 在这AAC中，随意挑选3张，肯定都达标
 * ...
 * 都是自己和自己比，肯定能达标
 *
 * 也就是统计27中可能，各种可能有多少张，在每一种可能中，任挑三张，都是达标的
 *
 * 考虑交错的情况
 * 1）ABC*100张  BAB*50张   CAC*60张   【看看这种情况下，组合数有多少。100*50*60】
 *
 *
 */
public class Code05_CardsProblem {

    public static int ways1(String[] cards) {
        LinkedList<String> picks = new LinkedList<>();
        return process1(cards, 0, picks);
    }

    /**
     * 考虑每张牌要或不要
     * 没相当选择的牌达到三张，就做一次验证。
     * 看看满不满足条件。满足就是一种方案。不满足就是选择无效. 0种方案
     *
     * 暴力方法
     *
     * @since 2022-03-20 11:24:44
     */
    public static int process1(String[] cards, int index, LinkedList<String> picks) {
        if (picks.size() == 3) {
            return getWays1(picks);
        }
        if (index == cards.length) {
            return 0;
        }
        int ways = process1(cards, index + 1, picks);
        picks.addLast(cards[index]);
        ways += process1(cards, index + 1, picks);
        picks.pollLast();
        return ways;
    }

    public static int getWays1(LinkedList<String> picks) {
        char[] s1 = picks.get(0).toCharArray();
        char[] s2 = picks.get(1).toCharArray();
        char[] s3 = picks.get(2).toCharArray();
        for (int i = 0; i < 3; i++) {
            if ((s1[i] != s2[i] && s1[i] != s3[i] && s2[i] != s3[i]) || (s1[i] == s2[i] && s1[i] == s3[i])) {
                continue;
            }
            return 0;
        }
        return 1;
    }

    /**
     * 考虑的方法
     * @since 2022-03-20 11:30:29
     */
    public static int ways2(String[] cards) {
        int[] counts = new int[27];
        for (String s : cards) {
            char[] str = s.toCharArray();
            // 统计牌面值对应的张数。ABC对应0、1、2.将其看成三进制。转化成十进制
            counts[(str[0] - 'A') * 9 + (str[1] - 'A') * 3 + (str[2] - 'A') * 1]++;
        }

        // 考虑只来自一种版面的情况下。有多少种选择。也就是三张牌的牌面都一样的情况。
        // 假设当前牌面有100张，那么，就是从100张中随机选择三张。有多少张可选方法
        int ways = 0;
        for (int status = 0; status < 27; status++) {
            int n = counts[status];
            if (n > 2) {

                // (n * (n - 1) * (n - 2) / 6)   就是C(n,3)
                ways += n == 3 ? 1 : (n * (n - 1) * (n - 2) / 6);
            }
        }

        // 考虑交错的情况
        LinkedList<Integer> path = new LinkedList<>();
        for (int i = 0; i < 27; i++) {
            if (counts[i] != 0) {
                path.addLast(i);
                ways += process2(counts, i, path);
                path.pollLast();
            }
        }
        return ways;
    }


    /**
     *     // 之前的牌面，拿了一些    ABC  BBB  ...
     *     // pre = BBB
     *     // ABC  ...
     *     // pre  = ABC
     *     // ABC BBB CAB
     *     // pre = CAB
     *     // 之前已经拿的牌面中，那个较大的就是pre
     *     // 牌面一定要依次变大，所有形成的有效牌面，把方法数返回
     *
     *     为了防止算重 如：【AAA,BBB,CCC】和【BBB,AAA,CCC】是同一种
     *     前面挑的牌中，如果牌面值较大的是x，那么这一次就从牌面值在x后面的这些牌中挑
     *     相当于不要往前拿
     *
     *      // 牌面一定要依次变大，所有形成的有效牌面，把方法数返回
     * @since 2022-03-20 11:45:27
     */
    public static int process2(int[] counts, int pre, LinkedList<Integer> path) {
        if (path.size() == 3) {

            // 选足了三张。计算一下方法数
            return getWays2(counts, path);
        }
        int ways = 0;

        // 从pre往后去挑选
        for (int next = pre + 1; next < 27; next++) {
            if (counts[next] != 0) {
                // 每种牌面考虑要或不要
                // 维护现场
                path.addLast(next);
                ways += process2(counts, next, path);
                path.pollLast();
            }
        }
        return ways;
    }

    /**
     * 三张牌。验证有效的方法数
     * @since 2022-03-21 12:00:41
     */
    public static int getWays2(int[] counts, LinkedList<Integer> path) {
        // 第一个牌面
        int v1 = path.get(0);
        // 第二个牌面
        int v2 = path.get(1);
        // 第三个牌面
        int v3 = path.get(2);
        for (int i = 9; i > 0; i /= 3) {

            // 拿到第k个属性值
            int cur1 = v1 / i;
            int cur2 = v2 / i;
            int cur3 = v3 / i;

            // 剩余的牌面【抹掉最高位】
            v1 %= i;
            v2 %= i;
            v3 %= i;

            // 是不是全都一样，或全都不一样
            if ((cur1 != cur2 && cur1 != cur3 && cur2 != cur3) || (cur1 == cur2 && cur1 == cur3)) {
                continue;
            }

            // 只要有一个属性不满足。直接返回
            return 0;
        }

        v1 = path.get(0);
        v2 = path.get(1);
        v3 = path.get(2);

        // 三个属性都满足，计算组合数返回
        return counts[v1] * counts[v2] * counts[v3];
    }

    // for test
    public static String[] generateCards(int size) {
        int n = (int) (Math.random() * size) + 3;
        String[] ans = new String[n];
        for (int i = 0; i < n; i++) {
            char cha0 = (char) ((int) (Math.random() * 3) + 'A');
            char cha1 = (char) ((int) (Math.random() * 3) + 'A');
            char cha2 = (char) ((int) (Math.random() * 3) + 'A');
            ans[i] = String.valueOf(cha0) + String.valueOf(cha1) + String.valueOf(cha2);
        }
        return ans;
    }

    // for test
    public static void main(String[] args) {
        int size = 20;
        int testTime = 100000;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            String[] arr = generateCards(size);
            int ans1 = ways1(arr);
            int ans2 = ways2(arr);
            if (ans1 != ans2) {
                for (String str : arr) {
                    System.out.println(str);
                }
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("test finish");

        long start = 0;
        long end = 0;
        String[] arr = generateCards(10000000);
        System.out.println("arr size : " + arr.length + " runtime test begin");
        start = System.currentTimeMillis();
        ways2(arr);
        end = System.currentTimeMillis();
        System.out.println("run time : " + (end - start) + " ms");
        System.out.println("runtime test end");
    }

}
