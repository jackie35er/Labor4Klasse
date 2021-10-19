package Labor2.student;

import Labor2.student.domain.Gender;
import Labor2.student.domain.Student;
import org.jetbrains.annotations.NotNull;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentStatistics {

    private final Collection<Student> students;

    /**
     * Reads all students from given csv-File.
     *
     * @param path the file
     * @throws IOException happy Suprise
     */
    public StudentStatistics(Path path) throws IOException {
        try (Stream<String> studentStream = Files.lines(path)) {
            students = studentStream.skip(1).map(Student::of).collect(Collectors.toList());
        }
    }

    /**
     * Counts all students of given gender
     *
     * @param gender the gender
     * @return number of students of the given gender
     */
    public long countGender(Gender gender) {
        return students.stream()
                .filter(s -> s.gender() == gender)
                .count();
    }

    /**
     * Returns all students classes sorted alphabetically.
     *
     * @return all students classes sorted alphabetically
     */
    public SortedSet<String> getClasses() {
        return students.stream()
                .map(Student::schoolClass)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Returns a map containing the number students of each gender in a given class.
     *
     * @param schoolClass the class
     * @return count of students of each gender
     */
    public Map<Gender, Long> getGenderCountForClass(String schoolClass) {
        return students.stream()
                .filter(n -> n.schoolClass().equals(schoolClass))
                .collect(Collectors.groupingBy(
                        Student::gender,
                        Collectors.counting()
                ));
    }

    /**
     * Returns all students whose second name contains the given sequence.
     *
     * @param sequence the sequence to search for. Case sensitive
     * @return students named like %sequence%
     */
    public List<Student> getAllWithSecondNameLike(String sequence) {
        return students.stream()
                .filter(s -> s.secondName().contains(sequence))
                .collect(Collectors.toList());
    }

    /**
     * Finds a student by number and class
     *
     * @param number      students number
     * @param schoolClass students class
     * @return the student or Optional.empty if no student matches criteria
     */
    public Optional<Student> findByNumberAndClass(int number, String schoolClass) {
        return students.stream()
                .filter(s -> s.number() == number)
                .filter(s -> s.schoolClass().equals(schoolClass))
                .findFirst();
    }

    /**
     * Returns the student with the longest name.
     *
     * @return any student whose name contains the highest number of letters in all names
     */
    public Student getStudentWithLongestName() {
        Optional<Student> op = students.stream()
                .max(StudentStatistics::longerName);
        return op.orElse(null);
    }

    private static int longerName(@NotNull Student student1, @NotNull Student student2) {
        String str1 = student1.firstName() + student1.secondName();
        String str2 = student2.firstName() + student2.secondName();
        return Integer.compare(str1.length(), str2.length());
    }

    /**
     * Returns the most frequently found first names
     *
     * @param count how many should be returned
     * @return the topX most frequent first names
     */
    public Set<String> getMostFrequentFirstNames(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("count can't be 0 or less");
        }
        //Studentennamen mit Häufigkeit mappen
        return students.stream()
                .collect(Collectors.toMap(
                        Student::firstName,
                        n -> 1L,
                        (oldValue, newValue) -> oldValue + 1)
                )
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))//Absteigend nach Häufigkeit sortiern
                .map(Map.Entry::getKey)
                .limit(count)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Returns the number of students in each year.
     *
     * @return count of students of each year
     */
    public Map<Integer, Long> countStudentsByYear() {
        return students.stream()
                .collect(Collectors.toMap(
                        s -> s.schoolClass().charAt(0) - '0',
                        n -> 1L,
                        (oldValue, newValue) -> oldValue + 1
                ));
    }
}
