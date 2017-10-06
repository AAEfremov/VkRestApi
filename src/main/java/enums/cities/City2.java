package enums.cities;

public enum City2 {

    CITY_ID("1053447"),
    CITY_TITLE("Алабушево"),
    CITY_AREA("Зеленоград город"),
    CITY_REGION("Москва город");

    private String value;

    City2(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
