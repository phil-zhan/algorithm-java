package org.study.system.class14;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 最多可做项目
 *
 * @author phil
 * @date 2021/7/8 16:40
 */
public class MainTest05 {


    public static class Program {
        // 利润
        public int profit;

        // 成本
        public int capital;

        public Program(int profit, int capital) {
            this.profit = profit;
            this.capital = capital;
        }
    }
    /**
     * k:最多可做K个项目
     * w：初始资金
     * profits[i]:第i个项目所能获得的利润
     * capital[i]:第i个项目所要投入的成本
     * @date 2021-07-08 16:45:35
     */
    private static int findMaximizedCapital(int k,int w, int[] profits, int[] capital){
        PriorityQueue<Program> minCost = new PriorityQueue<>(new MinCostComparator());
        PriorityQueue<Program> maxProfile = new PriorityQueue<>(new MaxProfitComparator());

        for (int i = 0; i < capital.length; i++) {
            minCost.add(new Program(profits[i],capital[i]));
        }

        for (int j = 0; j < k; j++) {
            while (!minCost.isEmpty() && minCost.peek().capital <= w){
                maxProfile.add(minCost.poll());
            }
            if(maxProfile.isEmpty()){
                return w;
            }

            w += maxProfile.poll().profit;
        }

        return w;
    }

    private static class MinCostComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.capital - o2.capital;
        }
    }

    private static class MaxProfitComparator implements Comparator<Program>{

        @Override
        public int compare(Program o1, Program o2) {
            return o2.profit - o1.profit;
        }
    }

    public static void main(String[] args) {

    }
}
