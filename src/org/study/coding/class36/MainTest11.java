package org.study.coding.class36;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTest11 {

    public static void main(String[] args) {
        System.out.println(new MainTest11().numBusesToDestination(new int[][]{{1, 2, 7}, {3, 6, 7}}, 1, 6));
    }

    public int numBusesToDestination(int[][] routes, int source, int target) {
        if (routes == null || routes.length == 0) {
            return -1;
        }
        if (target == source) {
            return 0;
        }


        // key: station
        // value: routes
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();
        for (int i = 0; i < routes.length; i++) {
            for (int j = 0; j < routes[i].length; j++) {
                if (!map.containsKey(routes[i][j])) {
                    map.put(routes[i][j], new ArrayList<>());
                }
                map.get(routes[i][j]).add(i);
            }
        }

        // 从起点开始  宽度优先
        int len = 1;
        List<Integer> queue = new ArrayList<>();
        boolean[] visited = new boolean[routes.length];
        for (Integer route : map.get(source)) {
            queue.add(route);
            visited[route] = true;
        }

        while (!queue.isEmpty()) {
            ArrayList<Integer> nextRoutes = new ArrayList<>();
            for (int i = 0; i < queue.size(); i++) {
                for (Integer station : routes[queue.get(i)]) {
                    if (station == target) {
                        return len;
                    }
                    for (Integer route : map.get(station)) {
                        if (!visited[route]) {
                            nextRoutes.add(route);
                            visited[route] = true;
                        }
                    }
                }
            }

            queue = nextRoutes;
            len++;
        }
        return -1;
    }
}
