package org.study;

import org.study.class05.Code003_QuickSort_2;

import java.util.Arrays;

/**
 * @author phil
 * @date 2022/1/7 14:26
 */
public class MainTest {

    // 采用插入排序完成
    public int[] sortArray(int[] nums) {

        for (int i = 1; i < nums.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (nums[j + 1] < nums[j]) {
                    swap(nums, j + 1, j);
                }else{
                    break;
                }
            }
        }

        return nums;
    }


    public void swap(int[] arr, int i1, int i2) {
        if (i1 == i2) {
            return;
        }
        arr[i1] = arr[i1] ^ arr[i2];
        arr[i2] = arr[i1] ^ arr[i2];
        arr[i1] = arr[i1] ^ arr[i2];
    }


    public static void main(String[] args) {
        int[] arr = new int[]{4, 5, 3, 2, 1};

        long start1 = System.currentTimeMillis();
        new MainTest().sortArray(arr);
        System.out.println(System.currentTimeMillis() - start1);


        System.out.println(Arrays.toString(arr));
    }

}
