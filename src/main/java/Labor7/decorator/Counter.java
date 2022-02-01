package Labor7.decorator;

public interface Counter {

    /**
     * Reads the current count
     *
     * @return current count
     */
    int read();

    /**
     * Increases the current count
     *
     * @return this
     */
    Counter tick();
}
