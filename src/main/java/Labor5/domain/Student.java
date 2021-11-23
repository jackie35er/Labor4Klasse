package Labor5.domain;

import util.validator.interfaces.Validator;

import java.util.Optional;

public record Student(
        String lastName,
        String firstName,
        Gender gender,
        int number,
        String schoolClass) {

    public Student {
        if (lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("Last name cannot be blank");
        if (firstName == null || firstName.isBlank())
            throw new IllegalArgumentException("First name cannot be blank");
        if (number < 1 || number > 36)
            throw new IllegalArgumentException("Number out of range, must be in [1,36]");
        if (schoolClass == null || !schoolClass.matches("[1-5][A-C]HIF"))
            throw new IllegalArgumentException("Student must belong to a class");
    }

    public static Optional<Student> get(String lastName,
                                        String firstName,
                                        Gender gender,
                                        int number,
                                        String schoolClass){
        try {
            return Optional.of(new Student(lastName, firstName, gender, number, schoolClass));
        }
        catch (Exception e){
            return Optional.empty();
        }

    }
}
