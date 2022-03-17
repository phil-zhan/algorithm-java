package org.study.coding.class15;

//leetcode 123
public class Code03_BestTimeToBuyAndSellStockIII {

	/**
	 *    给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
	 *    设计一个算法来计算你所能获取的最大利润。你最多可以完成两笔交易。
	 *    注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
	 *
	 *    在第四题的基础上。将 k 变成2.就OK了
	 * @since 2022-03-17 02:57:36
	 */
	public static int maxProfit(int[] prices) {
		if (prices == null || prices.length < 2) {
			return 0;
		}
		int ans = 0;
		int doneOnceMinusBuyMax = -prices[0];
		int doneOnceMax = 0;
		int min = prices[0];
		for (int i = 1; i < prices.length; i++) {
			min = Math.min(min, prices[i]);
			ans = Math.max(ans, doneOnceMinusBuyMax + prices[i]);
			doneOnceMax = Math.max(doneOnceMax, prices[i] - min);
			doneOnceMinusBuyMax = Math.max(doneOnceMinusBuyMax, doneOnceMax - prices[i]);
		}
		return ans;
	}

}
