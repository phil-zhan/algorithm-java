package org.study.coding.class39;

public class MainTest01 {
    public static void main(String[] args) {
        System.out.println(Code01_01AddValue.max1("111"));
        System.out.println(new MainTest01().max1("111"));
    }

    public int max1(String s){

        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int[] arr = new int[str.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = str[i] == '0' ? 0 : 1;
        }
        return process1(arr, 0, 0, 0);
    }


    public static int process1(int[] arr, int index, int lastNum, int baseValue) {
        if (index == arr.length) {
            return 0;
        }
        int curValue = lastNum == arr[index] ? (baseValue + 1) : 1;
        // 当前index位置的字符保留
        int next1 = process1(arr, index + 1, arr[index], curValue);
        // 当前index位置的字符不保留
        int next2 = process1(arr, index + 1, lastNum, baseValue);
        return Math.max(curValue + next1, next2);
    }

}
