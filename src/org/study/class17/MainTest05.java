package org.study.class17;

import java.util.Stack;

/**
 * 不增加额外空间的情况下翻转栈
 *
 * @author phil
 * @date 2021/7/15 10:30
 */
public class MainTest05 {

    private static void reverse(Stack<String> stack){
        if (null == stack || stack.size() < 2) {
            return;
        }

        String last = getLastElement(stack);
        reverse(stack);
        stack.push(last);
    }

    private static String getLastElement(Stack<String> stack) {
        String result = stack.pop();


        if (stack.isEmpty()) {
           return result;
        }else {
            // 这个last是下一步的result。也就是下一个栈顶
            String last = getLastElement(stack);
            stack.push(result);
            return last;
        }
    }


    public static void main(String[] args) {
        Stack<String> test = new Stack<>();
        test.push("1");
        test.push("2");
        test.push("3");
        test.push("4");
        test.push("5");
        reverse(test);

        test.forEach(System.out::println);


    }
}
