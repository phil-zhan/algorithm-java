package org.study.coding.class22;

import java.util.HashSet;
import java.util.Stack;


/**
 * 4. 一个不含有负数的数组可以代表一圈环形山，每个位置的值代表山的高度
 * 比如， {3,1,2,4,5}、{4,5,3,1,2}或{1,2,4,5,3}都代表同样结构的环形山
 * 山峰A和山峰B能够相互看见的条件为:
 * 1) 如果A和B是同一座山，认为不能相互看见
 * 2) 如果A和B是不同的山，并且在环中相邻，认为可以相互看见
 * 3) 如果A和B是不同的山，并且在环中不相邻，假设两座山高度的最小值为min
 * a) 如果A通过顺时针方向到B的途中没有高度比min大的山峰，认为A和B可以相互看见
 * b) 如果A通过逆时针方向到B的途中没有高度比min大的山峰，认为A和B可以相互看见
 * 两个方向只要有一个能看见，就算A和B可以相互看见
 * 给定一个不含有负数且没有重复值的数组 arr，请返回有多少对山峰能够相互看见
 * 进阶，给定一个不含有负数但可能含有重复值的数组arr，返回有多少对山峰能够相互看见
 * <p>
 * 题意：
 * 一个数组，组成的是环形山
 * 任意两点间【a和b之间】。都有两条路可以到达。【顺时针和逆时针】
 * 不管是顺时针还是逆时针，只要在去的路上，存在比这两个点的最小的一个高的山峰，这两个点都不可见【也就是中间的数需要同时比这两点小，才能算可见】
 * 任意两个相邻的山峰中间都是可见的
 * 返回有多少对可见山峰对
 * <p>
 * 解法：
 * （1）所给数组不含重复值
 * 时间复杂度 O(1) 就能出结果。有公式。
 * 遍历数组。每个数从尝试去把自己当较小的一个数，去看看能找到几对和自己配对的【顺时针逆时针都要试】
 * 顺时针时，肯定存在一个位置 p。使得这个p是刚刚大于当前位置的x 。这个p最大也就是和数组最大值【或次最大值】相等.【也就是x和p是第一对】【小找大】
 * 逆时针时，肯定存在一个位置 q。使得这个q是刚刚大于当前位置的x 。这个q最大也就是和数组次最大值【或最大值】相等。【也就是x和q是第二对】【小找大】
 * <p>
 * 数组中，出去最大值和次最大值。属于的 n-2 个数。每个数都能在顺逆时针各找到一对。再加上次max到max的这一对。就是总数
 * (n-2)*2 + 1
 * <p>
 * (2)所给的数组有重复值
 * 现在环形山脉中找到最大值。可能有多个最大值，随意选一个
 * 准备一个栈。该栈从栈底到栈顶严格从大到小【相等也不行】
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 将最大值入栈。【同时记录入栈值的当前个数】
 * 沿逆时针的方向，
 * 【新来的数小于栈顶元素】如果新来的数没有改变栈的严格由大到小，就直接进栈。
 * 【新来的数大于栈顶元素】如果新来的数会改变栈的严格由大到小。此时就需要从栈中弹出数
 * 针对被弹出的数，就找到了两对。【一对是让它弹出来的数与其组成的。另外一对是它压着的数与其组成的】
 * 【新来的数等于栈顶元素】，就改变栈顶原素的个数。让其加1
 * <p>
 * 一直往外弹出
 * 直到新来的数不能改变栈的严格从大到小。或者栈为空。
 * 弹到不能再弹之后，就将新来的数入栈。继续去到下一个位置
 * <p>
 * 如果新来的数，破坏严格从大到小时，栈顶元素的个数是多少个【1个或多个】，就能一次性形成多少对
 * 【栈顶元素对外（新来的和自己压着的 形成2*k对 ）和对内（当前值的元素个数之间形成的 形成C(k,2)对）】
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 遍历完成后。如果栈里面还剩下元素，该怎么清理栈？？
 * <p>
 * 当前元素不是栈的倒数第一和倒数第二的时候
 * 对外是 2*k 个元素对【k是当前这个数的个数】 【顺时针和逆时针】【顺时针时当前环形数组的最大值。逆时针是当前元素压着的值】【这里的”外“，指的是除当前值之外的其他元素】
 * 对内是 C(k,2) 【C是排列，K是当前这个数的个数】【这里的”内“，指的是当前值的k个元素之间形成的元素对】
 * <p>
 * 栈的倒数第二条的时候：下面就只压着一个数【就是当前环形数组的最大值】
 * 如果栈底的最大值的个数是1个，那么对外就只是 k 个元素对【k是当前这个数的个数】
 * 如果栈底的最大值的个数是不止1个，那么对外就只是 2*k 个元素对【k是当前这个数的个数】
 * 对内是 C(k,2) 【C是排列，K是当前这个数的个数】【这里的”内“，指的是当前值的k个元素之间形成的元素对】
 * <p>
 * 前面这个清理栈的过程，都只是考虑最大值只有一个的情况。如果栈底的最大值有多个的情况。不管是对外还是对内，都要乘以最大值的个数
 * <p>
 * 栈的倒数第一条的时候
 * 对外的元素对是0对
 * 对内是 C(k,2) 【C是排列，K是当前这个数的个数】【这里的”内“，指的是当前值的k个元素之间形成的元素对】
 *
 * @since 2022-03-24 22:33:05
 */
public class Code04_VisibleMountains {

    // 栈中放的记录，
    // value就是指，times是收集的个数
    public static class Record {
        public int value;
        public int times;

        public Record(int value) {
            this.value = value;
            this.times = 1;
        }
    }

