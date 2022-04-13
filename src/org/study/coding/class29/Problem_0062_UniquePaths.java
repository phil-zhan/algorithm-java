package org.study.coding.class29;

/**
 * 62. 不同路径
 * 排列组合
 * <p>
 * m 行
 * n 列
 * 下：m-1
 * 右：n-1
 * <p>
 * C(m+n-2,n-1)  或者 C(m+n-2,m-1)  【两个一样的】
 * <p>
 * 为了防止溢出。在求排列组合的时候，采用最大公约数
 *
 *
 * 也可以用动态规划去做
 *
 * @since 2022-04-13 22:41:31
 */
public class Problem_0062_UniquePaths {


    public static int uniquePaths(int m, int n) {

        // 往右的步数
        int right = n - 1;

        // 总共的步数 C(all,right)
        int all = m + n - 2;

        // o1就是分子
        long o1 = 1;

        // o2就是分母
        long o2 = 1;

        // all = 10
        // right = 4

        // C(all,right) = C(10,4) = C(10,6) = 5*6*7*8*9*10   /   1*2*3*4*5*6
        // 总之就是分子的个数和分母的个数是一样的。都是 right 个
        // C(n,m) = n*(n-1)*...*(n-m+1) / m*(m-1)*...*1

        // o1乘进去的个数 一定等于 o2乘进去的个数
        // 每乘一次都约一下分，不要越界了
        for (int i = right + 1, j = 1; i <= all; i++, j++) {
            o1 *= i;
            o2 *= j;
            long gcd = gcd(o1, o2);
            o1 /= gcd;
            o2 /= gcd;
        }
        return (int) o1;
    }

    /**
     * 调用的时候，请保证初次调用时，m和n都不为0
     *
     * @since 2022-04-13 22:43:02
     */
    public static long gcd(long m, long n) {
        return n == 0 ? m : gcd(n, m % n);
    }

}
