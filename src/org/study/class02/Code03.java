package org.study.class02;

/**
 * 在一个数组中，有一种数出现了k次，其他的数都出现了M次。M>1,K>M
 * 找出出现K次的数
 * 要求：额外空间复杂度 O(1) ,时间复杂度 O(N)
 *
 * 实现思路如下：
 * 由于int类型的字节长度是32
 * 准备一个长度为32的数组 设为 t。
 * 数组中的每一个值都置为0
 * 遍历所给数组 arr，将每个数都转为长度为32的二进制数组。然后将对应的位置相加，放到 自定义的数组 t 中的对应位置上
 * arr[1] = 0000 0000 0000 0000 0000 0000 0000 0100
 * arr[2] = 0000 0000 0000 0000 0000 0000 0000 1100
 * arr[3] = 0000 0000 0000 0000 0000 0000 0100 1100
 *      t = 0000 0000 0000 0000 0000 0000 0100 2300
 *
 * 也就是把所有数字的二进制状态的1都累加到t中
 *
 * 把t数组中的每一个数对M取余。如果这个位置上只有出现M次的数【全部出现M次的数或者部分出现M次的数】，不含出现K次的数，那么取余之后的结果肯定是0
 * 如果取余之后不等于0，那么这个位置上肯定有出现K次的数
 *
 * 最后再把取余之后不等于0的位置标记为1，其他位置标记为0.。也就是需要找到的出现K次的数
 *
 * @author phil
 * @date 2021/3/12 10:39
 */
public class Code03 {

    /**
     * 异或运算
     * 是从二进制的层面去计算的
     * 相同为0，不同为1【同或运算与它相反】
     * 11101 ^ 11001 = 00100
     * 1101 ^ 1101 = 0000【自己异或自己的结果为0】
     * 1101 ^ 0000 = 1101【和0做异或，结果为0】
     *
     * & 与运算
     * 只有全部为1才是1，否则都是0
     *
     *
     */

    public static void main(String[] args) {

        // 只有6出现了两次，其他都出现了四次
        // K=3,M=4 K<M
        int[] arr = {6,6,2,2,2,2,3,3,3,3,5,5,5,5};

        int answer = getOnlyKTimesNum(arr,2,4);

        System.out.println(answer);
    }

    /**
     * 必须保证arr中只有一种数出现了K次，其他数都出现了M次
     * @date 2021-03-12 14:09:08
     */
    public static int getOnlyKTimesNum(int[] arr,int k,int m){

        int[] t = new int[32];

        // t[0]：代表第0位置的1出现了几次
        // t[1]：代表第1位置的1出现了几次
        // t[2]：代表第2位置的1出现了几次

        // 虽然是两个循环，但是因为第二层的for循环每次都是固定循环32次。所以时间复杂度还是 O(n)
        for (int num:arr) {
            for (int i = 0; i <= 31; i++) {

                // 判断第i位是否是1
                // 将所有的位置都移动到最右边去做比较
                if(((num >> i) & 1)!=0 ){
                    // num在第i位置上是1
                    t[i]++;
                }
            }
        }

        int answer = 0;
        for (int i = 0; i < 32; i++) {
            if(t[i] % m != 0){
                //第i位置只有出现K次的数，没有出现M次的数

                // 将需要找的数的当前位置设置为1
                answer |= 1<<i;
            }
        }

        return answer;
    }

}
