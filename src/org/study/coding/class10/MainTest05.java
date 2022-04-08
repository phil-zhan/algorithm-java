package org.study.coding.class10;

/**
 * @author phil
 * @date 2022-04-08 09:59:17
 */
public class MainTest05 {
    public static void main(String[] args) {
        System.out.println(countEval0("0&0&0&1^1|0",1));
        System.out.println(Code05_BooleanEvaluation.countEval0("0&0&0&1^1|0",1));
    }

    public static class Info {
        public int trueWays;
        public int falseWays;

        public Info(int trueWays, int falseWays) {
            this.trueWays = trueWays;
            this.falseWays = falseWays;
        }
    }

    public static int countEval0(String express, int desired) {
        if (express == null || "".equals(express)) {
            return 0;
        }
        int len = express.length();
        Info info = process(0, express.length() - 1, express.toCharArray(),new Info[len][len]);
        return desired == 1 ? info.trueWays : info.falseWays;
    }

    public static Info process(int left, int right, char[] express,Info[][] dp) {
        if (dp[left][right] != null){
            return dp[left][right];
        }

        if (left == right) {
            // 注意这个判断的时候，用字符的1不要用数字的1.容易出错
            dp[left][right] =  express[left] == '1' ? new Info(1, 0) : new Info(0, 1);
        }else{

            int curTrueWays = 0;
            int curFalseWays = 0;
            for (int index = left + 1; index < right; index += 2) {
                // 枚举所有的符号位置 也就是index位置
                Info leftInfo = process(left, index - 1, express,dp);
                Info rightInfo = process(index + 1, right, express,dp);
                int a = leftInfo.trueWays;
                int b = leftInfo.falseWays;
                int c = rightInfo.trueWays;
                int d = rightInfo.falseWays;

                switch (express[index]) {
                    case '&':
                        curTrueWays += a * c;
                        curFalseWays += a * d + b * c + b * d;
                        break;
                    case '|':
                        curTrueWays += a * c + a * d + b * c;
                        curFalseWays += b * d;
                        break;
                    case '^':
                        curTrueWays += a * d + b * c;
                        curFalseWays += a * c + b * d;
                        break;
                    default:
                }
            }
            dp[left][right] = new Info(curTrueWays, curFalseWays);
        }
        return dp[left][right];
    }
}
