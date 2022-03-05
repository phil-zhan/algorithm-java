package org.study.coding.class03;

import java.util.HashSet;

/**
 * @author phil
 * @since 2022-0305 10:17:11
 */
public class MainTest02 {

    public int types(String[] strArr){
        HashSet<Integer> hashSet = new HashSet<>();
        for (String s:strArr) {
            char[] str = s.toCharArray();
            int key = 0;
            for (char ch : str) {
                key |= 1 << (ch - 'a');
            }
            hashSet.add(key);
        }
        return hashSet.size();
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strMaxSize = 10;
        int arrMaxSize = 100;
        int testTimes = 500000;
        System.out.println("test begin, test time : " + testTimes);
        for (int i = 0; i < testTimes; i++) {
            String[] arr = Code02_HowManyTypes.getRandomStringArray(possibilities, strMaxSize, arrMaxSize);
            int ans1 = Code02_HowManyTypes.types1(arr);
            int ans2 = new MainTest02().types(arr);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");

    }
}
