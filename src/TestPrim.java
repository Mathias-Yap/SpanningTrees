public class TestPrim {
    public static void main(String[]args) {
        WeightedGraph faka = RandomGraph.randomGraph(5);
        faka.printGraph();
        System.out.println("");
        faka.minimumSpanningTree().printGraph();
    }
}
