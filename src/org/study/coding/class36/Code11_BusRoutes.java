package org.study.coding.class36;

import java.util.ArrayList;
import java.util.HashMap;

// 来自三七互娱
// Leetcode原题 : https://leetcode.com/problems/bus-routes/
public class Code11_BusRoutes {

	// 0 : [1,3,7,0]
	// 1 : [7,9,6,2]
	// ....
	// 返回：返回换乘几次+1 -> 返回一共坐了多少条线的公交。
	public static int numBusesToDestination(int[][] routes, int source, int target) {
		if (source == target) {
			return 0;
		}
		int n = routes.length;
		// key : 车站
		// value : list -> 该车站拥有哪些线路！
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < routes[i].length; j++) {
				if (!map.containsKey(routes[i][j])) {
					map.put(routes[i][j], new ArrayList<>());
				}
				map.get(routes[i][j]).add(i);
			}
		}

		// 宽度优先遍历。一次搞一层
		ArrayList<Integer> queue = new ArrayList<>();

		// 表示该线路是否加入过队列
		boolean[] set = new boolean[n];

		// 先将源头的的线路加入队列。
		for (int route : map.get(source)) {
			queue.add(route);
			set[route] = true;
		}


		int len = 1;
		while (!queue.isEmpty()) {

			// 准备下一层
			ArrayList<Integer> nextLevel = new ArrayList<>();

			// 遍历当前层【也就是遍历当前队列中存在的线路。将因为当前队列中的线路而引入进来的新线路加入到下一层中去】
			for (int route : queue) {

				// 当前层的公交站。【遍历当前线路的公交站。看看能不能引入新的线路】
				int[] bus = routes[route];
				for (int station : bus) {

					if (station == target) {
						return len;
					}

					// 准备下一层。也就是准备新加入进来的路线
					for (int nextRoute : map.get(station)) {
						if (!set[nextRoute]) {
							nextLevel.add(nextRoute);
							set[nextRoute] = true;
						}
					}
				}
			}

			queue = nextLevel;
			len++;
		}
		return -1;
	}

}
