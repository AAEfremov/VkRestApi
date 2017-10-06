package enums.regions;

public enum Region1 {

    REGION_ID("1500595"),
    REGION_TITLE("Винницкая область");

    private String value;

    Region1(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
