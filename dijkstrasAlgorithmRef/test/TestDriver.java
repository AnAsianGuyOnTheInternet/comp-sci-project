package dijkstrasAlgorithmRef.test;
import dijkstrasAlgorithmRef.algorithm.Map;

public class TestDriver {
    public static void main(String[] args) {
        Map testMap = new Map();
        testMap.populateWithRandom(10);
        System.out.println(testMap.toString());
        testMap.addNode("Example way, Frederick");
        testMap.addNode("french fry yeezy factory");
        testMap.addConnection("Example way, Frederick", "french fry yeezy factory", 10);
        System.out.println(testMap.toString());
    }
}
