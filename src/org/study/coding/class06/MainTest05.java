package org.study.coding.class06;

/**
 * @author phil
 * @date 2022-03-23 16:10:28
 */
public class MainTest05 {
    public static void main(String[] args) {

        // 后手赢
        printWinner(new int[]{1,2,3,4,5});

        // 先手赢
        printWinner(new int[]{1,2,3,3,1,2});
    }


    private static void printWinner(int[] nums){
        int xor = 0;
        for (int num :nums) {
            xor ^= num;
        }
        if (xor == 0){
            System.out.println("后手赢！");
        }else{
            System.out.println("先手赢！");
        }
    }
}
