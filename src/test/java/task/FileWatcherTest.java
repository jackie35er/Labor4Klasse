package task;

import Labor3.FileWatcher.FileWatcher;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;

class FileWatcherTest {

    final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final PrintStream redirectedSout = new PrintStream(output);
    private final Path path = Path.of("test.file");
    private PrintStream originalSout;

    @BeforeEach
    void setup() throws IOException {
        createFile();
        redirectSout();
    }

    private void createFile() throws IOException {
        Files.createFile(path);
    }

    private void redirectSout() {
        originalSout = System.out;
        System.setOut(redirectedSout);
    }

    @AfterEach
    void cleanUp() throws IOException {
        restoreSout();
        deleteFile();
    }

    private void restoreSout() {
        System.setOut(originalSout);
    }

    private void deleteFile() throws IOException {
        Files.deleteIfExists(path);
    }

    @Test
    void constructor_fileDoesNotExist_exception() {
        Path path = Path.of("Z:", "shouldNotExist.file");

        assertThatThrownBy(() -> new FileWatcher(path, 1));
    }

    @Test
    void run_fileDeleted_logged() throws IOException {
        FileWatcher watcher = new FileWatcher(path, 10);

        Thread thread = new Thread(watcher);
        thread.start();
        Files.deleteIfExists(path);

        await().pollDelay(ofMillis(15))
                .atMost(ofSeconds(1))
                .untilAsserted(() ->
                        assertThat(output.toString()).contains("Can no longer find " + path));
    }

    @Test
    void run_fileDeleted_threadTerminated() throws IOException {
        FileWatcher watcher = new FileWatcher(path, 10);

        Thread thread = new Thread(watcher);
        thread.start();
        Files.deleteIfExists(path);

        await().pollDelay(ofMillis(15))
                .atMost(ofSeconds(1))
                .untilAsserted(() ->
                        assertThat(thread.isAlive()).isFalse());
    }

    @Test
    void run_fileChanged_changeLogged() throws IOException {
        FileWatcher watcher = new FileWatcher(path, 10);
        Thread watcherThread = new Thread(watcher);
        watcherThread.start();
        await().atMost(ofSeconds(1))
                .until(watcherThread::isAlive);

        Files.newBufferedWriter(path)
                .append("someContent")
                .close();

        await().pollDelay(ofMillis(15))
                .atMost(ofSeconds(1))
                .untilAsserted(() ->
                        assertThat(output.toString()).contains(path + " watched", path + " changed"));
    }
}