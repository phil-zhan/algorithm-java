package org.study.system.class02;

public class MainTest01 {

    public void swap(int[] arr,int i1,int i2){
        if (arr[i1] == arr[i2]){
            return;
        }

        arr[i1] = arr[i1] ^ arr[i2];
        arr[i2] = arr[i1] ^ arr[i2];
        arr[i1] = arr[i1] ^ arr[i2];
    }


    public void swap1(int[] arr,int i1,int i2){
        int temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = arr[i1];
    }
}
