# 大厂高频算法和数据结构面试题3

### 

1. 数组中所有数都异或起来的结果，叫做异或和。给定一个数组arr，返回arr的最大子数组异或和  

2. 数组中所有数都异或起来的结果，叫做异或和。给定一个数组arr，想知道arr中哪两个数的异或结果最大，返回最大的异或结果  
   Leetcode题目：https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/  

3. 给定一个非负整数组成的数组nums。另有一个查询数组queries，其中queries[i]=[xi, mi]  
   第i个查询的答案是xi和任何nums数组中不超过mi的元素按位异或（XOR）得到的最大值  
   换句话说，答案是max(nums[j] XOR xi)，其中所有j均满足nums[j]<= mi。如果nums中的所有元素都大于mi，最终答案就是-1  
   返回一个整数数组answer作为查询的答案，其中answer.length==queries.length且answer[i]是第i个查询的答案  
   Leetcode题目：https://leetcode.com/problems/maximum-xor-with-an-element-from-array/  

4. 数组中所有数都异或起来的结果，叫做异或和。给定一个数组arr，可以任意切分成若干个不相交的子数组。其中一定存在一种最优方案，使得切出异或和为0的子数组最多，返回这个最多数量  

5. Nim博弈，给定一个正数数组arr，先手和后手每次可以选择在一个位置拿走若干值，这个值要大于0，但是要小于该处的剩余，谁最先拿空arr谁赢，根据arr返回谁赢  

