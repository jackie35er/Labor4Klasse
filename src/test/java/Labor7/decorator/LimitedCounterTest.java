package Labor7.decorator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LimitedCounterTest {

    @Test
    void read_as_underlying_below_threshold() {
        Counter counter = new LimitedCounter(new SimpleCounter(-1), 2);
        List<Integer> actual = new ArrayList<>();
        actual.add(counter.read());

        for (int i = 0; i < 2; i++) {
            Counter ticked = counter.tick();
            actual.add(ticked.read());
        }

        assertThat(actual).containsExactly(-1, 0, 1);
    }

    @Test
    void read_as_limit_above_threshold() {
        Counter counter = new LimitedCounter(new SimpleCounter(), 1);
        List<Integer> actual = new ArrayList<>();
        actual.add(counter.read());

        for (int i = 0; i < 2; i++) {
            Counter ticked = counter.tick();
            actual.add(ticked.read());
        }

        assertThat(actual).containsExactly(0, 1, 1);
    }

    @Test
    void works_with_loop_counter() {
        Counter counter = new LimitedCounter(new LoopCounter(0, 1, 4), 3);
        List<Integer> actual = new ArrayList<>();
        actual.add(counter.read());

        for (int i = 0; i < 5; i++) {
            Counter ticked = counter.tick();
            actual.add(ticked.read());
        }

        assertThat(actual).containsExactly(0, 1, 3, 0, 1, 3);
    }
}