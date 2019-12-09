package algorithms;

import java.util.*;

/**
 * @author Robert Hill
 * 12.08.2019
 * Undirected.java
 * @version 1.0.0
 *
 * This class makes a graph is provided that can be used to represent the cells of the maze.
 * The graph is undirected and provides DFS and BFS traversal implementations.Ò
 */
public class Undirected {
    private int vertex;
    private Set<Integer>[] adjList;
    private boolean[] marked;
    private int[] edgePath;
    private int start = 0;

    /**
     * Instance of the Undirected graph class
     *
     * @param size int to represent the amount of vertices in the Graph
     */
    public Undirected(int size) {
        this.vertex = size;
        adjList = new HashSet[size];
        for (int i = 0; i < size; i++) {
            adjList[i] = new HashSet<>();
        }

    }

    /**
     * addEdge() adds an edge to the Graph
     *
     * @param source int to represent a source reference Vertex
     * @param dest   int to represent a destination reference Vertex
     */
    public void addEdge(int source, int dest) {
        adjList[source].add(dest);
    }

    /**
     * hasEdge() Checks if an edge exists
     *
     * @param source int to represent a source referenced Vertex of the graph
     * @param dest   int to represent a destination referenced Vertex of the graph
     * @return if edge is detected in the graph
     */
    public boolean hasEdge(int source, int dest) {
        return adjList[source].contains(dest);
    }


    /**
     * depthfsUtil() used by the dfs to iterate a depth first search over the graph
     *
     * @param source int to represent a source Vertex in the graph
     */
    public void depthfsUtil(int source) {
        edgePath = new int[adjList.length];
        this.start = source;

        Stack<Integer> stack = new Stack<>();


        stack.push(source);

        while (!stack.empty()) {

            source = stack.peek();
            stack.pop();

            if (!marked[source]) {
                marked[source] = true;
            }

            Iterator<Integer> itr = adjList[source].iterator();

            while (itr.hasNext()) {
                int neighbor = itr.next();
                if (!marked[neighbor]) {
                    edgePath[neighbor] = source;
                    stack.push(neighbor);
                }
            }

        }
    }

    /**
     * dfs() is method used to utilize the depth First search iterator
     * over the Graph instance
     */
    public void dfs() {
        marked = new boolean[adjList.length];

        for (int i = 0; i < vertex; i++) {
            marked[i] = false;
        }

        for (int i = 0; i < vertex; i++) {
            if (!marked[i]) {
                depthfsUtil(i);
            }
        }
    }

    /**
     * hasDFSpathTo() returns if the Vertex has been visited
     *
     * @param dest int to represent the index of the Vertex in the marked list
     * @return boolean to represent if the Vertex was visited
     */
    public boolean hasDFSpathTo(int dest) {
        return marked[dest];
    }

    /**
     * DFSpathTo() to track a path from the declared start Vertex in that graph
     * to the input destination Vertex
     *
     * @param dest int to represent the input Vertex
     * @return an iterable stack of integers
     */
    public Iterable<Integer> dFSpathTo(int dest) {
        if (!hasDFSpathTo(dest)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int x = dest; x != start; x = edgePath[x]) {
            path.push(x);
        }
        path.push(start);
        return path;
    }

    /**
     * breadthFS() to run a breadth first search over the graph instance
     *
     * @param source int to represent the starting Vertex point of the search
     */
    public void breadthFS(int source) {
        edgePath = new int[adjList.length];
        marked = new boolean[adjList.length];

        Queue<Integer> que = new PriorityQueue<>();
        marked[source] = true;

        que.add(source);

        while (!que.isEmpty()) {

            int next = que.remove();
            for (int vert : adjList[next]) {
                if (!marked[vert]) {
                    edgePath[vert] = next;
                    marked[vert] = true;
                    que.add(vert);
                }
            }
        }
    }

    @Override
    public String toString() {
        return null;
    }
}
