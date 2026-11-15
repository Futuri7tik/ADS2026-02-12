package by.it.group551002.brutski.lesson13;

import java.util.*;

public class GraphB {
    private static final int NOT_PROCESSED = 0, IN_PROCESS = 1, PROCESSED = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.hasNextLine() ? scanner.nextLine() : "";

        Map<String, List<String>> graph = new TreeMap<>();
        for (String edge : line.split(",")) {
            String[] parts = edge.split("->");
            if (parts.length != 2) 
                continue;
            
            String from = parts[0].trim();
            String to = parts[1].trim();
            
            graph.computeIfAbsent(from, _ -> new ArrayList<>()).add(to);
            graph.computeIfAbsent(to, _ -> new ArrayList<>());
        }

        Map<String, Integer> processMap = new HashMap<>();
        boolean isCycle = false;
        for (String v : graph.keySet()) {
            if (processMap.getOrDefault(v, NOT_PROCESSED) == NOT_PROCESSED && hasCycle(v, graph, processMap)) {
                isCycle = true;
                break;
            }
        }

        if (isCycle)
            System.out.println("yes");
        else
            System.out.println("no");
    }

    private static boolean hasCycle(String v, Map<String, List<String>> graph, Map<String, Integer> processMap) {
        processMap.put(v, IN_PROCESS);
        for (String next : graph.get(v)) {
            int c = processMap.getOrDefault(next, NOT_PROCESSED);
            if (c == IN_PROCESS)
                return true;

            if (c == NOT_PROCESSED && hasCycle(next, graph, processMap))
                return true;
        }
        processMap.put(v, PROCESSED);
        return false;
    }
}
