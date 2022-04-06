package org.study.coding.class08;

/**
 * @author phil
 * @date 2022-04-05 09:22:32
 */
public class MainTest02 {

    public static void main(String[] args) {
        int[] height = new int[]{1,8,6,2,5,4,8,3,7};
        System.out.println(maxArea2(height));
        System.out.println(Code02_ContainerWithMostWater.maxArea1(height));
    }

    public static int maxArea2(int[] height){
        int left = 0;
        int right = height.length-1;
        int max = 0;

        while (left<right){
            max = Math.max(max,(right-left)*(Math.min(height[left],height[right])));
            if (height[left] < height[right]){
                left++;
            }else{
                right--;
            }
        }

        return max;
    }
}
