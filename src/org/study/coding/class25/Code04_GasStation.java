package org.study.coding.class25;

/**
 * 本题测试链接 : https://leetcode.com/problems/gas-station/
 * <p>
 * 4. 良好加油站问题最优解
 * <p>
 * 在一条环路上有 n个加油站，其中第 i个加油站有汽油gas[i]升。
 * 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1个加油站需要消耗汽油cost[i]升。你从其中的一个加油站出发，开始时油箱为空。
 * 给定两个整数数组 gas 和 cost ，如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1 。如果存在解，则保证它是唯一的。
 *
 * <p>
 * 注意本题的实现比leetcode上的问法更加通用
 * leetcode只让返回其中一个良好出发点的位置
 * 本题是返回结果数组，每一个出发点是否是良好出发点都求出来了
 * 得到结果数组的过程，时间复杂度O(N)，额外空间复杂度O(1)
 * <p>
 * 解决办法：
 * 暴力解：O(N^2)
 * 每个点都去尝试一下
 * <p>
 * <p>
 * <p>
 * <p>
 * 最优解：时间复杂度O(N).空间复杂度 O(1)
 * 做出一个储能数组 can[i] = gas[i]-cost[i]
 * 这样一来，在纯能数组can中，哪个点出发，跑完一圈，纯能数组的累加和不跑到0一下。该点就是良好出发点
 * 后面的操作都基于纯能数组
 * <p>
 * 当然，这个纯能数组can可以省掉。用gas数组代替。gas[i] =  gas[i]-cost[i]
 * 相当于是复用gas数组。等到算完之后，在用 gas[i] = gas[i] + cost[i] 给其还原回去
 * <p>
 * 在纯能数组中。只要是负数的点，都不是良好出发点。因为一上来就是负数。
 * <p>
 * 找到纯能数组中，第一个不是负数的点。
 * 设置一个连通区。找到第一个不是负数的点，认为此时的连通区只有其自己一个点
 * 连通区只用记一个开头和一个结尾。左闭右开。
 * <p>
 * 第三个变量rest：如果车现在要从当前点出发，能带多少燃料到达连通区的尾部【也就是跑到尾巴上，还能剩下多少油】
 * 第四个变量need：代表要接上连通区的开头，至少需要多少油【要跑到连通区的头部，需要多少油】
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 遍历数组。看看能不能扩充连通区的尾部。
 * 检查连通区的下一个点，看看下一个点的纯能是多少，是否能用rest搞定
 * 如果能用rest搞定，说明扩充连通区成功
 * 如果不能用rest搞定，说明扩充连通区不成功
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 如果连通区不能扩回到连通区的头。就说明当前点不是良好出发点
 * <p>
 * 【考虑去扩充连通区的头部】
 * 当前点搞定之后，跳到当前点的上一个点【也就是顺时针方向的第一个紧挨着的点】
 * 检查当前点，能不能接到连通区的开头上
 * 检查当前点的纯能是否能搞定need【接到开头需要的燃油数量】
 * 如果能，就可以将其连接到连通区。此时看看还剩下多少油【搞定need之后还剩下的】，可以将其带到尾部，也就是将其加到rest上
 * 然后去检查连通区的尾，看看能不能继续向前扩，【此时去走尾部扩充的逻辑】
 * 如果不能。就增加need。也就是将当前这个点还需要多少汽油才能到大连通区的头。将这需要的量添加到need上去。并且选高当前点失败
 * <p>
 * <p>
 * <p>
 * <p>
 * 然后再去到当前点的上一个点【顺时针的紧挨着的点】【执行头部扩充的逻辑】
 * <p>
 * 在头部扩充的时候。
 * 如果当前的的纯能数量比need大，可以直接连接到连通区，然后去考虑尾部连通
 * 如果当前的的纯能数量比need小，直接宣告当前点的失败，
 * 【此时，如果当前点的纯能是负的，就将当前点需要的纯能加到need上，如果当前点的纯能是正的，但是没有pk过need。就将need减掉当前点能提供的纯能的量】
 * <p>
 * <p>
 * <p>
 * 注意，need降低的时候【也就是某个点的纯能是正数，可以冲低need】，降到0就不要再往下降了。顺时针的后面的点，只要不是纯能小于0的，都能接到连通区上去。
 * 每一步，在还清need之后，剩下的加到rest上去，也就是带到尾部去
 * <p>
 * 1）当顺时针都进入了连通区的尾部时，一路上都没有找到良好出发点【连通区是逆时针的】，此时，连通区里面的点都不会是良好出发点。
 * 因为此时从连通区的头出发都只能到达当前的尾部，而尾部之前的点，能到达尾部，肯定都是或多或少带着大于等于0的油量【也就是沿途的rest都大于等于0】，
 * 有前面的rest撑腰，都没有突破尾部的下一个点。没有rest撑腰，自己单枪匹马更无可能了
 * <p>
 * 2）当顺时针都进入了连通区的尾部时。但是在这中间扎到了某个点，遇到了某个良好出发点。
 * 那么这个良好出发点就会称为新的头。在之后的顺时针扩充中，以后的点只要能接上这个开头，它必能转完一圈
 * 【找到一个良好出发点之后，其后的点，就不需要再关注rest和连通区的尾部了。只需要关注need就行】
 * 因为已经找到了一个能转完一圈的点，其后的点只要能连到这个头上。这个头都能转完，其后的点又能连上来，那么其后的点能转一圈还多呢
 *
 *
 * 代码可以考虑暂时性放弃。当然也可以用来锻炼coding
 *
 * @since 2022-04-05 22:15:43
 */
