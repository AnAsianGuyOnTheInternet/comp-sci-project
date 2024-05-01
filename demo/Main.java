package demo;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        testLoadRoadSegments();
    }   

    private static void testLoadRoadSegments() {
        LocationParser loader = new LocationParser("");
        List<LocationParser.RoadSegment> segments = loader.loadRoadSegments();

        // Check the number of segments and print table header
        System.out.println("Total segments loaded: " + segments.size());
        System.out.println(String.format("%-40s %-10s %-15s %-15s", "Road Name", "Distance", "Speed Limit", "Daily Traffic"));
        System.out.println(String.format("%-40s %-10s %-15s %-15s", "---------", "--------", "-----------", "------------"));

        for (LocationParser.RoadSegment segment : segments) {
            System.out.println(String.format("%-40s %-10s %-15d %-15d",
                                             segment.roadName,
                                             segment.distance + " mi",
                                             segment.speedLimit,
                                             segment.dailyTraffic));
        }

        System.out.println("Test passed successfully!");
    }
}