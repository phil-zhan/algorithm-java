package org.study.coding.class10;

/**
 * // 测试链接 : https://leetcode.com/problems/k-inverse-pairs-array/
 *给出两个整数n和k，找出所有包含从1到n的数字，且恰好拥有k个逆序对的不同的数组的个数。
 *
 * 逆序对的定义如下：对于数组的第i个和第j个元素，如果满i<j且a[i]>a[j]，则其为一个逆序对；否则不是。
 *
 * 由于答案可能很大，只需要返回 答案 mod 10^9+ 7 的值。
 *
 * 示例 1:
 *
 * 输入: n = 3, k = 0
 * 输出: 1
 * 解释:
 * 只有数组 [1,2,3] 包含了从1到3的整数并且正好拥有 0 个逆序对。
 * 示例 2:
 *
 * 输入: n = 3, k = 1
 * 输出: 2
 * 解释:
 * 数组 [1,3,2] 和 [2,1,3] 都有 1 个逆序对。
 *
 *
 * 样本对应模型。可能性的划分往往和结尾有关。
 *
 * 考虑dp[i][j] ：当能用 arr[1.。。i] 这些数字玩排列的时候，正好逆序对 j 个的排列有几个
 * 行从 1 开始
 * dp[1][0]： 一个数形成0个逆序对为1
 * dp[1][1...k] = 0 一个数不能形成逆序对
 *
 * 第0列全是1 。n个数，形成0个逆序对，只能是递增顺序。没有其他可能
 *
 * 普遍位置：
 * dp[i][j]
 * 假设前面 i-1 个数形成的逆序对为 x 个。
 * 如果最后面再加一个 i 也不会减少逆序对的数量。i 个数的逆序对只会等于x
 * 如果倒数第二的位置加一个 i 。其逆序对的数量比原来的数量多一个【倒数第二和最后一个数形成的】。那么前面 i-1个数就要形成 j-1 个逆序对。加起来刚好等于 j个
 * 如果倒数第三的位置加一个 i 。其逆序对的数量比原来的数量多两个【倒数第三个和最后两个数形成的】。那么前面 i-1个数就要形成 j-2 个逆序对。加起来刚好等于 j 个
 * 如果倒数第四的位置加一个 i 。其逆序对的数量比原来的数量多三个【倒数第四个和最后三个数形成的】。那么前面 i-1个数就要形成 j-3 个逆序对。加起来刚好等于 j 个
 * 。。。
 * 以此类推【j在保证大于等于0的情况下，以此递减】
 * 例如：
 * dp[5][3] = dp[4][3] +
 *            dp[4][2] +
 *            dp[4][1] +
 *            dp[4][0]
 *
 * 考虑的是 当前 i 位置的数能贡献几个
 * j<i 时
 * 最后这个i，最多能撑起所有的逆序对。完全不需要前面的 i-1 个数。也就是前面的 i-1 个数可以选择贡献 j,j-1...0
 *
 * dp[i][j] = dp[i-1][j  ... 0]
 * dp[i][j-1] = dp[i-1][j-1 ... 0]
 * dp[i][j] = dp[i-1][j] + dp[i][j-1]
 *
 * j>=i 时
 * 最后这个i，最多能移动到前面 i-1个数的最前面。也就是最多能贡献 i-1 个逆序对出来 . 也就是前面的 i-1 个数可以选择贡献 j,j-1...j-(i-1)
 * dp[i][j] = dp[i-1][j,j-1, ... j-(i-1)]
 * dp[i][j-1] = dp[i-1][j-1,j-2, ... (j-1)-(i-1)]
 * dp[i][j] = dp[i-1][j] + dp[i][j-1]
 *
 * 注意减掉多余的 dp[i - 1][j - i]
 *
 * 可以考虑斜率优化
 *
 * @since 2022-03-11 08:46:35
 */
public class Code03_KInversePairs {


    public static int kInversePairs(int n, int k) {
        if (n < 1 || k < 0) {
            return 0;
        }
        int[][] dp = new int[n + 1][k + 1];
        dp[0][0] = 1;
        int mod = 1000000007;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1;
            for (int j = 1; j <= k; j++) {
                dp[i][j] = (dp[i][j - 1] + dp[i - 1][j]) % mod;
                if (j >= i) {
                    // j >= i 时。我们只需要 dp[i-1][j,j-1, ... j-(i-1)]
                    // 也就是 dp[i - 1][j - i] 是多余的

                    // 正常情况下，加或减，在没操作一次，都去取一个 mod。和操作完之后再去整体 mod 的结果是一样的。
                    // 但是每一个中间结果，都可能是 mod 过的结果
                    // 如果 a 是一个mod 后的结果
                    // 如果 b 是一个mod 后的结果
                    // 不能判断 mod 之前谁大谁小 .就可能在做减法操作的时候。正常本来应该是 a > b 的。减完之后也是大于 0 的
                    // 但是因为是取模的结果去减。就可能导致减完之后小于0
                    // 为了避免这种情况。将被减数加一个 mod 。再去减。 然后再去mod。就能得到和取模之前相减再取模的结果是一样的

                    dp[i][j] = (dp[i][j] - dp[i - 1][j - i] + mod) % mod;
                }
            }
        }
        return dp[n][k];
    }

    /**
     * 不取模
     * @since 2022-03-12 10:46:09
     */
    public static int kInversePairs2(int n, int k) {
        if (n < 1 || k < 0) {
            return 0;
        }
        int[][] dp = new int[n + 1][k + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1;
            for (int j = 1; j <= k; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                if (j >= i) {
                    dp[i][j] -= dp[i - 1][j - i];
                }
            }
        }
        return dp[n][k];
    }

}
