package contacts;

import java.time.temporal.ChronoUnit;

class OrganisationalRecord extends Record {
    private String address;

    OrganisationalRecord() {
        super();
    }

    public void setAddress(String address) {
        updateEditTime();
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Organization name: " + getName() + "\n" +
                "Address: " + getAddress() + "\n" +
                "Number: " + getNumber() + "\n" +
                super.toString();
    }

    @Override
    public String[] getFields() {
        return new String[]{Fields.NAME.label, Fields.ADDRESS.label, Fields.NUMBER.label};
    }

    @Override
    public void setField(String field, String value) {
        Fields fields = Fields.valueOf(field.toUpperCase());
        switch (fields) {
            case NUMBER:
                setNumber(value);
                break;
            case NAME:
                setName(value);
                break;
            case ADDRESS:
                setAddress(value);
                break;
        }
    }

    @Override
    public String getFieldValue(String field) {
        Fields fields = Fields.valueOf(field.toUpperCase());
        switch (fields) {
            case NUMBER:
                return getNumber();
            case NAME:
                return getName();
            case ADDRESS:
                return getAddress();
            case CREATION:
                return super.getCreationDateTime().truncatedTo(ChronoUnit.MINUTES).toString();
            case EDIT:
                return super.getEditDateTime().truncatedTo(ChronoUnit.MINUTES).toString();
            default:
                return null;
        }
    }
}