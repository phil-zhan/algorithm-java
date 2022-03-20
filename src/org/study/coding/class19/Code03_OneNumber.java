package org.study.coding.class19;

/**
 * 给定一个正数N，比如N = 13，在纸上把所有数都列出来如下：
 * 1 2 3 4 5 6 7 8 9 10 11 12 13
 * 可以数出1这个字符出现了6次，给定一个正数N，如果把1~N都列出来，返回1这个字符出现的多少次
 * <p>
 * 解法：
 * 1）：暴力解。遍历，一个一个数
 * 2）：假设要求12631。也就是[1...12631]的范围上，包含了多少个1
 * 我们先求[2632...12631] 这个范围上有多少个1.【2632.是去掉最高位，再加1来的】.【不递归，直接算出来】
 * 再考虑[1...2631]范围上有多少个1【范围是从1开始的。调递归】
 * 同样也是先考虑[632...2631]范围上有多少个1【不递归，硬算】
 * 再考虑[1...631]上有多少个1
 * ...
 * <p>
 * 过程中，将计算出的结果累加，就是我们要的数量
 * 我们在求[2632...12631]这种范围的时候，考虑的是最高位会产生几个1，次高位会产生几个1...个位上产生几个1。最后累加起来
 * 就此范围来看
 * 万位上：10000...19999 总共贡献了10000个1
 * 千位上
 * <p>
 * <p>
 * 也就是说。
 * 如果所给的数num=abcde的话
 * 我们会将其分为 [1...bcde] 和 [bcde+1 ...abcde]
 * <p>
 * <p>
 * <p>
 * 对于 [bcde+1 ...abcde] 而言
 * <p>
 * 最高位：
 * 如果 a=1的话。最高位贡献的1的个数就是 bcde+1 个
 * 如果 a!=1的话。最高位能贡献的1的个数为 10^(k-1) 【k是位数】
 * <p>
 * 非最高位。先将范围分组。如  5364. 我们先求的是 [365...5364]
 * 分成
 * [365...1364]
 * [1365...2364]
 * [2365...3364]
 * [3365...4364]
 * [4365...5364]
 * <p>
 * <p>
 * 次高位：
 * 当三高位到个位限定之后【都在0...9范围内变化】。又加上最高位的限定（一组内）。在指定范围上，只能找到一个次高位是1的数
 * 10*10*10 【除去最高位和次高位。剩余多少位，就有多少个10相乘】
 * <p>
 * 三高位：
 * 最高位只有一个。次高位到个位。都能随意变。如果固定三高位是1的话。次高位到个位【除了三高位】。都能有10中选择
 * 10*10*10 【除去最高位和三高位。剩余多少位，就有多少个10相乘】
 * <p>
 * ........
 * <p>
 * 个位：最高位只有一位，除了最高位。个位如果固定是1的话。次高位到十位。都能随意变化。变化范围都是0...9
 * 10*10*10 【除去最高位和个位。剩余多少位，就有多少个10相乘】
 *
 * <p>也就是除了最高位。其他每一个都是 10^(k-2) 次方 【k是位数】</p>
 * 最终非最高位的1的个数。就是  组数*10^(k-2)
 * <p>
 * <p>
 * 本题属于数位DP。不是很高频。遇到的题掌握就行
 *
 * @since 2022-03-20 03:11:43
 */
public class Code03_OneNumber {

    /**
     * 暴力解
     *
     * @since 2022-03-20 05:16:56
     */
    public static int solution1(int num) {
        if (num < 1) {
            return 0;
        }
        int count = 0;
        for (int i = 1; i != num + 1; i++) {
            count += get1Nums(i);
        }
        return count;
    }

    public static int get1Nums(int num) {
        int res = 0;
        while (num != 0) {
            if (num % 10 == 1) {
                res++;
            }
            num /= 10;
        }
        return res;
    }


    /**
     * 1 ~ num 这个范围上，画了几道1
     *
     * 时间复杂度：O(logN)^2  log以10为底的N   N是位数
     *
     * @since 2022-03-20 03:11:52
     */
    public static int solution2(int num) {
        if (num < 1) {
            return 0;
        }
        // num -> 13625
        // len = 5位数
        int len = getLenOfNum(num);
        if (len == 1) {
            return 1;
        }
        // num  13625
        // tmp1 10000
        //
        // num  7872328738273
        // tmp1 1000000000000
        int tmp1 = powerBaseOf10(len - 1);
        // num最高位 num / tmp1
        int first = num / tmp1;
        // 最高1 N % tmp1 + 1
        // 最高位first tmp1
        int firstOneNum = first == 1 ? num % tmp1 + 1 : tmp1;
        // 除去最高位之外，剩下1的数量
        // 最高位1 10(k-2次方) * (k-1) * 1
        // 最高位first 10(k-2次方) * (k-1) * first
        int otherOneNum = first * (len - 1) * (tmp1 / 10);
        return firstOneNum + otherOneNum + solution2(num % tmp1);
    }

    /**
     * 获取num的位数
     *
     * @since 2022-03-20 05:16:20
     */
    public static int getLenOfNum(int num) {
        int len = 0;
        while (num != 0) {
            len++;
            num /= 10;
        }
        return len;
    }

    public static int powerBaseOf10(int base) {
        return (int) Math.pow(10, base);
    }

    public static void main(String[] args) {
        int num = 50000000;
        long start1 = System.currentTimeMillis();
        System.out.println(solution1(num));
        long end1 = System.currentTimeMillis();
        System.out.println("cost time: " + (end1 - start1) + " ms");

        long start2 = System.currentTimeMillis();
        System.out.println(solution2(num));
        long end2 = System.currentTimeMillis();
        System.out.println("cost time: " + (end2 - start2) + " ms");

    }
}
