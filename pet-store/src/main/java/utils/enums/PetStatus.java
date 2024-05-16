package utils.enums;

public enum PetStatus {

    AVAILABLE("available"),
    PENDING("pending"),
    SOLD("sold");

    private final String petStatus;

    PetStatus(String status) {
        this.petStatus = status;
    }

    public String getValue() {
        return petStatus;
    }
}
