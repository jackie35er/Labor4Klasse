package Labor5.persistence5;

import Labor5.domain5.Student;
import Labor5.domain5.Gender;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface StudentRepository {

    void deleteAll() throws SQLException;

    /**
     * Tries to find a student matching given data
     *
     * @param number            the student's number
     * @param schoolClass the student's class
     * @return the student wrapped in an {@link Optional}
     * @throws SQLException
     */
    Optional<Student> findByNumberAndClass(int number, String schoolClass) throws SQLException;

    /**
     * Returns all saved students.
     *
     * @return all students
     * @throws SQLException
     */
    Stream<Student> findAll() throws SQLException;

    /**
     * Returns all students of a class, sorted by their numbers.
     *
     * @param schoolClass the class
     * @return the students
     * @throws SQLException
     */
    Set<Student> findStudentsByClass(String schoolClass) throws SQLException;

    /**
     * Returns all students of a given gender, sorted by class and number.
     *
     * @param gender the gender
     * @return the students
     * @throws SQLException
     */
    Set<Student> findStudentsByGender(Gender gender) throws SQLException;

    /**
     * Returns the number of students for every class with classes sorted alphabetically.
     *
     * @return the map structured like map[class]=numberOfStudents
     * @throws SQLException
     */
    Map<String, Integer> findClasses() throws SQLException;

    void save(Student student) throws SQLException;
}
