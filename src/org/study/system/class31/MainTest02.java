package org.study.system.class31;

import java.util.*;

/**
 * @author phil
 * @date 2021/10/5 9:26
 */
public class MainTest02 {

    public static class SegmentTree {
        public int length;
        public int[] max;
        public boolean[] update;
        public int[] change;

        public SegmentTree(int size) {
            this.length = size + 1;
            this.max = new int[length << 2];
            this.change = new int[length << 2];
            this.update = new boolean[length << 2];
        }


        public void pushUp(int root) {
            this.max[root] = Math.max(max[root << 1], max[root << 1 | 1]);
        }

        public void pushDown(int root, int leftNum, int rightNum) {
            if (update[root]) {
                update[root << 1] = true;
                update[root << 1 | 1] = true;

                change[root << 1] = change[root];
                change[root << 1 | 1] = change[root];

                max[root << 1] = change[root];
                max[root << 1 | 1] = change[root];

                update[root] = false;
            }
        }


        public void update(int L, int R, int C, int left, int right, int root) {
            if (L <= left && right <= R) {
                max[root] = C;
                update[root] = true;
                change[root] = C;
                return;
            }
            int mid = (left + right) >> 1;
            pushDown(root, mid - left + 1, right - mid);
            if (L <= left) {
                update(L, R, C, left, mid, root << 1);
            }
            if (R > mid) {
                update(L, R, C, mid + 1, right, root << 1 | 1);
            }
            pushUp(root);
        }


        public int query(int L, int R, int left, int right, int root) {
            if (L <= left && right <= R) {
                return max[root];
            }

            int mid = (left + right) >> 1;

            pushDown(root, mid - left + 1, right - mid);

            int answer = 0;

            if (L <= mid) {
                answer = Math.max(answer, query(L, R, left, mid, root << 1));
            }

            if (R > mid) {
                answer = Math.max(answer, query(L, R, mid + 1, right, root << 1 | 1));
            }
            return answer;
        }


    }


    public HashMap<Integer, Integer> index(int[][] positions) {
        TreeSet<Integer> pos = new TreeSet<>();
        for (int[] arr : positions) {
            pos.add(arr[0]);
            pos.add(arr[0] + arr[1] - 1);
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (Integer index : pos) {
            map.put(index, ++count);
        }
        return map;
    }

    public List<Integer> fallingSquares(int[][] positions) {
        HashMap<Integer, Integer> map = index(positions);
        int N = map.size();
        SegmentTree segmentTree = new SegmentTree(N);
        int max = 0;
        List<Integer> res = new ArrayList<>();
        // 每落一个正方形，收集一下，所有东西组成的图像，最高高度是什么
        for (int[] arr : positions) {
            int L = map.get(arr[0]);
            // 防止贴边。将范围处理为 左闭右开的
            int R = map.get(arr[0] + arr[1] - 1);

            // 这里要的不是累加和，要的是最大值。所以将线段树做改动。将累加和数组改为范围上的最大值数组
            int height = segmentTree.query(L, R, 1, N, 1) + arr[1];
            max = Math.max(max, height);
            res.add(max);
            segmentTree.update(L, R, height, 1, N, 1);
        }
        return res;
    }
}
