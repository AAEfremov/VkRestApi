package enums.cities;

public enum City1 {

    CITY_ID("1"),
    CITY_TITLE("Москва"),
    CITY_IMPORTANT("1");

    private String value;

    City1(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
