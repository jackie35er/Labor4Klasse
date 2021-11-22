package Labor3.piCalculator;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DistributionCalculator {
    @Contract(pure = true)
    public static @NotNull List<Integer> calculateOperations(int max, int count){
        List<Integer> out = new ArrayList<>();
        int base = max / count;
        int reminder = max - count * base;

        for(int i = 0; i < count; i++){
            if(reminder != 0){
                out.add(base + 1);
                reminder--;
            }
            else {
                out.add(base);
            }
        }

        return out;
    }
}
