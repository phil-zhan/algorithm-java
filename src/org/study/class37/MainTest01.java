package org.study.class37;

import org.study.class36.Code01_SizeBalancedTreeMap;

import java.util.HashSet;

/**
 * @author phil
 * @date 2021/10/13 14:31
 */
public class MainTest01 {

    public static class SizeBalanceNode {
        public long key;
        public long size;
        public long all;
        public SizeBalanceNode left;
        public SizeBalanceNode right;

        public SizeBalanceNode(long key) {
            this.key = key;
            this.size = 1;
            this.all = 1;
        }
    }


    public static class SizeBalanceTree {
        public SizeBalanceNode root;
        public HashSet<Long> set = new HashSet<>();

        public SizeBalanceNode leftRotate(SizeBalanceNode cur) {
            long same = cur.all - (cur.left != null?cur.left.all:0) - (cur.right != null?cur.right.all:0);

            SizeBalanceNode right = cur.right;
            cur.right = right.left;
            right.left = cur;

            // size
            right.size = cur.size;
            cur.size = (cur.left != null?cur.left.size:0) + (cur.right != null?cur.right.size:0) + 1;

            // all
            right.all = cur.all;
            cur.all = (cur.left != null?cur.left.all:0) + (cur.right != null?cur.right.all:0) + same;

            return right;
        }

        public SizeBalanceNode rightRotate(SizeBalanceNode cur){
            long same = cur.all - (cur.left != null?cur.left.all:0) - (cur.right != null?cur.right.all:0);
            SizeBalanceNode left = cur.left;
            cur.left = left.right;
            left.right = cur;

            // size
            left.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;

            // all
            left.all = cur.all;
            cur.all = (cur.left != null ? cur.left.all : 0) + (cur.right != null ? cur.right.all : 0) + same;

            return left;
        }


        public SizeBalanceNode maintain(SizeBalanceNode cur) {
            if (null == cur) {
                return null;
            }

            long leftSize = cur.left != null ? cur.left.size : 0;
            long leftLeftSize = cur.left != null && cur.left.left != null ? cur.left.left.size : 0;
            long leftRightSize = cur.left != null && cur.left.right != null ? cur.left.right.size : 0;

            long rightSize = cur.right != null ? cur.right.size : 0;
            long rightLeftSize = cur.right != null && cur.right.left != null ? cur.right.left.size : 0;
            long rightRightSize = cur.right != null && cur.right.right != null ? cur.right.right.size : 0;

            if (leftLeftSize > rightSize) {
                // LL
                cur = rightRotate(cur);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            } else if (leftRightSize > rightSize) {
                // LR
                cur.left = leftRotate(cur.left);
                cur = rightRotate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            } else if (rightLeftSize > leftSize) {
                // RL
                cur.right = rightRotate(cur.right);
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            } else if (rightRightSize > leftSize) {
                // RR
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur = maintain(cur);
            }

            return cur;
        }

        private SizeBalanceNode add(SizeBalanceNode cur, long key, boolean contains) {

            if (cur == null) {
                return new SizeBalanceNode(key);
            } else {

                // 不要忘记维护沿途附加数据项
                cur.all++;
                if (key == cur.key) {
                    return cur;
                } else { // 还在左滑或者右滑
                    if (!contains) {
                        cur.size++;
                    }
                    if (key < cur.key) {
                        cur.left = add(cur.left, key, contains);
                    } else {
                        cur.right = add(cur.right, key, contains);
                    }
                    return maintain(cur);
                }
            }
        }


        public void add(long sum) {
            boolean contains = set.contains(sum);
            root = add(root, sum, contains);
            set.add(sum);
        }

        public long lessKeySize(long key) {
            SizeBalanceNode cur = root;
            long ans = 0;
            while (cur != null) {
                if (key == cur.key) {

                    // 之前的答案 加上当前节点的左树
                    return ans + (cur.left != null ? cur.left.all : 0);
                } else if (key < cur.key) {
                    // 左滑
                    cur = cur.left;
                } else {

                    // 右滑
                    ans += cur.all - (cur.right != null ? cur.right.all : 0);
                    cur = cur.right;
                }
            }
            return ans;
        }
    }


    public static int countRangeSum2(int[] nums, int lower, int upper) {

        // 黑盒，加入数字（前缀和），不去重，可以接受重复数字
        // < num , 有几个数？

        SizeBalanceTree treeSet = new SizeBalanceTree();
        long sum = 0;
        int ans = 0;

        // 一个数都没有的时候，就已经有一个前缀和累加和为0
        // sum 一路上的累加和
        treeSet.add(0);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            // 原来要求 [lower ,upper]
            // 转换目标 [sum - upper, sum - lower]
            // 要求 [10, 20] 有多少个
            // 先求出 < 10 的个数
            // 在求出 < 21 的个数【也就把20包含进去】
            // 然后相减就能得到想要的结果

            long a = treeSet.lessKeySize(sum - lower + 1);
            long b = treeSet.lessKeySize(sum - upper);
            ans += a - b;


            treeSet.add(sum);
        }
        return ans;
    }

}
