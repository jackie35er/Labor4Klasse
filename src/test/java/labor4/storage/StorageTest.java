package labor4.storage;

import Labor4.storage.Consumer;
import Labor4.storage.Good;
import Labor4.storage.Storage;
import Labor4.storage.Supplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.TimeUnit;

import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;

class StorageTest {

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void mustHavePositiveCapacity(int capacity) {
        assertThatThrownBy(() -> new Storage(capacity));
    }

    @Test
    void withEnoughPresentReturnsRequestedAmount() throws InterruptedException {
        Storage storage = new Storage(1);
        storage.supply(new Good(1));

        assertThat(storage.get(1))
                .extracting(Good::getAmount)
                .isEqualTo(1);
    }

    @Test
    void deniesRequestsExceedingCapacity() throws InterruptedException {
        Storage storage = new Storage(1);

        await().atMost(ofSeconds(1))
                .untilAsserted(() ->
                        assertThatThrownBy(() -> storage.get(2)));
    }

    @Test
    void deniesSuppliesExceedingCapacity() throws InterruptedException {
        Storage storage = new Storage(1);

        await().atMost(ofSeconds(1))
                .untilAsserted(() ->
                        assertThatThrownBy(() -> storage.supply(new Good(2))));
    }

    @Test
    void requestedTooMuchWillWaitForSupply() throws InterruptedException {
        Storage storage = new Storage(2);
        Supplier supplier = new Supplier(storage, 0, 0);
        storage.register(supplier);

        Good[] actual = new Good[1];
        Thread consumer = new Thread(() -> {
            try {
                actual[0] = storage.get(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        consumer.start();
        await().atMost(1, TimeUnit.SECONDS)
                .until(() -> consumer.getState() == Thread.State.WAITING);
        storage.supply(new Good(1));

        await().atMost(ofSeconds(1))
                .untilAsserted(() ->
                        assertThat(actual[0].getAmount()).isOne());
    }

    @Test
    void suppliedTooMuchWillWaitForRequest() throws InterruptedException {
        Storage storage = new Storage(1);
        storage.supply(new Good(1));

        Thread producer = new Thread(() -> {
            try {
                storage.supply(new Good(1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producer.start();
        await().atMost(1, TimeUnit.SECONDS)
                .until(() -> producer.getState() == Thread.State.WAITING);
        storage.get(1);

        await().atMost(ofSeconds(1))
                .untilAsserted(() ->
                        assertThat(storage.get(1).getAmount()).isOne());
    }

    @Test
    void canBeSupplied() throws InterruptedException {
        Storage storage = new Storage(7);
        storage.supply(new Good(1));
        Supplier supplier = new Supplier(storage, 2, 3);

        supplier.run();

        assertThat(storage.size()).isEqualTo(7);
    }

    @Test
    void canBeRequested() throws InterruptedException {
        Storage storage = new Storage(9);
        storage.supply(new Good(5));
        Consumer consumer = new Consumer(storage, 2);

        consumer.run();

        assertThat(storage.size()).isZero();
    }

    @Test
    void isEmptyWhenFinished() {
        Storage storage = new Storage(4);
        Supplier supplier = new Supplier(storage, 3, 3);
        Consumer consumer = new Consumer(storage, 2);

        //new Thread(supplier).start();
        storage.register(supplier);
        new Thread(consumer).start();

        await().atMost(ofSeconds(10))
                .untilAsserted(() ->
                        assertThat(storage.size()).isZero());
    }

    @Test
    void takesTimeToFinish() throws InterruptedException {
        Storage storage = new Storage(3);
        Supplier supplier = new Supplier(storage, 3, 1, 3, 1, 3);
        Consumer consumer = new Consumer(storage, 1, 3, 2);

        Thread supplierThread = new Thread(supplier);
        supplierThread.start();
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();

        await().atLeast(ofSeconds(3))
                .untilAsserted(() ->
                        assertThat(supplierThread.isAlive() || consumerThread.isAlive()).isFalse());
    }
}