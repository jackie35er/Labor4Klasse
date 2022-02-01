package Labor7.decorator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BaseCounterTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 0, -1})
    void cannot_create_for_base_less_than_2(int base) {
        assertThatThrownBy(() -> new BaseCounter(base));
    }

    @Test
    void cannot_create_for_base_more_than_10() {
        assertThatThrownBy(() -> new BaseCounter(11));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 7})
    void initial_read_0(int base) {
        Counter counter = new BaseCounter(base);

        int count = counter.read();
        assertThat(count).isEqualTo(0);
    }

    @Test
    void tick_increases_read_value() {
        Counter counter = new BaseCounter(2);

        counter.tick();

        int count = counter.read();
        assertThat(count).isEqualTo(1);
    }

    @ParameterizedTest
    @CsvSource({"1, 1", "2, 2", "3, 10", "10, 101"})
    void tick_increases_read_value_in_given_base(int n, int expected) {
        Counter counter = new BaseCounter(3);

        for (int i = 0; i < n; i++)
            counter.tick();

        int count = counter.read();
        assertThat(count).isEqualTo(expected);
    }
}
