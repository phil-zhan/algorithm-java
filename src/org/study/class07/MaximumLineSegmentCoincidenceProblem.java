package org.study.class07;

/**
 * 最大线段重合问题
 *
 * 给定很多线段，每个线段都有两个数 [start,end],表示线段的开始和结束位置，左右都是闭区间
 * 规定：
 *      1、线段的开始和结束位置都是整数
 *      2、线段重合区域的长度必须 >= 1
 *      返回线段最多重合区域中，包含了几条线段
 *
 *
 *
 * 解题思路1
 *      找出所有线段的开始位置的最小值 和 所有线段的结束位置的最大值
 *      统计过 min+0.5、min+1.5、min+2.5 。。。max-0.5 的线段。每一个都统计。最后返回【当然，这是最low的】
 *
 * 解题思路2
 *      第一步，将所有线段，按照开始位置从小到大排序。再准备一个小跟堆
 *      第二部，循环所有线段，将当前线段的结束位置加入到小跟堆，再将小跟堆里面比当前线段的起始位置小（或等于）的数都剔除。
 *              小跟堆里面剩下的数的数量就是 [穿过当前线段的起始位置的线段的数量]
 *      第三部，
 * @author phil
 * @date 2021/6/9 14:38
 */
public class MaximumLineSegmentCoincidenceProblem {

}
