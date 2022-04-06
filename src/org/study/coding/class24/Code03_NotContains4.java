package org.study.coding.class24;

// 里程表不能含有4，给定一个数num，返回他是里程表里的第几个

/**
 * 3. 正常的里程表会依次显示自然数表示里程
 * 吉祥的里程表会忽略含有4的数字而跳到下一个完全不含有4的数
 * 正常：1 2 3 4 5 6 7 8  9 10 11 12 13 14 15
 * 吉祥：1 2 3 5 6 7 8 9 10 11 12 13 15 16 17 ... 38 39 50 51 52 53 55
 * 给定一个吉祥里程表的数字num(当然这个数字中不含有4)
 * 返回这个数字代表的真实里程
 * <p>
 * 解法：
 * 假设吉祥里程为 56273
 * 考虑吉祥里程表有1位数字，有多少个数 【0,1,2,3,5,6,7,8,9】 共9^1个
 * 考虑吉祥里程表有2位数字，有多少个数 【<=2位的。第一位数字有9个可选，第二维也有9个可选。总共9^2个】
 * 考虑吉祥里程表有3位数字，有多少个数 【<=3位的。第一位数字有9个可选，第二位也有9个可选，第三位也有9个可选。总共9^3个】
 * 考虑吉祥里程表有4位数字，有多少个数 【<=4位的。第一位数字有9个可选，第二位也有9个可选，第三位也有9个可选，第四位也有9个可选。总共9^4个】
 * ......
 * <p>
 * 毫无疑问，这些数字都比 562739 小
 * 实现了类似于前缀和的效果。需要的时候，直接减去前面的就好
 * <p>
 * 将位数和有效的里程个数做成一个数组arr[i]：表示<=i位的有效数字是arr[i]。也就是9^i
 * <p>
 * 拿到一个实际的数字后，算出它是真是里程表的第几个，也就是真是里程表的数字
 * <p>
 * 1）小于6位的情况
 * 562739 是6位数。假设arr[i] = a.那么当前数的实际里程不会在a之前
 * <p>
 * 2）等于6位的，但开头小的
 * 算出必须 1 开头的6位有多少个。第一位是确定的，后面的5位都可以随便选。也就是9^5 .在arr数组里面也就是 arr[5]
 * 算出必须 2 开头的6位有多少个。第一位是确定的，后面的5位都可以随便选。也就是9^5 .在arr数组里面也就是 arr[5]
 * 算出必须 3 开头的6位有多少个。第一位是确定的，后面的5位都可以随便选。也就是9^5 .在arr数组里面也就是 arr[5]
 * 没有4开头
 * 这些数字都在当前的真是里程之前。这些数字个数之后是 3*arr[5]
 * <p>
 * 3)等于6位的，但开头相等的
 * 必须以5开头的情况下，后面的数字要小于67329的有多少个
 *
 * @since 2022-04-04 15:55:50
 */
public class Code03_NotContains4 {

    /**
     * 暴力解
     * num中一定没有4这个数字
     *
     * @since 2022-04-04 15:56:32
     */
    public static long notContains4Nums1(long num) {
        long count = 0;
        for (long i = 1; i <= num; i++) {
            if (isNot4(i)) {
                count++;
            }
        }
        return count;
    }

    public static boolean isNot4(long num) {
        while (num != 0) {
            if (num % 10 == 4) {
                return false;
            }
            num /= 10;
        }
        return true;
    }


    /**
     * arr[1] : 比1位数还少1位，有几个数(数字里可以包含0但是不可以包含4)？0个
     * arr[2] : 比2位数还少1位，有几个数(数字里可以包含0但是不可以包含4)？9个
     * 1 -> 0 1 2 3 - 5 6 7 8 9 = 9
     * arr[3] :比3位数还少1位，有几个数(数字里可以包含0但是不可以包含4)？81个
     * 1 : 0 (0 1 2 3 - 5 6 7 8 9) = 9
     * 2 :
     * 1 (0 1 2 3 - 5 6 7 8 9) = 9
     * 2 (0 1 2 3 - 5 6 7 8 9) = 9
     * 3 (0 1 2 3 - 5 6 7 8 9) = 9
     * 5 (0 1 2 3 - 5 6 7 8 9) = 9
     * ...
     * 9 (0 1 2 3 - 5 6 7 8 9) = 9
     *
     * @since 2022-04-04 15:56:55
     */
    public static long[] arr = {0L, 1L, 9L, 81L, 729L, 6561L, 59049L, 531441L, 4782969L, 43046721L, 387420489L,
            3486784401L, 31381059609L, 282429536481L, 2541865828329L, 22876792454961L, 205891132094649L,
            1853020188851841L, 16677181699666569L, 150094635296999121L, 1350851717672992089L};

