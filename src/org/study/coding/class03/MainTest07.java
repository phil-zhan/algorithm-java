package org.study.coding.class03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author phil
 * @since 2022-0306 09:13:08
 */
public class MainTest07 {

    public int findRotateSteps(String r,String k){

        char[] ring = r.toCharArray();
        Map<Character, ArrayList<Integer>> map = new HashMap<>();

        for (int i = 0; i < ring.length; i++) {
            if (!map.containsKey(ring[i])){
                map.put(ring[i], new ArrayList<>());
            }

            map.get(ring[i]).add(i);
        }

        char[] str = k.toCharArray();
        int[][] dp = new int[ring.length][str.length+1];

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j] = -1;
            }
        }


        return process(0,0,dp,str,map, ring.length);

    }

    public int process(int pre,int index,int[][] dp,char[] str,Map<Character,ArrayList<Integer>> map,int ringSize){
        if (dp[pre][index] != -1){
            return dp[pre][index];
        }

        int ans = Integer.MAX_VALUE;
        if (index == str.length){
            ans = 0;
        }else {
            char cur = str[index];
            ArrayList<Integer> positions = map.get(cur);
            for (Integer pos:positions) {
                ans = Math.min(dial(pre,pos,ringSize)+1 + process(pos,index+1,dp,str,map,ringSize),ans);
            }
        }

        dp[pre][index] = ans;
        return ans;
    }

    public int dial(int pre,int cur,int size){
        return Math.min(Math.abs(pre - cur), Math.min(pre, cur) + size - Math.max(pre, cur));
    }
}
