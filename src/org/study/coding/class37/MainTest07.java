package org.study.coding.class37;

public class MainTest07 {

    public static void main(String[] args) {


        System.out.println(new MainTest07().decodeString("3[a]2[bc]"));
    }

    public String decodeString(String s) {
        return process(s.toCharArray(), 0).ans;
    }

    public static class Info {
        public String ans;
        public int end;

        public Info(String ans, int end) {
            this.ans = ans;
            this.end = end;
        }
    }

    public Info process(char[] str, int index) {
        int count = 0;
        StringBuilder builder = new StringBuilder();
        while (index < str.length && str[index] != ']') {
            if (str[index] >= '0' && str[index] <= '9') {
                // 数字
                count = count * 10 + str[index] - '0';
                index++;
            } else if ((str[index] >= 'a' && str[index] <= 'z') || (str[index] >= 'A' && str[index] <= 'Z')) {
                // 字母
                builder.append(str[index++]);
            } else {
                // 左括号
                Info next = process(str, index + 1);
                builder.append(timesStr(count, next.ans));
                count = 0;
                index = next.end + 1;
            }
        }

        return new Info(builder.toString(), index);

    }

    public String timesStr(int count, String str) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(str);
        }
        return builder.toString();
    }
}
