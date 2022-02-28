package org.study.system.class48;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * @author phil
 * @since 2022-0220 14:53:55
 */
public class Code23_699_FallingSquares {

    public static class SegmentTree {
        public int[] max;
        public int[] change;
        public boolean[] update;

        public SegmentTree(int size) {
            this.max = new int[size << 2];
            this.change = new int[size << 2];
            this.update = new boolean[size << 2];
        }

        public int getMax(int start, int end, int left, int right, int root) {
            if (start <= left && right <= end) {
                return max[root];
            }

            int mid = left + ((right - left) >> 1);
            pushDown(root, mid - left + 1, right - mid);
            int ans = 0;
            if (start <= mid) {
                ans = Math.max(ans, getMax(start, end, left, mid, root << 1));
            }
            if (end > mid) {
                ans = Math.max(ans, getMax(start, end, mid + 1, right, root << 1 | 1));
            }

            return ans;
        }

        public void pushDown(int root, int leftNum, int rightNum) {
            if (update[root]) {
                max[root << 1] = change[root];
                max[root << 1 | 1] = change[root];

                change[root << 1] = change[root];
                change[root << 1 | 1] = change[root];

                update[root << 1] = true;
                update[root << 1 | 1] = true;

                update[root] = false;
            }
        }

        public void pushUp(int root) {
            max[root] = Math.max(max[root << 1], max[root << 1 | 1]);
        }

        public void update(int start, int end, int val, int left, int right, int root) {
            if (start <= left && right <= end) {
                max[root] = val;
                change[root] = val;
                update[root] = true;
                return;
            }

            int mid = left + ((right - left) >> 1);

            pushDown(root, mid - left + 1, right - left);
            if (start <= mid) {
                update(start, end, val, left, mid, root << 1);
            }
            if (end > mid) {
                update(start, end, val, mid + 1, right, root << 1 | 1);
            }
            pushUp(root);
        }

        public HashMap<Integer, Integer> getIndexMap(int[][] matrix) {
            TreeSet<Integer> treeSet = new TreeSet<>();
            for (int[] ints : matrix) {
                treeSet.add(ints[0]);
                treeSet.add(ints[0] + ints[1] - 1);
            }
            HashMap<Integer, Integer> indexMap = new HashMap<>();
            int index = 0;
            for (Integer tree : treeSet) {
                indexMap.put(tree, ++index);
            }

            return indexMap;
        }

        public List<Integer> fallingSquares(int[][] positions) {
            HashMap<Integer, Integer> indexMap = getIndexMap(positions);
            int len = indexMap.size();
            SegmentTree segmentTree = new SegmentTree(len);
            int curMax = 0;
            List<Integer> ans = new ArrayList<>();

            for (int[] arr : positions) {
                int start = indexMap.get(arr[0]);
                int end = indexMap.get(arr[0] + arr[1] - 1);
                int height = segmentTree.getMax(start, end, 1, len, 1) + arr[1];
                curMax = Math.max(curMax, height);
                ans.add(curMax);
                segmentTree.update(start, end, height, 1, len, 1);
            }

            return ans;
        }


    }
}
