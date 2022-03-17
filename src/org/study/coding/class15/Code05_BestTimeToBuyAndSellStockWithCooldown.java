package org.study.coding.class15;

//leetcode 309

/**
 * 给定一个整数数组prices，其中第prices[i]表示第i天的股票价格 。
 * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
 * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 * 考虑 i 位置的时候。如果要卖，怎么获得最好收益。
 *
 * @since 2022-03-17 03:00:26
 */
public class Code05_BestTimeToBuyAndSellStockWithCooldown {

    /**
     * 该方法是对的，提交之后大量的测试用例通过，最后几个会超时
     * 如果把这个方法改成动态规划，是可以通过的，但这个尝试不是最优解
     *
     * @since 2022-03-17 03:00:39
     */
    public static int maxProfit1(int[] prices) {
        return process1(prices, false, 0, 0);
    }


    /**
     * buy == false 目前可以交易，而且当前没有购买行为
     * buy == true 已经买了，买入价buyPrices，待卖出
     *
     * @since 2022-03-17 03:00:48
     */
    public static int process1(int[] prices, boolean buy, int index, int buyPrices) {
        if (index >= prices.length) {
            return 0;
        }
        if (buy) {
            int noSell = process1(prices, true, index + 1, buyPrices);
            int yesSell = prices[index] - buyPrices + process1(prices, false, index + 2, 0);
            return Math.max(noSell, yesSell);
        } else {
            int noBuy = process1(prices, false, index + 1, 0);
            int yesBuy = process1(prices, true, index + 1, prices[index]);
            return Math.max(noBuy, yesBuy);
        }
    }


    /**
     * 最优尝试如下：
     * buy[i] : 在0...i范围上，最后一次操作是buy动作，
     * 这最后一次操作有可能发生在i位置，也可能发生在i之前
     *
     * buy[i]值的含义是：max{ 所有可能性[之前交易获得的最大收益 - 最后buy动作的收购价格] }
     * 比如：arr[0...i]假设为[1,3,4,6,2,7,1...i之后的数字不用管]
     * 什么叫，所有可能性[之前交易获得的最大收益 - 最后buy动作的收购价格]？
     * 比如其中一种可能性：
     * 假设最后一次buy动作发生在2这个数的时候，那么之前的交易只能在[1,3,4]上结束，因为6要cooldown的，
     * 此时最大收益是多少呢？是4-1==3。那么，之前交易获得的最大收益 - 最后buy动作的收购价格 = 3 - 2 = 1
     * 另一种可能性：
     * 再比如最后一次buy动作发生在最后的1这个数的时候，那么之前的交易只能在[1,3,4,6,2]上发生，因为7要cooldown的，
     * 此时最大收益是多少呢？是6-1==5。那么，之前交易获得的最大收益 - 最后buy动作的收购价格 = 5 - 1 = 4
     * 除了上面两种可能性之外，还有很多可能性，你可以假设每个数字都是最后buy动作的时候，
     * 所有可能性中，(之前交易获得的最大收益 - 最后buy动作的收购价格)的最大值，就是buy[i]的含义
     * 为啥buy[i]要算之前的最大收益 - 最后一次收购价格？尤其是最后为什么要减这么一下？
     * 因为这样一来，当你之后以X价格做成一笔交易的时候，当前最好的总收益直接就是 X + buy[i]了
     * //
     * sell[i] :0...i范围上，最后一次操作是sell动作，这最后一次操作有可能发生在i位置，也可能发生在之前
     * sell[i]值的含义：0...i范围上，最后一次动作是sell的情况下，最好的收益
     * //
     * 于是通过分析，能得到以下的转移方程：
     * buy[i - 1] ： i 位置不参与
     * sell[i - 2] - prices[i] ： i 位置要买的情况下，的指标
     *
     * buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i])
     * <p>
     * <p>
     * <p>
     * 如果i位置没有发生buy行为，说明有没有i位置都一样，那么buy[i] = buy[i-1]，这显而易见
     * 如果i位置发生了buy行为, 那么buy[i] = sell[i - 2] - prices[i]，
     * 因为你想在i位置买的话，你必须保证之前交易行为发生在0...i-2上，
     * 因为如果i-1位置有可能参与交易的话，i位置就要cooldown了，
     * 而且之前交易行为必须以sell结束，你才能buy，而且要保证之前交易尽可能得到最好的利润，
     * 这正好是sell[i - 2]所代表的含义，并且根据buy[i]的定义，最后一定要 - prices[i]
     * //
     * sell[i] ： [0...i] 范围上做无限次交易，最后一次必须是卖的情况下，获得的最后收益
     * 最后一次动作可以在 i 位置。也可以在 i 之前
     *
     * sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i])
     * 如果i位置没有发生sell行为，那么sell[i] = sell[i-1]，这显而易见
     * 如果i位置发生了sell行为，那么我们一定要找到 {之前获得尽可能好的收益 - 最后一次的收购价格尽可能低}，
     * 而这正好是buy[i - 1]的含义！之前所有的"尽可能"中，最好的一个！
     *
     * @since 2022-03-17 03:02:20
     */
    public static int maxProfit2(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }
        int N = prices.length;
        int[] buy = new int[N];
        int[] sell = new int[N];
        // buy[0] 不需要设置  buy[0] = -arr[0]
        // sell[0] = 0

        // 0位置发生买:0-price[0]
        // 1位置发生买. 那么0位置就不能发生卖的交易。因为有cool down。  0-prices[1]
        buy[1] = Math.max(-prices[0], -prices[1]);

        // 0位置发生卖。【0买0卖】
        // 1位置发生卖。【0买1卖。1买1卖】
        sell[1] = Math.max(0, prices[1] - prices[0]);


        for (int i = 2; i < N; i++) {
            buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i]);

            sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
        }
        return sell[N - 1];
    }

    /**
     * 最优解就是方法2的空间优化而已
     *
     * @since 2022-03-17 03:02:31
     */
    public static int maxProfit3(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }
        int buy1 = Math.max(-prices[0], -prices[1]);
        int sell1 = Math.max(0, prices[1] - prices[0]);
        int sell2 = 0;
        for (int i = 2; i < prices.length; i++) {
            int tmp = sell1;
            sell1 = Math.max(sell1, buy1 + prices[i]);
            buy1 = Math.max(buy1, sell2 - prices[i]);
            sell2 = tmp;
        }
        return sell1;
    }

}
