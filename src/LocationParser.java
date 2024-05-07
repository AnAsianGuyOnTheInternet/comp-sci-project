import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationParser {
    private String filePath;

    public LocationParser(String filePath) {
        this.filePath = filePath;
    }

    public List<RoadSegment> loadRoadSegments() {
        List<RoadSegment> roadSegments = new ArrayList<>();
        boolean parse = false;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.equals("**BEGINPARSE**")) {
                    parse = true;
                    System.out.println("Parsing started.");
                    continue;
                } else if (line.equals("**ENDPARSE**")) {
                    parse = false;
                    System.out.println("Parsing ended.");
                    break;
                }
    
                if (parse) {
                    System.out.println("Processing line: " + line);
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        String roadName = parts[0].trim();
                        String[] distanceParts = parts[1].trim().split(" ");
                        int speedLimit = Integer.parseInt(parts[2].trim());
                        int dailyTraffic = 0; // Default value for unknown daily traffic
    
                        if (parts.length >= 4 && !parts[3].isEmpty()) {
                            dailyTraffic = Integer.parseInt(parts[3].trim().replace(",", ""));
                        }
    
                        if (distanceParts.length > 0) {
                            double distance = Double.parseDouble(distanceParts[0]);
                                                    
                            RoadSegment segment = new RoadSegment(roadName, distance, speedLimit, dailyTraffic);
                            roadSegments.add(segment);
                            System.out.println("Added segment: " + segment);
                        }
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading or parsing the grid file: " + e.getMessage());
        }
    
        return roadSegments;
    }
    


    public static class RoadSegment {
        String roadName;
        double distance;
        int speedLimit;
        int dailyTraffic;

        public RoadSegment(String roadName, double distance, int speedLimit, int dailyTraffic) {
            this.roadName = roadName;
            this.distance = distance;
            this.speedLimit = speedLimit;
            this.dailyTraffic = dailyTraffic;
        }

        @Override
        public String toString() {
            return "RoadSegment{" +
                   "roadName='" + roadName + '\'' +
                   ", distance=" + distance +
                   ", speedLimit=" + speedLimit +
                   ", dailyTraffic=" + dailyTraffic +
                   '}';
        }
    }

    public void test() {
        List<RoadSegment> segments = loadRoadSegments();
        for (RoadSegment segment : segments) {
            System.out.println(segment);
        }
    }
}
