package org.study.coding.class16;

public class Code04_MergeRecord {

    /*
     * 腾讯原题
     *
     * 给定整数power，给定一个数组arr，给定一个数组reverse。含义如下：
     * arr的长度一定是2的power次方，reverse中的每个值一定都在0~power范围。
     * 例如power = 2, arr = {3, 1, 4, 2}，reverse = {0, 1, 0, 2}
     *
     *
     * 任何一个在前的数字可以和任何一个在后的数组，构成一对数。可能是升序关系、相等关系或者降序关系。
     * 比如arr开始时有如下的降序对：(3,1)、(3,2)、(4,2)，一共3个。
     *
     * 接下来根据reverse对arr进行调整：
     * reverse[0] = 0, 表示在arr中，划分每1(2的0次方)个数一组，然后每个小组内部逆序，那么arr变成
     * [3,1,4,2]，此时有3个逆序对。
     *
     * reverse[1] = 1, 表示在arr中，划分每2(2的1次方)个数一组，然后每个小组内部逆序，那么arr变成
     * [1,3,2,4]，此时有1个逆序对
     *
     * reverse[2] = 0, 表示在arr中，划分每1(2的0次方)个数一组，然后每个小组内部逆序，那么arr变成
     * [1,3,2,4]，此时有1个逆序对。
     *
     * reverse[3] = 2, 表示在arr中，划分每4(2的2次方)个数一组，然后每个小组内部逆序，那么arr变成
     * [4,2,3,1]，此时有5个逆序对。
     *
     * 所以返回[3,1,1,5]，表示每次调整之后的逆序对数量。
     *
     * 输入数据状况：
     * power的范围[0,20]
     * arr长度范围[1,10的7次方]
     * reverse长度范围[1,10的6次方]
     *
     *
     * 解法：
     * 求逆序对：
     * 1）：考虑以每个数出发，能求出多少。最后累计
     * 2）考虑2的n次方为一组(n=1...n)，组内有多少逆序对。注意，高次方的不要重复计算低次方的逆序对数量
     *    比如要统计 8 个数一组时的逆序对数量。那么，就只需统计左边四个数任意选择、右边四个数任意选择的逆序对数量。
     *    不能两个数都来自左边，也不能两个数都来自右边
     *    同在一边的情况，属于低次方的情况，在前面已经统计过了
     *
     * 本题：
     * 准备两个辅助数组。正序数组arr1和逆序数组arr2
     * arr1[i] : 表示 i 个数一组时的组内正序对的数量之和
     * arr2[i] : 表示 i 个数一组时的组内逆序对的数量之和
     * 先统计好arr1[i]和arr2[i]的信息。i 是2的某次方。最大到power次方
     * 当 reverseArr[i] = x 出现时，表示的是. 2^x 个数一组，组内逆序。逆序之后收集当前的逆序对
     * 要逆转顺序，那么也就是该组内，之前的正序对数量，就是逆序后的逆序对数量。即 arr1[2^x] 和 arr2[2^x] 交换值
     * 交换完成之后。收集 arr2[2^x] 。就是当前位置的逆序对数量
     *
     *
     * 任何统计 arr1[i] 和 arr2[i] ？？
     * 因为所给的数组是 2 的某次方，肯定是偶数个，在归并排序分组的时候，肯定会特别平均
     * 在merge的时候，去收集对应组的信息，特别好收集。
     * 如果merge的时候，左组的长度是2，那么，当前收集的就是 arr1[2] 和 arr2[2] 的信息
     *
     *
     */

    public static int[] reversePair1(int[] originArr, int[] reverseArr, int power) {
        int[] ans = new int[reverseArr.length];
        for (int i = 0; i < reverseArr.length; i++) {
            reverseArray(originArr, 1 << (reverseArr[i]));
            ans[i] = countReversePair(originArr);
        }
        return ans;
    }

    public static void reverseArray(int[] originArr, int teamSize) {
        if (teamSize < 2) {
            return;
        }
        for (int i = 0; i < originArr.length; i += teamSize) {
            reversePart(originArr, i, i + teamSize - 1);
        }
    }

