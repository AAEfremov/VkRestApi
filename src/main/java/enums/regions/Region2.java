package enums.regions;

public enum Region2 {

    REGION_ID("1502709"),
    REGION_TITLE("Донецкая область");

    private String value;

    Region2(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
