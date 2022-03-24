package org.study.coding.class21;

import java.util.HashMap;

/**
 * 1. 树链剖分专题
 * 给定数组father，大小为N，表示一共有N个节点
 * father[i] = j 表示点i的父亲是点j， father表示的树一定是一棵树而不是森林【多叉树】【无环】【i=j时，表示的是头结点】
 * 给定数组values，大小为N，values[i]=v表示节点i的权值是v
 * 实现如下4个方法，保证4个方法都很快！
 * 1) 让某个子树所有节点值加上v，入参：int head, int v
 * 2) 查询某个子树所有节点值的累加和，入参：int head
 * 3) 在树上从a到b的整条链上所有加上v，入参：int a, int b, int v
 * 4) 查询在树上从a到b的整条链上所有节点值的累加和，入参：int a, int b
 * <p>
 * <p>
 * 重儿子：对于以head为头的某棵树来说。它的所有子节点为头的子树中，
 * 谁的节点树多，谁就是重儿子
 * 如果有多棵子树的节点一样都是最多，那么可以随意选择一个，作为中儿子
 * <p>
 * 重链：从头结点开始，依次连接重儿子的链，称为重链。【不在重链上的节点，称为轻节点】【每个子树都可以有自己的重链】
 * 每一个重链，都可能以一个轻链连接到其父节点上【也就是该节点是其父节点的轻链】
 * <p>
 * 每一个重链上会记一个重链的头
 * 每一个节点都会存在一个重链头
 * 即使该节点是轻节点，那么它的重链头就是他自己。相当于是从它自己出发，重新划分重链
 * <p>
 * 这样可以迅速的跳到总头部上。每一个节点，先跳到自己的重链头，
 * 再往上蹿一个轻链【自己的父节点】，再跳到重链头
 * 再往上蹿一个轻链【自己的父节点】，再跳到重链头
 * ...
 * 以此跳下去。很快就会跳到总头部了【O(logN):由其与头节点直接的重链个数决定】
 * <p>
 * <p>
 * dfs深度优先【按照优先重链，再走轻链的方式。可以将原链表的每个节点的dfs序标出来】【也就是遍历时候的遍历序号】【这个遍历序号按照原树的结构组织起来】
 * 将原始树上的节点和新树上的节点用hash表给对应起来【img_DFS序.png】
 * <p>
 * 问题1）这样一来，【每个子树的节点都是连续的】
 * 想在原树的某个子树上，每个节点都加一个x。就相当于在新树的某个连续区间加一个x【连续区间的变化。线段树解决】
 * <p>
 * 问题2）想要查询某个子树上所有节点的累加和。也就相当于查询某个连续区间的累加和【线段树解决】
 * <p>
 * 问题3）
 * 可以看出，每条重链上的标号也是连续的。因为来到一个子树，先沿着重链标号。重链标完，再表当前子树的轻链。都完成了再回到其他子树
 * 想要将两个节点（a和b之间）之间的链上的节点，都加上一个x。
 * 思路是利用逐渐汇聚
 * 从节点a开始，逐渐向头节点跳，在这个过程中，
 * 当前节点 和 当前节点的重链头 之间的重链 ，这部分重链上的点都加一个x。然后跳到下一个重链【先到重链头，再跳到父节点，就是下一个重链】
 * 只是在重链的部分上完成操作。重链是连续的。也是线段树的操作。
 * <p>
 * 再从b节点开始，执行和a节点一样的操作【注意，在最终的树的头节点，a和b不要都加，加一次就行】
 * <p>
 * 问题4）
 * 查询两个节点（a和b）之间的链上的节点的累加和
 * 采用逐渐汇聚的方式
 * 从节点a开始，逐渐向头节点跳，在这个过程中，
 * 当前节点 和 当前节点的重链头 之间的重链 ，这部分重链上的点都查询一个累加和。然后跳到下一个重链【先到重链头，再跳到父节点，就是下一个重链】
 * 只是在重链的部分上完成操作。重链是连续的。也是线段树的操作。
 * <p>
 * 时间复杂度。O(logN)
 *
 * @since 2022-03-23 12:19:29
 */
public class TreeChainPartition {


