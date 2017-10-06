package enums.cities;

public enum City4 {

    CITY_ID("242"),
    CITY_TITLE("Петергоф"),
    CITY_REGION("Санкт-Петербург город");

    private String value;

    City4(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
