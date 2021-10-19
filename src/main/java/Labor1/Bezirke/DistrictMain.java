package Labor1.Bezirke;

import java.nio.file.Path;
import java.util.*;

public class DistrictMain {
    public static void main(String[] args) {
        try{
            List<District> list = new ArrayList<>(District.readCSV(Path.of("src/main/resources/labor1/bezirke_noe.csv")));
            System.out.println(list);
            System.out.println("--------");
            list.sort((o1,o2) -> Comparator.comparing(District::population)
                    .reversed()
                    .compare(o1,o2));

            System.out.println(list);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
