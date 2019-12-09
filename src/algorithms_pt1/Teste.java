package algorithms_pt1;

import algorithms.DisjointSets;

import java.util.Random;

public class Teste
{
    public static void main(String[] args)
    {
        int rows = 4 ;
        int col = 4 ;
        int [][] mus = new int [rows][col];
        int dime = rows * col;
        int count = 0;
        //generate a new graph
        UndirectedGraph<Integer> letterGraph = new UndirectedGraph<>();
        for(int i = 0; i < (dime) ;i++)
        {
                letterGraph.addVertex(i);
        }
        System.out.println();
        System.out.println(letterGraph);

        DisjointSets disjointSets = new DisjointSets(dime);
        for(int i = 0; i < (dime-1) ;i++)
        {
            Random num = new Random();
            int x = num.nextInt(dime - 1) ;
            disjointSets.union(i,x);
        }
//        letterGraph.addVertices(0,1,2,3);
//        letterGraph.addEdges(
//                new Edge<>('a', 'b'),
//                new Edge<>('a', 'c'),
//                new Edge<>('a', 'e'),
//                new Edge<>('b', 'c'),
//                new Edge<>('c', 'd'),
//                new Edge<>('c', 'e'),
//                new Edge<>('d', 'a'),
//                new Edge<>('d', 'b'),
//                new Edge<>('d', 'e')
//        );
        System.out.println();
        System.out.println(letterGraph);
        System.out.println(disjointSets.find(15));

        //Runs the program - generate a maze, and solve it with BFS and DFS for a maze of size 4
        UndirectedGraph<Integer> g = new UndirectedGraph(4);
        g.generateMaze();
        System.out.println("Grid: ");
        String grid = g.printGrid();
        System.out.println(grid);
        g.DFS(g.vertexListV[0][0]);
        String aGrid = g.printGrid1();
        System.out.println();
        System.out.println("DFS");
        System.out.println(aGrid);
        g.setPath();
        String dGrid = g.printGrid2();
        System.out.println();
        System.out.println(dGrid);

        UndirectedGraph<Integer> g1 = new UndirectedGraph<>(4);
        g1.generateMaze();
        g1.BFS(g1.vertexListV[0][0]);
        String bGrid = g1.printGrid1();
        System.out.println();
        System.out.println("BFS");
        System.out.println(bGrid);
        g1.setPath();
        String cGrid = g1.printGrid2();
        System.out.println();
        System.out.println(cGrid);

//        Set<Character> vertices = letterGraph.vertices();
//        Set<Edge<Character>> edges = letterGraph.edges();
//
//        boolean hasA = letterGraph.containsVertex('a'); //should be true
//        boolean hasF = letterGraph.containsVertex('f'); //should be false
//
//        boolean hasAB = letterGraph.containsEdge(new Edge<>('a', 'b')); //should be true
//        boolean hasAF = letterGraph.containsEdge(new Edge<>('a', 'f')); //should be false
//
//        //all of the following should throw an exception
////        letterGraph.addEdge(new Edge<>('a', 'a')); //self-loop
////        letterGraph.addEdge(new Edge<>('f', 'g')); //missing vertex
////        letterGraph.addEdge(new Edge<>('a', 'c')); //duplicate edge
//
//        double inf = Double.POSITIVE_INFINITY;
//
//        if (inf == Double.POSITIVE_INFINITY)
//        {
//            System.out.println("inf == inf");
//        }
    }
}
