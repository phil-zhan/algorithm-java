package org.study.coding.class20;

import java.util.Arrays;

/**
 * 3. 完美洗牌问题
 *    给定一个长度为偶数的数组arr，假设长度为N*2
 *    左部分：arr[L1...Ln] 右部分： arr[R1...Rn]
 *    请把arr调整成arr[L1,R1,L2,R2,L3,R3,...,Ln,Rn]
 *    要求时间复杂度O(N)，额外空间复杂度O(1)
 *
 * 先解决一个原型
 * 在一个数组中，L...R 范围上。假设左边部分长度为k，剩下的都是右边部分
 * 怎么在原地调整【不适应额外空间】。将其左边部分换到右边，右边部分换到左边
 * 如：[a,b,c,d,e,f]  k=4
 * 变换后 [e,f,a,b,c,d]
 * 解法是：
 * 三反转
 * 左边部分先逆序[d,c,b,a,e,f]
 * 右边部分再逆序[d,c,b,a,f,e]
 * 最后将整体再逆序[e,f,a,b,c,d]
 *
 *
 *
 *
 * @since 2022-03-22 09:13:43
 */
public class Code03_ShuffleProblem {

    /**
     * 左部分：arr[L1...Ln] 右部分： arr[R1...Rn]
     * 调整后：arr[R1,L1,R2,L2...Rn,Ln]
     * 考虑下标 i
     * 左边区在偶数位置。右半区奇数位置。
     * 下标循环怼
     * 下标不从0开始，从1开始。考察当前位置的数应该去哪，去到对应位置之后，会将之前原本在那个位置的数怼出来，再去考察新被怼出来的位置。
     * 直到回到其实位置停止
     *
     * 想的很美。事实很残酷。不行！！！
     * 可能还没怼完，就回到了起始点。导致剩下的位置不能完成调整debug
     *
     * 可以考虑，利用公式算出每个环的起始位置。然后循环跑完所有的环，也就达成目的了。
     *
     * 1）当长度 n是3的某次方-1的时候【 N = 3^k - 1 】
     * 出发点为 1,3,9,27...   以此是3的某次方。从1开始，数k个。就是所有的出发点    【是利用数论证明的。不用知道过程】
     * 2）当长度 n不是3的某次方-减1的时候。去找 离n最近的 小于等于n的 3的某次方-1  假设找到的是x=8
     *
     * 就先去高这x=8个数.【这8个数左半部分占据4个，右半部分占据4个】
     * 可以考利利用之前的原型。将左半部分除去4个之后剩余的部分，和右半部分的前四个组成的子区间 转动一下
     * 这样一来左半部分的前4个和右半部分的前4个。就整体去到左边了
     * 这8个数本身就是3的某次方减1【3的平方减1】
     * 那么肯定会形成两个环。环的起点分别是1和3
     *
     * 剩下不符合的部分，再去分解。考察是3的某次方减1，就能形成k个环，每个环的起点也能知道。以此循环玩下去。肯定能捣鼓玩。
     * 数组的长度是偶数。最少到长度为2的时候就会停【2=3^1-1】【也就是1个环】
     *
     *
     *
     * 数组的长度为len，调整前的位置是i，返回的是调整之后的位置
     * 下标不从0开始，从1开始
     *
     * @since 2022-03-22 09:28:43
     */
    public static int modifyIndex1(int i, int len) {
        // 计算 i 位置的数调整后该去哪个位置。
        // 这是应该分段函数。用下一个方法也一样的。【modifyIndex2】【下一个方法时观察出来的】


        if (i <= len / 2) {
            return 2 * i;
        } else {
            return 2 * (i - (len / 2)) - 1;
        }
    }

    /**
     * 数组的长度为len，调整前的位置是i，返回调整之后的位置
     * 标不从0开始，从1开始
     * @since 2022-03-22 09:28:54
     */
    public static int modifyIndex2(int i, int len) {
        return (2 * i) % (len + 1);
    }

    /**
     * 主函数
     * 数组必须不为空，且长度为偶数
     * @since 2022-03-22 09:29:05
     */
    public static void shuffle(int[] arr) {
        if (arr != null && arr.length != 0 && (arr.length & 1) == 0) {
            shuffle(arr, 0, arr.length - 1);
        }
    }

