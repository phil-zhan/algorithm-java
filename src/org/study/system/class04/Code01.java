package org.study.system.class04;

import java.util.Arrays;

/**
 * @author phil
 * @date 2021/3/15 14:03
 * 归并排序
 *
 * 大致思路是
 * 利用递归，先让左半部分有序，在让又半部分有序。最后再合并左右
 * 递归的时候，采用平均二分
 * 合并的时候，能做到时间复杂度为 O(N)
 *
 * master公式
 * T(N) = 2*T(N/2) +O(N)
 * a = 2
 * b = 2
 * d = 1
 * 利用master公式知道
 * 归并排序的时间复杂度为 O(N*logN)
 */
public class Code01 {


    public static void main(String[] args) {
        int[] arr = {1,9,7,5,2,4,6,8,3};
        mergeSort2(arr);
        //process(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }


    /**
     * 归并排序。【递归方法实现】
     * @date 2021-03-15 14:16:15
     */
    public static void process(int[] arr,int left,int right){
        if(left == right){
            return;
        }
        int mid = left + ((right-left) >> 1);
        process(arr,left,mid);
        process(arr,mid+1,right);
        merge(arr,left,mid,right);
    }

    /**
     * 将左右两个有序的数组合并为一个 有序的大数组
     *
     * 实现思路大概是
     * 定义一个新数组 arr，数组长度为左右两个数组的长度之和
     * 两个指针分别从左右两个数组的开始位置开始。
     * 比较左右两个指针所指的位置的数，谁小就拷贝谁到新数组，拷贝之后，被拷贝的指针向后移动，再次和另外一个数组的指针对应的数做比较
     *
     * 也就是谁小拷贝谁，拷贝之后对应的指针后移。当一个数组的指针移到最后，也就是一个数组的数被拷贝完了，而另外一个数组还有数
     * 就讲另外一个数组的数全部拷贝到新数组的最后。
     * @date 2021-03-15 14:16:05
     */
    public static void merge(int[] arr,int left,int mid,int right){
        int[] help = new int[right-left+1];

        int i = 0;
        int p1 = left;
        int p2 = mid + 1;

        while (p1 <= mid && p2 <=right){
            help[i++] = arr[p1] <= arr[p2]?arr[p1++]:arr[p2++];
        }

        // 要么p1越界了，要么p2越界了【这两个循环只会执行一个】
        while (p1 <= mid){
            help[i++] = arr[p1++];
        }
        while (p2 <= right){
            help[i++] = arr[p2++];
        }

        if (help.length >= 0) {
            System.arraycopy(help, 0, arr, left, help.length);
        }

    }
    
    /**
     * 归并排序【非递归方法实现】
     * 以步长来实现划分
     * 首先步长为2，将原数组划分为有个步长为二的数组，每相邻两个数组都去就行合并【也就是谁小拷贝谁】
     * 然后步长逐步增加，直到步长等于数组长度的一半。
     *
     * @date 2021-03-15 15:00:51
     */

    public static void mergeSort2(int[] arr){
        if(null == arr || arr.length <= 2){
            return;
        }
        int n = arr.length;

        // 当前有序左组长度.也就是步长
        int mergeSize = 1;

        while (mergeSize < n){
            int l = 0;
            while (l<n){
                int m = l+mergeSize-1;
                if(m>=n){
                    break;
                }
                int r = Math.min(m+mergeSize,n-1);
                merge(arr,l,m,r);
                l = r+1;
            }
            if(mergeSize > n/2){
                break;
            }

            // 也就是乘以二
            mergeSize <<= 1;
        }
    }
}
