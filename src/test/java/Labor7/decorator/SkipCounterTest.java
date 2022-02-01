package Labor7.decorator;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SkipCounterTest {

    @Test
    void cannot_create_negative_skip() {
        assertThatThrownBy(() -> new SkipCounter(new SimpleCounter(), -1));
    }

    @Test
    void initial_read_as_underlying() {
        Counter counter = new SkipCounter(new LoopCounter(42), 3);

        int count = counter.read();
        assertThat(count).isEqualTo(42);
    }

    @Nested
    class Skips_counts_of_underlying_stream {

        @Test
        void none_for_0() {
            Counter counter = new SkipCounter(new SimpleCounter(), 0);

            counter.tick()
                    .tick();

            int count = counter.read();
            assertThat(count).isEqualTo(2);
        }

        @Test
        void for_1() {
            Counter counter = new SkipCounter(new SimpleCounter(), 1);
            List<Integer> actual = new ArrayList<>();
            actual.add(counter.read());

            for (int i = 0; i < 3; i++) {
                Counter ticked = counter.tick();
                actual.add(ticked.read());
            }

            assertThat(actual).containsExactly(0, 2, 4, 6);
        }

        @Test
        void for_2() {
            Counter counter = new SkipCounter(new SimpleCounter(), 2);
            List<Integer> actual = new ArrayList<>();
            actual.add(counter.read());

            for (int i = 0; i < 3; i++) {
                Counter ticked = counter.tick();
                actual.add(ticked.read());
            }

            assertThat(actual).containsExactly(0, 3, 6, 9);
        }

        @Test
        void with_loop_counter() {
            Counter counter = new SkipCounter(new LoopCounter(0, 1, 2), 2);
            List<Integer> actual = new ArrayList<>();
            actual.add(counter.read());

            for (int i = 0; i < 3; i++) {
                Counter ticked = counter.tick();
                actual.add(ticked.read());
            }

            assertThat(actual).containsExactly(0, 0, 0, 0);
        }
    }
}
