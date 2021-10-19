package util;

import lombok.SneakyThrows;

public class Helper {
    @SneakyThrows
    public static void sleep(int ms){
        Thread.sleep(ms);
    }
}
