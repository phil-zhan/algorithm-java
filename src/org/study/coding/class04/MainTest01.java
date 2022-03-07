package org.study.coding.class04;

import java.util.ArrayList;
import java.util.HashMap;

public class MainTest01 {

    public static void main(String[] args) {
        int len = 300;
        int value = 20;
        int testTimes = 1000;
        int queryTimes = 1000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = Code01_QueryHobby.generateRandomArray(len, value);
            int N = arr.length;
            QueryBox box1 = new QueryBox(arr);
            Code01_QueryHobby.QueryBox2 box2 = new Code01_QueryHobby.QueryBox2(arr);
            for (int j = 0; j < queryTimes; j++) {
                int a = (int) (Math.random() * N);
                int b = (int) (Math.random() * N);
                int L = Math.min(a, b);
                int R = Math.max(a, b);
                int v = (int) (Math.random() * value) + 1;
                if (box1.query(L, R, v) != box2.query(L, R, v)) {
                    System.out.println("Oops!");
                }
            }
        }
        System.out.println("test end");
    }

    public static class QueryBox {
        public HashMap<Integer, ArrayList<Integer>> hashMap;

        public QueryBox(int[] nums) {
            this.hashMap = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                if (!hashMap.containsKey(nums[i])) {
                    hashMap.put(nums[i], new ArrayList<>());
                }
                hashMap.get(nums[i]).add(i);
            }
        }

        public int query(int left, int right, int value) {
            if (!hashMap.containsKey(value)) {
                return 0;
            }

            return countLess(hashMap.get(value), right + 1) - countLess(hashMap.get(value), left);
        }

        private int countLess(ArrayList<Integer> list, int limit) {
            int left = 0;
            int right = list.size() - 1;
            int mostRight = -1;
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                if (list.get(mid) < limit) {
                    mostRight = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            return mostRight + 1;
        }
    }
}
