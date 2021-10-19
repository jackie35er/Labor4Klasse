package students;

import Labor2.student.StudentStatistics;
import Labor2.student.domain.Student;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.*;

import static Labor2.student.domain.Gender.FEMALE;
import static Labor2.student.domain.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StudentStatisticsTest {

    final Path path;

    StudentStatisticsTest() throws URISyntaxException {
        path = Path.of(getClass().getClassLoader().getResource("schueler.csv").toURI());
    }

    @Test
    void gets_all_classes() throws IOException {
        var statistics = new StudentStatistics(path);

        assertThat(statistics.getClasses())
                .containsExactly("1AHIF", "1BHIF", "1CHIF",
                        "2AHIF", "2BHIF", "2CHIF",
                        "3AHIF", "3BHIF",
                        "4BHIF", "4CHIF",
                        "5AHIF", "5BHIF");
    }

    @Test
    void gets_gendercount_for_class() throws IOException {
        var statistics = new StudentStatistics(path);
        var expected = Map.of(MALE, 31L, FEMALE, 3L);

        assertThat(statistics.getGenderCountForClass("1AHIF"))
                .containsExactlyInAnyOrderEntriesOf(expected);
    }

    @Test
    void gets_all_with_second_name_like_given_name() throws IOException {
        var statistics = new StudentStatistics(path);
        var expected = List.of(
                new Student("2AHIF", 13, "Andre", "Meindorfer", MALE),
                new Student("1AHIF", 21, "Christoph", "Oberndorfer", MALE),
                new Student("1CHIF", 8, "David", "Hinterndorfer", MALE)
        );

        assertThat(statistics.getAllWithSecondNameLike("dorfer"))
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void gets_student_with_longest_name() throws IOException {
        var statistics = new StudentStatistics(path);
        var expected = new Student("5AHIF", 9, "Bernhard Peter Magnus Kunibert", "Kriechbaum", MALE);

        assertThat(statistics.getStudentWithLongestName())
                .isEqualTo(expected);
    }

    @Test
    void counts_students_per_year() throws IOException {
        var statistics = new StudentStatistics(path);
        var expected = new TreeMap<Integer, Long>(Map.of(
                1, 93L,
                2, 74L,
                3, 37L,
                4, 62L,
                5, 34L));

        assertThat(statistics.countStudentsByYear())
                .containsExactlyEntriesOf(expected);
    }

    @Nested
    class Counts {

        @Test
        void male_students() throws IOException {
            var statistics = new StudentStatistics(path);

            assertThat(statistics.countGender(MALE))
                    .isEqualTo(281);
        }

        @Test
        void female_students() throws IOException {
            var statistics = new StudentStatistics(path);

            assertThat(statistics.countGender(FEMALE))
                    .isEqualTo(19);
        }
    }

    @Nested
    class Finds_by_number_and_class {

        @Test
        void returns_target_for_existing_class() throws IOException {
            var statistics = new StudentStatistics(path);
            var expected = new Student("1AHIF", 1, "Lukas", "Berger", MALE);

            Optional<Student> actual = statistics.findByNumberAndClass(1, "1AHIF");

            assertThat(actual).contains(expected);
        }

        @Test
        void returns_empty_for_unknown_class() throws IOException {
            var statistics = new StudentStatistics(path);

            assertThat(statistics.findByNumberAndClass(1, "No such class"))
                    .isEmpty();
        }

        @Test
        void returns_empty_for_unknown_number() throws IOException {
            var statistics = new StudentStatistics(path);

            assertThat(statistics.findByNumberAndClass(0, "No such class"))
                    .isEmpty();
        }
    }

    @Nested
    class Gets_most_frequent_names {

        @ParameterizedTest
        @ValueSource(ints = {-1, 0})
        void fails_for_invalid_values(int count) throws IOException {
            var statistics = new StudentStatistics(path);

            assertThatThrownBy(() -> statistics.getMostFrequentFirstNames(count));
        }

        @Test
        void top_3() throws IOException {
            var statistics = new StudentStatistics(path);
            System.out.println(statistics.getMostFrequentFirstNames(3));
            assertThat(statistics.getMostFrequentFirstNames(3))
                    .containsExactly("Daniel", "Michael", "Lukas");
        }

        @Test
        void top_5() throws IOException {
            var statistics = new StudentStatistics(path);

            assertThat(statistics.getMostFrequentFirstNames(5))
                    .containsExactly("Daniel", "Michael", "Lukas", "Patrick", "Christoph");
        }
    }
}
