package org.study.system.class03;

/**
 * @author phil
 * @date 2021/3/15 9:15
 * 递归算法
 *
 * 底层实现思路：
 * 会有一个系统栈。当执行到需要递归的一行，就会往系统栈中压入
 * 当前方法，当前行、到当前行所产生的局部变量，以及当前在等待的结果（也就是需要等待某个返回结果）
 * 去执行他需要等待的结果的方法
 * 当拿到这个想要的结果后，就当当前的行出栈，继续往下执行
 *
 *
 * 如何递归行为都可以改为非递归形式。【也就是自己实现压栈过程】
 *
 *
 * 利用递归算法，求数组中的最大值
 */
public class Code05 {


    public static int getMax(int[] arr){
        return process(arr,0,arr.length-1);
    }

    private static int process(int[] arr, int left, int right) {

        // 只有一个数的时候。1向右移动一位或者是除以2取整都是0
        if(left == right){
            return arr[left];
        }
        // 右移一位，相当于十进制中的除以二并取整
        // mid:一定是大于等于left的
        int mid = left + ((right - left)>>1);

        int leftMax = process(arr,left,mid);
        int rightMax = process(arr,mid+1,right);

        return Math.max(leftMax, rightMax);
    }

    public static void main(String[] args) {

        int x = 7>>1;
        double m = 9.9;

        int t = (int)m;
        int x1 = 6>>1;

        System.out.println(t);
    }

}
