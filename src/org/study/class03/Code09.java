package org.study.class03;

/**
 * @author phil
 * @date 2021/3/15 11:14
 * Hash表和有序表
 *
 *
 * Hash表，
 * 以HashMap为例【实现了增删改查的时间复杂度均为O(1)】
 * 如果是基础类型和String类型，key和value的值有多大，就会在hash表中开辟多大的空间去存储
 * 如果是Object等引用类型，在hash表中，只会存对象的地址【8个字节】
 *
 * 有key的是HashMap，没有key就是HashSet。【在其内部组织都一样，都是以Node节点来存储，只是Node多个属性而已】
 *
 *
 * 有序表，
 * java中，表现为TreeMap.【实现了增删改查的时间复杂度均为O(logN)】
 * 实现方式有很多，红黑树、avl、sb树，跳变
 * 在Java中是用红黑树实现的
 * 在有序表中，对于自定义的类型作为key，需要自己写比较方法【也就是在new TreeMap的时候，作为参数传入比较器】
 *
 *
 */
public class Code09 {
}
