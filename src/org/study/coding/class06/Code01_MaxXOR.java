package org.study.coding.class06;


/**
 * 数组中所有数都异或起来的结果，叫做异或和。给定一个数组arr，返回arr的最大子数组异或和
 * <p>
 * 异或：就是无进位相加
 * 异或和：就是所有数都异或一遍【从头到尾】
 * <p>
 * 思路：
 * 考虑以 i 位置结尾的子数组，哪一个的异或和是最大的
 * [0...5] 的前缀异或和等于 a
 * [0...2] 的前缀异或和等于 b
 * 那么 a^b 就是[3...5] 的异或和
 * 相当于范围减法
 * <p>
 * 暴力解：
 * 考虑以 i 位置结尾的子数组,所有都去试一遍，抓一个最大的
 * 将每个前缀异或和都放在一个map里面，用来加速
 * O(N^2)
 * <p>
 * 优化解：
 * 找到一种结构，能够实现，当某个前缀异或和等于 a 时，能够迅速的知道，他与哪个前缀异或和结合，结合之后的异或和最大【也就是当前位置的最大异或和】
 * 定制前缀树的实现。优化map
 * <p>
 * 将所有的前缀异或和的二进制，都按照前缀树组织
 * 当目标 a 出现的时候。
 * 从高位往低位遍历。
 * 如果 a 的对应位是 1 ，就走前缀树 0 的分支，【没有0的分支，再走1的分支】
 * 如果 a 的对应位是 0 ，就走前缀树 1 的分支，【没有1的分支，再走0的分支】
 * 这样就能找到哪个前缀异或和 与 a 集合能实现最大
 * <p>
 * 因为所给的是int类型的数组。所以进入前缀树的数字，都打散成 32 位的，按照二进制去组织前缀树
 *
 * @since 2022-03-06 09:49:18
 */
public class Code01_MaxXOR {

    // O(N^2)
    public static int maxXorSubarray1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 准备一个前缀异或和数组eor
        // eor[i] = arr[0...i]的异或结果
        int[] eor = new int[arr.length];
        eor[0] = arr[0];
        // 生成eor数组，eor[i]代表arr[0..i]的异或和
        for (int i = 1; i < arr.length; i++) {
            eor[i] = eor[i - 1] ^ arr[i];
        }
        int max = Integer.MIN_VALUE;
        for (int j = 0; j < arr.length; j++) {
            for (int i = 0; i <= j; i++) { // 依次尝试arr[0..j]、arr[1..j]..arr[i..j]..arr[j..j]
                max = Math.max(max, i == 0 ? eor[j] : eor[j] ^ eor[i - 1]);
            }
        }
        return max;
    }

    // 前缀树的Node结构
    // nexts[0] -> 0方向的路
    // nexts[1] -> 1方向的路
    // nexts[0] == null 0方向上没路！
    // nexts[0] != null 0方向有路，可以跳下一个节点
    // nexts[1] == null 1方向上没路！
    // nexts[1] != null 1方向有路，可以跳下一个节点
    public static class Node {

        // 两个方向 0 和 1
        public Node[] nexts = new Node[2];
    }

    // 基于本题，定制前缀树的实现
    public static class NumTrie {
        // 头节点
        public Node head = new Node();

        public void add(int newNum) {
            Node cur = head;
            for (int move = 31; move >= 0; move--) {

                // 从高位到低位
                // 提取出第 move 位置的状态
                int path = ((newNum >> move) & 1);

                cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
                cur = cur.nexts[path];
            }
        }

        // 该结构之前收集了一票数字，并且建好了前缀树
        // num和 谁 ^ 最大的结果（把结果返回）

        // 整数的弟31号位置是符号位，
        // 第31号位是 0，表示正数 ，希望遇到0，保证该数是正数
        // 第31号位是 1表示负数 ，希望遇到1，可以变成非负数
        // 其他的位上，如果是1，希望遇到 0，如果是0，希望遇到1
        // 就算是负数，也希望优先高位变1，因为负数也是高位1比低位1更大。
        // 负数的实际值求的是补码
        // -5 的高位1 一定比 -100 的高位1 更多。或者更高
        public int maxXor(int num) {
            Node cur = head;
            int ans = 0;
            for (int move = 31; move >= 0; move--) {
                // 取出num中第move位的状态，path只有两种值0就1，整数
                int path = (num >> move) & 1;
                // 期待遇到的东西
                int best = move == 31 ? path : (path ^ 1);

                // 有路就走，没路被迫走另外一条路
                // 实际遇到的东西
                best = cur.nexts[best] != null ? best : (best ^ 1);
                // (path ^ best) 当前位位异或完的结果
                // path 非 0 即 1
                // best 非 0 即 1
                // 搞定答案的第 move 位
                ans |= (path ^ best) << move;

                // 准备去到下一个位
                cur = cur.nexts[best];
            }
            return ans;
        }
    }


    // O(N)
    public static int maxXorSubarray2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        // 0~i整体异或和
        int xor = 0;
        NumTrie numTrie = new NumTrie();

        // 没有前缀异或和
        numTrie.add(0);
        for (int num : arr) {

            // 0 ~ i
            xor ^= num;

            // [0...i]范围上的最大异或和，抓一个最大的
            // xor 和前面哪个异或之后的结果最大，
            // 再和当前的 max 去PK一下

            // maxXor() 该方法最多跑32次 O(1).整体 O(N)
            max = Math.max(max, numTrie.maxXor(xor));

            // 当前的整体异或结果也加进去
            numTrie.add(xor);
        }
        return max;
    }


    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 30;
        int maxValue = 50;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int comp = maxXorSubarray1(arr);
            int res = maxXorSubarray2(arr);
            if (res != comp) {
                succeed = false;
                printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
