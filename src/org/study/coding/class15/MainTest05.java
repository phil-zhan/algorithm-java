package org.study.coding.class15;

public class MainTest05 {

    public static void main(String[] args) {
        System.out.println(new MainTest05().maxProfit(new int[]{1, 2, 3, 0, 2}));
        System.out.println(new MainTest05().maxProfit2(new int[]{1, 2, 3, 0, 2}));
    }

    public int maxProfit(int[] prices) {
        return process(prices, false, 0, 0);
    }


    public int process(int[] prices, boolean buy, int buyPrice, int index) {
        if (index >= prices.length) {
            return 0;
        }

        if (buy) {
            int sell = prices[index] - buyPrice + process(prices, false, buyPrice, index + 2);
            int noSell = process(prices, true, buyPrice, index + 1);
            return Math.max(sell, noSell);

        } else {
            int yes = process(prices, true, prices[index], index + 1);
            int no = process(prices, false, buyPrice, index + 1);

            return Math.max(yes, no);
        }
    }

    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int len = prices.length;
        int[] buy = new int[len];
        int[] sell = new int[len];

        buy[0] = -prices[0];
        sell[0] = 0;

        buy[1] = Math.max(buy[0], -prices[1]);
        sell[1] = Math.max(sell[0], buy[0] + prices[1]);

        for (int i = 2; i < len; i++) {
            buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i]);
            sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
        }
        return sell[len - 1];
    }
}
