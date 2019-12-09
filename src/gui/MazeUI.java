package gui;

import generation.AlgorithmType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.NANOS;

/**
 * Provides scaffolding for a Maze generation program
 * using the Java FX GUI framework.
 *
 * DO NOT EDIT THIS FILE!
 *
 * @author Josh Archer
 * @version 1.0
 */
public abstract class MazeUI extends Application
{
    private static final double SPACER = 10;
    private static final double BUTTON_WELL = 30;
    private static final double CANVAS_WIDTH = 1000;
    private static final double CANVAS_HEIGHT = 700;
    private static final double WIN_WIDTH = CANVAS_WIDTH + 2 * SPACER;
    private static final double WIN_HEIGHT = CANVAS_HEIGHT + BUTTON_WELL + 3 * SPACER;
    public static final int MIN_MAZE_SIZE = 4;
    public static final int MAX_MAZE_SIZE = 1000;
    public static final int INITIAL_MAZE_SIZE = 20;
    public static final int NANOS_IN_SECOND = 1000000000;
    public static final int GRAY_COMPONENT = 230;

    //Java FX controls
    private Canvas canvas;
    private GraphicsContext graphics;
    private Text printTextbox;
    private Button solveDFS;
    private Button solveBFS;

    //size of them maze
    private double cellWidth, cellHeight;
    private int rows, cols;

    @Override
    public void start(Stage stage)
    {
        Scene scene = getScene();
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);

