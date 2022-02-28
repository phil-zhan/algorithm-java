package org.study.system.class23;

/**
 * @author phil
 * @date 2021/8/19 9:55
 */
public class MainTest03 {

    public static int num1(int n) {
        if (n < 1) {
            return 0;
        }
        int[] records = new int[n];
        return process1(records, 0, n);
    }

    public static int process1(int[] records, int index, int n) {
        if (index == n) {
            return 1;
        }
        int res = 0;
        for (int col = 0; col < n; col++) {
            if (isValid(records, index, col)) {
                records[index] = col;
                res += process1(records, index + 1, n);
            }
        }
        return res;
    }

    private static boolean isValid(int[] records, int index, int col) {
        for (int row = 0; row < index; row++) {
            if (records[row] == col || Math.abs(index - row) == Math.abs(col - records[row])) {
                return false;
            }
        }

        return true;

    }


    // 请不要超过32皇后问题
    public static int num2(int n) {
        if (n < 1 || n > 32) {
            return 0;
        }
        // 如果你是13皇后问题，limit 最右13个1，其他都是0
        // -1:所有位上都是 1
        int limit = n == 32 ? -1 : (1 << n) - 1;
        return process2(limit, 0, 0, 0);
    }

    // 7皇后问题
    // limit : 0....0 1 1 1 1 1 1 1   【固定不变】
    // 之前皇后的列影响：colLim
    // 之前皇后的左下对角线影响：leftDiaLim
    // 之前皇后的右下对角线影响：rightDiaLim

    // 来到某个位置时。该位置能不能填，只需要考虑当前的左上、右上和直属列。 后面的行还没填呢
    public static int process2(int limit, int colLim, int leftDiaLim, int rightDiaLim) {

        // 所有的皇后都填满了【只有增加了皇后，才会增加列限制的位，其相等时，也就是填满了】
        if (colLim == limit) {
            return 1;
        }
        // pos中所有是1的位置，是你可以去尝试皇后的位置
        // ~ : 取反
        // (colLim | leftDiaLim | rightDiaLim)  得到所有不能放的位置
        // (~(colLim | leftDiaLim | rightDiaLim))  得到所有能放的位置

        // 和 limit 取与运算，防止当前摆放的位置在 limit 的范围之外  【limit：就是要求的 n 的位表示数组】
        // pos中所有是1的位置，是你可以去尝试皇后的位置
        int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
        int mostRightOne = 0;
        int res = 0;
        while (pos != 0) {
            // 类似于循环遍历所有位置的1，考虑能不能摆放
            // 将 pos 中最右侧的 1
            mostRightOne = pos & (~pos + 1);

            pos = pos - mostRightOne;
            res += process2(limit,
                    // 调整列限制
                    colLim | mostRightOne,
                    // 调整左对角线限制
                    (leftDiaLim | mostRightOne) << 1,
                    // 调整右对角线限制
                    (rightDiaLim | mostRightOne) >>> 1);
        }
        return res;
    }


    public static void main(String[] args) {
        int n = 10;

        long start = System.currentTimeMillis();
        System.out.println(Code03_NQueens.num2(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(num1(n));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

    }
}