    public static class TreeChain {
        // 时间戳 0 1 2 3 4 【为了方便在重链遍历的时候，给每个节点编号】
        private int tim;
        // 节点个数是n，节点编号是1~n 【原始的是 0...n-1】
        private int n;
        // 谁是头
        private int h;
        // 朴素树结构【原来的从0开始，改成从1开始，将所有的序号都加1】
        // 第一维度是节点编号。第二维度是节点对应的子节点。每个节点都有多个子节点。也需要用数组表示，这里表现在第二维度
        // 不一定是矩阵。【第二维度可能有长有短】
        private int[][] tree;

        // 权重数组 原始的0节点权重是6 -> val[1] = 6
        private int[] val;

        // father数组一个平移，因为标号要+1
        private int[] fa;

        // 深度数组！dep[i] = x .i为头的这个树，其深度是x
        private int[] dep;

        // son[i] = 0 i这个节点，没有儿子
        // son[i] != j i这个节点，重儿子是j【只要有儿子，就肯定会有重儿子】
        private int[] son;
        // siz[i] i这个节点为头的子树，有多少个节点
        private int[] siz;
        // top[i] = j i这个节点，所在的重链，头是j
        private int[] top;
        // dfn[i] = j i这个节点，在dfs序中是第j个
        private int[] dfn;
        // 如果原来的节点a，权重是10
        // 如果a节点在dfs序中是第5个节点, tnw[5] = 10
        // 这个5号节点就是原来的a节点。只是重新编号了
        private int[] tnw;
        // 线段树，在tnw上，玩连续的区间查询或者更新
        private SegmentTree seg;

        public TreeChain(int[] father, int[] values) {
            // 原始的树 tree，弄好了，可以从i这个点，找到下级的直接孩子
            // 上面的一大堆结构，准备好了空间，values -> val
            // 找到头部点
            initTree(father, values);
            // fa;
            // dep;
            // son;
            // siz;
            // 假设头结点的父节点是那个废弃的0号节点。【因为各项数据都是0，方便使用】
            dfs1(h, 0);
            // top;
            // dfn;
            // tnw;
            // 第一遍dfs是取统计节点个数和重儿子等。第二遍是给dfs序标号
            dfs2(h, h);
            seg = new SegmentTree(tnw);
            seg.build(1, n, 1);
        }

        private void initTree(int[] father, int[] values) {
            tim = 0;
            n = father.length + 1;
            tree = new int[n][];
            val = new int[n];
            fa = new int[n];
            dep = new int[n];
            son = new int[n];
            siz = new int[n];
            top = new int[n];
            dfn = new int[n];
            tnw = new int[n--];

            // 初始化权重节点。后移一位
            for (int i = 0; i < n; i++) {
                val[i + 1] = values[i];
            }
            // 统计每个节点的子节点个数
            // cnum[i] = j  表示i号节点的子节点个数是j
            // 同时抓一下整体的头结点
            int[] cnum = new int[n];
            for (int i = 0; i < n; i++) {
                if (father[i] == i) {
                    // 所有的点都平移了。所以要加1
                    h = i + 1;
                } else {
                    cnum[father[i]]++;
                }
            }

            // 朴素树的第二维度。也就是将每个节点的所有子节点维护好
            // 先初始化空间，再维护数据
            tree[0] = new int[0];
            for (int i = 0; i < n; i++) {
                tree[i + 1] = new int[cnum[i]];
            }
            for (int i = 0; i < n; i++) {
                if (i + 1 != h) {
                    tree[father[i] + 1][--cnum[father[i]]] = i + 1;
                }
            }
        }

        // u 当前节点
        // f u的父节点
        private void dfs1(int u, int f) {
            fa[u] = f;
            dep[u] = dep[f] + 1;
            siz[u] = 1;
            int maxSize = -1;

            // 遍历u节点，所有的直接孩子
            for (int v : tree[u]) {
                // 递归。底下的节点会填好每个节点的size
                dfs1(v, u);
                siz[u] += siz[v];

                // 维护重儿子
                if (siz[v] > maxSize) {
                    maxSize = siz[v];
                    son[u] = v;
                }
            }
        }

        // u当前节点
        // t是u所在重链的头部
        private void dfs2(int u, int t) {
            dfn[u] = ++tim;
            top[u] = t;
            tnw[tim] = val[u];

            // 如果u有儿子 siz[u] > 1
            if (son[u] != 0) {
                // 先去重儿子
                dfs2(son[u], t);
                for (int v : tree[u]) {
                    // 再去轻儿子
                    if (v != son[u]) {
                        dfs2(v, v);
                    }
                }
            }
        }

