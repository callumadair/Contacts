package contacts;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class Record {
    private String number, name;
    private LocalDateTime creationDateTime, editDateTime;

    Record() {
        setCreationDateTime(LocalDateTime.now());
    }

    public void setName(String name) {
        updateEditTime();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNumber(String number) {
        updateEditTime();
        try {
            if (isValidPhoneNumber(number)) {
                this.number = number;
            } else {
                this.number = "[no number]";
                throw new IllegalArgumentException("Wrong number format!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    private boolean isValidPhoneNumber(String phoneNumber) {
        String firstRegex = "(\\+?\\(?[A-Za-z0-9]+\\)?)([-| ][A-Za-z0-9]{2,}[-| ]?)*";
        String secondRegex = "(\\+?[A-Za-z0-9]+)([-| ]\\(?[A-Za-z0-9]{2,}\\)?)([-| ][A-Za-z0-9]{2,})*";
        Pattern pattern = Pattern.compile(firstRegex + "|" + secondRegex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public String getNumber() {
        return number;
    }


    private void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
        updateEditTime();
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    protected void updateEditTime() {
        this.editDateTime = LocalDateTime.now();
    }

    public LocalDateTime getEditDateTime() {
        return editDateTime;
    }

    @Override
    public String toString() {
        return "Time created: " + getCreationDateTime().truncatedTo(ChronoUnit.MINUTES) +
                "\nTime last edit: " + getEditDateTime().truncatedTo(ChronoUnit.MINUTES);
    }

    public abstract void setField(String field, String value);

    public abstract String getFieldValue(String field);

    public abstract String[] getFields();
}
enum Fields {
    NUMBER("number"),
    NAME("name"),
    SURNAME("surname"),
    GENDER("gender"),
    BIRTH("birth"),
    ADDRESS("address"),
    CREATION("creationDateTime"),
    EDIT("editDateTime");

    String label;

    Fields(String label) {
        this.label = label;
    }
}