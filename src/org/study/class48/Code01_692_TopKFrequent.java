package org.study.class48;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/top-k-frequent-words/
 *
 * @author phil
 * @date 2022/2/14 9:15
 */
public class Code01_692_TopKFrequent {

    public static void main(String[] args) {
        List<String> strings = new Code01_692_TopKFrequent().topKFrequent(new String[]{"i", "love", "leetcode", "i", "love", "coding"}, 2);
        System.out.println(strings);
    }

    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> cnt = new HashMap<>();
        for (String word : words) {
            cnt.put(word, cnt.getOrDefault(word, 0) + 1);
        }


        HeapGreater<Map.Entry<String, Integer>> heap = new HeapGreater<>(((o1, o2) -> o1.getValue().equals(o2.getValue()) ? o1.getKey().compareTo(o2.getKey()) : o2.getValue() - o1.getValue()));

        for (Map.Entry<String, Integer> entry : cnt.entrySet()) {
            heap.push(entry);
        }

        List<String> resList = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            resList.add(heap.pop().getKey());
        }
        return resList;
    }

    public List<String> topKFrequent2(String[] words, int k) {
        Map<String, Integer> cnt = new HashMap<>();
        for (String word : words) {
            cnt.put(word, cnt.getOrDefault(word, 0) + 1);
        }


        PriorityQueue<Map.Entry<String, Integer>> heap = new PriorityQueue<>(((o1, o2) -> o1.getValue().equals(o2.getValue()) ? o1.getKey().compareTo(o2.getKey()) : o2.getValue() - o1.getValue()));

        heap.addAll(cnt.entrySet());

        List<String> resList = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            resList.add(Objects.requireNonNull(heap.poll()).getKey());
        }
        return resList;
    }

    static class HeapGreater<T> {
        private final Map<T, Integer> indexMap;
        private final ArrayList<T> heap;
        private int size;
        private final Comparator<? super T> comparator;


        public HeapGreater(Comparator<T> comparator) {
            this.indexMap = new HashMap<>();
            this.heap = new ArrayList<>();
            this.comparator = comparator;
        }

        public void push(T obj) {
            heap.add(obj);
            indexMap.put(obj, size);
            heapUp(size++);
        }

        public void remove(T obj) {
            swap(indexMap.get(obj), size - 1);
            heap.remove(--size);
            resign(indexMap.get(obj));
            indexMap.remove(obj);
        }

        public void resign(int index) {
            heapUp(index);
            heapDown(index);
        }

        public T pop() {
            if (size > 0) {
                T ans = heap.get(0);
                swap(0, this.size - 1);
                indexMap.remove(ans);
                heap.remove(--size);
                heapDown(0);
                return ans;
            }
            return null;
        }


        public void heapUp(int index) {
            while (comparator.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        public void heapDown(int index) {
            int left = (index << 1) + 1;
            while (left < this.size) {
                int smallest = comparator.compare(heap.get(index), heap.get(left)) <= 0 ? index : left;
                smallest = left + 1 < this.size && comparator.compare(heap.get(smallest), heap.get(left + 1)) > 0 ? left + 1 : smallest;

                if (index == smallest) {
                    break;
                }
                swap(index, smallest);
                index = smallest;
                left = (index << 1) + 1;
            }
        }

        public void swap(int i1, int i2) {
            if (i1 == i2) {
                return;
            }
            T t1 = heap.get(i1);
            T t2 = heap.get(i2);
            // 维护indexMap
            indexMap.put(t1, i2);
            indexMap.put(t2, i1);

            heap.set(i1, t2);
            heap.set(i2, t1);
        }
    }
}