public class Code04_GasStation {

    /**
     * 最优解
     * @since 2022-04-06 22:12:39
     */
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        if (gas == null || gas.length == 0) {
            return -1;
        }
        if (gas.length == 1) {
            return gas[0] < cost[0] ? -1 : 0;
        }
        boolean[] good = stations(cost, gas);
        for (int i = 0; i < gas.length; i++) {
            if (good[i]) {
                return i;
            }
        }
        return -1;
    }

    public static boolean[] stations(int[] cost, int[] gas) {
        if (cost == null || gas == null || cost.length < 2 || cost.length != gas.length) {
            return null;
        }

        // cost数组变成纯能数组，并且找到第一个纯能不为0的下标返回
        int init = changeDisArrayGetInit(cost, gas);

        // 没有纯能大于0，直接返回全false。否则去开始扩充连通区。从这个 init点出发
        return init == -1 ? new boolean[cost.length] : enlargeArea(cost, init);
    }


    /**
     * cost数组变成纯能数组，并且找到第一个纯能不为0的下标返回
     * @since 2022-04-06 22:14:02
     */
    public static int changeDisArrayGetInit(int[] dis, int[] oil) {
        int init = -1;
        for (int i = 0; i < dis.length; i++) {
            dis[i] = oil[i] - dis[i];
            if (dis[i] >= 0) {
                init = i;
            }
        }
        return init;
    }

    public static boolean[] enlargeArea(int[] dis, int init) {
        boolean[] res = new boolean[dis.length];

        // 连通去试左闭右开的【连通区的头的index没什么用，这个start就代表顺时针方向，当前考虑的点】
        int start = init;
        int end = nextIndex(init, dis.length);

        int need = 0;
        int rest = 0;
        do {
            // 当前来到的start已经在连通区域中，可以确定后续的开始点一定无法转完一圈
            if (start != init && start == lastIndex(end, dis.length)) {
                break;
            }
            // 当前来到的start不在连通区域中，就扩充连通区域
            // start(5) ->  联通区的头部(7) -> 2
            // start(-2) -> 联通区的头部(7) -> 9

            // 当前start无法接到连通区的头部
            if (dis[start] < need) {
                need -= dis[start];
            } else {
                // 当前start可以接到连通区的头部，开始扩充连通区域的尾巴

                // start(7) -> 联通区的头部(5)
                rest += dis[start] - need;
                need = 0;
                while (rest >= 0 && end != start) {
                    rest += dis[end];
                    end = nextIndex(end, dis.length);
                }
                // 如果连通区域已经覆盖整个环，当前的start是良好出发点，进入2阶段
                if (rest >= 0) {
                    res[start] = true;

                    // 找到一个良好出发点之后，就不要再在这个while循环里面转了
                    connectGood(dis, lastIndex(start, dis.length), init, res);
                    break;
                }
            }
            start = lastIndex(start, dis.length);
        } while (start != init);
        return res;
    }

    /**
     * 已知start的next方向上有一个良好出发点
     * start如果可以达到这个良好出发点，那么从start出发一定可以转一圈
     * @since 2022-04-06 22:15:10
     */
    public static void connectGood(int[] dis, int start, int init, boolean[] res) {
        int need = 0;
        while (start != init) {
            if (dis[start] < need) {
                need -= dis[start];
            } else {
                res[start] = true;
                need = 0;
            }
            start = lastIndex(start, dis.length);
        }
    }


    /**
     * 环形数组的上一个下标
     * @since 2022-04-06 22:16:11
     */
    public static int lastIndex(int index, int size) {
        return index == 0 ? (size - 1) : index - 1;
    }

    /**
     * 环形数组的下一个下标
     * @since 2022-04-06 22:16:23
     */
    public static int nextIndex(int index, int size) {
        return index == size - 1 ? 0 : (index + 1);
    }

}
