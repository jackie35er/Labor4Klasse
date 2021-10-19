package Labor3.FileWatcher;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileWatcherMain {
    public static void main(String[] args) {
        if(args.length == 0){
            throw new IllegalArgumentException("Kein Directory angegeben");
        }
        Path path = Path.of(args[0]);
        if( !path.toFile().exists() || !path.toFile().isDirectory()){
            throw new IllegalArgumentException("Kein Directory angegeben");
        }
        if(Objects.requireNonNull(path.toFile().listFiles()).length == 0){
            throw new IllegalArgumentException("Leeres Directory angegeben");
        }
        List<File> file = Arrays.stream(Objects.requireNonNull(path.toFile().listFiles()))
                .toList();
        file.forEach(n -> new FileWatcher(n,500).start());
    }
}
