package org.study.coding.class36;

/**
 * @author Administrator
 */
public class MainTest05 {

    public static class Query {
        private final SegmentTree segmentTree;
        private final int[] sum;
        private final int[] square;
        private final int length;

        public Query(int[] nums) {
            this.segmentTree = new SegmentTree(nums);
            this.sum = new int[nums.length + 1];
            this.square = new int[nums.length + 1];
            this.length = nums.length;
            for (int i = 0; i < nums.length; i++) {
                sum[i + 1] = sum[i] + nums[i];
                square[i + 1] = square[i] + nums[i] * nums[i];
            }
        }

        public int querySum(int left, int right) {
            return sum[right] - sum[left - 1];
        }

        public int querySum1(int left, int right) {
            return segmentTree.querySum(1, 1, length, left, right);
        }

        public int queryAim(int left, int right) {
            // (s-a)^2 + (s-b)^2 + (s-c)^2 + (s-d)^2
            // s^2 + a^2 -2as  + s^2 + b^2 -2bs + s^2 + c^2 -2cs  + s^2 + d^2 -2ds
            // 4s^2 + a^2 + b^2 + c^2 + d^2 - 2s(a+b+c+d)
            // 4s^2 + a^2 + b^2 + c^2 + d^2 - 2s^2
            // 2s^2 + a^2 + b^2 + c^2 + d^2
            // (k-2)s^2 + a^2 + b^2 + c^2 + d^2
            return (right - left - 1) * (sum[right] - sum[left - 1]) * (sum[right] - sum[left - 1]) + (square[right] - square[left - 1]);
        }

        public int queryMax(int left, int right) {
            return segmentTree.queryMax(1, 1, length, left, right);
        }

        public static class SegmentTree {
            private final int[] nums;

            private final int[] sum;
            private final int[] lazy;

            private final boolean[] update;
            private final int[] change;

            private final int[] max;


            public SegmentTree(int[] origin) {
                // init
                int length = origin.length + 1;
                this.nums = new int[length];

                this.sum = new int[length << 2];
                this.lazy = new int[length << 2];

                this.update = new boolean[length << 2];
                this.change = new int[length << 2];

                this.max = new int[length << 2];


                System.arraycopy(origin, 0, this.nums, 1, origin.length);

                build(1, 1, origin.length);
            }

            private void pushUp(int root) {

                max[root] = Math.max(max[root << 1], max[root << 1 | 1]);
                sum[root] = sum[root << 1] + sum[root << 1 | 1];
            }

            // 先add 再 update 【会更新update标志。同时清除lazy。下发的时候不影响】
            // 先update 再 add 【在下发的时候，先检查update。同时将子节点的lazy置空，再检查父节点的lazy。下发的时候不影响】
            private void pushDown(int root, int leftNum, int rightNum) {
                if (update[root]) {
                    update[root << 1] = true;
                    update[root << 1 | 1] = true;

                    change[root << 1] = change[root];
                    change[root << 1 | 1] = change[root];

                    lazy[root << 1] = 0;
                    lazy[root << 1 | 1] = 0;

                    sum[root << 1] = change[root] * leftNum;
                    sum[root << 1 | 1] = change[root] * rightNum;

                    max[root << 1] = change[root];
                    max[root << 1 | 1] = change[root];


                    update[root] = false;
                }

                if (lazy[root] != 0) {
                    lazy[root << 1] += lazy[root];
                    lazy[root << 1 | 1] += lazy[root];

                    sum[root << 1] = lazy[root] * leftNum;
                    sum[root << 1 | 1] = lazy[root] * rightNum;

                    max[root << 1] += lazy[root];
                    max[root << 1 | 1] += lazy[root];

                    lazy[root] = 0;
                }
            }

            private void build(int root, int left, int right) {

                if (left == right) {
                    sum[root] = nums[left];
                    max[root] = nums[left];
                    return;
                }

                int mid = left + ((right - left) >> 1);
                build(root << 1, left, mid);
                build(root << 1 | 1, mid + 1, right);

                pushUp(root);
            }

            public void update(int root, int left, int right, int l, int r, int c) {
                if (l <= left && right <= r) {
                    update[root] = true;
                    change[root] = c;
                    lazy[root] = 0;
                    sum[root] = c * (right - left + 1);
                    max[root] = c;

                    return;
                }

                int mid = left + ((right - left) >> 1);
                pushDown(root, mid - left + 1, right - mid);
                if (l <= mid) {
                    update(root << 1, left, mid, l, r, c);
                }

                if (r > mid) {
                    update(root << 1 | 1, mid + 1, right, l, r, c);
                }

                pushUp(root);
            }

            public void add(int root, int left, int right, int l, int r, int c) {
                if (l <= left && right <= r) {
                    lazy[root] += c;
                    sum[root] += c * (right - left + 1);
                    max[root] += c;
                    return;
                }

                int mid = left + ((right - left) >> 1);
                pushDown(root, mid - left + 1, right - mid);

                if (l <= mid) {
                    add(root << 1, left, mid, l, r, c);
                }
                if (r > mid) {
                    add(root << 1 | 1, mid + 1, right, l, r, c);
                }

                pushUp(root);
            }

            public int querySum(int root, int left, int right, int l, int r) {
                if (l <= left && right <= r) {
                    return sum[root];
                }

                int mid = left + ((right - left) >> 1);
                pushDown(root, mid - left + 1, right - mid);
                int ans = 0;
                if (l <= mid) {
                    ans += querySum(root << 1, left, mid, l, r);
                }
                if (r > mid) {
                    ans += querySum(root << 1 | 1, mid + 1, right, l, r);
                }

                return ans;
            }

            public int queryMax(int root, int left, int right, int l, int r) {
                if (l <= left && right <= r) {
                    return max[root];
                }
                int mid = left + ((right - left) >> 1);
                pushDown(root, mid - left + 1, right - mid);
                int leftMax = Integer.MIN_VALUE;
                int rightMax = Integer.MIN_VALUE;
                if (l <= mid) {
                    leftMax = queryMax(root << 1, left, mid, l, r);
                }

                if (r > mid) {
                    rightMax = queryMax(root << 1 | 1, mid + 1, right, l, r);
                }

                return Math.max(leftMax, rightMax);
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 3};
        Query q = new Query(arr);
        System.out.println(q.querySum(1, 3));
        System.out.println(q.querySum1(1, 3));
        System.out.println(q.queryAim(2, 4));
        System.out.println(q.queryMax(1, 4));
    }
}
