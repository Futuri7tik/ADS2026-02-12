package by.it.group551002.brutski.lesson13;
import java.util.*;


public class GraphA {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.hasNextLine() ? scanner.nextLine() : "";

        Map<String, List<String>> graph = new TreeMap<>(); // список смежности
        Map<String, Integer> inDegree = new TreeMap<>(); // степень каждой вершины

        for (String edge : line.split(",")) {
            String[] parts = edge.split("->");
            if (parts.length == 2) {
                String from = parts[0].trim();
                String to = parts[1].trim();

                graph.computeIfAbsent(from, _ -> new ArrayList<>()).add(to);
                graph.computeIfAbsent(to, _ -> new ArrayList<>());

                inDegree.putIfAbsent(from, 0);
                inDegree.merge(to, 1, Integer::sum);
            }
        }

        PriorityQueue<String> ready = new PriorityQueue<>();
        for (Map.Entry<String, Integer> e : inDegree.entrySet()) {
            if (e.getValue() == 0)
                ready.add(e.getKey());
        }

        StringJoiner result = new StringJoiner(" ");
        while (!ready.isEmpty()) {
            String v = ready.poll();
            result.add(v);
            for (String next : graph.get(v)) {
                if (inDegree.merge(next, -1, Integer::sum) == 0) {
                    ready.add(next);
                }
            }
        }
        System.out.println(result);
    }

}
