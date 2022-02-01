package Labor7.decorator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleCounterTest {

    @Test
    void initial_read_is_zero_if_no_value_given() {
        Counter counter = new SimpleCounter();

        int count = counter.read();
        assertThat(count).isEqualTo(0);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    void initial_read_is_given_value(int initialValue) {
        Counter counter = new SimpleCounter(initialValue);

        int count = counter.read();
        assertThat(count).isEqualTo(initialValue);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    void tick_increases_read_value(int initialValue) {
        Counter counter = new SimpleCounter(initialValue);

        counter.tick()
                .tick();

        int count = counter.read();
        assertThat(count).isEqualTo(initialValue + 2);
    }
}