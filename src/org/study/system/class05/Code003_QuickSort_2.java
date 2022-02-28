package org.study.system.class05;

import java.util.Arrays;

/**
 * 快速排序
 *
 * 快速排序2.0版本【时间复杂度是 O(n^2)】
 * 利用荷兰国旗问题（<x 的放在左边，=x的放在之间 ，>x的放在右边）。递归去执行【相当于每次选出一批相等的数】
 *
 * @author phil
 * @date 2021/6/2 15:34
 */
public class Code003_QuickSort_2 {


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
        for (int i = 0; i < 1000000; i++) {
            int[] arr = generateArr(1000000,1000000);
            quickSort2(arr,0,arr.length-1);
            if(isSorted(arr)){
                System.out.println("有问题的数组："+ Arrays.toString(arr));
                break;
            }
            System.out.println("第 "+ i + " 次成功！");
        }

        System.out.println("nice!!!");
    }

    public static void quickSort2(int[] arr,int left,int right){
        if(null == arr || arr.length < 2){
            return;
        }

        process2(arr,left,right);
    }

    public static void process2(int[] arr,int left,int right){
        if(left >= right){
            return;
        }

        int[] equalArea = Code001_NetherlandsFlagIssue.netherlandsFlagIssueVersion2(arr,left,right);
        process2(arr,left,equalArea[0]-1);
        process2(arr,equalArea[1]+1,right);
    }
}
