package labor5.persistence;

import Labor5.domain.Student;
import Labor5.persistence.JdbcStudentRepository;
import Labor5.persistence.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static Labor5.domain.Gender.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class StudentRepositoryTest {

    public static final String JDBC_URL = "jdbc:h2:mem:students-test;INIT=RUNSCRIPT FROM 'classpath:schema.sql'";
    private Connection connection;
    private StudentRepository repository;

    @BeforeEach
    void createRepository() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL);
        repository = new JdbcStudentRepository(connection);
    }

    @AfterEach
    void closeConnection() throws SQLException {
        connection.close();
    }

    @Nested
    class Saving {

        @Test
        void works() throws SQLException {
            var student = new Student("lastName", "firstName", MALE, 1, "1AHIF");

            repository.save(student);

            assertThat(repository.findAll())
                    .containsExactly(student);
        }

        @Test
        void fails_if_existing_student_has_same_class_and_number() throws SQLException {
            var adler = new Student("Adler", "Alfred", MALE, 1, "1AHIF");
            var bilic = new Student("Bilic", "Blib", FEMALE, 1, "1AHIF");
            repository.save(adler);

            assertThatThrownBy(() -> repository.save(bilic));
            assertThat(repository.findAll())
                    .containsExactly(adler);
        }
    }

    @Nested
    class FindingAllOfGivenGender {

        @Test
        void works_for_male() throws SQLException {
            var maleStudents = List.of(
                    new Student("Adler", "Alfred", MALE, 1, "1AHIF"),
                    new Student("Dumpf", "Daniel", MALE, 3, "1AHIF"),
                    new Student("Aas", "Thomas", MALE, 1, "4AHIF"));
            repository.save(new Student("Dan", "Som", FEMALE, 13, "3CHIF"));
            repository.save(new Student("Xim", "Som", DIVERSE, 2, "3CHIF"));
            for (var maleStudent : maleStudents)
                repository.save(maleStudent);

            var males = repository.findStudentsByGender(MALE);

            assertThat(males)
                    .containsExactlyElementsOf(maleStudents);
        }

        @Test
        void works_for_female() throws SQLException {
            var femaleStudents = List.of(
                    new Student("Adler", "Anna", FEMALE, 1, "1AHIF"),
                    new Student("Dumpf", "Daniela", FEMALE, 3, "1AHIF"));
            repository.save(new Student("Aas", "Thomas", MALE, 1, "4AHIF"));
            repository.save(new Student("Dan", "Som", MALE, 13, "3CHIF"));
            repository.save(new Student("Xim", "Som", DIVERSE, 2, "3CHIF"));
            for (var femaleStudent : femaleStudents)
                repository.save(femaleStudent);

            var females = repository.findStudentsByGender(FEMALE);

            assertThat(females)
                    .containsExactlyElementsOf(femaleStudents);
        }


        @Test
        void works_for_diverse() throws SQLException {
            var diverseStudents = List.of(
                    new Student("Adler", "Aop", DIVERSE, 1, "1AHIF"),
                    new Student("Dumpf", "Dela", DIVERSE, 3, "1AHIF"));
            repository.save(new Student("Aas", "Thomas", MALE, 1, "4AHIF"));
            repository.save(new Student("Dan", "Som", MALE, 13, "3CHIF"));
            repository.save(new Student("Xim", "Som", FEMALE, 2, "3CHIF"));
            for (var diverseStudent : diverseStudents)
                repository.save(diverseStudent);

            var diverse = repository.findStudentsByGender(DIVERSE);

            assertThat(diverse)
                    .containsExactlyElementsOf(diverseStudents);
        }
    }

    @Nested
    class FindingClasses {

        @Test
        void returns_empty_map_if_no_classes_persisted() throws SQLException {
            assertThat(repository.findClasses())
                    .isEmpty();
        }

        @Test
        void returns_all_classes_with_count_of_students() throws SQLException {
            var students = List.of(
                    new Student("Baar", "Simon", MALE, 1, "1AHIF"),
                    new Student("Dohnal", "Matthias Markus Edwin", MALE, 2, "1AHIF"),
                    new Student("Ertl", "Maxima", FEMALE, 36, "1AHIF"),
                    new Student("Schally", "Martin", MALE, 17, "3BHIF"),
                    new Student("Vesely", "Flora", FEMALE, 19, "3BHIF"),
                    new Student("Winkler", "Philipp Josef", MALE, 20, "3BHIF"),
                    new Student("Hofmann", "Dominik", MALE, 21, "3BHIF"),
                    new Student("Filipa", "Fec", DIVERSE, 15, "1CHIF")
            );
            for (var student : students)
                repository.save(student);

            assertThat(repository.findClasses())
                    .containsExactly(
                            Map.entry("1AHIF", 3),
                            Map.entry("1CHIF", 1),
                            Map.entry("3BHIF", 4)
                    );
        }
    }

    @Nested
    class FindingStudentByNumberAndClass {
        @Test
        public void findStudtentsByNumberAndClass() throws SQLException {
            var wantToFind = new Student("Vesely", "Flora", FEMALE, 19, "3BHIF");
            var students = List.of(
                    new Student("Baar", "Simon", MALE, 1, "1AHIF"),
                    new Student("Dohnal", "Matthias Markus Edwin", MALE, 2, "1AHIF"),
                    new Student("Ertl", "Maxima", FEMALE, 36, "1AHIF"),
                    new Student("Schally", "Martin", MALE, 17, "3BHIF"),
                    wantToFind,
                    new Student("Winkler", "Philipp Josef", MALE, 20, "3BHIF"),
                    new Student("Hofmann", "Dominik", MALE, 21, "3BHIF"),
                    new Student("Filipa", "Fec", DIVERSE, 15, "1CHIF")
            );
            for (var student : students){
                repository.save(student);
            }
            assertThat(repository.findByNumberAndClass(19,"3BHIF").get()).isEqualTo(wantToFind);
        }

    }

    @Nested
    class FindingStudentsByClass {
        @Test
        public void noStudentsinDatabase() throws SQLException {
            var wantToFind = new Student("Vesely", "Flora", FEMALE, 19, "3BHIF");
            assertThat(repository.findStudentsByClass("3BHIF")).isEmpty();
        }
    }

}
