package enums.cities;

public enum City3 {

    CITY_ID("64"),
    CITY_TITLE("Кемерово"),
    CITY_REGION("Кемеровская область");

    private String value;

    City3(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
