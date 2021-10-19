package Labor4.storage.logging;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class LogImpl implements Log {

    List<OutputStream> outputStreamList;
    OutputStream primary;

    public LogImpl(OutputStream @NotNull ... outputs) {
        if(outputs.length != 0){
            primary = outputs[0];
        }
        outputStreamList = new ArrayList<>();
        outputStreamList.addAll(List.of(outputs));
    }

    public LogImpl(OutputStream primary) {
        outputStreamList = new ArrayList<>();
        outputStreamList.add(primary);
        this.primary = primary;
    }

    public void log(String s) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            primary.write((timestamp + "- log :" + s + "\n").getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void info(String s) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        outputStreamList.forEach(outputStream -> {
            try {
                outputStream.write((timestamp + "- info :" + s + "\n").getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void close() {
        outputStreamList.forEach(outputStream -> {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