        stage.setTitle("Generating mazes!");
        stage.show();
    }

    //assembles the controls on the user interface
    private Scene getScene()
    {
        //panels
        VBox panel = new VBox();
        panel.setId("main-panel");
        HBox controls = new HBox();
        controls.setId("controls-region");

        //create controls
        createCanvas();
        createButtons(controls);
        createGridSlider(controls);
        printTextbox = new Text();

        //add any remaining children
        controls.getChildren().add(printTextbox);
        panel.getChildren().addAll(canvas, controls);

        return new Scene(panel, WIN_WIDTH, WIN_HEIGHT);
    }

    //creates the canvas to draw the maze upon
    private void createCanvas()
    {
        canvas = new Canvas();
        canvas.setWidth(CANVAS_WIDTH);
        canvas.setHeight(CANVAS_HEIGHT);
        graphics = canvas.getGraphicsContext2D();
    }

    //creates the button controls and event handlers in the UI
    private void createButtons(HBox controls)
    {
        Button generate = createButton("Generate!");
        solveDFS = createButton("Solve with DFS!");
        solveBFS = createButton("Solve with BFS!");
        enableDFSBFS(solveDFS, solveBFS, true);

        //button handlers for generating and solving mazes
        generate.setOnAction(event -> MazeUI.this.solve(AlgorithmType.GENERATE_MAZE, false));
        solveDFS.setOnAction(event -> MazeUI.this.solve(AlgorithmType.DFS, true));
        solveBFS.setOnAction(event -> MazeUI.this.solve(AlgorithmType.BFS, true));

        controls.getChildren().addAll(generate, solveDFS, solveBFS);
    }

    private void solve(AlgorithmType type, boolean disableButtons)
    {
        solveAndPrint(type);
        enableDFSBFS(solveDFS, solveBFS, disableButtons);
    }

    //controls which DFS/BFS button is enabled
    private void enableDFSBFS(Button dfs, Button bfs, boolean state)
    {
        dfs.disableProperty().set(state);
        bfs.disableProperty().set(state);
    }

    //creates the slider that controls the size of the maze
    private void createGridSlider(HBox controls)
    {
        Slider slider = new Slider();
        slider.setMin(MIN_MAZE_SIZE);
        slider.setMax(MAX_MAZE_SIZE);

        TextField gridDimensions = new TextField();
        gridDimensions.setId("grid-dimensions-display");
        gridDimensions.setEditable(false);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            rows = cols = newValue.intValue();
            gridDimensions.setText(rows + "x" + cols + " maze");

            //save cell dimensions
            cellWidth = canvas.getWidth() / cols;
            cellHeight = canvas.getHeight() / rows;
        });
        slider.setValue(INITIAL_MAZE_SIZE);

        controls.getChildren().addAll(slider, gridDimensions);
    }

    //creates a button in the controls section at the bottom of the UI
    private Button createButton(String text)
    {
        Button button = new Button(text);
        button.setPrefHeight(BUTTON_WELL);
        return button;
    }

    //runs an algorithm and prints the time elapsed during the execution
    private void solveAndPrint(AlgorithmType type)
    {
        LocalTime before = LocalTime.now();
        runAlgorithm(type);

        double nanos = Math.abs(NANOS.between(LocalTime.now(), before));
        double seconds = nanos / NANOS_IN_SECOND;

        NumberFormat formatter = new DecimalFormat("#.0000");
        String secondsPrint = formatter.format(seconds);
        printTextbox.setText(type.toString().toLowerCase().replace("_", " ") +
                ": " + secondsPrint + " seconds");
    }

    /**
     * Runs one of three algorithms_pt1 in a child class:
     *
     * GENERATE_MAZE: Generates a random maze using the DisjointSets class, which in turn
     * implements the unionByHeight-find algorithm. A graph is then used to represent the cells
     * in the maze. The graph is then used to draw the maze to this GUI using the protected
     * methods seen below.
     *
     * DFS: Performs the DFS traversal on the graph containing the maze. Then the
     * path from the starting vertex (0, 0) in the grid to the final position (cols - 1, rows - 1)
     * is highlighted in the GUI.
     *
     * BFS: Performs the alternative BFS traversal on the maze-graph and highlights
     * the solution on the provided GUI.
     *
     * @param type the algorithm to execute
     */
    protected abstract void runAlgorithm(AlgorithmType type);

    /**
     * Returns the number of rows in the maze.
     * @return the number of rows from 4-1000
     */
    protected int getRows()
    {
        return rows;
    }

    /**
     * Returns the number of cols in the maze.
     * @return the number of cols from 4-1000
     */
    protected int getCols()
    {
        return cols;
    }

    /**
     * Clears the canvas of any drawn content.
     */
    protected void clearScreen()
    {
        graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Draws a muted grid in the background of the GUI, where each cell of the
     * maze can be seen. It is expected that you use this method for debugging
     * purposes when drawing the randomly generated maze.
     */
    protected void drawBackgroundGrid()
    {
        //light background
        graphics.setStroke(Color.rgb(GRAY_COMPONENT, GRAY_COMPONENT, GRAY_COMPONENT));

        //draw rows
        for (int i = 0; i <= rows; i++)
        {
            double currentY = i * cellHeight;
            graphics.strokeLine(0, currentY, cols * cellWidth, currentY);
        }

        //draw cols
        for (int i = 0; i <= cols; i++)
        {
            double currentX = i * cellWidth;
            graphics.strokeLine(currentX, 0, currentX, rows * cellHeight);
        }
    }

    /**
     * Sets the width of the line to draw with when displaying the elements of the maze.
     * @param width the width of the line
     */
    protected void setStrokeWidth(int width)
    {
        graphics.setLineWidth(width);
    }

    /**
     * Sets the color of the line to draw with when displaying the elements of the maze.
     * @param color the color of the line
     */
    protected void setStrokeColor(Color color)
    {
        graphics.setStroke(color);
    }

    /**
     * Sets the background color to draw when displaying elements of the maze.
     * @param color the color of the background
     */
    protected void setFillColor(Color color)
    {
        graphics.setFill(color);
    }

    /**
     * Draws a cell in the maze, given a position and an array of walls.
     * @param index the index of the cell to draw from 0 - (rows * cols - 1)
     * @param walls an array of flags
     */
    protected void drawCell(int index, boolean[] walls)
    {
        int col = index % cols;
        int row = index / cols;

        double leftX = col * cellWidth;
        double rightX = (col + 1) * cellWidth;
        double topY = row * cellHeight;
        double bottomY = (row + 1) * cellHeight;

        if (walls[0]) { graphics.strokeLine(leftX, topY, rightX, topY); } //top
        if (walls[1]) { graphics.strokeLine(rightX, topY, rightX, bottomY); } //right
        if (walls[2]) { graphics.strokeLine(leftX, bottomY, rightX, bottomY); } //bottom
        if (walls[3]) { graphics.strokeLine(leftX, topY, leftX, bottomY); } //left
    }

    /**
     * Highlights the background of a cell in the maze.
     * @param index the index of the cell to highlight from 0 - (rows * cols - 1)
     */
    protected void fillCell(int index)
    {
        int col = index % cols;
        int row = index / cols;

        double spacer = cellWidth / 10;

        double leftX = col * cellWidth + spacer;
        double rightX = (col + 1) * cellWidth - spacer;
        double topY = row * cellHeight + spacer;
        double bottomY = (row + 1) * cellHeight - spacer;

        graphics.fillRect(leftX, topY, rightX - leftX, bottomY - topY);
    }

    @Override
    public String toString()
    {
        return "MazeUI {" + rows + "x" + cols + "}";
    }
}
