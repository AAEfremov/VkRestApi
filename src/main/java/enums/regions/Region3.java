package enums.regions;

public enum Region3 {

    REGION_ID("5127862"),
    REGION_TITLE("Nunavut");

    private String value;

    Region3(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
