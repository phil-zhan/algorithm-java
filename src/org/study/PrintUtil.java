package org.study;

import org.study.class03.Node;

/**
 * @author phil
 * @date 2021/3/12 15:21
 */
public class PrintUtil {

    /**
     * 打印数组
     * @date 2021-03-12 13:12:46
     */
    public static void printArr(int[] arr){
        for (int i : arr){
            System.out.print(i+"\t");
        }
    }
    public static void printLinkedList(Node<Object> node){
        while (null != node.next){
            System.out.println(node.value + "\t");
        }
    }

}
