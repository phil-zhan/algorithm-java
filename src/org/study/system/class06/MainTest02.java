package org.study.system.class06;

import java.util.Arrays;

/**
 * @author phil
 * @date 2022/1/7 12:34
 */
public class MainTest02 {
    public int[] sortArray(int[] nums) {
        if (null == nums || nums.length <2) {
            return nums;
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            heapDown(nums, i, nums.length);
        }
        int size = nums.length;
        swap(nums, 0, --size);
        while (size > 0) {
            heapDown(nums, 0, size);
            swap(nums, 0, --size);
        }

        return nums;

    }

    public void heapUp(int[] arr, int index) {
        while (arr[index] > arr[index - 1] / 2) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    public void heapDown(int[] arr, int index, int size) {
        int left = index * 2 + 1;
        while (left < size) {
            int largest = arr[index] > arr[left] ? index : left;
            largest = (left + 1) < size && arr[left + 1] > arr[largest] ? (left + 1) : largest;
            if (index == largest) {
                break;
            }
            swap(arr, index, largest);
            index = largest;
            left = index * 2 + 1;
        }
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
        int[] arr = new int[]{-4, 0, 7, 4, 9, -5, -1, 0, -7, -1};
        new MainTest02().sortArray(arr);
        System.out.println(Arrays.toString(arr));
    }
}
