package dijkstras;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

class Node implements Comparable<Node> {
    int vertex;
    int distance;

    public Node(int vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.distance, other.distance);
    }
}

class Dijkstras {
    public static void dijkstrasUsingPQ(int source, List<List<Node>> adjList, int numVertices) {
        int[] distances = new int[numVertices];

        Arrays.fill(distances, Integer.MAX_VALUE);

        distances[source] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(source, 0));

        while (!pq.isEmpty()) {
            Node currNode = pq.poll();

            int currVertex = currNode.vertex;

            for (Node neighbor : adjList.get(currVertex)) {
                int adjVertex = neighbor.vertex;
                int distance = neighbor.distance;
                int newDistance = distances[currVertex] + distance;

                if (newDistance < distances[adjVertex]) {
                    distances[adjVertex] = newDistance;
                    pq.add(new Node(adjVertex, newDistance));
                }
            }
        }

        for (int i = 0; i < numVertices; i++) {
            System.err.println(i + "\t\t" + distances[i]);
        }
    }

    public static void dijkstrasUsingSet(int source, List<List<Node>> adjList, int numVertices) {
        int[] distances = new int[numVertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;

        Set<Node> set = new TreeSet<>(Comparator.comparingInt(n -> n.distance));
        set.add(new Node(source, 0));

        while (!set.isEmpty()) {
            Node currNode = set.iterator().next();
            set.remove(currNode);

            int currVertex = currNode.distance;

            for (Node neighbor : adjList.get(currVertex)) {
                int adjVertex = neighbor.vertex;
                int newDistance = distances[currVertex] + neighbor.distance;

                if (newDistance < distances[adjVertex]) {
                    set.removeIf(n -> n.vertex == adjVertex);
                    distances[adjVertex] = newDistance;
                    set.add(new Node(adjVertex, newDistance));
                }
            }
        }

        System.out.println("Minimum distances of nodes from source: " + source);
        for (int i = 0; i < numVertices; i++) {
            System.err.println(i + "\t\t" + distances[i]);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        int numVertices = 5;

        List<List<Node>> adjList = new ArrayList<>();

        for (int i = 0; i < numVertices; i++) {
            adjList.add(new ArrayList<>());
        }

        adjList.get(0).add(new Node(1, 1));
        adjList.get(0).add(new Node(3, 2));

        adjList.get(1).add(new Node(0, 1));
        adjList.get(1).add(new Node(2, 2));
        adjList.get(1).add(new Node(4, 1));

        adjList.get(2).add(new Node(1, 2));
        adjList.get(2).add(new Node(4, 1));

        adjList.get(3).add(new Node(0, 2));
        adjList.get(3).add(new Node(4, 3));

        adjList.get(4).add(new Node(3, 3));
        adjList.get(4).add(new Node(1, 1));
        adjList.get(4).add(new Node(2, 1));

        // Dijkstras.dijkstrasUsingPQ(0, adjList, numVertices);
        Dijkstras.dijkstrasUsingSet(0, adjList, numVertices);
    }
}