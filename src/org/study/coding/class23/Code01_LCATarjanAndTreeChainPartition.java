package org.study.coding.class23;

import java.util.HashSet;

/**
 * 给定数组tree大小为N，表示一共有N个节点
 * tree[i] = j 表示点i的父亲是点j，tree一定是一棵树而不是森林
 * queries是二维数组，大小为M*2，每一个长度为2的数组都表示一条查询
 * [4,9], 表示想查询4和9之间的最低公共祖先
 * [3,7], 表示想查询3和7之间的最低公共祖先
 * tree和queries里面的所有值，都一定在0~N-1之间
 * 返回一个数组ans，大小为M，ans[i]表示第i条查询的答案
 * <p>
 * 暴力：
 * 将father数组做成表
 * 每次查询的时候。将第一个节点的从自己开始到跟节点的所有节点都放到set里去。然后第二个节点从自己开始，往跟节点遍历。每次都去查一下，遍历到的节点是不是在set集合中
 * 第一次在set出现的时候，就是他们的最低公共祖先
 * 每次查询都O(N)。假设二维数组有M行。那么整个查询就是 O(N*M)
 * <p>
 * 最优解：
 * 利用树链剖分
 * 只需要看要查询的两个节点的共同的重链的时候【第一次共重链】，对应的重链头就是他们对最低公共祖先
 * 树链剖分，每个节点到跟节点的时间复杂度才O(logN).到中间部分，肯定也是logN
 * 当然，建立树链剖分的时间复杂度是O(N)
 * 总体是 O(N) + O(M*logN)
 * 这个是支持在线查询的【也就是可以随时支持来一个查询，给一个答案】
 * <p>
 * <p>
 * <p>
 * <p>
 * 遍历第一个节点，将第一个节点到跟节点的沿途重链头放到set中去
 * <p>
 * <p>
 * 离线查询【只能是一次性将整个要查询的M条查询一次性给过来，查询后一起返回答案，不支持来一个查一个】
 * 【复杂度是O(N+M)】
 * 需要利用并查集
 * 1）利用father数组生成一棵树
 * 2）查询
 * ①如果要查询的两个节点是一样的话，可以直接返回答案。他们的最低公共祖先就是他们自己
 * ②设计一个查询表dp[]。【dp[i]=j】
 * 查询的时候，每次查询到对节点的最低公共祖先。生成两个列表。
 * 假设要查询的节点是a和b。他们的公共祖先是x。这是第i条查询【也就是生成一个key，对应两个list】
 * <p>
 * 列表1【一条查询对应两个记录】
 * key：a
 * value：[b]  // value是一个list，可能需要查询多个节点和a的最低公共祖先
 * key：b
 * value：[a]  // value是一个list，可能需要查询多个节点和b的最低公共祖先
 * <p>
 * <p>
 * <p>
 * 列表2
 * key：a
 * value：[i]  // value是一个list，可能需要查询多个节点和a的最低公共祖先.他们的序号为 i
 * <p>
 * key：b
 * value：[i]  // value是一个list，可能需要查询多个节点和b的最低公共祖先,他们的序号为 i
 * <p>
 * <p>
 * <p>
 * <p>
 * 列表3.【答案列表】
 * list[i]  // 是一个list，list[i] 表示第 i 条查询的结果。这个i即代表第 i 号查询，也代表第 i 号查询的答案在list中的位置
 *
 * 对于原树。利用并查集。初始化的时候，每个点在一个集合。每一个集合上打一个tag
 * 对每个集合打tag。整体的套路是二叉树的递归遍历。遍历过的就打上tag，没遍历过的就不打tag。
 * 【利用的是先序遍历】
 *
 * 在二叉树遍历的时候，第一次到某个节点，看看前面的列表中，有没有关于当前节点的问题需要求解。如果没有就直接去到下一个点。
 * 如果有：
 * 看看当前点是和谁要求最低公共祖先。如果他的伙伴点所对对应的集合没有遍历过，就先不解决这个问题
 * 【当第二次来到某个点的时候或者是第三次来到某个节点的时候，将其子树的集合和当前集合合并。打上统一的tag。tag就等于当前子树的头节点】
 * 如果他的伙伴点已经遍历过，再去解决这个问题
 * 解决的时候。
 * 当前节点的伙伴节点已经遍历过了，那么他的伙伴节点所在的集合的tag，就是他们的最低公共祖先。
 * 设置好答案。继续遍历【不要忘记第二、三次的时候，合并集合，设置tag】
 *
 *
 *
 *
 *
 * @since 2022-03-28 07:49:53
 */
