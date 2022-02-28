package org.study.system.class03;

/**
 * @author phil
 * @date 2021/3/13 11:11
 * 双链表结构
 */
public class DoubleNode<T> {

    public T value;

    public DoubleNode<T> pre;
    public DoubleNode<T> next;

    public DoubleNode(T value){
        this.value = value;
    }
}
