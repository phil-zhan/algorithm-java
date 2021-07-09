package org.study.class05;

import org.study.Util;

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

    public static void main(String[] args) {
        for (int i = 0; i < 1000000; i++) {
            int[] arr = Util.generateArr(1000000,1000000);
            quickSort2(arr,0,arr.length-1);
            if(Util.isSorted(arr)){
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
