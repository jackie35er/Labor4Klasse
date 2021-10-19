package streaming;

import Labor2.streaming.Streaming;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;


class StreamingTest {

    @Test
    void a() {
        assertThat(Streaming.a())
                .containsExactly(1, 9, 25, 49, 81, 121, 169, 225, 289, 361);
    }

    @Test
    void b() {
        assertThat(Streaming.b())
                .isCloseTo(0.49019607843137253, offset(1e-10));
    }

    @RepeatedTest(100)
    void c() {
        assertThat(Streaming.c())
                .hasSize(6)
                .isSorted()
                .matches(tip -> Arrays.stream(tip).max().getAsInt() < 46)
                .matches(tip -> Arrays.stream(tip).min().getAsInt() > 0);
    }

    @Test
    void d() {
        assertThat(Streaming.d())
                .isEqualTo(2432902008176640000L);
    }

    @Test
    void e() {
        assertThat(Streaming.e())
                .isEqualTo(301);
    }

    @Test
    void f() {
        assertThat(Streaming.f())
                .isEqualTo(new BigInteger("1124000727777607680000"));
    }

    @Test
    void g() {
        assertThat(Streaming.g())
                .isEqualTo(3249);
    }

    @Test
    void h() {
        assertThat(Streaming.h())
                .isEqualTo(4782);
    }

}