    public static void reversePart(int[] arr, int L, int R) {
        while (L < R) {
            int tmp = arr[L];
            arr[L++] = arr[R];
            arr[R--] = tmp;
        }
    }

    public static int countReversePair(int[] originArr) {
        int ans = 0;
        for (int i = 0; i < originArr.length; i++) {
            for (int j = i + 1; j < originArr.length; j++) {
                if (originArr[i] > originArr[j]) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public static int[] reversePair2(int[] originArr, int[] reverseArr, int power) {
        // 原始数组的逆序数组
        // 在求逆序对数量的时候，也可以用逆序数组求正序对数量【可以用一个方法就搞定两个辅助记录】
        int[] reverse = copyArray(originArr);
        reversePart(reverse, 0, reverse.length - 1);

        // 就是记录正序对数量和逆序对数量的辅助数组
        // 逆序对数量
        int[] recordDown = new int[power + 1];

        // 正序对数量
        int[] recordUp = new int[power + 1];

        process(originArr, 0, originArr.length - 1, power, recordDown);
        process(reverse, 0, reverse.length - 1, power, recordUp);


        int[] ans = new int[reverseArr.length];

        // 遍历reverse数组。该反转的反转，该累加的累加
        for (int i = 0; i < reverseArr.length; i++) {
            int curPower = reverseArr[i];

            // 反转受影响的位置 【正序和逆序交换】
            for (int p = 1; p <= curPower; p++) {
                int tmp = recordDown[p];
                recordDown[p] = recordUp[p];
                recordUp[p] = tmp;
            }

            // 累加逆序对数量
            for (int p = 1; p <= power; p++) {
                ans[i] += recordDown[p];
            }
        }
        return ans;
    }

    // originArr[L...R]完成排序！
    // L...M左  M...R右  merge
    // L...R  2的power次方

    public static void process(int[] originArr, int L, int R, int power, int[] record) {
        if (L == R) {
            return;
        }
        int mid = L + ((R - L) >> 1);
        process(originArr, L, mid, power - 1, record);
        process(originArr, mid + 1, R, power - 1, record);

        // 这里是累加。需要将左右两部分的都累加起来
        record[power] += merge(originArr, L, mid, R);
    }

    public static int merge(int[] arr, int L, int m, int r) {
        int[] help = new int[r - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = m + 1;
        int ans = 0;
        while (p1 <= m && p2 <= r) {
            ans += arr[p1] > arr[p2] ? (m - p1 + 1) : 0;
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= m) {
            help[i++] = arr[p1++];
        }
        while (p2 <= r) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        return ans;
    }

    // for test
    public static int[] generateRandomOriginArray(int power, int value) {
        int[] ans = new int[1 << power];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value);
        }
        return ans;
    }

    // for test
    public static int[] generateRandomReverseArray(int len, int power) {
        int[] ans = new int[len];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * (power + 1));
        }
        return ans;
    }

    // for test
    public static void printArray(int[] arr) {
        System.out.println("arr size : " + arr.length);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int powerMax = 8;
        int msizeMax = 10;
        int value = 30;
        int testTime = 50000;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int power = (int) (Math.random() * powerMax) + 1;
            int msize = (int) (Math.random() * msizeMax) + 1;
            int[] originArr = generateRandomOriginArray(power, value);
            int[] originArr1 = copyArray(originArr);
            int[] originArr2 = copyArray(originArr);
            int[] reverseArr = generateRandomReverseArray(msize, power);
            int[] reverseArr1 = copyArray(reverseArr);
            int[] reverseArr2 = copyArray(reverseArr);
            int[] ans1 = reversePair1(originArr1, reverseArr1, power);
            int[] ans2 = reversePair2(originArr2, reverseArr2, power);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

        powerMax = 20;
        msizeMax = 1000000;
        value = 1000;
        int[] originArr = generateRandomOriginArray(powerMax, value);
        int[] reverseArr = generateRandomReverseArray(msizeMax, powerMax);
        // int[] ans1 = reversePair1(originArr1, reverseArr1, powerMax);
        long start = System.currentTimeMillis();
        reversePair2(originArr, reverseArr, powerMax);
        long end = System.currentTimeMillis();
        System.out.println("run time : " + (end - start) + " ms");
    }

}
