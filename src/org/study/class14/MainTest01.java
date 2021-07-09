package org.study.class14;

import java.util.HashSet;

/**
 * 照光问题
 *
 * @author phil
 * @date 2021/7/8 16:09
 */
public class MainTest01 {


    public static int minLight1(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }
        return process(road.toCharArray(), 0, new HashSet<>());
    }

    // str[index....]位置，自由选择放灯还是不放灯
    // str[0..index-1]位置呢？已经做完决定了，那些放了灯的位置，存在lights里
    // 要求选出能照亮所有.的方案，并且在这些有效的方案中，返回最少需要几个灯
    public static int process(char[] str, int index, HashSet<Integer> lights) {
        if (index == str.length) { // 结束的时候
            for (int i = 0; i < str.length; i++) {
                if (str[i] != 'X') { // 当前位置是点的话
                    if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
                        return Integer.MAX_VALUE;
                    }
                }
            }
            return lights.size();
        } else { // str还没结束
            // i X .
            int no = process(str, index + 1, lights);
            int yes = Integer.MAX_VALUE;
            if (str[index] == '.') {
                lights.add(index);
                yes = process(str, index + 1, lights);
                lights.remove(index);
            }
            return Math.min(no, yes);
        }
    }

    private static int minLight2(String road){
        char[] roads = road.toCharArray();
        int index = 0;
        int lights=0;

        while (index < roads.length){
            if(roads[index] == 'X'){
                index ++;
            }else{
                // 到这一步，不管如何，都需要点灯
                lights ++;

                if(index +1 == roads.length){
                    break;
                }else{
                    if(roads[index +1] == 'X'){
                        index = index +2;
                    }else{

                        // index  index+1  index+2 [这连续的三个位置都不是X。那么直接跳到到 index+3 的位置]
                        index = index +3;
                    }
                }
            }
        }

        return lights;
    }



    // for test
    public static String randomString(int len) {
        char[] res = new char[(int) (Math.random() * len) + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = Math.random() < 0.5 ? 'X' : '.';
        }
        return String.valueOf(res);
    }

    public static void main(String[] args) {
        int len = 20;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            String test = randomString(len);
            int ans1 = minLight1(test);
            int ans2 = minLight2(test);
            if (ans1 != ans2) {
                System.out.println("oops!");
            }
        }
        System.out.println("finish!");
    }
}