public class Code01_LCATarjanAndTreeChainPartition {


    // 暴力方法
    public static int[] query1(int[] father, int[][] queries) {
        int M = queries.length;
        int[] ans = new int[M];

        // 一个节点往上蹿，将路上的我节点都放到set集合中去。。另外一个节点在往上蹿的时候，第一次在set集合中出现的节点就是他们的最低公共祖先
        HashSet<Integer> path = new HashSet<>();
        for (int i = 0; i < M; i++) {
            int jump = queries[i][0];
            while (father[jump] != jump) {
                path.add(jump);
                jump = father[jump];
            }
            path.add(jump);
            jump = queries[i][1];
            while (!path.contains(jump)) {
                jump = father[jump];
            }
            ans[i] = jump;
            path.clear();
        }
        return ans;
    }


    /**
     * 离线批量查询最优解 -> Tarjan + 并查集
     * 如果有M条查询，时间复杂度O(N + M)
     * 但是必须把M条查询一次给全，不支持在线查询
     *
     * @since 2022-03-28 07:50:25
     */
    public static int[] query2(int[] father, int[][] queries) {
        int N = father.length;
        int M = queries.length;
        int[] help = new int[N];
        int h = 0;
        for (int i = 0; i < N; i++) {
            if (father[i] == i) {
                h = i;
            } else {
                help[father[i]]++;
            }
        }
        int[][] mt = new int[N][];
        for (int i = 0; i < N; i++) {
            mt[i] = new int[help[i]];
        }
        for (int i = 0; i < N; i++) {
            if (i != h) {
                mt[father[i]][--help[father[i]]] = i;
            }
        }
        for (int i = 0; i < M; i++) {
            if (queries[i][0] != queries[i][1]) {
                help[queries[i][0]]++;
                help[queries[i][1]]++;
            }
        }
        int[][] mq = new int[N][];
        int[][] mi = new int[N][];
        for (int i = 0; i < N; i++) {
            mq[i] = new int[help[i]];
            mi[i] = new int[help[i]];
        }
        for (int i = 0; i < M; i++) {
            if (queries[i][0] != queries[i][1]) {
                mq[queries[i][0]][--help[queries[i][0]]] = queries[i][1];
                mi[queries[i][0]][help[queries[i][0]]] = i;
                mq[queries[i][1]][--help[queries[i][1]]] = queries[i][0];
                mi[queries[i][1]][help[queries[i][1]]] = i;
            }
        }
        int[] ans = new int[M];
        UnionFind uf = new UnionFind(N);
        process(h, mt, mq, mi, uf, ans);
        for (int i = 0; i < M; i++) {
            if (queries[i][0] == queries[i][1]) {
                ans[i] = queries[i][0];
            }
        }
        return ans;
    }

    // 当前来到head点
    // mt是整棵树 head下方有哪些点 mt[head] = {a,b,c,d} head的孩子是abcd
    // mq问题列表 head有哪些问题 mq[head] = {x,y,z} (head，x) (head，y) (head z)
    // mi得到问题的答案，填在ans的什么地方 {6,12,34}
    // uf 并查集
    public static void process(int head, int[][] mt, int[][] mq, int[][] mi, UnionFind uf, int[] ans) {
        // head有哪些孩子，都遍历去吧！
        for (int next : mt[head]) {

            // 去遍历孩子
            process(next, mt, mq, mi, uf, ans);

            // 遍历回来的时候，合并、打tag
            uf.union(head, next);
            uf.setTag(head, head);
        }
        // 解决head的问题！
        int[] q = mq[head];
        int[] i = mi[head];
        for (int k = 0; k < q.length; k++) {
            // head和谁有问题 q[k] 答案填哪 i[k]
            int tag = uf.getTag(q[k]);
            if (tag != -1) {
                ans[i[k]] = tag;
            }
        }
    }

    public static class UnionFind {

        // father -> 并查集里面father信息，i -> i的father
        private int[] f;

        // size[] -> 集合 --> i size[i]
        private int[] s;

        // tag[] -> 集合 ---> tag[i] = ?      //结合的代表点 i 的tag 就是 t[i]
        private int[] t;

        // 栈？并查集搞扁平化
        private int[] h;

        public UnionFind(int N) {
            f = new int[N];
            s = new int[N];
            t = new int[N];
            h = new int[N];
            for (int i = 0; i < N; i++) {
                f[i] = i;
                s[i] = 1;

                // 初始化时，认为所有的节点都没遍历过，也就没有tag信息
                t[i] = -1;
            }
        }

