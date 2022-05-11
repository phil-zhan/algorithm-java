package org.study.coding.class15;

public class MainTest06 {

    public static void main(String[] args) {
        System.out.println(new MainTest06().maxProfit(new int[]{1, 3, 2, 8, 4, 9}, 2));
    }

    public int maxProfit(int[] prices, int fee) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int bestBuy = -prices[0] - fee;
        int bestSell = 0;

        for (int i = 1; i < prices.length; i++) {
            bestBuy = Math.max(bestBuy, bestSell - prices[i] - fee);

            bestSell = Math.max(bestSell, bestBuy + prices[i]);
        }

        return bestSell;
    }
}
