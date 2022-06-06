package org.study.coding.class36;

import java.util.HashMap;

public class MainTest06 {

    public static void main(String[] args) {

    }

    public static void w(int h, int[][] m, int[] w, int[] c) {

        if (m[h].length == 0){
            return;
        }

        // 有若干个直接孩子
        // key 是节点
        // value 是该节点对应子节点的颜色种数
        // 1 7个
        // 3 10个
        HashMap<Integer, Integer> colors = new HashMap<>();

        // key 是颜色
        // value 是该颜色的权重之和
        // 1 20
        // 3 45
        HashMap<Integer, Integer> weihts = new HashMap<>();
        for (int child : m[h]) {
            w(child, m, w, c);
            colors.put(c[child], colors.getOrDefault(c[child], 0) + 1);
            weihts.put(c[child], weihts.getOrDefault(c[child], 0) + w[child]);
        }
        for (int color : colors.keySet()) {
            w[h] = Math.max(w[h], colors.get(color) + weihts.get(color));
        }

    }
}
