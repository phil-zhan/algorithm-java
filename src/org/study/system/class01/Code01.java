package org.study.system.class01;


import java.util.Arrays;

/**
 * @author phil
 */
public class Code01 {

    public static void main(String[] args) {

        InsertSort mySort = new InsertSort();

        // 测试次数
        int testTimes = 5000000;

        // 随机数组的长度
        int maxSize = 100;

        // 值  -100~100
        int maxValue = 100;

        // 成功标识
        boolean succeed = true;

        for (int i = 0; i < testTimes; i++) {
            // 得到应该随机长度的随机数组
            int[] arr = generateRandomArray(maxSize,maxValue);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);

            // 算法去操作第一个数组
            mySort.sort(arr1);
            // 计算机系统去操作第二个数组
            comparator(arr2);

            // 比较得到的数组是否一直
            if (!isEqual(arr1,arr2)){
                succeed = false;

                // 将不满足算法的数组打印出来
                printArray(arr);
                break;
            }
        }

        System.out.println(succeed?"Nice!":"Fucking fucked!!!");

        int[] arr = generateRandomArray(maxSize,maxValue);
        // printArray(arr);
        // insertSort(arr);
        // printArray(arr);
    }

    /**
     * 打印数组
     * @date 2021-03-09 18:09:49
     */
    private static void printArray(int[] arr) {
        if (null == arr){
            return;
        }

        for (int value : arr) {
            System.out.print(value);
            System.out.print(" , ");

        }

        System.out.println();
    }

    /**
     * 产生一个随机长度的随机数组
     * @date 2021-03-09 17:57:47
     */
    private static int[] generateRandomArray(int maxSize, int maxValue) {
        // 长度随机..。Math.random()：随机等概率产生一个0~1之间的小数..[0,1)
        // N*Math.random()：随机等概率产生一个0~N之间的数..[0,N)
        int[] arr = new int[(int) ((maxSize + 1)*Math.random())];

        for (int i = 0; i < arr.length; i++) {

            // 随机产生一个 -maxValue ~ maxValue
            arr[i] = (int) ((maxValue + 1)*Math.random()) - (int)(maxValue*Math.random());
        }
        return arr;
    }


    /**
     * 利用计算机进行排序
     * @date 2021-03-09 17:57:16
     */
    private static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    /**
     * 复制出一个完全一样的数组
     * @date 2021-03-09 18:04:51
     */
    private static int[] copyArray(int[] arr){
        int[] res = new int[arr.length];
        System.arraycopy(arr, 0, res, 0, arr.length);
        return res;
    }

    /**
     * 判断两个数组是否相等
     * @date 2021-03-09 18:17:41
     */
    private static boolean isEqual(int[] arr1,int[] arr2){

        boolean flag = (null == arr1 && null != arr2) || (null != arr1 && null == arr2);
        if (flag){
            return false;
        }

        if (null == arr1 && null == arr2){
            return true;
        }


        if (arr1.length != arr2.length){
            return false;
        }


        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]){
                return false;
            }
        }
        return true;

    }
}
