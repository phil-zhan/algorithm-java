package org.study;

/**
 * 工具类
 *
 * @author phil
 * @date 2021/6/2 16:02
 */
public class Util {
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


}