        // head为头的子树上，所有节点值+value
        // 因为节点经过平移，所以head(原始节点) -> head(平移节点)
        public void addSubtree(int head, int value) {
            // 原始点编号 -> 平移编号
            head++;
            // 平移编号 -> dfs序编号 dfn[head]
            seg.add(dfn[head], dfn[head] + siz[head] - 1, value, 1, n, 1);
        }

        // 查询head为头的树上的累加和
        public int querySubtree(int head) {
            head++;
            return seg.query(dfn[head], dfn[head] + siz[head] - 1, 1, n, 1);
        }

        // 从 a 到 b 的整条链都加一个v
        public void addChain(int a, int b, int v) {

            // 原始点编号 -> 平移编号
            a++;
            b++;

            // 不在一条重链上
            while (top[a] != top[b]) {

                // 谁的深度比较大，就让谁先跳。防止小头的节点跳的太快，错过去了
                // 每次跳完一次之后，直接去到父节点。进入下一轮循环。直到跳到a和b两个节点在一条链上是。跳出循环，走下面的逻辑
                // 深的节点先蹦。交替着蹦。每次都蹦一条重链。在蹦的时候，处理对应的重链
                if (dep[top[a]] > dep[top[b]]) {
                    // 处理当前链
                    // 跳到父节点
                    seg.add(dfn[top[a]], dfn[a], v, 1, n, 1);
                    a = fa[top[a]];
                } else {
                    // 处理当前链
                    // 跳到父节点
                    seg.add(dfn[top[b]], dfn[b], v, 1, n, 1);
                    b = fa[top[b]];
                }
            }

            // 在一个链上。【深度不可能是一样的。肯定都先后】
            if (dep[a] > dep[b]) {
                seg.add(dfn[b], dfn[a], v, 1, n, 1);
            } else {
                seg.add(dfn[a], dfn[b], v, 1, n, 1);
            }
        }

        public int queryChain(int a, int b) {
            // 原始点编号 -> 平移编号
            a++;
            b++;
            int ans = 0;

            // 不在一条链上
            while (top[a] != top[b]) {

                // 谁的深度比较大，就让谁先跳。防止小头的节点跳的太快，错过去了
                // 每次跳完一次之后，直接去到父节点。进入下一轮循环。直到跳到a和b两个节点在一条链上是。跳出循环，走下面的逻辑
                // 深的节点先蹦。交替着蹦。每次都蹦一条重链。在蹦的时候，处理对应的重链
                if (dep[top[a]] > dep[top[b]]) {
                    // 处理当前链
                    // 跳到父节点
                    ans += seg.query(dfn[top[a]], dfn[a], 1, n, 1);
                    a = fa[top[a]];
                } else {
                    // 处理当前链
                    // 跳到父节点
                    ans += seg.query(dfn[top[b]], dfn[b], 1, n, 1);
                    b = fa[top[b]];
                }
            }

            // 在一个链上。【深度不可能是一样的。肯定都先后】
            if (dep[a] > dep[b]) {
                ans += seg.query(dfn[b], dfn[a], 1, n, 1);
            } else {
                ans += seg.query(dfn[a], dfn[b], 1, n, 1);
            }
            return ans;
        }
    }

    public static class SegmentTree {
        private int MAXN;
        private int[] arr;
        private int[] sum;
        private int[] lazy;

        public SegmentTree(int[] origin) {
            MAXN = origin.length;
            arr = origin;
            sum = new int[MAXN << 2];
            lazy = new int[MAXN << 2];
        }

        private void pushUp(int rt) {
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }

        private void pushDown(int rt, int ln, int rn) {
            if (lazy[rt] != 0) {
                lazy[rt << 1] += lazy[rt];
                sum[rt << 1] += lazy[rt] * ln;
                lazy[rt << 1 | 1] += lazy[rt];
                sum[rt << 1 | 1] += lazy[rt] * rn;
                lazy[rt] = 0;
            }
        }

        public void build(int l, int r, int rt) {
            if (l == r) {
                sum[rt] = arr[l];
                return;
            }
            int mid = (l + r) >> 1;
            build(l, mid, rt << 1);
            build(mid + 1, r, rt << 1 | 1);
            pushUp(rt);
        }

