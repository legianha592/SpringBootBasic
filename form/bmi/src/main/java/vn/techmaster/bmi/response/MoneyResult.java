package vn.techmaster.bmi.response;

public class MoneyResult {
    float moneyChanged;

    public MoneyResult(float moneyChanged) {
        this.moneyChanged = moneyChanged;
    }

    public float getMoneyChanged() {
        return moneyChanged;
    }

    public void setMoneyChanged(float moneyChanged) {
        this.moneyChanged = moneyChanged;
    }
}
