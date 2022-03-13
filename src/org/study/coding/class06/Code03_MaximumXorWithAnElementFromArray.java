package org.study.coding.class06;


/**
 * Leetcode题目：https://leetcode.com/problems/maximum-xor-with-an-element-from-array/
 * <p>
 * 给定一个非负整数组成的数组nums。另有一个查询数组queries，其中queries[i]=[xi, mi]
 * 第i个查询的答案是xi和任何nums数组中不超过mi的元素按位异或（XOR）得到的最大值
 * <p>
 * 换句话说，答案是max(nums[j] XOR xi)，其中所有j均满足nums[j]<= mi。如果nums中的所有元素都大于mi，最终答案就是-1
 * 返回一个整数数组answer作为查询的答案，其中answer.length==queries.length且answer[i]是第i个查询的答案
 * <p>
 * <p>
 * 在前一题的基础上。建立前缀树的时候，在节点Node上，添加一个数据线，记录通过该节点的最小值
 * 空树的时候，该数据线设置为系统最大
 * <p>
 * 树中添加的仍然是原数组的数据想
 * <p>
 * 当一个数需要匹配的时候，
 * 在[0...i] 上任意选择一个不超过 mi 的数，和自己异或，目的是让异或的结果尽量大
 * 去当前的树上找，从头结点开始，看看有没有比 mi 小的数，如果没有，就返回 -1
 * 有小于等于 mi 的，才有的玩
 * <p>
 * 还是从高位到低位
 * 0的时候希望遇到1
 * 1的时候希望遇到0
 * <p>
 * 前提是所选的方向的下一个节点的最小值满足 小于等于 mi
 * 否则就只能被迫选择另外一条路
 *
 * @since 2022-03-06 02:22:11
 */
public class Code03_MaximumXorWithAnElementFromArray {

    /**
     * 要查询多个结果。每个查询时的要求和限制，都在 queries[][] 中
     *
     * @since 2022-03-06 02:50:15
     */
    public static int[] maximizeXor(int[] nums, int[][] queries) {
        int N = nums.length;
        NumTrie trie = new NumTrie();

        // 先将所有元素都加入前缀树
        for (int num : nums) {
            trie.add(num);
        }

        // collect answer
        int M = queries.length;
        int[] ans = new int[M];
        for (int i = 0; i < M; i++) {
            ans[i] = trie.maxXorWithXBehindM(queries[i][0], queries[i][1]);
        }
        return ans;
    }

    public static class Node {
        public int min;
        public Node[] nexts;

        public Node() {
            min = Integer.MAX_VALUE;
            nexts = new Node[2];
        }
    }

    public static class NumTrie {
        public Node head = new Node();

        public void add(int num) {
            Node cur = head;
            head.min = Math.min(head.min, num);

            // 最高位，符号位，一定是 0
            for (int move = 30; move >= 0; move--) {
                int path = ((num >> move) & 1);
                cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
                cur = cur.nexts[path];

                // 维护最小值
                cur.min = Math.min(cur.min, num);
            }
        }

        // 这个结构中，已经收集了一票数字
        // 请返回哪个数字与X异或的结果最大，返回最大结果
        // 但是，只有<=m的数字，可以被考虑
        public int maxXorWithXBehindM(int x, int m) {
            if (head.min > m) {
                return -1;
            }
            // 一定存在某个数可以和x结合
            Node cur = head;
            int ans = 0;
            for (int move = 30; move >= 0; move--) {
                int path = (x >> move) & 1;
                // 期待遇到的东西
                int best = (path ^ 1);

                // 不满足条件，只能被迫去另外一条路。
                // 满足条件，就去自己最想去的路
                best ^= (cur.nexts[best] == null || cur.nexts[best].min > m) ? 1 : 0;

                // best变成了实际遇到的
                // (path ^ best) 当前位位异或完的结果.因为要的就是异或后的结果
                // path 非 0 即 1
                // best 非 0 即 1
                // 搞定答案的第 move 位
                ans |= (path ^ best) << move;


                cur = cur.nexts[best];
            }
            return ans;
        }
    }

}
