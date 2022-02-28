package org.study.system.class06;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 堆：也叫优先级队列
 * @author phil
 * @date 2021/6/4 16:28
 */
public class Code02_Heap {

    public static class MyMaxHeap {
        private final int[] heap;
        private final int limit;
        private int heapSize;

        public MyMaxHeap(int limit) {
            heap = new int[limit];
            this.limit = limit;
            heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public boolean isFull() {
            return heapSize == limit;
        }

        public void push(int value) {
            if (heapSize == limit) {
                throw new RuntimeException("heap is full");
            }
            heap[heapSize] = value;
            // value  heapSize
            heapInsert(heap, heapSize++);
        }

        // 用户此时，让你返回最大值，并且在大根堆中，把最大值删掉
        // 剩下的数，依然保持大根堆组织
        public int pop() {
            int ans = heap[0];
            swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return ans;
        }


        // 时间复杂度是 LogN
        // 新加进来的数，现在停在了index位置，请依次往上移动，
        // 移动到0位置，或者干不掉自己的父亲了，停！
        // 形成大顶堆【即父节点要大于子节点】
        private void heapInsert(int[] arr, int index) {
            // [index]    [index-1]/2
            // index == 0
            // (index - 1) / 2 也等于0.也就相当于0位置和0位置比较。不可能出现大于的情况。所以循环也会结束
            // 子节点比父节点大，交换位置。交换之后再去检查父节点是否还比父节点的父节点大
            // 【直到所有父节点都比子节点大，或者index到达0的位置才会结束】
            while (arr[index] > arr[(index - 1) / 2]) {
                swap(arr, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }



        // 时间复杂度是 LogN
        // 从index位置，往下看，不断的下沉
        // 停：较大的孩子都不再比index位置的数大；已经没孩子了
        private void heapify(int[] arr, int index, int heapSize) {
            int left = index * 2 + 1;

            // 如果有左孩子，有没有右孩子，可能有可能没有！
            // 如果左孩子都没有，那右孩子肯定也没有
            while (left < heapSize) {
                // 把较大孩子的下标，给largest
                int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
                largest = arr[largest] > arr[index] ? largest : index;
                if (largest == index) {
                    break;
                }
                // index和较大孩子，要互换
                swap(arr, largest, index);

                // 继续检查交换后的子节点
                index = largest;
                left = index * 2 + 1;
            }
        }

        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

    }

    public static class RightMaxHeap {
        private final int[] arr;
        private final int limit;
        private int size;

        public RightMaxHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("heap is full");
            }
            arr[size++] = value;
        }

        public int pop() {
            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }
            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return ans;
        }

    }


    public static class MyComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }

    }

    public static void main(String[] args) {
        // 小根堆【堆：也叫优先级队列】 【如果是基础类型的话，可以不指定比较器。但是如果是非基础类型的话，不指定比较器会报错】
        // 构造函数里面不传任何参数的话，默认是小跟堆
        // 要弄成打跟堆得话，自己重新定义比较器
        PriorityQueue<Integer> heap = new PriorityQueue<>(new MyComparator());
        heap.add(5);
        heap.add(5);
        heap.add(5);
        heap.add(3);
        //  5 , 3
        // peek 只是取出来，但是不删除
        System.out.println(heap.peek());
        heap.add(7);
        heap.add(0);
        heap.add(7);
        heap.add(0);
        heap.add(7);
        heap.add(0);

        System.out.println(heap.peek());
        while(!heap.isEmpty()) {

            // poll 会依次弹出最大（或最小值）
            System.out.println(heap.poll());
        }







        int value = 1000;
        int limit = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            MyMaxHeap my = new MyMaxHeap(curLimit);
            RightMaxHeap test = new RightMaxHeap(curLimit);
            int curOpTimes = (int) (Math.random() * limit);
            for (int j = 0; j < curOpTimes; j++) {
                if (my.isEmpty() != test.isEmpty()) {
                    System.out.println("Oops!");
                }
                if (my.isFull() != test.isFull()) {
                    System.out.println("Oops!");
                }
                if (my.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    my.push(curValue);
                    test.push(curValue);
                } else if (my.isFull()) {
                    if (my.pop() != test.pop()) {
                        System.out.println("Oops!");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        my.push(curValue);
                        test.push(curValue);
                    } else {
                        if (my.pop() != test.pop()) {
                            System.out.println("Oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");

    }
}
