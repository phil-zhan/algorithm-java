### 链表常见面试题（续）
[Leetcode](https://leetcode.com/problems/linked-list-cycle-ii/?utm_source=LCUS&utm_medium=ip_redirect&utm_campaign=transfer2china)
```text
给定两个可能有环也可能无环的单链表，头节点head1和head2。请实现一个函数，如果两个链表相交，请返回相交的 第一个节点。如果不相交，返回null 
【要求】
如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度 请达到O(1)。 

1、找到链表的第一个入环节点

不论环外几个点、环里几个点。都设置两个指针【快指针和慢指针】，快指针一次走两步，慢指针一次走一步。
如果快指针走到null，说明当前链表无环，直接返回空。
如果当前链表有环，那么快慢指针一定会在环上相遇（不用问为什么）。
当快慢指针在环上相遇时，直接让快指针回到头节点，然后，快慢指针都一次跳一步，
当快慢指针再次相遇时的节点，就是第一个入环节点

2、两个链表要相交【要么都无环，出现 Y 现状】【要么都有环】【没有第三种情况（因为只有一个next指针）】

    无环的情况：
     * // 如果两个链表都无环，返回第一个相交节点，如果不想交，返回null
     * 遍历两个链表，记录下各自的长度。
     * 如果两个链表的最后一个节点不是同一个节点。那么，这两个链表不相交。
     * 如果最后一个节点是同一个节点，说明这两个节点相交。
     * 再次遍历两个链表，长的一个链表 先跳过他们的差值的长度。
     * 然后一起一步一步的走，直至遇到同一个节点【就是他们相交的第一个节点】
     
     有环的情况：
      *// 见 两个链表有环的情况.png 【三种情况】
      * 区分的判断是 如果两个链表的第一个入环节点相同【肯定是情况2】
      * 【将它们的第一个入环节点认为是它们的终点，然后按照无环单链表去找他们的第一个相交节点】
      * 
      * 区分的判断是 如果两个链表的第一个入环节点不相同【肯定是情况1或情况3】
      * 【从链表1的第一个入环节点开始往下走，如果转一圈（回到自己）都没遇到链表2的入环节点，那就是情况一】【他们不相交，返回空】
      * 
      * 【从链表1的第一个入环节点开始往下走，如果转一圈（回到自己），在这个过程中，遇到链表2的入环节点，那就是情况三】
      * 【他们的入环节点都是第一个相交的节点，返回谁都对】
```


### 二叉树入门
**递归遍历二叉树【先序、中序、后序】**
**非递归遍历二叉树【先序、中序、后序】**