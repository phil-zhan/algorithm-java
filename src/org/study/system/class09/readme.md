### 排序算法总结
| 排序算法 | 时间复杂度|额外空间复杂度|稳定性|基本思想|
|:---:|:---:|:---:|:---:|:---|
|选择排序|O(N^2)|O(1)|无|每次选择一个最小的。|
|冒泡排序|O(N^2)|O(1)|有|从左到右，依次比较两个相邻的，谁大谁往右|
|插入排序|O(N^2)|O(1)|有|从第二个开始，在前面有序的子数组中找到合适的位置|
|归并排序|O(N*logN)|O(N)|有|两个有序的子数组，谁小copy谁|
|随机快排|O(N*logN)|O(logN)|无|选定一个数，比其大的放在右边，比其小的放在左边。再对左右两边进行同样的操作。|
|堆排序|O(N*logN)|O(1)|无|利用大小跟堆。|
|====|====|====|====|====|
|计数排序|O(N)|O(M)|有|计数排序要求，样本是整数，且范围比较窄|
|基数排序|O(N)|O(N)|有|基数排序要求，样本是10进制的正整数|


### 总结
1. 不基于比较的排序，对样本数据有严格要求，不易改写  
2. 基于比较的排序，只要规定好两个样本怎么比大小就可以直接复用  
3. 基于比较的排序，时间复杂度的极限是O(N*logN)  
4. 时间复杂度O(N*logN)、额外空间复杂度低于O(N)、且稳定的基于比较的排序是`不存在的`。  
    `时间复杂度O(N*logN)的三个排序中。追求速度的话，选择快速排序，追求算法的稳定性，选归并排序。  
    追求算法使用绝对少的空间，选择堆排序`  
5. 为了绝对的速度选快排、为了省空间选堆排、为了稳定性选归并  

### 常见的坑
1. 归并排序的额外空间复杂度可以变成O(1)，“归并排序 内部缓存法”，但是将变得不再稳定。  
2. “原地归并排序" 是垃圾贴，会让时间复杂度变成O(N^2)  
3. 快速排序稳定性改进，“01 stable sort”，但是会对样本数据要求更多。  
4. 在整型数组中，请把奇数放在数组左边，偶数放在数组右边，要求所有奇数之间原始的相对次序不变，所有偶数之间原始相对次序不变。  
   `要求：时间复杂度做到O(N)，额外空间复杂度做到O(1)`  
   面试时:告诉面试官，说这题你不会，让面试官讲给你听【他肯定也不会】  


### 工程上对排序的改进
1. 稳定性的考虑  
2. 充分利用O(N*logN)和O(N^2)排序各自的优势  


java中的Arrays.sort()。针对基础类型，用的是比较快的快速排序【基础类型的稳定性是没有意义的】。  
针对非基础类型，用的是归并排序【保证稳定性】  

### 链表相关面试题
1. 输入链表头节点，奇数长度返回中点，偶数长度返回上中点  
2. 输入链表头节点，奇数长度返回中点，偶数长度返回下中点  
3. 输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个  
4. 输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个  
5. 给定一个单链表的头节点head，请判断该链表是否为回文结构。  
6. 将单向链表按某值划分成左边小、中间相等、右边大的形式  
7. random随机指针，完整复制链表问题  
```text
    一种特殊的单链表节点类描述如下
        class Node {
            int value;
            Node next;
            Node rand;
            Node(int val) { value = val; }
        }
    rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节点，也可能指向null。
    给定一个由Node节点类型组成的无环单链表的头节点 head，请实现一个函数完成这个链表的复制，并返回复制的新链表的头节点。
    【要求】
    时间复杂度O(N)，额外空间复杂度O(1) 
```
    