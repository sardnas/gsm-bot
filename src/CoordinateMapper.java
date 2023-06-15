import java.util.HashMap;
import java.util.Map;

public class CoordinateMapper {
    private Map<Integer, Coordinate> coordinateMap;

    public CoordinateMapper() {
        coordinateMap = new HashMap<>();
    }

    public void addMapping(int x, int y, Coordinate secondCoordinate) {
        int key = computeKey(x, y);
        coordinateMap.put(key, secondCoordinate);
    }

    public Coordinate getSecondCoordinate(int x, int y) {
        int key = computeKey(x, y);
        return coordinateMap.get(key);
    }

    private int computeKey(int x, int y) {
        // You can choose any method to compute the key based on the x and y values
        // Here's a simple example: concatenating x and y as a single integer
        String keyString = String.valueOf(x) + String.valueOf(y);
        return Integer.parseInt(keyString);
    }
}
