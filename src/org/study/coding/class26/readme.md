# 大厂高频算法和数据结构面试题26

###  

1. 有三个有序数组，分别在三个数组中挑出3个数，x、y、z。返回 |x-y| + |y-z| + |z-x|最小是多少？   
   Leetcode题目：https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/

2. 给定一个m x n 二维字符网格board和一个单词（字符串）列表 words，找出所有同时在二维网格和字典中出现的单词   
   单词必须按照字母顺序，通过 相邻的单元格 内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格   
   同一个单元格内的字母在一个单词中不允许被重复使用。   
   Leetcode题目：https://leetcode.com/problems/word-search-ii/

3. 给定一个仅包含数字0-9的字符串和一个目标值，在数字之间添加 二元 运算符（不是一元）+、-或*，返回所有能够得到目标值的表达式。   
   输入: num = "123", target = 6   
   输出: ["1+2+3", "1*2*3"]   
   示例2:   
   输入: num = "232", target = 8   
   输出: ["2*3+2", "2+3*2"]   
   示例 3:   
   输入: num = "105", target = 5   
   输出: ["1*0+5","10-5"]   
   示例4:   
   输入: num = "00", target = 0   
   输出: ["0+0", "0-0", "0*0"]   
   Leetcode题目：https://leetcode.com/problems/expression-add-operators/

4. 按字典wordList 完成从单词 beginWord 到单词 endWord 转化，一个表示此过程的 转换序列 是形式上像 beginWord -> s1 -> s2 -> ... -> sk 这样的单词序列，并满足：   
   每对相邻的单词之间仅有单个字母不同。   
   转换过程中的每个单词 si（1 <= i <= k）必须是字典wordList 中的单词。注意，beginWord 不必是字典 wordList 中的单词   
   给你两个单词 beginWord 和 endWord ，以及一个字典 wordList   
   请你找出并返回所有从 beginWord 到 endWord 的 最短转换序列 ，如果不存在这样的转换序列，返回一个空列表   
   每个序列都应该以单词列表 [beginWord, s1, s2, ..., sk] 的形式返回   
   Leetcode题目：https://leetcode.com/problems/word-ladder-ii/   






