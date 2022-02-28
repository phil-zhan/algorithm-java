package org.study.system.class06;

import java.util.Arrays;

/**
 * @author phil
 * @date 2022/1/7 9:41
 */
public class MainTest01 {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6};
        new Heap(arr.length).heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }


    public static class Heap {
        public int[] heapArr;
        public int size;
        public int cap;

        public Heap(int cap) {
            this.cap = cap;
            heapArr = new int[cap];
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == cap;
        }


        public void heapSort(int[] arr) {
            if (null == arr || arr.length == 0) {
                return;
            }
            for (int i = arr.length - 1; i >= 0; i--) {
                heapDown(arr, i, arr.length);
            }
            int heapSize = arr.length;

            // 将最大值放到最后，减小 heapSize
            swap(arr, 0, --heapSize);
            // O(N*logN)
            // O(N)
            while (heapSize > 0) {

                // 重新将 0 位置的数 下沉，调整为大跟堆
                // O(logN)
                heapDown(arr, 0, heapSize);

                // 将最大值放到最后，减小 heapSize
                // O(1)
                swap(arr, 0, --heapSize);
            }
        }

        public int pop() {
            if (isEmpty()) {
                throw new HeapNullException(502, "堆空异常");
            }
            int res = heapArr[0];
            swap(heapArr, 0, --size);
            heapDown(heapArr, 0, size);

            return res;
        }

        public void push(int value) {
            if (isFull()) {
                throw new HeapFullException(501, "堆满异常");
            }
            heapArr[size] = value;
            heapUp(heapArr, size++);
        }

        private void heapUp(int[] arr, int index) {

            while (arr[index] > arr[(index - 1) / 2]) {
                swap(arr, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapDown(int[] arr, int index, int size) {
            int leftIndex = index * 2 + 1;
            while (leftIndex < size) {
                int largest = arr[index] > arr[leftIndex] ? index : leftIndex;
                largest = (leftIndex + 1) < size && arr[leftIndex + 1] > arr[largest] ? (leftIndex + 1) : largest;
                if (largest == index) {
                    break;
                }

                swap(arr, index, largest);
                index = largest;
                leftIndex = index * 2 + 1;
            }
        }

        private void swap(int[] arr, int i1, int i2) {
            if (i1 == i2) {
                return;
            }
            arr[i1] = arr[i1] ^ arr[i2];
            arr[i2] = arr[i1] ^ arr[i2];
            arr[i1] = arr[i1] ^ arr[i2];
        }
    }

}