    public static int getVisibleNum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int maxIndex = 0;
        // 先在环中找到其中一个最大值的位置，哪一个都行
        for (int i = 0; i < N; i++) {
            maxIndex = arr[maxIndex] < arr[i] ? i : maxIndex;
        }
        Stack<Record> stack = new Stack<Record>();
        // 先把(最大值,1)这个记录放入stack中
        stack.push(new Record(arr[maxIndex]));
        // 从最大值位置的下一个位置开始沿next方向遍历
        int index = nextIndex(maxIndex, N);
        // 用“小找大”的方式统计所有可见山峰对
        int res = 0;
        // 遍历阶段开始，当index再次回到maxIndex的时候，说明转了一圈，遍历阶段就结束
        while (index != maxIndex) {
            // 当前数要进入栈，判断会不会破坏第一维的数字从顶到底依次变大
            // 如果破坏了，就依次弹出栈顶记录，并计算山峰对数量
            while (stack.peek().value < arr[index]) {
                int k = stack.pop().times;
                // 弹出记录为(X,K)，如果K==1，产生2对; 如果K>1，产生2*K + C(2,K)对。
                res += getInternalSum(k) + 2 * k;
            }
            // 当前数字arr[index]要进入栈了，如果和当前栈顶数字一样就合并
            // 不一样就把记录(arr[index],1)放入栈中
            if (stack.peek().value == arr[index]) {
                stack.peek().times++;
            } else { // >
                stack.push(new Record(arr[index]));
            }
            index = nextIndex(index, N);
        }
        // 清算阶段开始了
        // 清算阶段的第1小阶段
        while (stack.size() > 2) {
            int times = stack.pop().times;
            res += getInternalSum(times) + 2 * times;
        }
        // 清算阶段的第2小阶段
        if (stack.size() == 2) {
            int times = stack.pop().times;
            res += getInternalSum(times)
                    + (stack.peek().times == 1 ? times : 2 * times);
        }
        // 清算阶段的第3小阶段
        res += getInternalSum(stack.pop().times);
        return res;
    }

    /**
     * 如果k==1返回0，如果k>1返回C(2,k)
     *
     * @since 2022-03-27 22:55:21
     */
    public static int getInternalSum(int k) {
        return k == 1 ? 0 : (k * (k - 1) / 2);
    }

    /**
     * 环形数组中当前位置为i，数组长度为size，返回i的下一个位置
     *
     * @since 2022-03-27 22:55:28
     */
    public static int nextIndex(int i, int size) {
        return i < (size - 1) ? (i + 1) : 0;
    }

    /**
     * 环形数组中当前位置为i，数组长度为size，返回i的上一个位置
     *
     * @since 2022-03-27 22:55:34
     */
    public static int lastIndex(int i, int size) {
        return i > 0 ? (i - 1) : (size - 1);
    }

    // for test, O(N^2)的解法，绝对正确
    public static int rightWay(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int res = 0;
        HashSet<String> equalCounted = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            // 枚举从每一个位置出发，根据“小找大”原则能找到多少对儿，并且保证不重复找
            res += getVisibleNumFromIndex(arr, i, equalCounted);
        }
        return res;
    }

    // for test
    // 根据“小找大”的原则返回从index出发能找到多少对
    // 相等情况下，比如arr[1]==3，arr[5]==3
    // 之前如果从位置1找过位置5，那么等到从位置5出发时就不再找位置1（去重）
    // 之前找过的、所有相等情况的山峰对，都保存在了equalCounted中
    public static int getVisibleNumFromIndex(int[] arr, int index,
                                             HashSet<String> equalCounted) {
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            // 不找自己
            if (i != index) {
                if (arr[i] == arr[index]) {
                    String key = Math.min(index, i) + "_" + Math.max(index, i);
                    // 相等情况下，确保之前没找过这一对
                    if (equalCounted.add(key) && isVisible(arr, index, i)) {
                        res++;
                    }
                } else if (isVisible(arr, index, i)) {
                    // 不相等的情况下直接找
                    res++;
                }
            }
        }
        return res;
    }

    // for test
    // 调用该函数的前提是，lowIndex和highIndex一定不是同一个位置
    // 在“小找大”的策略下，从lowIndex位置能不能看到highIndex位置
    // next方向或者last方向有一个能走通，就返回true，否则返回false
    public static boolean isVisible(int[] arr, int lowIndex, int highIndex) {
        if (arr[lowIndex] > arr[highIndex]) {
            // “大找小”的情况直接返回false
            return false;
        }
        int size = arr.length;
        boolean walkNext = true;
        int mid = nextIndex(lowIndex, size);
        // lowIndex通过next方向走到highIndex，沿途不能出现比arr[lowIndex]大的数
        while (mid != highIndex) {
            if (arr[mid] > arr[lowIndex]) {
                // next方向失败
                walkNext = false;
                break;
            }
            mid = nextIndex(mid, size);
        }
        boolean walkLast = true;
        mid = lastIndex(lowIndex, size);
        // lowIndex通过last方向走到highIndex，沿途不能出现比arr[lowIndex]大的数
        while (mid != highIndex) {
            if (arr[mid] > arr[lowIndex]) {
                // last方向失败
                walkLast = false;
                break;
            }
            mid = lastIndex(mid, size);
        }

        // 有一个成功就是能相互看见
        return walkNext || walkLast;
    }

    // for test
    public static int[] getRandomArray(int size, int max) {
        int[] arr = new int[(int) (Math.random() * size)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int size = 10;
        int max = 10;
        int testTimes = 3000000;
        System.out.println("test begin!");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = getRandomArray(size, max);
            if (rightWay(arr) != getVisibleNum(arr)) {
                printArray(arr);
                System.out.println(rightWay(arr));
                System.out.println(getVisibleNum(arr));
                break;
            }
        }
        System.out.println("test end!");
    }

}