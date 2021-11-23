package Labor5.domain;

import java.util.Locale;

public enum Gender {
    MALE, FEMALE, DIVERSE;

    public static Gender parseGender(String s){
        return switch (s.toLowerCase(Locale.ROOT)){
            case "male","m" -> MALE;
            case "female","f","w" -> FEMALE;
            case "diverse","d" -> DIVERSE;
            default -> throw new IllegalStateException("Unexpected value: " + s);
        };
    }

    @Override
    public String toString() {
        return switch (this){
            case MALE -> "m";
            case FEMALE -> "w";
            case DIVERSE -> "d";
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }
}
