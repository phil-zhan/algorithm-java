package org.study.coding.class01;

public class MainTest01 {


    public int maxPoint2(int[] arr,int len){
        int left = 0;
        int right = 0;

        int max = 0;
        while (left <= right){
            while (right < arr.length && arr[right] - arr[left] <= len){
                right++;
            }
            max = Math.max(max,right-(left++));
        }

        return max;
    }

    public static void main(String[] args) {
        int ans = new MainTest01().maxPoint2(new int[]{14, 21, 68, 86, 145, 190, 222, 336, 339, 341, 345, 379, 408, 435, 475, 496, 637, 650, 758, 771, 822, 855, 863, 912, 927, 960, 962, 995},639);
        System.out.println(ans);
    }
}
