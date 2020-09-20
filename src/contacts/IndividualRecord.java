package contacts;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

class IndividualRecord extends Record {
    private String firstName, surname, gender;
    private LocalDate birthDate;

    IndividualRecord() {
        super();
    }

    public void setSurname(String surname) {
        updateEditTime();
        this.surname = surname;
        setName();
    }

    public String getSurname() {
        return surname;
    }

    public void setName() {
        if (firstName != null && surname != null) {
            setName(firstName + " " + surname);
        } else if (firstName == null && surname != null) {
            setName(surname);
        } else setName(Objects.requireNonNullElse(firstName, "[no name]"));
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setGender(String gender) {
        updateEditTime();
        try {
            if (isValidGender(gender)) {
                this.gender = gender;
            } else {
                this.gender = "[no data]";
                throw new IllegalArgumentException("Bad gender!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isValidGender(String gender) {
        return gender.matches("[MF]");
    }

    public String getGender() {
        return gender;
    }

    public void setBirthDate(String birthDate) {
        updateEditTime();
        try {
            if (!birthDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                throw new DateTimeException("Bad birth date!");
            } else {
                this.birthDate = LocalDate.parse(birthDate);
            }
        } catch (DateTimeException e) {
            System.out.println(e.getMessage());
        }

    }

    private LocalDate getBirthDate() {
        return birthDate;
    }

    public String getBirthDateString() {
        if (this.getBirthDate() != null) {
            return this.getBirthDate().toString();
        } else {
            return "[no data]";
        }

    }

    @Override
    public String toString() {
        return "Name: " + getFirstName() + "\n" +
                "Surname: " + getSurname() + "\n" +
                "Birth date: " + getBirthDateString() + "\n" +
                "Gender: " + getGender() + "\n" +
                "Number: " + getNumber() + "\n" +
                super.toString();
    }

    @Override
    public String[] getFields() {
        return new String[]{Fields.NAME.label, Fields.SURNAME.label,
                Fields.GENDER.label, Fields.BIRTH.label, Fields.NUMBER.label};
    }

    @Override
    public void setField(String field, String value) {
        Fields fields = Fields.valueOf(field.toUpperCase());
        switch (fields) {
            case NAME:
                setFirstName(value);
                break;
            case SURNAME:
                setSurname(value);
                break;
            case GENDER:
                setGender(value);
                break;
            case BIRTH:
                setBirthDate(value);
                break;
            case NUMBER:
                setNumber(value);
                break;
            default:
                System.out.println("Error!");
                break;
        }
    }

    @Override
    public String getFieldValue(String field) {
        Fields fields = Fields.valueOf(field.toUpperCase());
        switch (fields) {
            case NAME:
                return getFirstName();
            case SURNAME:
                return getSurname();
            case BIRTH:
                return getBirthDateString();
            case GENDER:
                return getGender();
            case NUMBER:
                return getNumber();
            case CREATION:
                return super.getCreationDateTime().truncatedTo(ChronoUnit.MINUTES).toString();
            case EDIT:
                return super.getEditDateTime().truncatedTo(ChronoUnit.MINUTES).toString();
            default:
                return null;
        }
    }
}