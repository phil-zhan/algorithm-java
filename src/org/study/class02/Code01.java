package org.study.class02;

/**
 * 在一个数组中，只有两个数出现了奇数次，其他都出现了偶数次
 * 找出这两个数
 * 实现思路如下：
 * 将数组中每一个数都做异或【arr[1]^arr[2]^arr[3]^...^arr[n]】。===》得到的结果也就是两个奇数的异或结果 设为eor
 * 找出这个结果的任何一个1的位置【设找最右侧一个1的位置】【也就是说在这个位置上，这两个奇数的值是不一样的】
 * 将数组中该位置是1的数和该位置是0的数分开为两个子数组  设为arr1 和arr2
 * 再利用之前的异或结果和其中一个子数组就行异或 【eor^arr1[1]^arr1[2]^...^arr1[n]】就得到了其中一个奇数 设为eor1
 * 最后再利用 eor ^ eor1 = eor2 得到另外一个奇数
 *
 *
 * @author phil
 * @date 2021/3/12 10:39
 */
public class Code01 {

    /**
     * 异或运算
     * 是从二进制的层面去计算的
     * 相同为0，不同为1【同或运算与它相反】
     * 11101 ^ 11001 = 00100
     * 1101 ^ 1101 = 0000【自己异或自己的结果为0】
     * 1101 ^ 0000 = 1101【和0做异或，结果为0】
     *
     */

    public static void main(String[] args) {

        // 只有4、5出现了奇数次
        int[] arr = {1,1,2,2,3,3,4,5};

        int[] res = getOddTimesNum(arr);
        printArr(res);

    }

    /**
     * 提取出一个数【转换为二进制后】的最右侧的一个1的位置
     * i = eor & (-eor)    或者
     * i = eor & (~eor + 1 )
     * @date 2021-03-12 10:50:45
     */

    public static int getRightNonZeroPoseNum(int i){

        // 两种写法是一样的
        //return i & (-i);
        return i & (~i+1);
    }

    /**
     * 获取出现奇数次的数字
     * @date 2021-03-12 13:13:01
     */
    public static int[] getOddTimesNum(int[] arr){
        int eor=0;

        for (int item : arr) {
            eor ^= item;
        }


        // a和b 是两种数
        // eor != 0
        // eor 最右侧的 1 提取出来
        // eor :            100111000111100001101000
        // rightOnePosNum : 000000000000000000001000
        int rightOnePosNum = getRightNonZeroPoseNum(eor);

        // 其中的一个奇数次的数
        int onlyOne = 0;

        for (int value : arr) {

            // 找到所有对应位置是1的数字
            // & 必须同时为1 才能是1
            // arr[1] =         111000101010111001011010
            // rightOnePosNum = 000000000000000000001000
            if ((value & rightOnePosNum) != 0) {
                onlyOne ^= value;
            }
        }

        return new int[]{onlyOne,onlyOne^eor};
    }

    /**
     * 打印数组
     * @date 2021-03-12 13:12:46
     */
    private static void printArr(int[] arr){

        for (int i : arr){
            System.out.print(i+"\t");
        }
    }
}