        private int find(int i) {
            int j = 0;
            // i -> j -> k -> s -> a -> a
            while (i != f[i]) {
                h[j++] = i;
                i = f[i];
            }
            // i -> a
            // j -> a
            // k -> a
            // s -> a
            while (j > 0) {
                h[--j] = i;
            }
            // a
            return i;
        }

        public void union(int i, int j) {
            int fi = find(i);
            int fj = find(j);
            if (fi != fj) {
                int si = s[fi];
                int sj = s[fj];
                int m = si >= sj ? fi : fj;
                int l = m == fi ? fj : fi;
                f[l] = m;
                s[m] += s[l];
            }
        }

        // 集合的某个元素是i，请把整个集合打上统一的标签，tag
        public void setTag(int i, int tag) {
            t[find(i)] = tag;
        }

        // 集合的某个元素是i，请把整个集合的tag信息返回
        public int getTag(int i) {
            return t[find(i)];
        }

    }

    // 在线查询最优解 -> 树链剖分
    // 空间复杂度O(N), 支持在线查询，单次查询时间复杂度O(logN)
    // 如果有M次查询，时间复杂度O(N + M * logN)
    public static int[] query3(int[] father, int[][] queries) {
        TreeChain tc = new TreeChain(father);
        int M = queries.length;
        int[] ans = new int[M];
        for (int i = 0; i < M; i++) {
            // x y ?
            // x x x
            if (queries[i][0] == queries[i][1]) {
                // 要查询的节点是同一个。他们的最低公共祖先就是他们自己
                ans[i] = queries[i][0];
            } else {

                // 分别从两个节点出发，沿着重链跳。当跳到同一个重链上的时候，将答案返回
                // 返回的是深度较小的重链头
                ans[i] = tc.lca(queries[i][0], queries[i][1]);
            }
        }
        return ans;
    }

    public static class TreeChain {
        private int n;
        private int h;
        private int[][] tree;
        private int[] fa;
        private int[] dep;
        private int[] son;
        private int[] siz;
        private int[] top;

        public TreeChain(int[] father) {
            initTree(father);
            dfs1(h, 0);
            dfs2(h, h);
        }

        private void initTree(int[] father) {
            n = father.length + 1;
            tree = new int[n][];
            fa = new int[n];
            dep = new int[n];
            son = new int[n];
            siz = new int[n];
            top = new int[n--];
            int[] cnum = new int[n];
            for (int i = 0; i < n; i++) {
                if (father[i] == i) {
                    h = i + 1;
                } else {
                    cnum[father[i]]++;
                }
            }
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

        private void dfs1(int u, int f) {
            fa[u] = f;
            dep[u] = dep[f] + 1;
            siz[u] = 1;
            int maxSize = -1;
            for (int v : tree[u]) {
                dfs1(v, u);
                siz[u] += siz[v];
                if (siz[v] > maxSize) {
                    maxSize = siz[v];
                    son[u] = v;
                }
            }
        }

        private void dfs2(int u, int t) {
            top[u] = t;
            if (son[u] != 0) {
                dfs2(son[u], t);
                for (int v : tree[u]) {
                    if (v != son[u]) {
                        dfs2(v, v);
                    }
                }
            }
        }

        public int lca(int a, int b) {
            a++;
            b++;
            while (top[a] != top[b]) {
                if (dep[top[a]] > dep[top[b]]) {
                    a = fa[top[a]];
                } else {
                    b = fa[top[b]];
                }
            }
            return (dep[a] < dep[b] ? a : b) - 1;
        }
    }

    // 为了测试
    // 随机生成N个节点树，可能是多叉树，并且一定不是森林
    // 输入参数N要大于0
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
    // 随机生成M条查询，点有N个，点的编号在0~N-1之间
    // 输入参数M和N都要大于0
    public static int[][] generateQueries(int M, int N) {
        int[][] ans = new int[M][2];
        for (int i = 0; i < M; i++) {
            ans[i][0] = (int) (Math.random() * N);
            ans[i][1] = (int) (Math.random() * N);
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
    public static boolean equal(int[] a, int[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    // 为了测试
    public static void main(String[] args) {
        int N = 1000;
        int M = 200;
        int testTime = 50000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int size = (int) (Math.random() * N) + 1;
            int ques = (int) (Math.random() * M) + 1;
            int[] father = generateFatherArray(size);
            int[][] queries = generateQueries(ques, size);
            int[] ans1 = query1(father, queries);
            int[] ans2 = query2(father, queries);
            int[] ans3 = query3(father, queries);
            if (!equal(ans1, ans2) || !equal(ans1, ans3)) {
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }

}
