package org.study.system.class04;


import java.util.*;

/**
 * @author phil
 * @date 2021/3/16 9:01
 * 面试题
 *
 *
 * 题目：给定一个只包含三种字符的字符串，【左括号"("，右括号")"和星号"*"】，写一个函数来验证这个字符串是否为有效字符串。
 * 有效字符串的规则入校
 * 1、任何左括号"(" 必须有相应的右括号")"
 * 2、任何右括号")" 必须有相应的左括号"("
 * 3、左括号"(" 必须在对应的右括号")"之前
 * 4、*可以视为单个右括号")" 或者单个左括号 "("，或者是一个空字符串
 * 5、一个空字符串也被视为有效字符
 *
 * 示例：
 * 输入："(*))"
 * 输出：True
 *
 * 输入："(*()"
 * 输出：True
 *
 * 输入："((*)"
 * 输出：True
 *
 *
 * 实现思路1：
 * 利用栈来实现，从第一个字符开始，拿到字符后，
 * 如果是左括号，直接压栈，
 * 如果是右括号，就和栈顶元素比较，【左右能配上的话，就上这对括号移出当前移出本次匹配，进入下一次匹配】
 * 如果是星号，就当星号放入一个单独的容器中。
 *
 *
 * 匹配结束后，看一下有多少个星号，就将栈里元素从栈顶开始，去掉与星号对应的树木
 * 最后，判断栈是否为空。若为空，返回True，不为空，返回False
 */
public class Code02 {


    public static void main(String[] args) {
        boolean res = testStack("(*()");


        System.out.println(res);
    }

    public static boolean testStack(String str){
        Stack<Character> stack = new Stack<>();
        List<Character> list = new ArrayList<>();
        char[] chars = new char[str.length()];

        str.getChars(0,str.length(),chars,0);

        // 循环字符串
        for (char aChar : chars) {
            if (aChar == '(') {
                // 压栈
                stack.push(aChar);
            } else if (aChar == ')') {
                // 此时的栈里全是 "(" 或者是空

                if (!stack.isEmpty()) {

                    // 出栈
                    stack.pop();
                } else if (list.size() > 0) {
                    list.remove(list.size() - 1);
                } else {
                    return false;
                }

            } else {
                list.add(aChar);
            }
        }
        while (!stack.isEmpty()){
            if(list.size()>0){
                list.remove(list.size()-1);
                stack.pop();
            }else {
                return false;
            }
        }
        return true;

    }



}
