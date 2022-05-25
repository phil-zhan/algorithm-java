package org.study.coding.class18;

public class MainTest01 {

    public static int kth(int[] arr) {

        return process(arr, arr.length - 1, 1, 3, 2);
    }

    public static int process(int[] arr, int index, int from, int to, int other) {
        if (index == -1){
            return 0;
        }

        // 0...i-1  from --> other
        // i        from --> to
        // 0...i-1  other -->to
        if (arr[index] == other){
            return -1;
        }

        if (arr[index] == from){

            return process(arr,index-1,from,other,to);
        }else{
            int rest = process(arr,index-1,other,to,from);
            if (rest == -1){
                return -1;
            }
            // n 层汉诺塔的总步数是 2^n-1步
            // index 已经在 to上了
            // 说明前两大步已经完成。再加上最后一步需要的步数就好了

            return (index << 1) + rest;
        }

    }


    public static void main(String[] args) {
        int[] arr = {3, 3, 2, 1};
        System.out.println(kth(arr));
    }
}