    /**
     * 最优解：O(log[10,num]) log以10为底，num的复杂度
     * num中一定没有4这个数字
     *
     *
     * @since 2022-04-04 15:57:03
     */
    public static long notContains4Nums2(long num) {
        if (num <= 0) {
            return 0;
        }
        // num的十进制位数，len
        int len = decimalLength(num);
        // 35621
        // 10000
        long offset = offset(len);

        // 第一位数字
        long first = num / offset;

        // 情况1
        // arr[len] - 1 是情况1。需要减去数字0。数字0在情况三里面考虑
        // 正常我们需要拿的是 arr[len-1] - 1 。我们在数组arr的前面加了一个0.就不用减1了，直接拿。拿len，就表示小于len位的有多少

        // 情况2
        // 这里的first必须小于第一位，所以至少需要减1
        // (first - (first < 4 ? 1 : 2)) * arr[len]

        // 情况3
        // process(num % offset, offset / 10, len - 1)
        // offset 是一个和目标的位数一样的最小数。如num是 76217 ，那么它对应的 offset 就是10000
        // arr[len] 表示的是小于 len 位的有 arr[len] 个
        return arr[len] - 1 + (first - (first < 4 ? 1 : 2)) * arr[len] + process(num % offset, offset / 10, len - 1);
    }


    /**
     * num之前，开头固定！。传进来的时候，第一维数字就去掉了
     * 在算0的情况下，num是第几个真是的里程数字
     * num 76217
     * 10000
     * 6217
     * 1000
     * len
     *
     * @since 2022-04-04 15:57:30
     */
    public static long process(long num, long offset, int len) {
        if (len == 0) {
            return 1;
        }
        long first = num / offset;

        // 这里只有情况2和情况三。
        // 这里的first必须 小于等于 第一位。不需要减1
        // (first < 4 ? first : (first - 1)) * arr[len]
        return (first < 4 ? first : (first - 1)) * arr[len] + process(num % offset, offset / 10, len - 1);
    }

    /**
     * 求num的十进制位数
     * num = 7653210
     * 7
     *
     * @since 2022-04-04 15:57:38
     */
    public static int decimalLength(long num) {
        int len = 0;
        while (num != 0) {
            len++;
            num /= 10;
        }
        return len;
    }


    /**
     * 给定一个长度n，就生成第一位是1，后面的n-1位都是0的数
     * len = 6
     * 100000
     * len = 4
     * 1000
     * 目的是方便得到第一维数字。
     *
     * 如：num=3452168
     * 生成：1000000
     * 相除等于3就是第一维数字
     *
     * @since 2022-04-04 15:58:01
     */
    public static long offset(int len) {
        long offset = 1;
        for (int i = 1; i < len; i++) {
            offset *= 10L;
        }
        return offset;
    }


    /**
     * 讲完之后想到了课上同学的留言
     * 突然意识到，这道题的本质是一个9进制的数转成10进制的数
     * 不过好在课上的解法有实际意义，就是这种求解的方式，很多题目都这么弄
     * 还有课上的时间复杂度和"9进制的数转成10进制的数"的做法，时间复杂度都是O(lg N)
     * 不过"9进制的数转成10进制的数"毫无疑问是最优解
     *
     * @since 2022-04-04 15:58:16
     */
    public static long notContains4Nums3(long num) {
        if (num <= 0) {
            return 0;
        }
        long ans = 0;
        for (long base = 1, cur = 0; num != 0; num /= 10, base *= 9) {
            cur = num % 10;
            ans += (cur < 4 ? cur : cur - 1) * base;
        }
        return ans;
    }

    public static void main(String[] args) {
        long max = 88888888L;
        System.out.println("功能测试开始，验证 0 ~ " + max + " 以内所有的结果");
        for (long i = 0; i <= max; i++) {
            // 测试的时候，输入的数字i里不能含有4，这是题目的规定！
            if (isNot4(i) && notContains4Nums2(i) != notContains4Nums3(i)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("如果没有打印Oops说明验证通过");

        long num = 888888888L;
        long start;
        long end;
        System.out.println("性能测试开始，计算 num = " + num + " 的答案");

        start = System.currentTimeMillis();
        long ans1 = notContains4Nums1(num);
        end = System.currentTimeMillis();
        System.out.println("方法二答案 : " + ans1 + ", 运行时间 : " + (end - start) + " ms");

        start = System.currentTimeMillis();
        long ans2 = notContains4Nums2(num);
        end = System.currentTimeMillis();
        System.out.println("方法二答案 : " + ans2 + ", 运行时间 : " + (end - start) + " ms");

        start = System.currentTimeMillis();
        long ans3 = notContains4Nums3(num);
        end = System.currentTimeMillis();
        System.out.println("方法三答案 : " + ans3 + ", 运行时间 : " + (end - start) + " ms");

    }

}
