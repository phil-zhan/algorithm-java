package org.study.class18;

import java.util.Arrays;

/**
 * @author phil
 * @date 2021/7/26 14:24
 */
public class MainTest02 {

    private static int win1(int[] arr){
        if (null == arr || arr.length == 0) {
            return 0;
        }

        int first = first1(arr,0,arr.length-1);
        int second = second1(arr,0,arr.length-1);

        return Math.max(first,second);
    }

    private static int first1(int[] arr,int left,int right){
        if (left == right) {
            return arr[left];
        }

        int p1 = arr[left] + second1(arr,left+1,right);
        int p2 = arr[right] + second1(arr,left,right-1);

        return Math.max(p1,p2);
    }
    private static int second1(int[] arr,int left,int right){
        if (left == right) {
            return arr[left];
        }

        int p1 = first1(arr,left+1,right);
        int p2 = first1(arr,left,right-1);

        return Math.min(p1,p2);
    }


    private static int win2(int[] arr){
        if (null == arr || arr.length == 0) {
            return 0;
        }

        int length = arr.length;
        int[][] firstmap = new int[length][length];
        int[][] secondmap = new int[arr.length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                firstmap[i][j] = -1;
                secondmap[i][j] = -1;
            }
        }

        int first = first2(arr,0,arr.length-1,firstmap,secondmap);
        int second = second2(arr,0,arr.length-1,firstmap,secondmap);

        return Math.max(first,second);

    }

    private static int first2(int[] arr, int left, int right, int[][] firstmap, int[][] secondmap) {
        if (firstmap[left][right] != -1) {
            return firstmap[left][right];
        }
        int answer;
        if(left == right){
            answer = arr[left];
        }else{
            int p1 = arr[left] + second2(arr,left +1 , right ,firstmap,secondmap);
            int p2 = arr[right] + second2(arr, left , right -1 ,firstmap,secondmap);
            answer = Math.max(p1,p2);
        }

        firstmap[left][right] = answer;


        return answer;
    }


    private static int second2(int[] arr, int left, int right, int[][] firstmap, int[][] secondmap) {

        if (secondmap[left][right] != -1) {
            return secondmap[left][right];
        }

        int answer;
        if (left == right) {
            answer = arr[left];
        }else{
            int p1 = first2(arr,left+1,right,firstmap,secondmap);
            int p2 = first2(arr,left,right-1,firstmap,secondmap);
            answer = Math.min(p1,p2);
        }
        secondmap[left][right] = answer;

        return answer;
    }

    private static int win3(int[] arr){
        if (null == arr || arr.length==0) {
            return 0;
        }

        int length = arr.length;
        int[][] firstmap = new int[length][length];
        int[][] secondmap = new int[length][length];

        for (int i = 0; i < length; i++) {
            firstmap[i][i] = arr[i];
        }

        for (int startCol = 1; startCol < length; startCol++) {
            int L=0;
            int R = startCol;

            while (R < length){
                firstmap[L][R] = Math.max(arr[L] + secondmap[L+1][R],arr[R]+secondmap[L][R-1]);
                secondmap[L][R] = Math.min(firstmap[L+1][R],firstmap[L][R-1]);
                L++;
                R++;
            }
        }


        return Math.max(firstmap[0][length-1],secondmap[0][length-1]);
    }

    public static void main(String[] args) {
        int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
        System.out.println(win1(arr));
        System.out.println(win2(arr));
        System.out.println(win3(arr));

    }

}
