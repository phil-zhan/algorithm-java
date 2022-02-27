package org.study.class03;

/**
 * @author phil
 * @date 2021/3/15 10:29
 * Master公式
 *
 * 用来分析递归函数的时间复杂度的
 * 仅限子问题规模的是一致的【也就是分解后的子问题的规模是一样的】
 * T(n) = 2*T(n/2) +O(1)
 * T(n) = 3*T(n/3) +O(1)
 * T(n) = 3*T(n/3) +O(1)
 *
 * T(n) = a*T(n/b) +O(N^d)  [a、b、d都是常数]
 *
 * 。。。
 *
 * 当递归形式符合上面的式子的时候。其复杂度如下【注：log(b,a)表示以log以b为底a的对数】
 * 1）log(b,a) < d	  O(N^d)
 * 2）log(b,a) > d	  O(N^log(b,a)
 * 3）log(b,a) == d	  O(N^d * logN)
 *
 */
public class Code06 {
}
