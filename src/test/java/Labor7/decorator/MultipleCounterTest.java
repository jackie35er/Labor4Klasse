package Labor7.decorator;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MultipleCounterTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void cannot_create_for_invalid_arguments(int multiple) {
        assertThatThrownBy(() -> new MultipleCounter(new SimpleCounter(), multiple));
    }

    @Test
    void initial_read_as_underlying() {
        Counter counter = new MultipleCounter(new LoopCounter(42), 3);

        int count = counter.read();
        assertThat(count).isEqualTo(42);
    }

    @Nested
    class Multiple_ticks_needed_to_increase_value {

        @Test
        void for_2() {
            Counter counter = new MultipleCounter(new SimpleCounter(), 2);
            List<Integer> actual = new ArrayList<>();
            actual.add(counter.read());

            for (int i = 0; i < 6; i++) {
                Counter ticked = counter.tick();
                actual.add(ticked.read());
            }

            assertThat(actual).containsExactly(0, 0, 1, 1, 2, 2, 3);
        }

        @Test
        void for_3() {
            Counter counter = new MultipleCounter(new SimpleCounter(), 3);
            List<Integer> actual = new ArrayList<>();
            actual.add(counter.read());

            for (int i = 0; i < 9; i++) {
                Counter ticked = counter.tick();
                actual.add(ticked.read());
            }

            assertThat(actual).containsExactly(0, 0, 0, 1, 1, 1, 2, 2, 2, 3);
        }
    }
}