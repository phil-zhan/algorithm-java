package org.study.system.class03;

/**
 * 单链表结构
 * @author phil
 * @date 2021/3/13 10:59
 */
public class Node<T> {

    public T value;
    public Node<T> next;

    public Node(T value){
        this.value = value;
    }
}