        public void add(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && r <= R) {
                sum[rt] += C * (r - l + 1);
                lazy[rt] += C;
                return;
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                add(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                add(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        public int query(int L, int R, int l, int r, int rt) {
            if (L <= l && r <= R) {
                return sum[rt];
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            int ans = 0;
            if (L <= mid) {
                ans += query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return ans;
        }

    }

    /**
     * 暴力方法，用于测试
     *
     * @since 2022-03-23 21:08:55
     */
    public static class Right {
        private int n;
        private int[][] tree;
        private int[] fa;
        private int[] val;
        private HashMap<Integer, Integer> path;

        public Right(int[] father, int[] value) {
            n = father.length;
            tree = new int[n][];
            fa = new int[n];
            val = new int[n];
            for (int i = 0; i < n; i++) {
                fa[i] = father[i];
                val[i] = value[i];
            }
            int[] help = new int[n];
            int h = 0;
            for (int i = 0; i < n; i++) {
                if (father[i] == i) {
                    h = i;
                } else {
                    help[father[i]]++;
                }
            }
            for (int i = 0; i < n; i++) {
                tree[i] = new int[help[i]];
            }
            for (int i = 0; i < n; i++) {
                if (i != h) {
                    tree[father[i]][--help[father[i]]] = i;
                }
            }
            path = new HashMap<>();
        }

        public void addSubtree(int head, int value) {
            val[head] += value;
            for (int next : tree[head]) {
                addSubtree(next, value);
            }
        }

        public int querySubtree(int head) {
            int ans = val[head];
            for (int next : tree[head]) {
                ans += querySubtree(next);
            }
            return ans;
        }

        public void addChain(int a, int b, int v) {
            path.clear();
            path.put(a, null);
            while (a != fa[a]) {
                path.put(fa[a], a);
                a = fa[a];
            }
            while (!path.containsKey(b)) {
                val[b] += v;
                b = fa[b];
            }
            val[b] += v;
            while (path.get(b) != null) {
                b = path.get(b);
                val[b] += v;
            }
        }

        public int queryChain(int a, int b) {
            path.clear();
            path.put(a, null);
            while (a != fa[a]) {
                path.put(fa[a], a);
                a = fa[a];
            }
            int ans = 0;
            while (!path.containsKey(b)) {
                ans += val[b];
                b = fa[b];
            }
            ans += val[b];
            while (path.get(b) != null) {
                b = path.get(b);
                ans += val[b];
            }
            return ans;
        }

    }

    /**
     * 为了测试
     * 随机生成N个节点树，可能是多叉树，并且一定不是森林
     * 输入参数N要大于0
     *
     * @since 2022-03-23 21:28:49
     */
    public static int[] generateFatherArray(int N) {
        int[] order = new int[N];
        for (int i = 0; i < N; i++) {
            order[i] = i;
        }
        for (int i = N - 1; i >= 0; i--) {
            swap(order, i, (int) (Math.random() * (i + 1)));
        }
        int[] ans = new int[N];
        ans[order[0]] = order[0];
        for (int i = 1; i < N; i++) {
            ans[order[i]] = order[(int) (Math.random() * i)];
        }
        return ans;
    }

    // 为了测试
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 为了测试
    public static int[] generateValueArray(int N, int V) {
        int[] ans = new int[N];
        for (int i = 0; i < N; i++) {
            ans[i] = (int) (Math.random() * V) + 1;
        }
        return ans;
    }

    // 对数器
    public static void main(String[] args) {
        int N = 50000;
        int V = 100000;
        int[] father = generateFatherArray(N);
        int[] values = generateValueArray(N, V);
        TreeChain tc = new TreeChain(father, values);
        Right right = new Right(father, values);
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            double decision = Math.random();
            if (decision < 0.25) {
                int head = (int) (Math.random() * N);
                int value = (int) (Math.random() * V);
                tc.addSubtree(head, value);
                right.addSubtree(head, value);
            } else if (decision < 0.5) {
                int head = (int) (Math.random() * N);
                if (tc.querySubtree(head) != right.querySubtree(head)) {
                    System.out.println("出错了!");
                }
            } else if (decision < 0.75) {
                int a = (int) (Math.random() * N);
                int b = (int) (Math.random() * N);
                int value = (int) (Math.random() * V);
                tc.addChain(a, b, value);
                right.addChain(a, b, value);
            } else {
                int a = (int) (Math.random() * N);
                int b = (int) (Math.random() * N);
                if (tc.queryChain(a, b) != right.queryChain(a, b)) {
                    System.out.println("出错了!");
                }
            }
        }
        System.out.println("测试结束");
    }

}
