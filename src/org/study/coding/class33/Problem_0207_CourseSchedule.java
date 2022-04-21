package org.study.coding.class33;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 你这个学期必须选修 numCourses 门课程，记为0到numCourses - 1 。
 *
 * 在选修某些课程之前需要一些先修课程。 先修课程按数组prerequisites 给出，其中prerequisites[i] = [ai, bi] ，表示如果要学习课程ai 则 必须 先学习课程 bi 。
 *
 * 例如，先修课程对[0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
 * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
 *
 * 
 *
 * 示例 1：
 *
 * 输入：numCourses = 2, prerequisites = [[1,0]]
 * 输出：true
 * 解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
 * 示例 2：
 *
 * 输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
 * 输出：false
 * 解释：总共有 2 门课程。学习课程 1 之前，你需要先完成​课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。
 * 
 *
 * 
 * @since 2022-04-20 08:01:32
 */
public class Problem_0207_CourseSchedule {

	/**
	 * 一个node，就是一个课程
	 * name是课程的编号
	 * in是课程的入度
	 *
	 * @since 2022-04-20 08:02:20
	 */
	public static class Course {
		public int name;
		public int in;
		public ArrayList<Course> nexts;

		public Course(int n) {
			name = n;
			in = 0;
			nexts = new ArrayList<>();
		}
	}

	/**
	 * numCourses：总的课程数量
	 * prerequisites[i][0...1] : prerequisites[i][0]号课程依赖 prerequisites[i][1] 号课程
	 *
	 * 解法：
	 * 就是图的拓扑排序
	 * 能排序，就能修完所有的课程。不能完成拓扑排序，就不能修完
	 *
	 * @since 2022-04-20 08:02:37
	 */
	public static boolean canFinish1(int numCourses, int[][] prerequisites) {
		if (prerequisites == null || prerequisites.length == 0) {
			return true;
		}
		// 完成图的建立
		// 一个编号 对应 一个课的实例
		HashMap<Integer, Course> nodes = new HashMap<>();
		for (int[] arr : prerequisites) {

			// from -> to
			int to = arr[0];
			int from = arr[1];

			if (!nodes.containsKey(to)) {
				nodes.put(to, new Course(to));
			}
			if (!nodes.containsKey(from)) {
				nodes.put(from, new Course(from));
			}

			Course t = nodes.get(to);
			Course f = nodes.get(from);
			f.nexts.add(t);
			t.in++;
		}

		// 找到入度为0的节点，将其加到队列里面去
		int needPrerequisiteNums = nodes.size();
		Queue<Course> zeroInQueue = new LinkedList<>();
		for (Course node : nodes.values()) {
			if (node.in == 0) {
				zeroInQueue.add(node);
			}
		}
		// 遍历入度为0的节点。将其对应的to节点的入度减减
		// 如果其to节点的入度被减少为0，就也加入队列
		// 反复遍历队列，直到队列为空
		// 在这个过程中，统计遍历的节点数。最后比较遍历过的节点的数量是否等于总的课程数量
		int count = 0;
		while (!zeroInQueue.isEmpty()) {
			Course cur = zeroInQueue.poll();
			count++;
			for (Course next : cur.nexts) {
				if (--next.in == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		return count == needPrerequisiteNums;
	}

	/**
	 * 和方法1算法过程一样
	 * 但是写法优化了
	 *
	 * @since 2022-04-20 20:49:44
	 */
	public static boolean canFinish2(int courses, int[][] relation) {
		if (relation == null || relation.length == 0) {
			return true;
		}
		// 这是应该list里面套list【下标代表当前课程】【里面的list，代表当前下标对应的课程完成后，哪些课程可以开始了】【相当于图的邻接表】
		// 3 :  0 1 2
		// nexts :   0   {}
		//           1   {}
		//           2   {}
		//           3   {0,1,2}
		ArrayList<ArrayList<Integer>> nexts = new ArrayList<>();
		for (int i = 0; i < courses; i++) {
			nexts.add(new ArrayList<>());
		}


		// 这是入度表
		// 3 入度 1  in[3] == 1
		int[] in = new int[courses];
		for (int[] arr : relation) {
			// arr[1] from   arr[0] to
			nexts.get(arr[1]).add(arr[0]);
			in[arr[0]]++;
		}

		
		// 队列【知道队列的元素个数。完全可以用一个数组来实现队列】【L...R表示有效的数据范围】
		int[] zero = new int[courses];
		// 该队列有效范围是[l,r)
		// 新来的数，放哪？r位置，r++
		// 出队列的数，从哪拿？l位置，l++
		// l == r 队列无元素  l < r 队列有元素
		int l = 0;
		int r = 0;
		for (int i = 0; i < courses; i++) {
			if (in[i] == 0) {
				zero[r++] = i;
			}
		}


		int count = 0;
		while (l != r) {
			count++; // zero[l] 出队列   l++
			for (int next : nexts.get(zero[l++])) {
				if (--in[next] == 0) {
					zero[r++] = next;
				}
			}
		}
		return count == nexts.size();
	}

}