    /**
     * 在arr[L..R]上做完美洗牌的调整（arr[L..R]范围上一定要是偶数个数字）
     * @since 2022-03-22 10:26:20
     */
    public static void shuffle(int[] arr, int L, int R) {

        // 切成一块一块的解决，每一块的长度满足(3^k)-1
        while (R - L + 1 > 0) {
            int len = R - L + 1;
            int base = 3;
            int k = 1;
            // 计算小于等于len并且是离len最近的，满足(3^k)-1的数
            // 也就是找到最大的k，满足3^k <= len+1
            // base > (N+1)/3
            while (base <= (len + 1) / 3) {
                base *= 3;
                k++;
            }
            // 3^k -1
            // 当前要解决长度为base-1的块，一半就是再除2
            int half = (base - 1) / 2;
            // [L..R]的中点位置
            int mid = (L + R) / 2;
            // 要旋转的左部分为[L+half...mid], 右部分为arr[mid+1..mid+half]
            // 注意在这里，arr下标是从0开始的
            rotate(arr, L + half, mid, mid + half);
            // 旋转完成后，从L开始算起，长度为base-1的部分进行下标连续推
            cycles(arr, L, base - 1, k);
            // 解决了前base-1的部分，剩下的部分继续处理
            // L ->     [] [+1...R]
            L = L + base - 1;
        }
    }

    /**
     * 从start位置开始，往右len的长度这一段，做下标连续推
     * 出发位置依次为1,3,9...
     * @since 2022-03-22 10:21:34
     */
    public static void cycles(int[] arr, int start, int len, int k) {
        // 找到每一个出发位置trigger，一共k个
        // 每一个trigger都进行下标连续推
        // 出发位置是从1开始算的，而数组下标是从0开始算的。【记得减去1】
        for (int i = 0, trigger = 1; i < k; i++, trigger *= 3) {
            int preValue = arr[trigger + start - 1];
            int cur = modifyIndex2(trigger, len);

            // 每一次都是将前一个节点的值取出来，存在下一个节点。记得将最后的节点的值存入开始节点
            while (cur != trigger) {
                int tmp = arr[cur + start - 1];
                arr[cur + start - 1] = preValue;
                preValue = tmp;
                cur = modifyIndex2(cur, len);
            }
            arr[cur + start - 1] = preValue;
        }
    }

    /**
     * [L..M]为左部分，[M+1..R]为右部分，左右两部分互换
     *
     * @since 2022-03-22 09:26:22
     */
    public static void rotate(int[] arr, int L, int M, int R) {
        reverse(arr, L, M);
        reverse(arr, M + 1, R);
        reverse(arr, L, R);
    }

    /**
     * [L..R]做逆序调整
     *
     * @since 2022-03-22 09:26:30
     */
    public static void reverse(int[] arr, int L, int R) {
        while (L < R) {
            int tmp = arr[L];
            arr[L++] = arr[R];
            arr[R--] = tmp;
        }
    }

    public static void wiggleSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        // 假设这个排序是额外空间复杂度O(1)的，当然系统提供的排序并不是，你可以自己实现一个堆排序
        Arrays.sort(arr);
        if ((arr.length & 1) == 1) {
            shuffle(arr, 1, arr.length - 1);
        } else {
            shuffle(arr, 0, arr.length - 1);
            for (int i = 0; i < arr.length; i += 2) {
                int tmp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = tmp;
            }
        }
    }

    // for test
    public static boolean isValidWiggle(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if ((i & 1) == 1 && arr[i] < arr[i - 1]) {
                return false;
            }
            if ((i & 1) == 0 && arr[i] > arr[i - 1]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static int[] generateArray() {
        int len = (int) (Math.random() * 10) * 2;
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * 100);
        }
        return arr;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5000000; i++) {
            int[] arr = generateArray();
            wiggleSort(arr);
            if (!isValidWiggle(arr)) {
                System.out.println("ooops!");
                printArray(arr);
                break;
            }
        }
    }

}
