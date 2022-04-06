# 大厂高频算法和数据结构面试题25

###  

1. An IP address is a formatted 32-bit unsigned integer where each group of 8 bits is printed as a decimal number and the dot character '.' splits the groups.   
   For example, the binary number 00001111 10001000 11111111 01101011 (spaces added for clarity) formatted as an IP address would be "15.136.255.107".   
   A CIDR block is a format used to denote a specific set of IP addresses. It is a string consisting of a base IP address, followed by a slash, followed by a prefix length k.   
   The addresses it covers are all the IPs whose first k bits are the same as the base IP address.   
   For example, "123.45.67.89/20" is a CIDR block with a prefix length of 20. Any IP address whose binary representation matches   
   01111011 00101101 0100xxxx xxxxxxxx, where x can be either 0 or 1, is in the set covered by the CIDR block.   
   You are given a start IP address ip and the number of IP addresses we need to cover n.   
   Your goal is to use as few CIDR blocks as possible to cover all the IP addresses in the inclusive range [ip, ip + n - 1] exactly   
   No other IP addresses outside of the range should be covered.   
   Return the shortest list of CIDR blocks that covers the range of IP addresses. If there are multiple answers, return any of them.   
   Leetcode题目：https://leetcode.com/problems/ip-to-cidr/  

2. 给你一个包含 n 个整数的数组nums，判断nums中是否存在三个元素 a，b，c ，使得a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组   
   注意：答案中不可以包含重复的三元组   
   Leetcode题目：https://leetcode.com/problems/3sum/  

3. 给你一个数组 points ，其中 points[i] = [xi, yi] 表示 X-Y 平面上的一个点。求最多有多少个点在同一条直线上。   
   Leetcode题目：https://leetcode.com/problems/max-points-on-a-line/  

4. 良好加油站问题最优解    
   Leetcode题目：https://leetcode.com/problems/gas-station/    






