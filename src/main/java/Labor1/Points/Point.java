package Labor1.Points;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public record Point(int qudrant, double x, double y) {

    public static Collection<Point> readFromBinaryFile(@NotNull Path path) throws IOException {
        List<Point> list = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(path.toFile()))) {
            while (dis.available() >= 20) {//20 bytes -> 1 int and 2 double
                dis.skipBytes(4); //int quadrant = dis.readInt();
                double x = dis.readDouble();
                double y = dis.readDouble();
                int quadrant = getQudrantFromCordinates(x, y);
                list.add(new Point(quadrant, x, y));
            }
        }
        return list;
    }

    public static void writeToBinaryFile(@NotNull Path path, @NotNull List<Point> list) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(path.toFile()))) {
            for (Point point : list) {
                dos.writeInt(point.qudrant);
                dos.writeDouble(point.x);
                dos.writeDouble(point.y);
            }
        }
    }

    public static Map<Integer, Set<Point>> getQuadrantMap(@NotNull List<Point> points) {
        return points.stream()
                .collect(Collectors.toMap(
                        Point::qudrant,
                        a -> new HashSet<>(List.of(a)),
                        (oldValue, newValue) -> {
                            oldValue.addAll(newValue);
                            return oldValue;
                        }));
    }

    @Override
    public String toString() {
        return qudrant + ": " + x + " " + y;
    }

    public static int getQudrantFromCordinates(double x, double y) {
        if (x > 0 && y > 0) {
            return 1;
        } else if (x < 0 && y > 0) {
            return 2;
        } else if (x < 0 && y < 0) {
            return 3;
        } else if (x > 0 && y < 0) {
            return 4;
        }
        return 0;
    }


}
