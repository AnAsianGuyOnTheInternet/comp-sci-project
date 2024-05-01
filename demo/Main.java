package demo;

public class Main {
    public static void main(String[] args) {
        testLoadRoadSegments();
    }   

    private static void testLoadRoadSegments() {
        LocationParser loader = new LocationParser("demo\\locationdata.txt");
        loader.test();
    }
}