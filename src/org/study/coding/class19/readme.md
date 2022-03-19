# 大厂高频算法和数据结构面试题19

### 
1. LRU内存/缓存替换算法   
   Leetcode题目：https://leetcode.com/problems/lru-cache/




2. LFU内存/缓存替换算法   
   Leetcode题目：https://leetcode.com/problems/lfu-cache/




3. 给定一个正数N，比如N = 13，在纸上把所有数都列出来如下：   
   1 2 3 4 5 6 7 8 9 10 11 12 13   
   可以数出1这个字符出现了6次，给定一个正数N，如果把1~N都列出来，返回1这个字符出现的多少次





4. 你有k个非递减排列的整数列表。找到一个最小区间，使得k个列表中的每个列表至少有一个数包含在其中   
   我们定义如果b-a < d-c或者在b-a == d-c时a < c，则区间 [a,b] 比 [c,d] 小。   
   Leetcode题目：https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/





5. 一张扑克有3个属性，每种属性有3种值（A、B、C）   
   比如"AAA"，第一个属性值A，第二个属性值A，第三个属性值A   
   比如"BCA"，第一个属性值B，第二个属性值C，第三个属性值A   
   给定一个字符串类型的数组cards[]，每一个字符串代表一张扑克   
   从中挑选三张扑克，一个属性达标的条件是：这个属性在三张扑克中全一样，或全不一样   
   挑选的三张扑克达标的要求是：每种属性都满足上面的条件   
   比如："ABC"、"CBC"、"BBC"   
   第一张第一个属性为"A"、第二张第一个属性为"C"、第三张第一个属性为"B"，全不一样   
   第一张第二个属性为"B"、第二张第二个属性为"B"、第三张第二个属性为"B"，全一样   
   第一张第三个属性为"C"、第二张第三个属性为"C"、第三张第三个属性为"C"，全一样   
   每种属性都满足在三张扑克中全一样，或全不一样，所以这三张扑克达标   
   返回在cards[]中任意挑选三张扑克，达标的方法数   





