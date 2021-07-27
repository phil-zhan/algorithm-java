package org.study.class17;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 打印字符串的全部子序列
 *
 * @author phil
 * @date 2021/7/15 10:08
 */
public class MainTest03 {


    private static List<String> subs(String str){
        char[] strSeq = str.toCharArray();
        List<String> list = new ArrayList<>();
        String path = "";
        process(strSeq,list,path,0);
        return list;
    }

    private static void process(char[] strSeq, List<String> list, String path, int index) {
        if (index == strSeq.length) {
            list.add(path);
            return;
        }

        // 要 index 位置的字符
        process(strSeq,list,path+strSeq[index],index+1);

        // 不要 index 位置的字符
        process(strSeq,list,path,index+1);
    }



    private static List<String> subsRepeat(String str){
        char[] strSeq = str.toCharArray();
        Set<String> set = new HashSet<>();
        String path = "";
        process2(strSeq,set,path,0);

        return new ArrayList<>(set);
    }

    private static void process2(char[] strSeq, Set<String> set, String path, int index) {
        if (index == strSeq.length) {
            set.add(path);
            return;
        }

        // 要 index 位置的字符
        process2(strSeq,set,path+strSeq[index],index+1);

        // 不要 index 位置的字符
        process2(strSeq,set,path,index+1);
    }


    public static void main(String[] args) {
        String test = "acc";
        List<String> ans1 = subs(test);
        List<String> ans2 = subsRepeat(test);

        System.out.println("==============================="+"\t解决方案1\t"+"===============================");
        ans1.forEach((System.out::println));
        System.out.println("==============================="+"\t解决方案2\t"+"===============================");
        ans2.forEach((System.out::println));
    }
}
