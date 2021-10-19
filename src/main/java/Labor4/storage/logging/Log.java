package Labor4.storage.logging;

public interface Log extends AutoCloseable{

    void log(String s);

    void info(String s);

    void close();
}
