# 大厂高频算法和数据结构面试题2

### 有限的能力，赚钱最多
1. 给定数组hard和money，长度都为N，hard[i]表示i号工作的难度， money[i]表示i号工作的收入  
   给定数组ability，长度都为M，ability[j]表示j号人的能力，每一号工作，都可以提供无数的岗位，难度和收入都一样  
   但是人的能力必须>=这份工作的难度，才能上班。返回一个长度为M的数组ans，ans[j]表示j号人能获得的最好收入   
   * 解法：
   * 先根据难度排序
   * 难度一样的情况下，根据报酬排序【难度升序。报酬降序】	 
   * 相同难度的，只保留报酬高的
   * 难度提高，报酬反而降低的，也去掉	 
   * 留下的就是随着难度提高，收入也提高的工作
   * 每个人来的时候，可以直接采用二分法，拿到自己能力范围内的最大报酬的工作
   * 当然，这里也可以用有序表【java中也就是 TreeMap】

### 计算投币次数
2. 贩卖机只支持硬币支付，且收退都只支持10 ，50，100三种面额  
   一次购买只能出一瓶可乐，且投钱和找零都遵循优先使用大钱的原则  
   需要购买的可乐数量是m，其中手头拥有的10、50、100的数量分别为a、b、c，可乐的价格是x(x是10的倍数)  
   请计算出需要投入硬币次数
   * 解法：
   * 计算每一种面值花完，需要投多少次硬币
   * 计算第一次购买时，需要考虑前面大面值的遗留数额和遗留张数
   * 每次购买会被找零，记得将找零的数量累加到后面具体的数额上

### 设计消息盒子
3. 已知一个消息流会不断地吐出整数1~N，但不一定按照顺序依次吐出，如果上次打印的序号为i， 那么当i+1出现时  
   请打印i+1及其之后接收过的并且连续的所有数，直到1~N全部接收并打印完，请设计这种接收并打印的结构
   * 解法：
   * 设计消息盒子。采用链表结构来完成
   * 准备两个集合。头指针集合和尾指针集合
   * 在外层设计一个属性。记录当前正在等待的消息序号。
   * 将该消息缓存。
   * 看看能不能和当前存在的链表的头尾连起来
   * 【如果在头集合里面，存在该消息的下一个节点，就将当前节点的next指针设为那个存在的节点，然后将其节点从头集合中删除，再把当前节点放入头集合】
   * 【如果在尾集合里面，存在该消息的上一个节点，就将上一个节点的next指针设为当前节点，然后将其节点从尾集合中删除，再把当前节点放入尾集合】
   * 如果连不起来。就认为当前节点是一个新链表。在头和尾集合中都放一个该节点。以等待后面的节点来连接
   * 
   * 缓存完成后，检查当前节点是否是现在正在等待的节点。若是，就从当前节点开始，沿着链表next指针打印。
   * 打印时，及得从头和尾集合中，将对应的头和尾删除。

### 求司机的最高收入
4. 现有司机N*2人，调度中心会将所有司机平分给A、B两区域，i号司机去A可得收入为income[i][0]，去B可得收入为income[i][1]  
   返回能使所有司机总收入最高的方案是多少钱?
   * 设计一个动态规划
   * 解法：
   * [index...right]随机选择。但是得保证最终A、B两地的司机人数一样
   * 可以将参数设计为，当前来带index的司机位置。A区域还剩多少个位置
   * 在每个位置。考虑将该司机发到A区域和B区域。最后抓一下最大收入

### 设计Map的setAll
5. 设计有setAll功能的哈希表，put、get、setAll方法，时间复杂度O(1)
   * 解法：
   * 对每一个value进行封装，增加一个进入当前数据结构的时间点属性。  
   * 每次get的时候，先将其从原始的map里面get出来，然后检查其进入map的时间点和putAll()被调用的时间点
   * 如果get出来的数据在putAll()被调用的时间点之前。就直接返回putAll时锁给定的值。否则就返回原始值

### 最短排序长度
6. 给定一个数组arr，只能对arr中的一个子数组排序，但是想让arr整体都有序，返回满足这一设定的子数组中最短的是多长  
   * 解法：
   * 从左往右遍历
   * 准备一个max指针，表示当前位置左边的最大值的位置
   * 如果左边位置的最大值大于当前数 就将当前位置标记为 `×`     【违反了单调递增规律】
   * 如果左边位置的最大值小于等于当前数 就将当前位置标记为 `√`  【满足单调递增规律】
   * 
   * 从右往左遍历
   * 准备一个min指针，表示当前位置右边的最小值的位置
   * 如果右边位置的最小值大于当前数 就将当前位置标记为 `√`     【满足单调递减规律】
   * 如果右边位置的最小值小于等于当前数 就将当前位置标记为 `×`  【违反了单调递减规律】
   * 
   * 从右往左的最后个 `×` 的位置 和 从左往右的最后 `×` 的位置。  中间就是需要排序的最短长度
   * 
   * 从左往右。最后一个画 × 的，表示其后面的数都不需要给前面的数让位置。从右往左也是一样的
   * 确定了右边不需要排序的位置，也确定了左边不需要排序的位置。中间就是需要排序的
