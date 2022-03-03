# 大厂高频算法和数据结构面试题1

### 绳子压点问题
1. 给定一个有序数组arr，代表坐落在X轴上的点，给定一个正数K，代表绳子的长度，返回绳子最多压中几个点？    
   即使绳子边缘处盖住点也算盖住  
   <font color="red">**【Code01】**</font>   
   * 解法1：考虑以每个位置为绳子的终点，其前面能覆盖多少数【利用二分法去处理。找到【0...index】范围上。找到当前线段能覆盖的最左 的下标】  
   * 解法2：利用固定绳子长度的窗口去滑动处理。窗口不会退。O(N)  

### 统计文件个数
2. 给定一个文件目录的路径，写一个函数统计这个目录下所有的文件数量并返回，隐藏文件也算，但是文件夹不算    
   <font color="red">**【Code02】**</font>  
   * 利用递归的思路。   
   * 利用队列来完成宽度优先遍历  
   * 利用栈来完成深度优先遍历  

### 找出大于等于num，且最接近num的2的某次方
3. 给定一个非负整数num，如何不用循环语句，返回>=num，并且离num最近的，2的某次方  
   <font color="red">**【Code03】**</font>
   * 思路是将该数字，变为从最高位开始，后面全是1的一个数  
   * 先减去1，是为了防止所给的数正好是2的某次方。将最高位后面的所有数都变为1，最后再加以，就会导致多一个2的某次方  
   * 如果n-1是一个负数的话【最高位是1】。经过下面的流程后，会变成一个所有位置全是1的数字。而实际上应该返回1【特殊处理】  

### 两种字符，最少移动问题   
4. 一个数组中只有两种字符'G'和'B'，可以让所有的G都放在左侧，所有的B都放在右侧  
   或者可以让所有的G都放在右侧，所有的B都放在左侧，但是只能在相邻字符之间进行交换操作，返回至少需要交换几次  
   <font color="red">**【Code04】**</font>  
   * 解法1：   
   * 准备一个step1指针。表示下一个G应该放的位置    
   * 从左往右遍历  
   * 遇到的如果是B，直接跳过   
   * 遇到的如果是G。累加当前位置的G一定到`gi`的位置需要多少步（ans += index-gi）.  【在考虑下一个元素之前，不要忘记gi++】    
   * 两个标准。所有G在左，也可以所有B在左。  
   * 考虑这两种情况下的最下值  
   * 时间复杂度 O(N)  

### 矩阵中能走出来的最长的递增链长度
5. 给定一个二维数组matrix，你可以从任何位置出发，走向上、下、左、右四个方向，返回能走出来的最长的递增链长度  
   <font color="red">**【Code05】**</font>    
   * 尝试从每一个位置出发的上下左右四个方向，收集最大值    
   * 对应的方向，可以走就去尝试，不能走就是0。  
   * 注意是否满足递增的条件【后一个比前一个大1】  

### AOE问题
6. 给定两个非负数组x和hp，长度都是N，再给定一个正数range  
   x有序，x[i]表示i号怪兽在x轴上的位置  
   hp[i]表示i号怪兽的血量  
   再给定一个正数range，表示如果法师释放技能的范围长度  
   被打到的每只怪兽损失1点血量。返回要把所有怪兽血量清空，至少需要释放多少次AOE技能？  
   <font color="red">**【Code06】**</font>   
   * 思路是利用线段树    
   * 根据数组初始化一个线段树结构  
   * 考虑从每个正数位置开始，所给长度的范围上。要将最左边的数化为0，需要释放多少次AOE。就在该范围上减去最左边的值  
   * 相当于先把最左边的搞死【同时会带着以他为左边的界的范围内的怪兽掉血】    
   * 然后去找下一个还没死的（血量为正数的怪兽位置），以他为左边界，重复进行  
   * 最后累加统计所有释放AOE的次数  

### 加或减搞定target
7. 给定一个数组arr，你可以在每个数字之前决定+或者-但是必须所有数字都参与，再给定一个数target  
   请问最后算出target的方法数  
   <font color="red">**【Code07】**</font>       
   * 简单的递归：  
   * 可以自由使用arr[index....]所有的数字！  
   * 搞出rest这个数，方法数是多少？返回  
   * 当然，可以改为傻缓存【自上向下】和dp动态规划【自底向上】  
   * 涉及到一些巧妙的优化点，是从业务本身出发的。可以了解，长长见识  
