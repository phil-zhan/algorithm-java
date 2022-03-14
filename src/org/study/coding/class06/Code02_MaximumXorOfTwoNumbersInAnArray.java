package org.study.coding.class06;



/**
 * 本题测试链接 : https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/
 *
 * 数组中所有数都异或起来的结果，叫做异或和。给定一个数组arr，想知道arr中哪两个数的异或结果最大，返回最大的异或结果
 * <p>
 * 前一题是任何一个子数组，所有元素异或，最后求最大的异或结果
 * 本体是求任意两个元素的异或结果。
 * 在前一题的基础上，
 * 从左往右
 * 来到i位置的时候，在 i 的前面，任意选一个，看看谁和自己的异或结果最大，最后去和最局的最大 PK
 * <p>
 * 为了实现在 i 位置之前的数中，任意选一个的加速效果。采用前缀树。前一题是将从 0 开始，到 i 位置的所有元素的异或结果加入前缀树
 * 本题直接将 i 位置的元素加入前缀树即可
 * <p>
 * <p>
 * 优化解：
 * 找到一种结构，能够实现，当某个元素等于 a 时，能够迅速的知道，他与前面哪个元素结合，结合之后的异或和最大【也就是当前位置的最大异或和】
 * 定制前缀树的实现。优化map
 * <p>
 * 将所有的元素二进制，都按照前缀树组织
 * 当目标 a 出现的时候。
 * 从高位往低位遍历。
 * 如果 a 的对应位是 1 ，就走前缀树 0 的分支，【没有0的分支，再走1的分支】
 * 如果 a 的对应位是 0 ，就走前缀树 1 的分支，【没有1的分支，再走0的分支】
 * 这样就能找到哪个前缀异或和 与 a 集合能实现最大
 * <p>
 * 因为所给的是int类型的数组。所以进入前缀树的数字，都打散成 32 位的，按照二进制去组织前缀树
 *
 * @since 2022-03-06 02:07:40
 */
public class Code02_MaximumXorOfTwoNumbersInAnArray {

    public static class Node {
        public Node[] nexts = new Node[2];
    }

    public static class NumTrie {
        public Node head = new Node();

        public void add(int newNum) {
            Node cur = head;
            for (int move = 31; move >= 0; move--) {
                int path = ((newNum >> move) & 1);
                cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
                cur = cur.nexts[path];
            }
        }

        public int maxXor(int sum) {
            Node cur = head;
            int res = 0;
            for (int move = 31; move >= 0; move--) {
                int path = (sum >> move) & 1;
                int best = move == 31 ? path : (path ^ 1);
                best = cur.nexts[best] != null ? best : (best ^ 1);
                res |= (path ^ best) << move;
                cur = cur.nexts[best];
            }
            return res;
        }
    }

    public static int findMaximumXOR(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        NumTrie numTrie = new NumTrie();
        numTrie.add(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            max = Math.max(max, numTrie.maxXor(nums[i]));
            numTrie.add(nums[i]);
        }
        return max;
    }

}
