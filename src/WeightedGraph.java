import java.util.Arrays;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Comparator;

public class WeightedGraph {
    private int[][] adjMatrix;
    private int size;

    // Initialize an empty graph with a maximum of size vertices
    public WeightedGraph(int size) {
        adjMatrix = new int[size][size];
        this.size = size;
        for (int[] row : adjMatrix) {
            Arrays.fill(row, 0);
        }
    }

    // Returns the degree of vertex with id/index n
    public int degree(int n) {
        int degreeCounter = 0;
        for (int i = 0; i < size; i++) {
            if (adjMatrix[n][i] != 0) {
                degreeCounter++;
            }
        }
        return degreeCounter;
    }



    // Returns the total number of edges in the graph
    public int edgeCount() {
        int edgeCounter = 0;
        for (int[] row : adjMatrix) {
            for (int edge : row) {
                if (edge != 0) {
                    edgeCounter++;
                }
            }
        }
        return edgeCounter / 2;
    }

    // Returns the weight of the edge connecting vertices m and n.
    // If the vertices are equal, return 0.
    // If the vertices are not adjacent, return -1
    public int adjacent(int m, int n) {
        if (m == n) {
            return 0;
        }
        if (adjMatrix[m][n] == 0) {
            return -1;
        }
        return adjMatrix[m][n];
    }

    // Return the minimum spanning tree of this WeightedGraph in the form of a new WeightedGraph
    public WeightedGraph minimumSpanningTree() {

        PriorityQueue<Node> minHeap = new PriorityQueue<Node>(size, new compareNode());
        Node[] vertices = new Node[size];
        boolean[] mstSet = new boolean[size];

        Node firstVertex = new Node(0, 0);
        minHeap.add(firstVertex);
        vertices[0] = firstVertex;
        mstSet[0] = true;

        for(int i = 1; i<size; i++) {
            Node node = new Node(i,Integer.MAX_VALUE);
            node.mstParent = -1;
            vertices[i] = node;
            minHeap.add(node);
        }

        while(!minHeap.isEmpty()) {
            Node pulled = minHeap.poll();
            mstSet[pulled.vertex] = true;

            for(int i = 0; i<size; i++) {
                if(adjMatrix[pulled.vertex][i] != 0 && mstSet[i] == false) {
                    if(adjMatrix[pulled.vertex][i] < vertices[i].keyValue) {
                        minHeap.remove(vertices[i]);
                        vertices[i].mstParent = pulled.vertex;
                        vertices[i].keyValue = adjMatrix[pulled.vertex][i];
                        minHeap.add(vertices[i]);
                    }
                }
            }
        }

        //create the new graph.

        WeightedGraph MST = new WeightedGraph(size);
        for(int i = 1; i<size; i++) {
            if(vertices[i].mstParent != 0 ) {
                MST.insert(vertices[i].vertex, vertices[i].mstParent, adjMatrix[vertices[i].vertex][vertices[i].mstParent]);
            }
        }
        return MST;
    }

    // If the weight w is 0, remove any edge between m and n (if any).
    // Otherwise, add an edge between vertices m and n with weight w.
    // If an edge already exists, replace the weight of the edge with the new weight.
    // If the vertices do not exist or are equal, throw an exception.
    public void insert(int m, int n, int w) throws IllegalArgumentException {
        if (m == n || m > size || n > size || m < 0 || n < 0) {
            throw new IllegalArgumentException();
        }
        adjMatrix[m][n] = w;
        adjMatrix[n][m] = w;
    }

    // Give your submission a name here so you can recognise the results when posted (keep it civil though please :))
    public String getName() {
        return "Joe Biden";
    }

    public void printGraph() {
        for (int[] array : adjMatrix) {
            System.out.println(Arrays.toString(array));
        }
    }


    private class Node {
        public int vertex;
        public int keyValue;
        public int mstParent;

        public Node(int vertex, int keyValue) {
            this.vertex = vertex;
            this.keyValue = keyValue;
            this.mstParent = -1;
        }
    }

    private class compareNode implements Comparator<Node> {
        public int compare(Node n1, Node n2) {
            return n1.keyValue - n2.keyValue;
        }
    }
}

