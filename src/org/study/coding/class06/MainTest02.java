package org.study.coding.class06;

/**
 * @author phil
 * @since 2022-0313 08:57:52
 */
public class MainTest02 {

    public int findMaximumXOR(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int ans = Integer.MIN_VALUE;
        NumTrie numTrie = new NumTrie();
        numTrie.add(nums[0]);
        for (int index = 1; index < nums.length; index++) {
            ans = Math.max(ans, numTrie.maxXor(nums[index]));
            numTrie.add(nums[index]);
        }

        return ans;
    }

    public class Node {
        public Node[] next;
        public Node() {
            this.next = new Node[2];
        }
    }

    public class NumTrie {
        public Node head;

        public NumTrie() {
            this.head = new Node();
        }

        public void add(int num) {
            Node cur = this.head;
            for (int move = 31; move >= 0; move--) {
                int path = (num >> move) & 1;
                cur.next[path] = cur.next[path] == null ? new Node() : cur.next[path];
                cur = cur.next[path];
            }
        }

        public int maxXor(int num) {
            Node cur = this.head;
            int ans = 0;
            for (int move = 31; move >= 0; move--) {
                int path = (num >> move) & 1;
                int best = move == 31 ? path : path ^ 1;

                best = cur.next[best] != null ? best : best ^ 1;
                ans |= ((path ^ best) << move);
                cur = cur.next[best];
            }

            return ans;
        }
    }
}
