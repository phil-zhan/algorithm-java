package org.study.class17;

/**
 * 汉诺塔
 *
 * @author phil
 * @date 2021/7/15 9:09
 */
public class MainTest02 {

    private static void leftToRight(int n){
        if (n == 1) {
            System.out.println("move "+n+" l ====================> r");
            return;
        }
        leftToMin(n-1);
        System.out.println("move "+ n + " l ====================> r");
        minToRight(n-1);
    }

    private static void leftToMin(int n){
        if (n == 1) {
            System.out.println("move "+n+" l ====================> m");
            return;
        }

        leftToRight(n-1);
        System.out.println("move "+n+" l ====================> m");
        rightToMin(n-1);
    }

    private static void minToRight(int n){
        if (n == 1) {
            System.out.println("move "+n+" m ====================> r");
            return;
        }

        minToLeft(n-1);
        System.out.println("move "+n+" m ====================> r");

        leftToRight(n-1);
    }

    private static void rightToMin(int n){
        if (n == 1) {
            System.out.println("move "+n+" r ====================> m");
            return;
        }
        rightToLeft(n-1);
        System.out.println("move "+n+" r ====================> m");
        leftToMin(n-1);
    }

    private static void minToLeft(int n){
        if (n == 1) {
            System.out.println("move "+n+" m ====================> l");
            return;
        }

        minToRight(n-1);
        System.out.println("move "+n+" m ====================> l");
        rightToLeft(n-1);
    }

    private static void rightToLeft(int n){
        if (n == 1) {
            System.out.println("move "+n+" r ====================> l");
            return;
        }

        rightToMin(n-1);
        System.out.println("move "+n+" r ====================> l");
        minToLeft(n-1);
    }

    /**
     * 汉诺塔 1 入口
     * @date 2021-07-15 09:39:43
     */
    private static void hanoi1(int n){
        if (n > 0) {
            leftToRight(n);
        }
    }


    private static void hanoi2(int n){
        if (n > 0) {
            process(n,"l","r","m");
        }
    }

    private static void process(int n,String from,String to,String other){
        if (n == 1) {
            System.out.println("move "+n+" "+from+" ====================> "+to);
            return;
        }

        process(n-1,from,other,to);
        System.out.println("move "+n+" "+from+" ====================> "+to);
        process(n-1,other,to,from);

    }
    public static void main(String[] args) {
        int n = 3;
        System.out.println("==============================="+"\t解决方案1\t"+"===============================");
        hanoi1(n);
        System.out.println("==============================="+"\t解决方案2\t"+"===============================");
        hanoi2(n);
    }
}
