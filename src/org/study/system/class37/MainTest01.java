package org.study.system.class37;

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

                // ???????????????????????????????????????
                cur.all++;
                if (key == cur.key) {
                    return cur;
                } else { // ????????????????????????
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

                    // ??????????????? ???????????????????????????
                    return ans + (cur.left != null ? cur.left.all : 0);
                } else if (key < cur.key) {
                    // ??????
                    cur = cur.left;
                } else {

                    // ??????
                    ans += cur.all - (cur.right != null ? cur.right.all : 0);
                    cur = cur.right;
                }
            }
            return ans;
        }
    }


    public static int countRangeSum2(int[] nums, int lower, int upper) {

        // ???????????????????????????????????????????????????????????????????????????
        // < num , ???????????????

        SizeBalanceTree treeSet = new SizeBalanceTree();
        long sum = 0;
        int ans = 0;

        // ?????????????????????????????????????????????????????????????????????0
        // sum ?????????????????????
        treeSet.add(0);
        for (int num : nums) {
            sum += num;
            // ???????????? [lower ,upper]
            // ???????????? [sum - upper, sum - lower]
            // ?????? [10, 20] ????????????
            // ????????? < 10 ?????????
            // ????????? < 21 ?????????????????????20???????????????
            // ???????????????????????????????????????

            long a = treeSet.lessKeySize(sum - lower + 1);
            long b = treeSet.lessKeySize(sum - upper);
            ans += a - b;


            treeSet.add(sum);
        }
        return ans;
    }



    // for test
    public static void printArray(int[] arr) {
        for (int j : arr) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    // for test
    public static int[] generateArray(int len, int varible) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * varible);
        }
        return arr;
    }

    public static void main(String[] args) {
        int len = 200;
        int variable = 50;
        for (int i = 0; i < 10000; i++) {
            int[] test = generateArray(len, variable);
            int lower = (int) (Math.random() * variable) - (int) (Math.random() * variable);
            int upper = lower + (int) (Math.random() * variable);
            int ans1 = Code01_CountofRangeSum.countRangeSum1(test, lower, upper);
            int ans2 = countRangeSum2(test, lower, upper);
            if (ans1 != ans2) {
                printArray(test);
                System.out.println(lower);
                System.out.println(upper);
                System.out.println(ans1);
                System.out.println(ans2);
            }
        }

    }

}
