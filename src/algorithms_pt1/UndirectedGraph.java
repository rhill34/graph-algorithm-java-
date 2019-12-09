package algorithms_pt1;


import gui.MazeUI;

import java.util.*;

    public class UndirectedGraph<V> implements IGraph<V>
    {
        int[][] maze;
        private Map<V, Node<V>> aLists;
        private Node<V> vertexList[][];
        private int edgeSize = 0;

        Vertex<V> vertexListV[][];  //2d array of the vertices
        private int amountVertices; //total vertices in maze
        private int dimension; // dimension of maze
        private Random myRandGen; // random number generator
        private Vertex<V> startVertex; //start of maze
        private Vertex<V> endVertex; //end of maze
        private int time; //time used for dfs
        private int[] traversed; //array either 0 or 1. 0 means that order # hasn't been selected and 1 means it has been

        private MazeUI mazeUI;

        public UndirectedGraph()
        {
            aLists = new HashMap<>();
        }

        public UndirectedGraph(int dimension_in) {
            this.vertexListV = new Vertex[dimension_in][dimension_in];
            // for simplicity of naming vertices, r keeps track of what "row" the
            // vertex is being created in
            int r = 1;
            for (int i = 0; i < dimension_in; i++) {
                for (int j = 0; j < dimension_in; j++) {
                    vertexListV[i][j] = new Vertex(i + r + j);
                }
                // r increases by a function of the length of the column
                r += dimension_in - 1;
            }

            dimension = dimension_in;
            amountVertices = dimension * dimension;
            myRandGen = new java.util.Random(0); // seed is 0
            startVertex = vertexListV[0][0]; // set startVertex to top left
            endVertex = vertexListV[dimension - 1][dimension - 1]; // set endVertex to bottom right
            traversed = new int[dimension*dimension];
        }

        @Override
        public void addVertex(V vertex)
        {
            //only add if the vertex is new
            if (!containsVertex(vertex))
            {
                //null list until an edge is added
                aLists.put(vertex, null);
            }
        }

        @Override
        public void addVertices(V... vertices)
        {
            //take each input vertex and add it
            //using our singular add() method
            for (V vertex : vertices)
            {
                addVertex(vertex);
            }
        }

        @Override
        public void addEdge(Edge<V> edge)
        {
            checkValidEdge(edge);

            edgeSize++;

            //otherwise add the edge
            if (aLists.get(edge.getSource()) == null)
            {
                aLists.put(edge.getSource(), new Node(edge.getDest()));
            }
            else
            {
                Node oldHead = aLists.get(edge.getSource());
                Node newHead = new Node(edge.getDest(), oldHead);
                aLists.put(edge.getSource(), newHead);
            }
        }

        private void checkValidEdge(Edge<V> edge)
        {
            //prevent self loops
            if (edge.getSource().equals(edge.getDest()))
            {
                throw new IllegalArgumentException("No self loops allowed!");
            }

            //make sure the vertices exist
            if (!containsVertex(edge.getSource()))
            {
                throw new IllegalArgumentException("Source " + edge.getSource() +
                        " does not exist!");
            }
            else if (!containsVertex(edge.getDest()))
            {
                throw new IllegalArgumentException("Dest " + edge.getDest() +
                        " does not exist!");
            }

            //make sure the edge is new
            if (containsEdge(edge))
            {
                throw new IllegalArgumentException("Edge " + edge + " already exists");
            }
        }

        @Override
        public void addEdges(Edge<V>... edges)
        {
            for (Edge<V> edge : edges)
            {
                addEdge(edge);
            }
        }

        @Override
        public void removeVertex(V vertex)
        {

        }

        @Override
        public void removeEdge(Edge<V> edge)
        {

        }

        public Map<V, V> dijkstras(V source)
        {
            //double check my input
            if (!containsVertex(source))
            {
                throw new IllegalArgumentException("Source vertex does not exist in graph - " + source);
            }

//            //create helper algorithms
//            Map<V, V> spanningTree = new HashMap<>();
//            Map<V, Double> labels = new HashMap<>();
//            PriorityQueue<WeightedPair<V>> pairs = new PriorityQueue<>();
//
//            //set up initial labels
//            labels.put(source, 0.0);
//            pairs.add(new WeightedPair<>(source, 0.0));
//            spanningTree.put(source, null);
//
//            for (V vertex : aLists.keySet())
//            {
//                if (!vertex.equals(source))
//                {
//                    labels.put(vertex, Double.POSITIVE_INFINITY);
//                    pairs.add(new WeightedPair<>(vertex, Double.POSITIVE_INFINITY));
//                }
//            }
//
//            //perform dijstra's routine
//            while (!pairs.isEmpty())
//            {
//                //pull out a pair
//                WeightedPair<V> pair = pairs.remove();
//                V vertex = pair.getVertex();
//                double label = pair.getLabel();
//
//                //update adjacent vertices and finalize the current label
//                Node adjacent = aLists.get(vertex);
//                while (adjacent != null)
//                {
//                    //ask whether I should update the adjacent vertex
//                    double candidate = adjacent.weight + label;
//                    double adjacentVertexWeight = labels.get(adjacent.destVertex);
//
//                    if (candidate < adjacentVertexWeight)
//                    {
//                        //update the value in the map and heap
//                        labels.put(adjacent.destVertex, candidate);
//                        pairs.remove(new WeightedPair<>(adjacent.destVertex, 0.0));
//                        pairs.add(new WeightedPair<>(adjacent.destVertex, candidate));
//
//                        spanningTree.put(adjacent.destVertex, vertex);
//                    }
//                    adjacent = adjacent.next;
//                }
//            }

//            return spanningTree;
            return null;
        }

        @Override
        public void clear()
        {
            //clear out adjacency lists
            aLists.clear();
            edgeSize = 0;
        }

        @Override
        public Set<V> vertices()
        {
            //create a new set to try and prevent
            //concurrent modification exceptions
            //in for loops outside this class
            return new HashSet<>(aLists.keySet());
        }

        @Override
        public Set<Edge<V>> edges()
        {
            Set<Edge<V>> results = new HashSet<>();

            //loop over all adjacency lists
            for (V vertex : aLists.keySet())
            {
                Node<V> node = aLists.get(vertex);
                while (node != null)
                {
                    Edge<V> edge = new Edge<>(vertex, node.destVertex);
                    results.add(edge);
                    node = node.next;
                }
            }

            return results;
        }

        @Override
        public boolean containsVertex(V vertex)
        {
            return aLists.containsKey(vertex);
        }

        @Override
        public boolean containsEdge(Edge<V> edge)
        {
            if (edge.getSource().equals(edge.getDest()))
            {
                return false;
            }

            //make sure the vertices exist
            if (!containsVertex(edge.getSource()) || !containsVertex(edge.getDest()))
            {
                return false;
            }

            //otherwise, search for the edge
            Node<V> node = aLists.get(edge.getSource());
            while (node != null)
            {
                if (node.destVertex.equals(edge.getDest()))
                {
                    return true;
                }
                node = node.next;
            }

            return false;
        }

        @Override
        public int vertexSize()
        {
            //return the number of pairs in the map
            return aLists.size();
        }

        @Override
        public int edgeSize()
        {
            return edgeSize;
        }


        private class Node<V>
        {

            private V destVertex;
            private Node<V> next;
            private double weight;

            public Node(V vertex)
            {
                this(vertex, null);
            }

            public Node(V vertex, double weight)
            {
                this.destVertex = vertex;
                this.weight = weight;
            }

            public Node(V destVertex, Node next)
            {
                this.destVertex = destVertex;
                this.next = next;
            }

            public Node(V destVertex, Node next, double weight)
            {
                this.destVertex = destVertex;
                this.next = next;
                this.weight = weight;
            }

            @Override
            public String toString() {
                return "Node{" +
                        "destVertex=" + destVertex +
                        ", next=" + next +
//                        ", weight=" + weight +
                        '}';
            }
        }

        private class Vertex<V> {
            private  int label; // to hold the name / number of the vertex
            Vertex<V>[] neighbors; // array of neighbors, adjacency list
            private int[] walls; //array of walls, -1 edge of maze, 0 broken wall, 1 intact wall, 4 entry/exit
            private int color; // white 0, grey 1, black 2
            private Vertex<V> pi; // parent
            private int startTime; // found time when it turns grey
            private int endTime; // when it turns black
            private int distance; // ADDED
            private boolean inPath; //true if the Vertex is in the solution path and false if it is not
            private int traverseOrder; //the order in which the vertex is traversed while solving


            public Vertex(int lab) {
                label = lab;
                /*
                 * index correspondence for neighbors 0 = up 1 = right 2 = down 3 = left
                 */
                neighbors = new Vertex[4];
                walls = new int[4];
                /*
                 * index correspondence for walls 0 = up wall;  1 = right wall;
                 * 2 = down wall;  3 = left wall
                 */
                setAllWallsIntact();
                startTime = Integer.MAX_VALUE; //start time = infinity
                endTime = Integer.MAX_VALUE; //end time = infinity
                pi = null;
                distance = 0;
                inPath = false;
                traverseOrder = 0;
            }

            /*
             * checks if all walls intact
             */
            public boolean allWallsIntact() {
                for (int i = 0; i < walls.length; i++) {
                    if (walls[i] == 0) {
                        return false;
                    }
                }
                return true;
            }

            /*
             * sets all walls intact
             */
            public void setAllWallsIntact() {
                for (int i = 0; i < walls.length; i++) {
                    walls[i] = 1;
                }
            }


            /*
             * Methods to break walls
             */
            public void breakUpWall() {
                if (walls[0] != -1)
                    walls[0] = 0;
            }

            public void breakRightWall() {
                if (walls[1] != -1)
                    walls[1] = 0;
            }

            public void breakDownWall() {
                if (walls[2] != -1)
                    walls[2] = 0;
            }

            public void breakLeftWall() {
                if (walls[3] != -1)
                    walls[3] = 0;
            }
            /*
             * Methods to set neighbors
             */
            public void setLeft(Vertex<V> v) {
                neighbors[3] = v;
            }

            public void setRight(Vertex<V> v) {
                neighbors[1] = v;
            }

            public void setUp(Vertex<V> v) {
                neighbors[0] = v;
            }

            public void setDown(Vertex<V> v) {
                neighbors[2] = v;
            }


            /*
             * Methods to get neighbors
             */
            public Vertex<V> getLeft() {
                return this.neighbors[3];
            }

            public Vertex<V> getRight() {
                return this.neighbors[1];
            }

            public Vertex<V> getUp() {
                return this.neighbors[0];
            }

            public Vertex<V> getDown() {
                return this.neighbors[2];
            }
            /*
             * finds the relationship between this vertex and vertex v
             */
            public int vertexRelationship(Vertex<V> v) {
                if (getUp() != null && getUp().equals(v)) {
                    return 0;
                } else if (getRight() != null && getRight().equals(v)) {
                    return 1;
                } else if (getDown() != null && getDown().equals(v)) {
                    return 2;
                } else { // if (getLeft().equals(v)){
                    return 3;
                }
            }

        }

        /*
         * sets the path of the solution using the parent and working backwards
         * useful for printing the DFS and BFS
         */
        public void setPath(){
            Vertex<V> current = vertexListV[dimension-1][dimension-1];
            while (current != null){
                current.inPath = true;
                current = current.pi;
            }
        }

        /*
         * resets graph
         * makes all vertices in vertex list to white, changes start and end times back to infinity, distance from source to 0
         */
        public void graphReset() {
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    vertexListV[i][j].color = 0;
                    vertexListV[i][j].pi = null;
                    vertexListV[i][j].startTime = Integer.MAX_VALUE; //infinity
                    vertexListV[i][j].endTime = Integer.MAX_VALUE;
                    vertexListV[i][j].distance = 0;
                }
            }
            startVertex.walls[0] = 4; //sets startVertex up wall as entry point
            endVertex.walls[2] = 4; //sets endVertex down wall as exit point
        }

        /**
         * DFS Solution
         * Solves the maze using Depth-first search, and uses a stack.
         * @param s - the starting vertex - the root
         */
        public void DFS(Vertex<V> s) {
            int traverseO = 1;
            Stack<Vertex<V>> q = new Stack<>();
            q.push(s);
            while (!q.isEmpty() && !q.peek().equals(endVertex)) { //while we aren't at the end
                Vertex<V> u = q.pop();
                for (int i = 0; i < u.neighbors.length; i++) {
                    Vertex<V> v = u.neighbors[i];
                    int direction = u.vertexRelationship(v);
                    if ((u.walls[direction] == 0) && v != null && v.color == 0) { //if the wall is broken and it's not null and it hasn't been visited
                        v.color = 1; //color = grey
                        if (v.traverseOrder == 0){ //if this is the first time it has been visited
                            if (traversed[traverseO] == 0){ //if this number hasn't been used
                                v.traverseOrder = traverseO;
                                traversed[traverseO] = 1;	//set it to used
                            } else {		//if the number has been used
                                traverseO++;
                                v.traverseOrder = traverseO;
                                traversed[traverseO] = 1; //set it to used
                            }
                        }
                        v.distance = u.distance + 1;
                        v.pi = u;
                        q.push(v);
                    }
                }
                u.color = 2; //color = black
            }
        }

        /**
         * BFS: Breadth-first Search solution to the maze
         * uses a queue
         *
         * @param s - the starting vertex - the root
         */
        public void BFS(Vertex<V> s) {
            int traverseO = 1;
            Queue<Vertex<V>> q = new LinkedList<>();
            q.add(s);
            while (!q.isEmpty() && !q.peek().equals(endVertex)) { //while we aren't at the end
                Vertex<V> u = q.remove();
                for (int i = 0; i < u.neighbors.length; i++) {
                    Vertex<V> v = u.neighbors[i];
                    int direction = u.vertexRelationship(v);
                    if ((u.walls[direction] == 0) && v != null && v.color == 0) { //if the wall is broken and it's not null and it hasn't been visited
                        v.color = 1; //color = grey
                        if (v.traverseOrder == 0){ //if this is the first time it has been visited
                            if (traversed[traverseO] == 0){ //if this number hasn't been used
                                v.traverseOrder = traverseO;
                                traversed[traverseO] = 1;	//set it to used
                            } else {		//if the number has been used
                                traverseO++;
                                v.traverseOrder = traverseO;
                                traversed[traverseO] = 1; //set it to used
                            }
                        }
                        v.distance = u.distance + 1;
                        v.pi = u;
                        q.add(v);
                    }
                }
                u.color = 2; //color = black
            }
        }

        /*
         * populates graph to the dimension provided in the constructor of graph
         * also sets the neighbors and walls of the vertices
         */
        public void populateGraph() {
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    Vertex<V> mine = vertexListV[i][j];

                    if (i == 0) {
                        mine.setUp(null);
                        mine.walls[0] = -1; // edge wall
                    }

                    else {
                        mine.setUp(vertexListV[i - 1][j]); //setting the up wall
                        mine.neighbors[0] = vertexListV[i - 1][j]; //setting up neighbor
                    }

                    if (j == 0) {
                        mine.setLeft(null);
                        mine.walls[3] = -1;
                    }

                    else {
                        mine.setLeft(vertexListV[i][j - 1]);
                        mine.neighbors[3] = vertexListV[i][j - 1];
                    }

                    if (i == dimension - 1) {
                        mine.setDown(null);
                        mine.walls[2] = -1;
                    }

                    else {
                        mine.setDown(vertexListV[i + 1][j]);
                        mine.neighbors[2] = vertexListV[i + 1][j];
                    }

                    if (j == dimension - 1) {
                        mine.setRight(null);
                        mine.walls[1] = -1;
                    }

                    else {
                        mine.setRight(vertexListV[i][j + 1]);
                        mine.neighbors[1] = vertexListV[i][j + 1];
                    }
                }
            }
        }

        /**
         * Get a random number
         *
         * @return random double between 0 and 1
         */
        double myRandom() {
            return myRandGen.nextDouble();
        }

        /**
         * Generates a pseudo-random perfect maze.
         */
        void generateMaze() {
            //the general algorithm
            /*
             * create a CellStack (LIFO) to hold a list of cell locations set
             * TotalCells= number of cells in grid choose the starting cell and call
             * it CurrentCell set VisitedCells = 1 while VisitedCells < TotalCells
             * find all neighbors of CurrentCell with all walls intact if one or
             * more found choose one at random knock down the wall between it and
             * CurrentCell push CurrentCell location on the CellStack make the new
             * cell CurrentCell add 1 to VisitedCells else pop the most recent cell
             * entry off the CellStack make it CurrentCell
             */

            graphReset(); //first reset the graph
            populateGraph();

            //4 is a special designation for starting and ending vertices
            startVertex.walls[0] = 4;
            endVertex.walls[2] = 4;

            Stack<Vertex<V>> cellStack = new Stack<>();
            int totalCells = amountVertices;
            Vertex<V> currentCell = vertexListV[0][0];
            int visitedCells = 1;
            while (visitedCells < totalCells) {

                ArrayList<Vertex<V>> neighborsIntact = new ArrayList<>();
                for (int i = 0; i < currentCell.neighbors.length; i++) {
                    Vertex<V> neighbor = currentCell.neighbors[i];
                    if (neighbor != null) {
                        if (neighbor.allWallsIntact()) {
                            neighborsIntact.add(neighbor);
                        }
                    }
                }
                // if one or more walls
                if (neighborsIntact.size() != 0) {
                    // rand tells you which vertex you are knocking down a wall between
                    int rand = (int) (myRandom() * neighborsIntact.size());
                    Vertex<V> knockDown = neighborsIntact.get(rand);
                    int relationship = currentCell.vertexRelationship(knockDown);
                    // finds relationship between current and knockDown
                    if (relationship == 0) {// knockDown is above currentCell
                        currentCell.breakUpWall();
                        knockDown.breakDownWall();
                    } else if (relationship == 1) { // knockDown is to the right of currentCell
                        currentCell.breakRightWall();
                        knockDown.breakLeftWall();
                    } else if (relationship == 2) { // knockDown is below currentCell
                        currentCell.breakDownWall();
                        knockDown.breakUpWall();
                    } else { // knockDown is to the left of currentCell
                        currentCell.breakLeftWall();
                        knockDown.breakRightWall();
                    }
                    // push CurrentCell location on the CellStack
                    cellStack.push(currentCell);
                    // make the new cell CurrentCell
                    currentCell = knockDown;
                    // add 1 to VisitedCells
                    visitedCells++;

                } else {
                    currentCell = cellStack.pop();
                }
            }

        }

        /*
         * Prints empty grid
         * Prints all the top walls, the left and right, and the bottom row for an empty Grid
         */
        public String printGrid() {
            String grid = "";
            // first print the top layer

            int n = 2;
            for (int i = 0; i < dimension; i++) {
                if (i == dimension - 1)
                    n = 3;
                for (int layer = 1; layer <= n; layer++) {
                    // layer represents the layers of a cell: up, left/right, and down
                    // top layer already printed; for the rest, print sides and bottom
                    // 1 = top
                    // 2 = left and right
                    // 3 = bottom

                    if (layer == 1) {
                        grid += "+"; //first symbol of layer 1
                    }

                    if (layer == 2) {
                        grid += "|"; //first symbol of layer 2
                    }

                    if ((layer == 3) && (i == dimension - 1)) {
                        grid += "+"; //first symbol of layer three
                    }
                    for (int j = 0; j < dimension; j++) {
                        Vertex v = vertexListV[i][j];

                        // prints according to the layer
                        // layer one --> print up
                        if (layer == 1) {
                            if ((v.walls[0] != 0) && (v.walls[0] != 4)) // if -1, edge wall; if 1, inner wall, 0 is broken wall
                                grid += "-";
                            else
                                grid += " ";

                            grid += "+";
                        }

                        // layer two --> print left/right and label
                        else if (layer == 2) {
                            grid += " ";

                            if (v.walls[1] != 0) // right wall is 1
                                grid += "|";
                            else
                                grid += " ";
                        }

                        // layer three --> print bottom layer
                        else if ((layer == 3) && (i == dimension - 1)) {
                            // down wall
                            // if there is an down wall, include symbol

                            if ((v.walls[2] != 0) && (v.walls[2] != 4)) // if -1, edge wall; if 1, inner  wall, 0 is broken wall, 4 is entrance/exit
                                grid += "-";
                            else
                                grid += " ";

                            grid += "+";
                        }
                    }
                    grid += "\n";
                }
            }
            return grid;
        }

        /*
         * Prints the grid for BFS and DFS - displays the numbers and the path
         *
         */
        public String printGrid1() {
            String grid = "";
            // first print the top layer

            int n = 2;
            for (int i = 0; i < dimension; i++) {
                if (i == dimension - 1)
                    n = 3;
                for (int layer = 1; layer <= n; layer++) {
                    // layer represents the layers of a cell: up, left/right, and
                    // down
                    // top layer already printed; for the rest, print sides and
                    // bottom
                    // 1 = top
                    // 2 = left and right
                    // 3 = bottom

                    if (layer == 1) {
                        grid += "+";
                    }

                    if (layer == 2) {
                        grid += "|";
                    }

                    if ((layer == 3) && (i == dimension - 1)) {
                        grid += "+";
                    }for (int j = 0; j < dimension; j++) {
                        Vertex v = vertexListV[i][j];

                        // prints according to the layer
                        // layer one --> print up
                        if (layer == 1) {
                            if ((v.walls[0] != 0) && (v.walls[0] != 4)) // if -1, edge wall; if 1, inner wall, 0 is broken
                                grid += "-";
                            else
                                grid += " ";

                            grid += "+";
                        }

                        // layer two --> print left/right and label
                        else if (layer == 2) {

                            // print label

                            if ((v != null) && v == (vertexListV[0][0])){
                                grid += "0";
                            } else if (v.pi == null) { // don't print label
                                grid += " ";
                            } else {
                                grid += ((v.traverseOrder)%10);
                            }


                            if (v.walls[1] != 0) // right wall is 1
                                grid += "|";
                            else
                                grid += " ";
                        }

                        // layer three --> print bottom layer
                        else if ((layer == 3) && (i == dimension - 1)) {
                            // down wall
                            // if there is an down wall, include symbol
                            if ((v.walls[2] != 0) && (v.walls[2] != 4)) // if -1, edge wall; if 1, inner wall, 0 is broken wall, 4 is entry/exit
                                grid += "-";
                            else
                                grid += " ";

                            grid += "+";

                        }
                    }
                    grid += "\n";
                }
            }

            return grid;
        }

        /*
         * Prints the maze with the '#'s and not numbers - for BFS and DFS
         */
        public String printGrid2() {
            String grid = "";
            // first print the top layer

            int n = 2;
            for (int i = 0; i < dimension; i++) {
                if (i == dimension - 1)
                    n = 3;
                for (int layer = 1; layer <= n; layer++) {
                    // layer represents the layers of a cell: up, left/right, and
                    // down
                    // top layer already printed; for the rest, print sides and
                    // bottom
                    // 1 = top
                    // 2 = left and right
                    // 3 = bottom

                    if (layer == 1) {
                        grid += "+";
                    }

                    if (layer == 2) {
                        grid += "|";
                    }

                    if ((layer == 3) && (i == dimension - 1)) {
                        grid += "+";
                    }

                    for (int j = 0; j < dimension; j++) {
                        Vertex v = vertexListV[i][j];

                        // prints according to the layer
                        // layer one --> print up
                        if (layer == 1) {
                            if ((v.walls[0] != 0) && (v.walls[0] != 4)) // if -1, edge wall; if 1 inner wall if 0 broken wall														// wall
                                grid += "-";
                            else if (v.equals(startVertex)){
                                grid += "#";
                            }
                            else if (v.inPath && v.getUp() != null && v.getUp().inPath) //makes sure the one above is in path too
                                grid += "#";
                            else {
                                grid += " ";
                            }

                            grid += "+";
                        }

                        // layer two --> print left/right and label
                        else if (layer == 2) {
                            if (v == null) { // don't print label
                                grid += " ";
                            } else if (v.inPath){
                                grid += (("#"));
                            } else {
                                grid += " ";
                            }

                            if (v.walls[1] != 0) // right wall is 1
                                grid += "|";
                            else
                                grid += " ";
                        }

                        // layer three --> print bottom layer
                        else if ((layer == 3) && (i == dimension - 1)) {
                            // down wall
                            // if there is an down wall, include symbol

                            if ((v.walls[2] != 0) && (v.walls[2] != 4)) // if -1, edge wall; if 1, inner wall, 0 is broken wall, 4 is entry/exit
                                grid += "-";
                            else if (v.inPath)
                                grid += "#";
                            else
                                grid += " ";

                            grid += "+";

                        }
                    }
                    grid += "\n";
                }
            }

            return grid;
        }

        @Override
        public String toString() {
            return "UndirectedGraph{" +
                    "aLists=" + aLists +
                    ", edgeSize=" + edgeSize +
                    '}';
        }
    }

