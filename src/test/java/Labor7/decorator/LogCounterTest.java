package Labor7.decorator;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class LogCounterTest {

    private Path logFile;

    @BeforeEach
    void createFile() throws IOException {
        logFile = Files.createTempFile("log", ".txt");
    }

    @AfterEach
    void deleteFile() throws IOException {
        Files.delete(logFile);
    }

    @Test
    void initial_read_as_underlying() throws IOException {
        Counter counter = new LogCounter(new LoopCounter(42), logFile);

        int count = counter.read();
        assertThat(count).isEqualTo(42);
    }

    @Test
    void tick_increases_read_value() throws IOException {
        Counter counter = new LogCounter(new SimpleCounter(), logFile);

        counter.tick()
                .tick();

        int count = counter.read();
        assertThat(count).isEqualTo(2);
    }

    @Test
    void reads_logged() throws IOException {
        Files.writeString(logFile, "existingContent\n");
        Counter counter = new LogCounter(new SimpleCounter(), logFile);

        counter.read();

        var log = Files.readAllLines(logFile);
        assertThat(log.get(0)).isEqualTo("existingContent");
        assertThat(log.get(1)).contains("read() = 0");
    }

    @Test
    void tick() throws IOException {
        Files.writeString(logFile, "existingContent\n");
        Counter counter = new LogCounter(new SimpleCounter(), logFile);

        counter.tick();

        var log = Files.readAllLines(logFile);
        assertThat(log.get(0)).isEqualTo("existingContent");
        assertThat(log.get(1)).contains("tick()");
    }
}
