package gui;

import algorithms.*;
import generation.AlgorithmType;
import javafx.scene.paint.*;
import java.util.*;

/**
 * @author Robert Hill
 * 12/8/2019
 * MazeUICompleted.java
 * @version 1.0.0
 *
 * This class is used to run Algorithms that are displayed by the JavaFX code.
 */
public class MazeUICompleted extends MazeUI {
    private int size; //maze size
    private Undirected graph;
    private DisjointSets disJoint;
    private int[] wall;
    private Color black = Color.CHOCOLATE;
    private Random random = new Random();


    /**
     * This method takes Enumerators to execute events that will
     * run Code that is provided that draws the maze to the screen as seen in the
     * provided video.
     *
     * @param type the algorithm to execute
     */
    @Override
    public void runAlgorithm(AlgorithmType type) {

        //from the Rows and cols
        // can be altered in the UI
        // and the maze size changes accordingly.
        size = getCols() * getRows();
        int start = 0;
        int exit = size - 1;
        wall = new int[]{-getCols(), 1, getCols(), -1};
        disJoint = new DisjointSets(size);
        graph = new Undirected(size);

        // preparing canvas
        drawBackgroundGrid();

        if (type == AlgorithmType.GENERATE_MAZE) { // Creates Maze

            generateWall(size, wall);

        } else if (type == AlgorithmType.BFS) { // Breadth First Search
            generateWall(size, wall);
            setFillColor(Color.LIGHTSKYBLUE);
            graph.breadthFS(start);
            for (int cell :
                    graph.dFSpathTo(exit)) {
                fillCell(cell);
            }
        } else if (type == AlgorithmType.DFS) { // Depth First Search
            generateWall(size, wall);
            setFillColor(Color.LIGHTSKYBLUE);
            graph.dfs();
            for (int cell :
                    graph.dFSpathTo(exit)) {
                fillCell(cell);
            }
        }
    }

    private void genMaze() {

        // while there are values to union
        while (disJoint.getCount() > 1) {

            // set first cell (source)
            int firstCell = random.nextInt(size);

            // get second cell (destination)
            int secondCell = -1;

            // check the destination placement
            while (secondCell < 0 || secondCell >= size
                    || firstCell % getCols() == 0 && secondCell + 1 == firstCell
                    || secondCell % getCols() == 0 && firstCell + 1 == secondCell) {
                secondCell = wall[random.nextInt(wall.length)] + firstCell;
            }

            // union the source and destination
            if (this.disJoint.union(firstCell, secondCell)) {
                this.graph.addEdge(firstCell, secondCell);
                this.graph.addEdge(secondCell, firstCell);
            }
        }

    }

    private void generateWall(int size, int[] wall) {
        clearScreen();
        genMaze();
        setStrokeWidth(5);
        setStrokeColor(black);
        for (int paint = 0; paint < size; paint++) {
            drawCell(paint, new boolean[]{
                    !graph.hasEdge(paint, paint + wall[0]),
                    !graph.hasEdge(paint, paint + wall[1]),
                    !graph.hasEdge(paint, paint + wall[2]),
                    !graph.hasEdge(paint, paint + wall[3])});
        }
    }

    @Override
    public String toString() {
        return "MazeUICompleted{" +
                "Has a toString for the linter" +
                '}';
    }
}
