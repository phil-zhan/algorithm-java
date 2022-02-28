package org.study.system.class05;

import java.util.Arrays;

/**
 * 荷兰国旗问题
 * 给定一个数组arr[],和一个指定的数字x。限制条件是不能使用辅助数组。
 * 版本1
 *      <=x 的放在左边，>x的放在右边
 * 版本2
 *      <x 的放在左边，=x的放在之间 ，>x的放在右边
 *
 * 解题思路：设计一个 小于等于 区域。一开始，该区域的右边界停留在整个数组的左边 index = -1
 * 遍历数组，便利的过程中会遇到两种情况。
 * 情况1：数组的元素 <= 给定的数x
 *      把当前数和 小于等于 的下一个数做交换， 小于等于 区向右拓展，当前数跳下一个
 * 情况2：数组的元素 > 给定的数x
 *      当前数直接跳下一个
 *
 *
 * 一个数组中，有一个目标数x。 <x 的放在左边，=x的放在之间 ，>x的放在右边
 *
 * 解题思路
 *      设置两个区域，小于区和大于区。小于区的初始index为 -1.大于区的初始index为 数组的长度
 *      便利数组。
 *      若元素小于目标数，就和小于区的下一位交换，小于区的index = index +1,继续遍历下一个元素
 *      若元素等于目标数，就不用动
 *      若元素大于目标数，就和大于区的前一位交换,大于区的index = index -1。先不要跳到下一个元素，先将换过来的元素在和目标元素比较
 *  【备注：因为是从左往右遍历，左边换过来的数一定是遍历过的。但是右边换过来的数是没有遍历过的】
 * @author phil
 * @date 2021/5/28 15:49
 */
public class Code001_NetherlandsFlagIssue {
    /**
     * 交换数组中 i和 j的位置
     * @date 2021-06-02 14:10:43
     */
    public static void swap(int[] arr,int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    /**
     * 生成一个随机数组
     * @date 2021-05-31 17:51:16
     */
    public static int[] generateArr(int maxLength,int maxValue){
        int length = (int) ((maxLength + 1)*Math.random());
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int)  ((maxValue +1)*(Math.random())) - (int)((maxValue)*Math.random());
        }
        return arr;
    }
    /**
     * 判断一个数组是否有序
     * @date 2021-07-06 14:09:01
     */
    public static boolean isSorted(int[] arr){
        for (int i = 0; i < arr.length-1; i++) {
            if(arr[i] > arr[i+1]){
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {

        // 版本1 ：<=x 的放在左边，>x的放在右边
        int[] arr = generateArr(30,20);
        System.out.println("版本1之前："+Arrays.toString(arr));
        int target = 10;
        int leftIndex = -1;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] <= target){
                int temp ;
                temp = arr[i];
                arr[i] = arr[leftIndex + 1];
                arr[leftIndex + 1] = temp;
                leftIndex ++ ;
            }
        }

        System.out.println("版本1之后："+Arrays.toString(arr));


        // 以下是版本2
        int[] arr2 = generateArr(20, 10);
        int[] ints = netherlandsFlagIssueVersion2(arr2, 0, arr2.length-1);

        System.out.println(Arrays.toString(ints));

    }

    public static int netherlandsFlagIssueVersion1(int[] arr,int left,int right){
        if (left > right) {
            return -1;
        }

        if (left == right) {
            return left;
        }

        int less = left-1;
        int index = left;
        while (index < right){
            if(arr[index] <= arr[right]){
                swap(arr,index,++less);
            }
            index ++;
        }

        swap(arr,++less,right);

        return less;
    }

    /**
     * 荷兰国旗问题，版本2
     * 返回的是等于区域的左右下标
     * @date 2021-06-02 14:25:26
     */
    public static int[] netherlandsFlagIssueVersion2(int[] arr,int left,int right){
        if (left > right) {
            return new int[] {-1,-1};
        }

        if (left == right) {
            return new int[] {left,right};
        }
        int less = left-1;
        int more = right;
        int index = left;

        while (index < more){
            if(arr[index] == arr[right]){
                index ++ ;
            }else if(arr[index] < arr[right]){
                swap(arr,++less,index++);
            }else{
                swap(arr,index,--more);
            }
        }
        swap(arr,more,right);
        //System.out.println(arr[more]);
        //System.out.println(Arrays.toString(arr));
        return new int[] {less+1,more};
    }


}
