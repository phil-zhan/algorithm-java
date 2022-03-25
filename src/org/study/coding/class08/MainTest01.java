package org.study.coding.class08;

import java.util.Objects;
import java.util.Stack;

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
     * @since 2022-03-25 17:43:40
     */
    public static int cal(String exp){
        if (exp.contains(")") || exp.contains("(")){
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
        Stack<String> stack = new Stack<>();
        int cur = 0;

        while (index != exps.length && exps[index] != ')') {
            if (exps[index] <= '9' && exps[index] >= '0') {
                // 数字
                cur = cur * 10 + (exps[index++] - '0');
            } else if (exps[index] == ')') {
                // 左括号
                int[] bra = process(exps, index + 1);
                cur = bra[0];
                index = bra[1] + 1;
            } else {
                // 运算符
                pushNum(stack, cur);
                stack.push(String.valueOf(exps[index++]));
                cur = 0;
            }

        }
        pushNum(stack, cur);
        return new int[]{calculateStack(stack), index};
    }

    public static void pushNum(Stack<String> stack, int num) {
        if (!stack.isEmpty() && (stack.peek().equals("*") || stack.peek().equals("/"))) {
            // merge
            num = stack.pop().equals("*") ? num * Integer.parseInt(stack.pop()) : num / Integer.parseInt(stack.pop());
        }
        stack.push(String.valueOf(num));
    }

    public static int calculateStack(Stack<String> stack) {
        int num = 0;

        while (!stack.isEmpty()){
            String top = stack.pop();

            if (top.equals("+")){
                num += Integer.parseInt(stack.pop());

            }else if (top.equals("-")){
                num -= Integer.parseInt(stack.pop());
            }else{
                num = Integer.parseInt(top);
            }
        }

        return num;
    }
}
