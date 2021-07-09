package org.study.class03;

/**
 * @author phil
 * @date 2021/3/13 14:35
 * 数组实现栈：在数组的尾端就行插入和删除操作
 *
 *
 * 数组实现队列：利用循环数组来操作【垃圾操作，不推荐】
 * 设置两个指针：begin和end
 * begin指向第一个数，end指向最后一个数
 * 入队的时候在end的下一个位置插入。若end的值等于数组的长度，就去检查数组的起始位置
 * 出队的时候，从begin的位置去加，当begin等于数组长度，就回到数组的起始位置
 *
 *
 *
 *
 *
 *  * 数组实现队列：利用循环数组来操作
 *  * 设置三个指针：begin和end、size
 *  * begin指向第一个数【真实数据的位置】，
 *  * end指向下一个数放的位置【空位置】，也就是最后一个数的后一个位置
 *  * size标识当前数组中的数据长度。。【初始化的时候size=0，begin和end都指向第一个位置】
 *  * 入队的时候判断size是否等于数组长度，若不等于，在end的位置插入，end往后挪一个位置。同时size++
 *
 *  * 出队的时候，判断size是否等于0，将begin位置的数出队，同时begin往后挪动一个位置。。同时size--
 */
public class Code04 {
}
