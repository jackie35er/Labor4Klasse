package Labor7.decorator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class LogCounter extends CounterDecorator {

    private Path logFile;


    public LogCounter(Counter counter, Path path) throws IOException {
        super(counter);
        if(!path.toFile().exists()){
            throw new IllegalArgumentException("File doesnt exist: " +path);
        }
        this.logFile = path;
    }

    @Override
    public int read() {
        log("read() = " + super.read());
        return super.read();
    }

    @Override
    public Counter tick() {
        log("tick()");
        super.tick();
        return this;
    }

    private void log(String string){
        try(OutputStream outputStream = new FileOutputStream(logFile.toFile(),true)){
            outputStream.write(string.getBytes(StandardCharsets.UTF_8));
            outputStream.write("\r\n".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IllegalStateException("Log file got deleted or moved");
        }
    }
}