package org.study.system.class18;

import java.util.Arrays;

/**
 * 机器人走路方案
 *
 * @author phil
 * @date 2021/7/26 12:11
 */
public class MainTest01 {

    private static int ways1(int N,int start,int aim,int K){
        if (  N < 2 || start < 1 || start > N || K < 1 || aim < 1 || aim > N) {
            return -1;
        }

        return process1(start,aim,K,N);
    }

    private static int process1(int cur,int aim,int rest,int N){
        if (rest == 0) {
            return cur == aim ? 1 : 0;
        }
        if(cur == 1){
            return process1(2,aim,rest-1,N);
        }

        if(cur == N){
            return process1(N-1,aim,rest-1,N);
        }


        return process1(cur-1,aim,rest-1,N) + process1(cur+1,aim,rest-1,N);

    }


    private static int ways2(int N,int start,int aim,int K){
        if (  N < 2 || start < 1 || start > N || K < 1 || aim < 1 || aim > N) {
            return -1;
        }

        int[][] dp = new int[N+1][K+1];

        // 初始化数组所有元素为 -1；方便后面判断
        for (int[] ints : dp) {
            Arrays.fill(ints, -1);
        }
        
        return process2(N,start,aim,K,dp);

    }

    private static int process2(int N,int cur,int aim,int rest,int[][] dp){

        if (dp[cur][rest] != -1) {
            return dp[cur][rest];
        }

        if (rest == 0) {
            return cur == aim ? 1 : 0;
        }

        int answer;
        if(cur == 1){
            answer = process2(N,cur + 1,aim,rest - 1,dp);
        }else if(cur == N){
            answer = process2(N,cur - 1,aim,rest - 1,dp);
        }else{
            answer = process2(N,cur + 1,aim,rest - 1,dp) + process2(N,cur - 1,aim,rest - 1,dp);
        }

        dp[cur][rest] = answer;

        return answer;
    }


    private static int ways3(int N,int start,int aim,int K){
        if (  N < 2 || start < 1 || start > N || K < 1 || aim < 1 || aim > N) {
            return -1;
        }

        int[][] dp = new int[N+1][K+1];
        
        dp[aim][0]=1;
        for (int rest = 1; rest <= K; rest++) {
            dp[1][rest] = dp[1+1][rest-1];

            for (int cur = 2; cur < N; cur++) {
                dp[cur][rest] = dp[cur-1][rest-1] + dp[cur+1][rest-1];
            }

            dp[N][rest] = dp[N-1][rest-1];
        }
        
        

        return dp[start][K];
    }
    public static void main(String[] args) {
        long start1 = System.currentTimeMillis();
        System.out.println(ways1(100, 2, 25, 31));
        long end1 = System.currentTimeMillis();
        System.out.println("way1 used time ："+ (end1 - start1));

        long start2 = System.currentTimeMillis();
        System.out.println(ways2(10000, 2, 2500, 3100));
        long end2 = System.currentTimeMillis();
        System.out.println("way2 used time ："+ (end2 -start2));


        long start3 = System.currentTimeMillis();
        System.out.println(ways3(10000, 2, 2500, 3100));
        long end3 = System.currentTimeMillis();
        System.out.println("way2 used time ："+ (end3 -start3));
    }
}
