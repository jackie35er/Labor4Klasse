package Labor2.student.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public record Student(String schoolClass,
                      int number,
                      String firstName,
                      String secondName,
                      Gender gender) {


    public static @NotNull Student of(@NotNull String csv) {
        var splitted = csv.split(",");
        var firstName = splitted[1];
        var secondName = splitted[0];
        Gender gender = getGenderFromString(splitted[2]);
        int number = Integer.parseInt(splitted[3]);
        var schoolClass = splitted[4];
        return new Student(schoolClass, number, firstName, secondName, gender);
    }

    @Override
    public String toString() {
        return String.format("%s/%02d %s %s %s", schoolClass, number, secondName, firstName, gender);
    }

    private static Gender getGenderFromString(@NotNull String str){
        return switch (str.toUpperCase(Locale.ROOT).charAt(0)){
            case 'M' -> Gender.MALE;
            case 'W' -> Gender.FEMALE;
            default -> Gender.DIVERSE;
        };
    }
}
