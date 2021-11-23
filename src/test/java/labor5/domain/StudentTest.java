package labor5.domain;

import Labor5.domain.Student;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static Labor5.domain.Gender.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class StudentTest {

    @Nested
    class Construction {

        @ParameterizedTest
        @NullAndEmptySource
        void fails_if_last_name_blank(String lastName) {
            assertThatThrownBy(() -> new Student(lastName, "firstName", MALE, 1, "1AHIF"));
        }

        @ParameterizedTest
        @NullAndEmptySource
        void fails_if_first_name_blank(String firstName) {
            assertThatThrownBy(() -> new Student("lastName", firstName, FEMALE, 36, "5CHIF"));
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 37})
        void fails_if_number_smaller_than_0_or_larger_than_36(int number) {
            assertThatThrownBy(() -> new Student("lastName", "firstName", DIVERSE, number, "3CHIF"));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"0AHIF", "1DHIF", "1aHIF", "1AFIF", "1AHMBA", "6AHIF", " 1AHIF", "1AHIF "})
        void fails_if_class_not_valid_informatikclass(String schoolClass) {
            assertThatThrownBy(() -> new Student("lastName", "firstName", DIVERSE, 1, schoolClass));
        }

        @Test
        void works() {
            assertDoesNotThrow(() -> new Student("lastName", "firstName", DIVERSE, 1, "4AHIF"));
        }
    }
}
