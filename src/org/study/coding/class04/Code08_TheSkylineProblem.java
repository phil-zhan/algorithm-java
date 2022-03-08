package org.study.coding.class04;

import java.util.*;
import java.util.Map.Entry;

// 本题测试链接 : https://leetcode.com/problems/the-skyline-problem/

/**
 * 沿着x轴的正方向，从走往右。最大高度发生变化的时候，就记录一下当前轮廓线的起点和高度，
 * 在下一次高度发生变化（增加或减少）的时候，补全当前轮廓线的结束点，同时记录下一次的起点和高度
 * 最后一次变为0的时候，补全上一次的结束点就行。
 * <p>
 * 最大高度怎么追踪？？
 * 原始数据是[x1,x2,h] 表示一栋大楼，x1开始到x2结束，高度为h。
 * 将其封装为两个对象。
 * 对象1：x1位置，增加了h
 * 对象2：x2位置，减少了h
 * 对于一栋楼来说，只会在这两个点，发生高度的变化。收集答案的时候，只需要考虑这些可能导致最大高度发生变化的点。
 * 设最开始的高度为0，高度发生变化，就收集答案
 * <p>
 * 这样就将所有的楼封装成了 2N 个对象
 * 将这些对象按照第一维度排序（也就是x的坐标位置）。如果x的值是一样的，加的放在减的前面，
 * 防止纸片大楼，也就是一个大楼的起点和终点的值是一样的。避免先减后加，导致map出错
 * <p>
 * 然后从小到大考察这些对象，就等于是沿着x轴的方向考察可能发生变化的点
 * <p>
 * 考察：
 * 准备一个TreeMap有序表
 * key: 高度
 * value: 该高度加入的次数【value设计成次数，每一次高度减少，代表一栋大楼的结束，应该移除这栋大楼，为了防止多个大楼等高，只能移除一次】
 * <p>
 * 在每个对象来的时候，先将该对象对应的高度加入map（如果是增加高度，就将对应的key加1，如果是减少，就将对应的高度减1.)
 * 增加的时候，如果不存在，value就是1，减少的时候，如果减成0，就将该key高度从map中移除
 * 【代表该高度考察完成了，后面不会有该高度。不要影响后面的高度取最大值】
 * 加完之后，get一下map，拿到最大高度【有序表拿最大值LogN】，看高度相对于之前的最大高度，是否有变化。
 *
 * @since 2022-03-05 08:39:25
 */
public class Code08_TheSkylineProblem {

    /**
     * 封装对象
     *
     * @since 2022-03-05 09:24:00
     */
    public static class Node {
        public int x;
        public boolean isAdd;
        public int h;

        public Node(int x, boolean isAdd, int h) {
            this.x = x;
            this.isAdd = isAdd;
            this.h = h;
        }
    }


    public static class NodeComparator implements Comparator<Node> {

        /**
         * 这里只按第一维度排序，LeetCode没有纸片大楼
         *
         * @since 2022-03-05 09:23:33
         */
        @Override
        public int compare(Node o1, Node o2) {
            return o1.x - o2.x;
        }
    }

    public static List<List<Integer>> getSkyline(int[][] buildings) {

        // 封装对象
        Node[] nodes = new Node[buildings.length * 2];
        for (int i = 0; i < buildings.length; i++) {
            nodes[i * 2] = new Node(buildings[i][0], true, buildings[i][2]);
            nodes[i * 2 + 1] = new Node(buildings[i][1], false, buildings[i][2]);
        }

        // sort
        Arrays.sort(nodes, new NodeComparator());

        // key  高度  value 次数
        // 根据次数找到每个位置的最大高度
        TreeMap<Integer, Integer> mapHeightTimes = new TreeMap<>();

        // key  位置   value 高度
        // 收集每个位置的对打高度，最后根据每个位置的最大高度生成轮廓线
        // 方便沿x位置从左往右
        TreeMap<Integer, Integer> mapXHeight = new TreeMap<>();


        for (Node node : nodes) {
            if (node.isAdd) {
                // 增加高度
                if (!mapHeightTimes.containsKey(node.h)) {
                    mapHeightTimes.put(node.h, 1);
                } else {
                    mapHeightTimes.put(node.h, mapHeightTimes.get(node.h) + 1);
                }
            } else {
                // 减少高度
                if (mapHeightTimes.get(node.h) == 1) {
                    mapHeightTimes.remove(node.h);
                } else {
                    mapHeightTimes.put(node.h, mapHeightTimes.get(node.h) - 1);
                }
            }
            if (mapHeightTimes.isEmpty()) {

                // 最后一个节点remove了
                mapXHeight.put(node.x, 0);
            } else {

                // 当前x位置的最大高度
                mapXHeight.put(node.x, mapHeightTimes.lastKey());
            }
        }

        // collect answer
        List<List<Integer>> ans = new ArrayList<>();


        for (Map.Entry<Integer, Integer> entry : mapXHeight.entrySet()) {
            int curX = entry.getKey();
            int curMaxHeight = entry.getValue();

            // 本题只需要记录轮廓线的左端点和高度
            if (ans.isEmpty() || ans.get(ans.size() - 1).get(1) != curMaxHeight) {
                ans.add(new ArrayList<>(Arrays.asList(curX, curMaxHeight)));
            }
        }
        return ans;
    }

}
