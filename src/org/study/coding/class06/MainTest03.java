package org.study.coding.class06;

/**
 * @author phil
 * @since 2022-0313 09:22:06
 */
public class MainTest03 {
    public int[] maximizeXor(int[] nums, int[][] queries) {
        int[] ans = new int[queries.length];
        NumTrie numTrie = new NumTrie();

        for (int num:nums) {
            numTrie.add(num);
        }

        for (int i = 0; i < queries.length; i++) {
            ans[i] = numTrie.maxXorWithXBehindM(queries[i][0],queries[i][1]);
        }

        return ans;
    }

    public class Node{
        public int min;
        public Node[] next;
        public Node(){
            this.min = Integer.MAX_VALUE;
            this.next = new Node[2];
        }
    }

    public class NumTrie{
        public Node head;
        public NumTrie(){
            this.head = new Node();
        }


        public void add(int num){
            Node cur = this.head;
            head.min = Math.min(head.min,num);

            for (int move = 30; move >= 0; move--) {
                int path = (num >> move) & 1;
                cur.next[path] = cur.next[path] == null ? new Node() : cur.next[path];
                cur = cur.next[path];
                cur.min = Math.min(cur.min,num);
            }
        }

        public int maxXorWithXBehindM(int num,int max){
            if (head.min > max) {
                return -1;
            }
            Node cur = this.head;
            int ans = 0;
            for (int move = 30; move >= 0; move--) {
                int path = (num >> move) & 1;
                int best = path ^ 1;
                best ^= (cur.next[best] == null || cur.next[best].min > max) ? 1 : 0;

                ans |= (path ^ best) << move;
                cur = cur.next[best];

            }

            return ans;
        }
    }
}
