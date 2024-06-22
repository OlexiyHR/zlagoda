package ua.training.validator.field;

import ua.training.entity.Status;

public enum FieldValidatorKey {

    NAME("empl_name"), SURNAME("empl_surname"), EMAIL("email"), PASSWORD("password"), PHONE("phone_number"), PATHRONYMIC("empl_pathronymic"),
    ROLE("empl_role"), SALARY("salary"), BIRTH("date_of_birth"), START("date_of_start"), CITY("city"), STREET("street"), CODE("zip_code"),
    DESCRIPTION("description"), WEIGHT("weight"), COST("cost");


    private String value;

    FieldValidatorKey(String value) {
    }

    public String getValue() {
        return value;
    }

    public static Status forValue(String value) {
        for (final Status status : Status.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new RuntimeException("FieldValidatorKey with such string value doesn't exist");
    }
}