package enums;

public enum Error {

    ERROR_CODE("100"),
    ERROR_MSG("One of the parameters specified was missing or invalid: country_id is undefined");

    private String value;

    Error(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }
}
