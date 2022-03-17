package org.study.coding.class15;

/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
 *
 * 给定一个数组 prices ，其中prices[i] 表示股票第 i 天的价格。
 * 在每一天，你可能会决定购买和/或出售股票。你在任何时候最多只能持有 一股 股票。你也可以购买它，然后在 同一天 出售。
 * 返回 你能获得的 最大 利润。
 *
 * 最大收益：
 * 在每一次低点买入，在每一次的高点卖出
 *
 * 从1位置开始。用当前位置的数去减前一个数。
 * 如果减出来的是正数。那么这个差值就是当前位置的收益
 * 如果减出来的是小于等于0. 那么该位置挣的钱就是0
 *
 * 最后将所有的收益累加起来
 *
 *
 * @since 2022-03-17 11:13:21
 */
public class Code02_BestTimeToBuyAndSellStockII {

	public static int maxProfit(int[] prices) {
		if (prices == null || prices.length == 0) {
			return 0;
		}
		int ans = 0;
		for (int i = 1; i < prices.length; i++) {
            ans += Math.max(prices[i] - prices[i-1], 0);
		}
		return ans;
	}
	
}
