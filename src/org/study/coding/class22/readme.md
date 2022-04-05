# 大厂高频算法和数据结构面试题22

###  
1. 给定数组nums由正整数组成，找到三个互不重叠的子数组的最大和。每个子数组的长度为k，我们要使这3*k个项的和最大化。返回每个区间起始索引的列表（索引从 0 开始）。   
   如果有多个结果，返回字典序最小的一个。   
   Leetcode题目：https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/




2. 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水   
   Leetcode题目：https://leetcode.com/problems/trapping-rain-water/




3. 给你一个 m x n 的矩阵，其中的值均为非负整数，代表二维高度图每个单元的高度，请计算图中形状最多能接多少体积的雨水。   
   Leetcode题目：https://leetcode.com/problems/trapping-rain-water-ii/




4. 一个不含有负数的数组可以代表一圈环形山，每个位置的值代表山的高度   
   比如， {3,1,2,4,5}、{4,5,3,1,2}或{1,2,4,5,3}都代表同样结构的环形山   
   山峰A和山峰B能够相互看见的条件为:  
   1) 如果A和B是同一座山，认为不能相互看见  
   2) 如果A和B是不同的山，并且在环中相邻，认为可以相互看见  
   3) 如果A和B是不同的山，并且在环中不相邻，假设两座山高度的最小值为min    
      a. 如果A通过顺时针方向到B的途中没有高度比min大的山峰，认为A和B可以相互看见   
      b. 如果A通过逆时针方向到B的途中没有高度比min大的山峰，认为A和B可以相互看见   
         两个方向只要有一个能看见，就算A和B可以相互看见   
         给定一个不含有负数且没有重复值的数组 arr，请返回有多少对山峰能够相互看见   
         进阶，给定一个不含有负数但可能含有重复值的数组arr，返回有多少对山峰能够相互看见  




5. 你正在安装一个广告牌，并希望它高度最大。这块广告牌将有两个钢制支架，两边各一个。每个钢支架的高度必须相等。   
   你有一堆可以焊接在一起的钢筋 rods。举个例子，如果钢筋的长度为 1、2 和 3，则可以将它们焊接在一起形成长度为 6 的支架。   
   返回广告牌的最大可能安装高度。如果没法安装广告牌，请返回 0。   
   Leetcode题目：https://leetcode.com/problems/tallest-billboard/   


