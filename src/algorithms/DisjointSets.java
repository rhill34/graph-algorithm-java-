package algorithms;


/**
 *
 * @author Robert Hill
 * 12/8/2019
 * @version 1.0.0
 *
 * The DisjointSets class is implemented as described in class and in your author's book.
 * From pages 221 - 226.
 */
public class DisjointSets {

    private int[] sets;
    private int count;

    /**
     * Constructor for an instance of DisJointSets class
     *
     * @param size int to represent the amount of Vertexes
     */
    public DisjointSets(int size) {
        sets = new int[size];
        setCount(size);

        for (int i = 0; i < size; i++) {
            sets[i] = -1;

        }
    }

    /**
     * getCount() returns the size count of the Instance
     *
     * @return int to represent Cardinal |V| of the instance
     */
    public int getCount() {
        return count;
    }

    private void setCount(int count) {
        this.count = count;
    }

    /**
     * This method find the parent Component relationship
     * find() operation returns an integer component identifier for a given site,
     *
     * @param element int to represent a Vertex in the Graph
     * @return int to represent the parent Vertex of the element
     */
    public int find(int element) {
        if (sets[element] < 0) {
            return element;
        } else {
            return sets[element] = find(sets[element]);
        }
    }

    /**
     * The union() operation merges two components
     * if the two sites are in different components,
     *
     * @param source int to represent the Source Vertex
     * @param dest   int to represent the Destination Vertex
     * @return boolean to represent if components were merged.
     */
    public boolean union(int source, int dest) {
        int sourceRoot = find(source);
        int destRoot = find(dest);

        if (sourceRoot != destRoot) {
            count--;

            if (sets[sourceRoot] < sets[destRoot]) {
                sets[destRoot] = sourceRoot;
            } else if (sets[destRoot] < sets[sourceRoot]) {
                sets[sourceRoot] = destRoot;
            } else {
                sets[destRoot] = sourceRoot;
                sets[sourceRoot]--;
            }
            return true;
        }
        return false;
    }

    /**
     * To String stubbed for linter compliance
     *
     * @return count
     */
    @Override
    public String toString() {
        return "count" + getCount();
    }
}
