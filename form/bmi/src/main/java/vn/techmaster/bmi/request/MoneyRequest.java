package vn.techmaster.bmi.request;

public class MoneyRequest {
    String moneyCodeToChange;
    float moneyToChange;
    String moneyCodeChanged;

    public String getMoneyCodeToChange() {
        return moneyCodeToChange;
    }

    public void setMoneyCodeToChange(String moneyCodeToChange) {
        this.moneyCodeToChange = moneyCodeToChange;
    }

    public float getMoneyToChange() {
        return moneyToChange;
    }

    public void setMoneyToChange(float moneyToChange) {
        this.moneyToChange = moneyToChange;
    }

    public String getMoneyCodeChanged() {
        return moneyCodeChanged;
    }

    public void setMoneyCodeChanged(String moneyCodeChanged) {
        this.moneyCodeChanged = moneyCodeChanged;
    }

}
