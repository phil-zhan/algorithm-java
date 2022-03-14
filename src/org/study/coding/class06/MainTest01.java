package org.study.coding.class06;

/**
 * @author phil
 * @since 2022-0312 23:24:08
 */
public class MainTest01 {
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 30;
        int maxValue = 50;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = Code01_MaxXOR.generateRandomArray(maxSize, maxValue);
            int comp = maxXorSubarray1(arr);
            int res = maxXorSubarray2(arr);
            if (res != comp) {
                succeed = false;
                Code01_MaxXOR.printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

    public static int maxXorSubarray1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int len = arr.length;
        int[] eor = new int[len];
        eor[0] = arr[0];
        for (int i = 1; i < len; i++) {
            eor[i] = eor[i - 1] ^ arr[i];
        }


        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j <= i; j++) {
                max = Math.max(max, j == 0 ? eor[i] : eor[i] ^ eor[j - 1]);
            }
        }

        return max;
    }

    public static int maxXorSubarray2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int ans = Integer.MIN_VALUE;
        int eor = 0;
        NumTrie numTrie = new NumTrie();
        numTrie.add(eor);
        for (int num : nums) {
            eor ^= num;
            ans = Math.max(ans, numTrie.maxXor(eor));
            numTrie.add(eor);
        }

        return ans;
    }

    public static class Node {
        public Node[] nextList;

        public Node() {
            this.nextList = new Node[2];
        }
    }

    public static class NumTrie {
        Node head = new Node();

        public void add(int num) {
            Node cur = head;
            for (int i = 31; i >= 0; i--) {
                int path = (num >> i) & 1;
                if (cur.nextList[path] == null) {
                    cur.nextList[path] = new Node();
                }
                cur = cur.nextList[path];
            }
        }

        public int maxXor(int num) {
            Node cur = head;
            int ans = 0;
            for (int i = 31; i >= 0; i--) {
                int path = (num >> i) & 1;
                int best = i == 31 ? path : path ^ 1;
                best = cur.nextList[best] != null ? best : best ^ 1;
                ans |= (path ^ best) << i;
                cur = cur.nextList[best];
            }
            return ans;
        }
    }


}
