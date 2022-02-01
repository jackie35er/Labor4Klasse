package Labor7.decorator;

import Labor7.decorator.LoopCounter;
import Labor7.decorator.TypeCounter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TypeCounterTest {

    @ParameterizedTest
    @EmptySource
    @NullSource
    void cannot_create_for_invalid_arguments(int[] arguments) {
        assertThatThrownBy(() -> new TypeCounter(null,0,arguments));
    }

    @Test
    void initial_read_is_first_argument() {
        Counter counter = new TypeCounter(new SimpleCounter(3),1,1);

        int count = counter.read();
        assertThat(count).isEqualTo(3);
    }

    @Test
    void tick_forwards_with_no_matching_values(){
        Counter counter = new TypeCounter(new SimpleCounter(0),-1,1,2,3,4,5,6,7,8,9,10);

        for(int i = 0; i < 100; i++){
            counter.tick();
        }

        assertThat(counter.read()).isEqualTo(0);
    }

    @Test
    void tick_forwards_with_one_matching_values(){
        Counter counter = new TypeCounter(new SimpleCounter(0),1,1,2,3,4,5,6,7,8,9,10);

        for(int i = 0; i < 100; i++){
            counter.tick();
        }

        assertThat(counter.read()).isEqualTo(10);
    }

    @Test
    void tick_forwards_with_three_matching_values(){
        Counter counter = new TypeCounter(new SimpleCounter(0),1,1,1,1,4,5,6,7,8,9,10);

        for(int i = 0; i < 100; i++){
            counter.tick();
        }

        assertThat(counter.read()).isEqualTo(30);
    }



}
