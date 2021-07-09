package org.study.class06;

import org.study.PrintUtil;
import org.study.Util;

/**
 * @author phil
 * @date 2021/6/9 13:31
 */
public class MyHeapSort {
    public static void main(String[] args) {
        int testTimes = 10000000;
        while (--testTimes >= 0){
            int[] arr = Util.generateArr(100,20);


            for (int j = arr.length-1;j>=0;j--){
                heapify(arr,j, arr.length);
            }

            int currentLength = arr.length;
            while (currentLength>0){
                Util.swap(arr,0,currentLength-1);
                heapify(arr,0,--currentLength);
            }

            if(!Util.isSorted(arr)){
                System.err.println("Debug出错啦！");
                PrintUtil.printArr(arr);
                System.err.println("Debug出错啦！");
                break;
            }
        }
        System.out.println("测试结束！ nice");

    }

    private static void heapInsert(int[] arr,int index){
       while (arr[index] > arr[(index - 1)/2]){
           Util.swap(arr,index,(index - 1)/2);

           index = (index - 1)/2;
       }
    }


    private static void heapify(int[] arr, int index, int heapSize){
        int leftChildIndex = (index *2) +1;

        while (leftChildIndex < heapSize){

            // 返回左右孩子中，值大的一个的下标
            int largest = leftChildIndex + 1 < heapSize && arr[leftChildIndex +1] > arr[leftChildIndex]?leftChildIndex+1:leftChildIndex;
            largest = arr[largest] > arr[index] ? largest:index;
            if (largest == index) {
                break;
            }
            Util.swap(arr,index,largest);
            index = largest;
            leftChildIndex = largest*2 +1;
        }
    }
}
