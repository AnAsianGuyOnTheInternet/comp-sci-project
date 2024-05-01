package demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationParser{
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
                    continue;
                } else if (line.equals("**ENDPARSE**")) {
                    parse = false;
                    break;
                }

                if (parse) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        String roadName = parts[0].trim();
                        String distanceWithUnit = parts[1].trim();
                        String distance = distanceWithUnit.split(" ")[0].trim(); 
                        int speedLimit = Integer.parseInt(parts[2].trim());

                        // Optional: Parse daily traffic if available
                        int dailyTraffic = 0;
                        if (parts.length >= 4) {
                            dailyTraffic = Integer.parseInt(parts[3].trim().replace(",", ""));
                        }

                        roadSegments.add(new RoadSegment(roadName, distance, speedLimit, dailyTraffic));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the grid file: " + e.getMessage());
        }

        return roadSegments;
    }



    public static class RoadSegment {
        String roadName;
        String distance; // Storing as string to keep the "mi" unit; parse as needed
        int speedLimit;
        int dailyTraffic;

        public RoadSegment(String roadName, String distance, int speedLimit, int dailyTraffic) {
            this.roadName = roadName;
            this.distance = distance;
            this.speedLimit = speedLimit;
            this.dailyTraffic = dailyTraffic;
        }

        @Override
        public String toString() {
            return "RoadSegment{" +
                   "roadName='" + roadName + '\'' +
                   ", distance='" + distance + '\'' +
                   ", speedLimit=" + speedLimit +
                   ", dailyTraffic=" + dailyTraffic +
                   '}';
        }
    }
}