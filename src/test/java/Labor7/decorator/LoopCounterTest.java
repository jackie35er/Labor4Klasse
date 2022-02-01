package Labor7.decorator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LoopCounterTest {

    @ParameterizedTest
    @EmptySource
    @NullSource
    void cannot_create_for_invalid_arguments(int[] arguments) {
        assertThatThrownBy(() -> new LoopCounter(arguments));
    }

    @Test
    void initial_read_is_first_argument() {
        Counter counter = new LoopCounter(3);

        int count = counter.read();
        assertThat(count).isEqualTo(3);
    }

    @Test
    void tick_forwards_to_next_argument() {
        Counter counter = new LoopCounter(1, 3, 2);

        counter.tick()
                .tick();

        int count = counter.read();
        assertThat(count).isEqualTo(2);

        count = counter.read();
        assertThat(count).isEqualTo(2);
    }

    @Test
    void tick_loops_through_arguments() {
        Counter counter = new LoopCounter(1, 2);

        counter.tick()
                .tick();

        int count = counter.read();
        assertThat(count).isEqualTo(1);
    }
}
