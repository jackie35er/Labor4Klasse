package labor4.storage;

import Labor4.storage.Good;
import Labor4.storage.Storage;
import Labor4.storage.Supplier;
import Labor4.storage.logging.Log;
import Labor4.storage.logging.LogImpl;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class LogTest {

    @Test
    void canLogToPrimaryOutput() {
        OutputStream primary = new ByteArrayOutputStream();
        OutputStream secondary = new ByteArrayOutputStream();
        Log log = new LogImpl(primary, secondary);

        log.info("logged");
        log.close();

        assertThat(primary.toString()).contains("logged");
        assertThat(secondary.toString()).isEmpty();
    }

    @Test
    void canLogToAllOutputs() {
        OutputStream[] outputs = Stream.generate(ByteArrayOutputStream::new)
                .limit(3)
                .toArray(OutputStream[]::new);
        Log log = new LogImpl(outputs);

        log.log("logged");
        log.close();

        assertThat(outputs).allMatch(out -> out.toString().contains("logged"));
    }

    @Test
    void logsConsumption() throws InterruptedException {
        OutputStream[] outputs = Stream.generate(ByteArrayOutputStream::new)
                .limit(3)
                .toArray(OutputStream[]::new);
        Log log = new LogImpl(outputs);
        Storage storage = new Storage(1, log);
        storage.supply(new Good(1));

        storage.get(1);
        log.close();

        assertThat(outputs).allMatch(out -> out.toString().contains("Consumption"));
    }

    @Test
    void logsSupply() throws InterruptedException {
        OutputStream[] outputs = Stream.generate(ByteArrayOutputStream::new)
                .limit(3)
                .toArray(OutputStream[]::new);
        Log log = new LogImpl(outputs);
        Storage storage = new Storage(1, log);

        storage.supply(new Good(1));
        log.close();

        assertThat(outputs).allMatch(out -> out.toString().contains("Supply"));
    }

    @Test
    void logsWaitingSupply() throws InterruptedException {
        OutputStream output = new ByteArrayOutputStream();
        Log log = new LogImpl(output);
        Storage storage = new Storage(1, log);
        storage.supply(new Good(1));
        Thread supplier = new Thread(() -> {
            try {
                storage.supply(new Good(1));
            } catch (InterruptedException ignored) {
            }
        });

        supplier.start();
        await().atMost(1, TimeUnit.SECONDS)
                .until(() -> supplier.getState() == Thread.State.WAITING);
        log.close();

        assertThat(output.toString()).contains("waiting");
    }

    @Test
    void logsWaitingStorage() {
        OutputStream output = new ByteArrayOutputStream();
        Log log = new LogImpl(output);
        Storage storage = new Storage(1, log);
        storage.register(new Supplier(storage, 0, 0));
        Thread consumer = new Thread(() -> {
            try {
                storage.get(1);
            } catch (InterruptedException ignored) {
            }
        });

        consumer.start();
        await().atMost(1, TimeUnit.SECONDS)
                .until(() -> consumer.getState() == Thread.State.WAITING);
        log.close();

        assertThat(output.toString()).contains("waiting");
    }
}