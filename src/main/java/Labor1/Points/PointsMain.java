package Labor1.Points;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PointsMain {
    public static void main(String[] args) {
        List<Point> points;
        Path pathIn = Path.of("src/main/resources/labor1/punkte.dat");
        Path pathOut = Path.of("src/main/resources/labor1/punkteNew.dat");
        try {
            points = new ArrayList<>(Point.readFromBinaryFile(pathIn));
            Point.writeToBinaryFile(pathOut, points);
        } catch (IOException e) {
            return;
        }

        Map<Integer, Set<Point>> quadrantMap = Point.getQuadrantMap(points);
        //Map output
        for (int i = 1; i <= quadrantMap.size(); i++) {
            System.out.println(i);
            for (var a : quadrantMap.get(i)) {
                System.out.println(a);
            }
        }
    }
}
