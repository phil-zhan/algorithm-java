package org.study.system.class38;

/**
 * @author phil
 * @date 2021/10/15 13:56
 */
public class MainTest01 {

    public static int minBags(int apples) {
        if (apples < 0) {
            return -1;
        }
        int bag8 = apples >> 3;
        int rest = apples - (bag8 << 3);
        while (bag8 >= 0) {
            if (rest % 6 == 0) {
                return bag8 + (rest / 6);
            } else {
                bag8--;
                rest += 8;
            }

        }
        return -1;
    }

    public static int minBagAwesome(int apple) {
        if ((apple & 1) != 0) { // 如果是奇数，返回-1
            return -1;
        }
        if (apple < 18) {
            return apple == 0 ? 0 : (apple == 6 || apple == 8) ? 1
                    : (apple == 12 || apple == 14 || apple == 16) ? 2 : -1;
        }
        return (apple - 18) / 8 + 3;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            System.out.println(minBagAwesome(i)==minBags(i));
        }
    }
}
