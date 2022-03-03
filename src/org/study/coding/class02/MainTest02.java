package org.study.coding.class02;

/**
 * @author phil
 * @since 2022-0303 20:03:48
 */
public class MainTest02 {

    /**
     * 购买 m 瓶可乐
     * 每瓶的价格是x元
     * 拥有 10元的a张
     * 拥有 50元的b张
     * 拥有 100元的c张
     *
     * @since 2022-03-03 08:04:50
     */
    public int putTimes(int m, int a, int b, int c, int x) {


        int[] money = new int[]{100, 50, 10};
        int[] nums = new int[]{c, b, a};

        int putTimes = 0;
        int preNum = 0;
        int preMoney = 0;

        for (int i = 0; i < money.length && m != 0; i++) {
            int firstNum = (x - preMoney + money[i] - 1) / money[i];
            if (nums[i] >= firstNum) {
                giveRest(money, nums, i + 1, preMoney + money[i] * firstNum - x, 1);
                nums[i] -= firstNum ;
                putTimes += firstNum + preNum;
                m--;
            } else {
                preMoney += money[i] * nums[i];
                preNum += nums[i];
                continue;
            }


            int curMoneyBuyOneNum = (x + money[i] - 1) / money[i];
            int curBuyColas = Math.min(m, nums[i] / curMoneyBuyOneNum);

            giveRest(money, nums, i + 1, curMoneyBuyOneNum * money[i] - x, curBuyColas);
            putTimes += curBuyColas * curMoneyBuyOneNum;

            m -= curBuyColas;

            nums[i] -= curBuyColas * curMoneyBuyOneNum;

            preMoney = nums[i] * money[i];
            preNum = nums[i];
        }


        return m == 0 ? putTimes : -1;
    }

    public void giveRest(int[] money, int[] nums, int index, int oneTimeRest, int times) {
        for (; index < money.length; index++) {
            nums[index] += (oneTimeRest / money[index]) * times;

            oneTimeRest += oneTimeRest % money[index] ;
        }
    }


    public static void main(String[] args) {
        int testTime = 1000;
        int zhangMax = 10;
        int colaMax = 10;
        int priceMax = 20;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int m = (int) (Math.random() * colaMax);
            int a = (int) (Math.random() * zhangMax);
            int b = (int) (Math.random() * zhangMax);
            int c = (int) (Math.random() * zhangMax);
            int x = ((int) (Math.random() * priceMax) + 1) * 10;
            int ans1 = new MainTest02().putTimes(m, a, b, c, x);
            int ans2 = Code02_Cola.right(m, a, b, c, x);
            if (ans1 != ans2) {
                System.out.println("int m = " + m + ";");
                System.out.println("int a = " + a + ";");
                System.out.println("int b = " + b + ";");
                System.out.println("int c = " + c + ";");
                System.out.println("int x = " + x + ";");
                break;
            }
        }
        System.out.println("test end");
    }

}
