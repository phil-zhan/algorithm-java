package org.study.coding.class04;

public class Code06_MakeNo {

    /**
     * // 生成长度为size的达标数组
     * // 达标：对于任意的 i<k<j，满足 [i] + [j] != [k] * 2
     * 如果 a、b、c 达标。即 a+c != 2b
     * 那么 2a、2b、2c  也达标
     * 2a+1、2b+1、2c+1 也达标
     *
     * 由此可以得出
     * [2a、2b、2c,2a+1、2b+1、2c+1]  也达标
     * 如果只在左半部分选，达标
     * 如果只在右半部分选，达标
     * 如果选中的是左边一个，右边一个。那么肯定也达标。因为一个偶数加一个奇数，肯定是奇数，不可能是谁的2倍
     *
     *
     * 要生成长度为 n 的
     * 只需要一个长度为 n/2 【向上取整。到时候翻倍。如果是奇数，就扔掉一个好了】
     *
     * 如 n = 7
     * 需要长度为7的，准备长度为 4 的
     * 需要长度为4的，准备长度为 2 的
     * 需要长度为2的，准备长度为 1 的
     *
     *
     * [1]
     * [2,3]
     * [4,6,5,7]
     * [8,12,10,14,9,13,11,15]
     *
     * 长度为1 的时候，写啥都行，往后就是=翻倍和翻倍加1
     *
     * @since 2022-03-03 11:48:15
     */
    public static int[] makeNo(int size) {
        if (size == 1) {
            return new int[]{1};
        }
        // size
        // 一半长达标来
        // 7 : 4
        // 8 : 4
        // [4个奇数] [3个偶]
        int halfSize = (size + 1) / 2;
        int[] base = makeNo(halfSize);
        // base -> 等长奇数达标来
        // base -> 等长偶数达标来
        int[] ans = new int[size];
        int index = 0;
        for (; index < halfSize; index++) {
            ans[index] = base[index] * 2 - 1;
        }
        for (int i = 0; index < size; index++, i++) {
            ans[index] = base[i] * 2;
        }
        return ans;
    }

    // 检验函数
    public static boolean isValid(int[] arr) {
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            for (int k = i + 1; k < N; k++) {
                for (int j = k + 1; j < N; j++) {
                    if (arr[i] + arr[j] == 2 * arr[k]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int N = 1; N < 1000; N++) {
            int[] arr = makeNo(N);
            if (!isValid(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
        System.out.println(isValid(makeNo(1042)));
        System.out.println(isValid(makeNo(2981)));
    }

}
