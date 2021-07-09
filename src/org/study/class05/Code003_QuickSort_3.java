package org.study.class05;

import org.study.Util;

import java.util.Arrays;

/**
 * 快速排序【随机快排，也就是最常见的快速排序】
 *
 * 快速排序3.0版本【可以让时间复杂度达到 O(N*logN)】
 * 快速排序的额外空间复杂度 【 O(logN) 】【递归版本】【非递归版本空间复杂度也是一样的】
 *
 * @author phil
 * @date 2021/6/2 15:34
 */
public class Code003_QuickSort_3 {

    public static void main(String[] args) {

        boolean flag = true;
        for (int i = 0; i < 1000000; i++) {
            int[] arr = Util.generateArr(1000000,1000000);
            quickSort3(arr,0,arr.length-1);
            if(!Util.isSorted(arr)){
                System.out.println("有问题的数组："+ Arrays.toString(arr));
                flag = false;
                break;
            }
            System.out.println("第 "+ i + " 次成功！");
        }


        if(flag){
            System.out.println("nice!!!");
        }else {
            System.err.println("error!!!");
        }

    }

    public static void quickSort3(int[] arr,int left,int right){
        if(null == arr || arr.length < 2){
            return;
        }

        process3(arr,left,right);
    }

    public static void process3(int[] arr,int left,int right){
        if(left >= right){
            return;
        }

        // 相对于2.0 只是加了这一行代码
        // 不是每次都拿最右边的值作为划分值，而是在待排序的数组中，随机选择一个数，作为目标值来比较
        // 担心目标值会达到太边缘【目标值达到最之间是最好的】
        Util.swap(arr,left + (int) (Math.random()*(right - left +1)),right);

        int[] equalArea = Code001_NetherlandsFlagIssue.netherlandsFlagIssueVersion2(arr,left,right);
        process3(arr,left,equalArea[0]-1);
        process3(arr,equalArea[1]+1,right);
    }
}
