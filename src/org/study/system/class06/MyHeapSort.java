package org.study.system.class06;

/**
 * @author phil
 * @date 2021/6/9 13:31
 */
public class MyHeapSort {


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
        int testTimes = 10000000;
        while (--testTimes >= 0){
            int[] arr = generateArr(100,20);


            for (int j = arr.length-1;j>=0;j--){
                heapify(arr,j, arr.length);
            }

            int currentLength = arr.length;
            while (currentLength>0){
                swap(arr,0,currentLength-1);
                heapify(arr,0,--currentLength);
            }

            if(!isSorted(arr)){
                System.err.println("Debug出错啦！");
                // PrintprintArr(arr);
                System.err.println("Debug出错啦！");
                break;
            }
        }
        System.out.println("测试结束！ nice");

    }

    private static void heapInsert(int[] arr,int index){
       while (arr[index] > arr[(index - 1)/2]){
           swap(arr,index,(index - 1)/2);

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
            swap(arr,index,largest);
            index = largest;
            leftChildIndex = largest*2 +1;
        }
    }
}
