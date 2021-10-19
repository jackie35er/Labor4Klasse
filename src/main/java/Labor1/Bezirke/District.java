package Labor1.Bezirke;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record District(int licensePlate, String name, int population) implements Comparable<District> {
    @Override
    public int compareTo(District o) {
        return Comparator.comparing(District::name)
                .compare(this,o);
    }

    public static Collection<District> readCSV(@NotNull Path path) throws IOException {
        try(Stream<String> lines = Files.lines(path).skip(1);){
            return lines.map(District::csvStringToObject)
                    .sorted()
                    .collect(Collectors.toList());
        }
    }

    public static District csvStringToObject(@NotNull String csvString){
        String[] splits = csvString.split(",");
        try{
            int id = Integer.parseInt(splits[0]);
            int population = Integer.parseInt(splits[2].replace(".", ""));
            return new District(id,splits[1], population);
        }
        catch (NumberFormatException e){
            throw new IllegalArgumentException("CSV line Invadlid: " + csvString);
        }
    }

    @Override
    public String toString() {
        return name +": " + licensePlate + " - " + population;
    }
}
