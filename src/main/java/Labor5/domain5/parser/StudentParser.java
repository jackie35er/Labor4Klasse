package Labor5.domain5.parser;


import Labor5.domain5.Gender;
import Labor5.domain5.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentParser {

    /**
     * Converts all lines of a csv-formatted file to the students. Invalid lines are skipped
     *
     * @param path the path of the csv-file
     */
    public Collection<Student> readFromCsv(Path path) throws IOException {
        try(Stream<String> csv = Files.lines(path)){
            return csv.map(StudentParser::parse)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        }
    }

    public static Optional<Student> parse(String csvLine) {
        String[] splits = csvLine.split(",");
        if (splits.length != 5 || csvLine.endsWith(",")) {
            return Optional.empty();
        }

        return Student.get(splits[0], splits[1], Gender.parseGender(splits[2]), Integer.parseInt(splits[3]), splits[4]);
    }
}
