package Labor5.persistence5;

import Labor5.domain5.Gender;
import Labor5.domain5.Student;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.summingInt;

public record JdbcStudentRepository(Connection connection) implements StudentRepository {


    @Override
    public void deleteAll() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("TRUNCATE TABLE students");
    }

    /**
     * Tries to find a student matching given data
     *
     * @param number      the student's number
     * @param schoolClass the student's class
     * @return the student wrapped in an {@link Optional}
     * @throws SQLException
     */
    @Override
    public Optional<Student> findByNumberAndClass(int number, String schoolClass) throws SQLException {
        String sql = "SELECT * from students " +
                "where student_number = ? and class = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1,number);
        statement.setString(2,schoolClass);

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return getStudentFromResultSet(resultSet);
    }



    /**
     * Returns all saved students.
     *
     * @return all students
     * @throws SQLException
     */
    @Override
    public Stream<Student> findAll() throws SQLException {
        String sql = "Select * from students";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        ArrayList<Optional<Student>> list = new ArrayList<>();
        while (resultSet.next()){
            list.add(getStudentFromResultSet(resultSet));
        }

        return list.stream().filter(Optional::isPresent).map(Optional::get);
    }

    /**
     * Returns all students of a class, sorted by their numbers.
     *
     * @param schoolClass the class
     * @return the students
     * @throws SQLException
     */
    @Override
    public Set<Student> findStudentsByClass(String schoolClass) throws SQLException {
        String sql = "Select * from students " +
                "where class = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,schoolClass);

        return getStudents(statement)
                .stream()
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Student::number))));
    }

    /**
     * Returns all students of a given gender, sorted by class and number.
     *
     * @param gender the gender
     * @return the students
     * @throws SQLException
     */
    @Override
    public Set<Student> findStudentsByGender(Gender gender) throws SQLException {
        String sql = "Select * from students " +
                "where gender = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,gender.toString());
        List<Student> students = getStudents(statement);
        students.sort(Comparator.comparing(Student::schoolClass).thenComparing(Student::number));
        return new LinkedHashSet<>(students);
    }

    /**
     * Returns the number of students for every class with classes sorted alphabetically.
     *
     * @return the map structured like map[class]=numberOfStudents
     * @throws SQLException
     */
    @Override
    public Map<String, Integer> findClasses() throws SQLException {
        return findAll().sorted(Comparator.comparing(Student::schoolClass))
                .collect(Collectors.groupingBy(Student::schoolClass,LinkedHashMap::new ,summingInt(x -> 1)));
    }

    @Override
    public void save(Student student) throws SQLException {

        String sql = "Insert into students values(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,student.lastName());
        preparedStatement.setString(2,student.firstName());
        preparedStatement.setString(3,student.gender().toString());
        preparedStatement.setInt(4,student.number());
        preparedStatement.setString(5,student.schoolClass());

        preparedStatement.execute();
    }

    private Optional<Student> getStudentFromResultSet(ResultSet resultSet) throws SQLException {
        return Student.get(resultSet.getString("last_name"),
                resultSet.getString("first_name"),
                Gender.parseGender(resultSet.getString("gender")),
                resultSet.getInt("student_number"),
                resultSet.getString("class")
        );
    }

    @NotNull
    private List<Student> getStudents(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();

        ArrayList<Optional<Student>> list = new ArrayList<>();
        while (resultSet.next()){
            list.add(getStudentFromResultSet(resultSet));
        }

        return list.stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }
}
