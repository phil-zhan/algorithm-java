package org.study.coding.class05;

import java.util.Stack;

/**
 * 搜索二叉树
 * 无重复，恢复出来的树肯定是唯一的
 * 有重复，不一定对应唯一的树
 *
 *
 * 题目：根据先序遍历的结果，重新建立二叉树。返回二叉树的头节点
 * 考虑[L...R]范围上，是某个二叉树的先序结果，返回其建成树后的头结点
 *
 *
 * 先序遍历，又是搜索二叉树。
 * L位置肯定是头结点
 * 从L开始，只要比L位置小的数，都肯定属于左树，
 * 其他剩下的都是比L位置大的。肯定属于右树
 * 递归搞定就完事了
 *
 *
 * 注意边界：
 * 在[L...R]范围上，没有比L小的【全部都比L位置的数大】，也就是没有左树，要返回null。
 * 右树也是一样，在[L...R]范围上。全部都是比L位置的数小，也就是没有右树，要返回null。
 *
 * @since 2022-03-05 03:54:24
 */

// 本题测试链接 : https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/
public class Code01_ConstructBinarySearchTreeFromPreorderTraversal {

    // 不用提交这个类
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 提交下面的方法
    public static TreeNode bstFromPreorder1(int[] pre) {
        if (pre == null || pre.length == 0) {
            return null;
        }
        return process1(pre, 0, pre.length - 1);
    }

    /**
     * [L...R]范围上是某个二叉树的先序结果，返回其建成树后的头结点
     * O(N^2)
     *
     * @since 2022-03-05 03:57:50
     */
    public static TreeNode process1(int[] pre, int L, int R) {

        // 该范围不成立。也就是边界
        if (L > R) {
            return null;
        }

        // 找到第一个大于L位置的数的位置
        int firstBig = L + 1;
        for (; firstBig <= R; firstBig++) {
            if (pre[firstBig] > pre[L]) {
                break;
            }
        }

        // head
        TreeNode head = new TreeNode(pre[L]);

        // 没有大于L位置的数。就会出现 L>R 的情况，
        head.left = process1(pre, L + 1, firstBig - 1);

        // 没有小于 L 位置的数【firstBig就会去到 R+1 的位置】
        head.right = process1(pre, firstBig, R);


        return head;
    }



    /**
     * 已经是时间复杂度最优的方法了，但是常数项还能优化
     * 每次都要遍历找到第一次大于当前L位置的数在哪。时间复杂度太高
     * 可以考虑单调栈【最值找窗口】
     * 要找到大于 当前数，右边离自己最近的在哪
     *
     * O(N)
     *
     * @since 2022-03-05 04:17:38
     */
    public static TreeNode bstFromPreorder2(int[] pre) {

        // 生成一个辅助记录nearBig
        // nearBig[i] = j  表示在原始数组arr中，右边离 i 最近的且大于 arr[i] 的位置在 j位置
        // 没有离 i 最大的， nearBig[i] = -1
        // 整个过程只需要遍历一次， O(N)
        if (pre == null || pre.length == 0) {
            return null;
        }
        int N = pre.length;
        int[] nearBig = new int[N];
        for (int i = 0; i < N; i++) {
            nearBig[i] = -1;
        }
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < N; i++) {
            while (!stack.isEmpty() && pre[stack.peek()] < pre[i]) {
                nearBig[stack.pop()] = i;
            }
            stack.push(i);
        }
        return process2(pre, 0, N - 1, nearBig);
    }

    public static TreeNode process2(int[] pre, int L, int R, int[] nearBig) {
        if (L > R) {
            return null;
        }

        // 无效的时候，直接认为是在 R+1 位置。好让下游节点失效
        int firstBig = (nearBig[L] == -1 || nearBig[L] > R) ? R + 1 : nearBig[L];
        TreeNode head = new TreeNode(pre[L]);
        head.left = process2(pre, L + 1, firstBig - 1, nearBig);
        head.right = process2(pre, firstBig, R, nearBig);
        return head;
    }


    /**
     * 最优解
     * 手写数组代替单调栈
     * @since 2022-03-05 04:24:11
     */
    public static TreeNode bstFromPreorder3(int[] pre) {
        if (pre == null || pre.length == 0) {
            return null;
        }
        int N = pre.length;
        int[] nearBig = new int[N];
        for (int i = 0; i < N; i++) {
            nearBig[i] = -1;
        }
        int[] stack = new int[N];
        int size = 0;
        for (int i = 0; i < N; i++) {
            while (size != 0 && pre[stack[size - 1]] < pre[i]) {
                nearBig[stack[--size]] = i;
            }
            stack[size++] = i;
        }
        return process3(pre, 0, N - 1, nearBig);
    }

    public static TreeNode process3(int[] pre, int L, int R, int[] nearBig) {
        if (L > R) {
            return null;
        }
        int firstBig = (nearBig[L] == -1 || nearBig[L] > R) ? R + 1 : nearBig[L];
        TreeNode head = new TreeNode(pre[L]);
        head.left = process3(pre, L + 1, firstBig - 1, nearBig);
        head.right = process3(pre, firstBig, R, nearBig);
        return head;
    }

}
