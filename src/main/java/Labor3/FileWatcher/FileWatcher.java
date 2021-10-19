package Labor3.FileWatcher;

import Labor3.Worker.Helper;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;



public class FileWatcher extends Thread {
    private final File fileToWatch;
    private final int intervalTime;
    private long lastModified;

    public FileWatcher(@NotNull File file, int intervalTime) {
        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist");
        }
        this.fileToWatch = file;
        this.lastModified = file.lastModified();
        this.intervalTime = intervalTime;
    }

    public FileWatcher(@NotNull Path file, int intervalTime) {
        this(file.toFile(), intervalTime);
    }

    @Override
    public void run() {
        while (fileToWatch.exists()) {
            if (fileToWatch.lastModified() != lastModified) {
                System.out.println(fileToWatch.getName() + " watched: " + fileToWatch.getName() + " changed");
                lastModified = fileToWatch.lastModified();
            }
            Helper.sleep(intervalTime);
        }
        System.out.println("Can no longer find " + fileToWatch.getName());
    }
}
