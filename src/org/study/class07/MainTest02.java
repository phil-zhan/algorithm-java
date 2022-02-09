package org.study.class07;

import java.util.*;

/**
 * @author phil
 * @date 2022/1/14 15:48
 */
public class MainTest02 {

    public static class HeapGreater<T> {
        public List<T> list;

        public Map<T, Integer> indexMap;

        public int heapSize;

        public Comparator<T> comparator;

        public HeapGreater(Comparator<T> comparator) {
            this.list = new ArrayList<>();
            this.indexMap = new HashMap<>();
            this.heapSize = 0;
            this.comparator = comparator;
        }

        public boolean isEmpty() {
            return 0 == heapSize;
        }

        public int size(){
            return heapSize;
        }

        public boolean contains(T obj){
            return indexMap.containsKey(obj);
        }

        public T peek(){
            return list.get(0);
        }

        public void push(T obj){
            list.add(obj);
            indexMap.put(obj,heapSize);
            heapUp(heapSize++);
        }

        public T pop(){
            T res = list.get(0);
            swap(0,heapSize-1);
            list.remove(heapSize);
            indexMap.remove(res);
            heapDown(0);
            return res;
        }

        public void remove(T obj){
            T replace = list.get(heapSize-1);
            int index = indexMap.get(obj);
            indexMap.remove(obj);
            list.remove(--heapSize);

            if (obj != replace) {
                list.set(index, replace);
                indexMap.put(replace, index);
                resign(replace);
            }
        }

        // 请返回堆上的所有元素
        public List<T> getAllElements() {
            return new ArrayList<>(list);
        }

        private void resign(T obj) {
            heapUp(indexMap.get(obj));
            heapDown(indexMap.get(obj));
        }

        private void heapUp(int index) {
            while (index > 0 && comparator.compare(list.get(index), list.get((index - 1) >> 1)) < 0) {
                swap(index, (index - 1) >> 1);
                index = (index - 1) >> 1;
            }
        }

        private void heapDown(int index) {
            int left = index << 1 + 1;
            while (left < heapSize) {
                int largest = left + 1 < heapSize && comparator.compare(list.get(left), list.get(left + 1)) < 0 ? left + 1 : left;
                largest = comparator.compare(list.get(index), list.get(largest)) < 0 ? largest : index;
                if (largest == index) {
                    break;
                }
                swap(index, largest);
                index = largest;
                left = index << 1 + 1;

            }
        }


        private void swap(int i1, int i2) {
            T t1 = list.get(i1);
            T t2 = list.get(i2);
            list.set(i1, t2);
            list.set(i2, t1);
            indexMap.put(t1, i2);
            indexMap.put(t2, i1);
        }
    }
}
