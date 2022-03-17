package org.study.coding.class15;

/**
 * leetcode 714
 *    给定一个整数数组prices，其中 prices[i]表示第i天的股票价格 ；整数fee 代表了交易股票的手续费用。
 *    你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
 *    返回获得利润的最大值。
 *
 *
 * @since 2022-03-17 05:04:53
 */
public class Code06_BestTimeToBuyAndSellStockWithTransactionFee {

	/**
	 * 就是可以无限次交易。但是每一次交易都要扣除手续费
	 * @since 2022-03-17 05:07:45
	 */
	public static int maxProfit(int[] prices, int fee) {
		if (prices == null || prices.length < 2) {
			return 0;
		}
		int N = prices.length;
		// 0..0   0 -[0] - fee
		int bestbuy = -prices[0] - fee;
		// 0..0  卖  0
		int bestsell = 0;
		for (int i = 1; i < N; i++) {

			// 来到i位置了！
			// 如果在i必须买  收入 - 批发价 - fee
			int curbuy = bestsell - prices[i] - fee;

			// 如果在i必须卖  整体最优（收入 - 良好批发价 - fee）
			int cursell = bestbuy + prices[i];

			bestbuy = Math.max(bestbuy, curbuy);

			bestsell = Math.max(bestsell, cursell);
		}
		return bestsell;
	}

}
