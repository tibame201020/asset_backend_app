package myself.custom.asset.enums;

public enum RecordAction {
    BUY(0),
    SELL(1);
    private int value;

    RecordAction(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
