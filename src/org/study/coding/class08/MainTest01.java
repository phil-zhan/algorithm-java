package org.study.coding.class08;

import java.util.LinkedList;
import java.util.Objects;

/**
 * @author phil
 * @date 2022-03-25 16:35:44
 */
public class MainTest01 {
    public static void main(String[] args) {
        String exp = "31+2*3";
        System.out.println(cal(exp));
        System.out.println(calculate("-3+2-(-3)*5"));

    }

    /**
     * 只支持加减乘除，不支持括号
     *
     * @since 2022-03-25 17:43:40
     */
    public static int cal(String exp) {
        if (exp.contains(")") || exp.contains("(")) {
            return 0;
        }
        char[] str = exp.toCharArray();
        String[] stack = new String[exp.length()];
        int si = 0;

        int cur = 0;
        for (char c : str) {
            if (c >= '0' && c <= '9') {
                // 数字
                cur = cur * 10 + (c - '0');
            } else {
                // 符号
                while (si > 1 && (Objects.equals(stack[si - 1], "*") || Objects.equals(stack[si - 1], "/"))) {
                    String preNum = stack[si - 2];
                    String preOperator = stack[si - 1];
                    if (preOperator.equals("*")) {
                        cur = cur * Integer.parseInt(preNum);
                    } else {
                        cur = cur / Integer.parseInt(preNum);
                    }

                    si = si - 2;
                }
                stack[si++] = String.valueOf(cur);
                stack[si++] = String.valueOf(c);
                cur = 0;
            }
        }

        while (si > 1) {
            String preNum = stack[si - 2];
            String preOperator = stack[si - 1];
            switch (preOperator) {
                case "*":
                    cur = cur * Integer.parseInt(preNum);
                    break;
                case "/":
                    cur = cur / Integer.parseInt(preNum);
                    break;
                case "+":
                    cur = cur + Integer.parseInt(preNum);
                    break;
                case "-":
                    cur = cur - Integer.parseInt(preNum);
                    break;
            }
            si = si - 2;
        }
        return cur;
    }


    public static int calculate(String exp) {

        return process(exp.toCharArray(), 0)[0];
    }


    public static int[] process(char[] exps, int index) {
        LinkedList<String> queue = new LinkedList<>();
        int cur = 0;

        while (index != exps.length && exps[index] != ')') {
            if (exps[index] <= '9' && exps[index] >= '0') {
                // 数字
                cur = cur * 10 + (exps[index++] - '0');
            } else if (exps[index] == '(') {
                // 左括号
                int[] bra = process(exps, index + 1);
                cur = bra[0];
                index = bra[1] + 1;
            } else {
                // 运算符
                pushNum(queue, cur);
                queue.addLast(String.valueOf(exps[index++]));
                cur = 0;
            }

        }
        pushNum(queue, cur);
        return new int[]{calculateStack(queue), index};
    }

    public static void pushNum(LinkedList<String> queue, int num) {
        if (!queue.isEmpty() && (queue.peekLast().equals("*") || queue.peekLast().equals("/"))) {
            // merge
            num = queue.pollLast().equals("*") ?
                    num * Integer.parseInt(Objects.requireNonNull(queue.pollLast())) :
                    num / Integer.parseInt(Objects.requireNonNull(queue.pollLast()));
        }
        queue.addLast(String.valueOf(num));
    }

    /**
     * 应该用队列
     *
     * @since 2022-03-25 18:17:06
     */
    public static int calculateStack(LinkedList<String> queue) {
        int num = 0;

        while (!queue.isEmpty()) {
            String top = queue.pollFirst();

            if ("+".equals(top)) {
                num += Integer.parseInt(Objects.requireNonNull(queue.pollFirst()));

            } else if ("-".equals(top)) {
                num = num - Integer.parseInt(Objects.requireNonNull(queue.pollFirst()));
            } else {
                // 第一个位置的时候
                num = Integer.parseInt(top);
            }
        }

        return num;
    }
}
