package org.study.coding.class20;

import java.util.HashMap;

// 本题为leetcode原题
//

/**
 * 测试链接：https://leetcode.com/problems/largest-component-size-by-common-factor/
 * 法1会超时，但是方法2直接通过
 * <p>
 * 给定一个由不同正整数的组成的非空数组nums ，考虑下面的图：
 * <p>
 * 有nums.length个节点，按从nums[0]到nums[nums.length - 1]标记；
 * 只有当nums[i]和nums[j]共用一个大于 1 的公因数时，nums[i]和nums[j]之间才有一条边。
 * 返回 图中最大连通组件的大小 。
 * <p>
 * 题意：
 * 数组中，任意两个数。如果其最大公约数不等于1，就算一类。
 * 比如 3,7,21
 * 3和21 算一类
 * 7和21 算一类
 * 那么3,7,21 这三个数都算一类。
 * 但是单独的3和7不算一类
 * 返回数组中有几个类
 * <p>
 * 解法：
 * 1）：暴力解。考虑每个数和剩余的数是否拥有最大公约数。O(n^2)
 * 2): 并查集
 * 准备一张表。key：因子。value：当前数对应数组下标
 * 遍历数组。求出每个数的因子，放到hash表中去。如果hash表中，已经存在了对应的因子，就将对应的数合并【并查集】
 * 如果hash表中，已经存在了对应的因子。通过因子取出对应的数组下标。然后将两个数合并【拥有共同因子的数】
 * hash表中，如果两个数拥有共同的因子，因子是key，value存一个就行。因为在并查集中，他们是合并的。找到一个就等于找到另外一个
 * 【"1"这个因子不考虑】
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 判断一个数x是不是质数
 * 1）：暴力解。从1开始，到x-1.都和他取一下摸。看看会不会有余数为0 【O(x)】
 * 2)：
 * 当发现x%2不等于0。那么x/2往后都不用验证了
 * 当发现x%3不等于0。那么x/3往后都不用验证了
 * 当发现x%4不等于0。那么x/4往后都不用验证了
 * 。。。
 * 当发现x%根号x不等于0。那么x除以根号x往后都不用验证了
 * 时间复杂度 O(根号x)
 * <p>
 * 找一个数的所有因子
 * 从1开始。一对一对的来
 * 假设求100的所有因子
 * 1...100
 * 2...50
 * 4...25
 * 5...20
 * 10...10
 * 也就是从1开始，试到根号n，就停【O(根号n)】
 *
 * @since 2022-03-22 07:48:25
 */
public class Code02_LargestComponentSizebyCommonFactor {

    /**
     * 时间复杂度 O(n^2)
     *
     * @since 2022-03-22 09:05:46
     */
    public static int largestComponentSize1(int[] arr) {
        int N = arr.length;
        UnionFind set = new UnionFind(N);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (gcd(arr[i], arr[j]) != 1) {
                    set.union(i, j);
                }
            }
        }
        return set.maxSize();
    }

    /**
     * 时间复杂度 O( n* 根号value )
     *
     * @since 2022-03-22 09:05:46
     */
    public static int largestComponentSize2(int[] arr) {
        int N = arr.length;
        // arr中，N个位置，在并查集初始时，每个位置自己是一个集合
        // 并查集中的元素是位置。不是元素
        UnionFind unionFind = new UnionFind(N);

        // key 某个因子
        // value 哪个位置拥有这个因子
        // 多个位置都有某个因子。记一个就行
        HashMap<Integer, Integer> fatorsMap = new HashMap<>();

        for (int i = 0; i < N; i++) {
            int num = arr[i];

            // 求出根号N， -> limit
            int limit = (int) Math.sqrt(num);

            // 抓出因子
            for (int j = 1; j <= limit; j++) {
                // 都不考虑1这个因子

                // j 是num的因子
                if (num % j == 0) {
                    if (j != 1) {
                        // 不存在就记录，存在就合并
                        if (!fatorsMap.containsKey(j)) {
                            fatorsMap.put(j, i);
                        } else {
                            unionFind.union(fatorsMap.get(j), i);
                        }
                    }
                    // 另一个因子
                    int other = num / j;

                    if (other != 1) {
                        if (!fatorsMap.containsKey(other)) {
                            fatorsMap.put(other, i);
                        } else {
                            unionFind.union(fatorsMap.get(other), i);
                        }
                    }
                }
            }
        }
        return unionFind.maxSize();
    }

    /**
     * 求m和n的最大公约数【辗转相除法。背住】
     * O(1)
     * m,n 要是正数，不能有任何一个等于0
     *
     * @since 2022-03-22 07:49:29
     */
    public static int gcd(int m, int n) {
        return n == 0 ? m : gcd(n, m % n);
    }

    public static class UnionFind {
        private int[] parents;
        private int[] sizes;
        private int[] help;

        public UnionFind(int N) {
            parents = new int[N];
            sizes = new int[N];
            help = new int[N];
            for (int i = 0; i < N; i++) {
                parents[i] = i;
                sizes[i] = 1;
            }
        }

        public int maxSize() {
            int ans = 0;
            for (int size : sizes) {
                ans = Math.max(ans, size);
            }
            return ans;
        }

        private int find(int i) {
            int hi = 0;
            while (i != parents[i]) {
                help[hi++] = i;
                i = parents[i];
            }
            for (hi--; hi >= 0; hi--) {
                parents[help[hi]] = i;
            }
            return i;
        }

        public void union(int i, int j) {
            int f1 = find(i);
            int f2 = find(j);
            if (f1 != f2) {
                int big = sizes[f1] >= sizes[f2] ? f1 : f1;
                int small = big == f1 ? f2 : f1;
                parents[small] = big;
                sizes[big] = sizes[f1] + sizes[f2];
            }
        }
    }

}
