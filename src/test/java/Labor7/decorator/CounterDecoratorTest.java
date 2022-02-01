package Labor7.decorator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CounterDecoratorTest {

    @Test
    void underlying_counter_must_not_be_null() {
        assertThatThrownBy(() -> new MockCounterDecorator(null));
    }

    @Test
    void decorators_may_be_combined_at_will() {
        Counter simpleCounter = new SimpleCounter();
        Counter skipCounter = new SkipCounter(simpleCounter, 3);
        Counter multipleCounter = new MultipleCounter(skipCounter, 2);
        Counter limitedCounter = new LimitedCounter(multipleCounter, 7);
        List<Integer> actual = new ArrayList<>();
        actual.add(limitedCounter.read());

        for (int i = 0; i < 4; i++) {
            Counter ticked = limitedCounter.tick();
            actual.add(ticked.read());
        }

        assertThat(actual).containsExactly(0, 0, 4, 4, 7);
    }

    private static class MockCounterDecorator extends CounterDecorator {

        public MockCounterDecorator(Counter counter) {
            super(counter);
        }
    }

}
