package by.epam.banks.entity;

import by.epam.banks.enumeration.DepositType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Deposit {
    private String depositId;
    private String bank;
    private String country;
    private DepositType type;
    private String depositorFirstName;
    private String depositorLastName;
    private Amount amount;
    private double profitability;
    private Calendar time;

    public String getDepositId() {
        return depositId;
    }

    public void setDepositId(String depositId) {
        this.depositId = depositId;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public DepositType getType() {
        return type;
    }

    public void setType(DepositType type) {
        this.type = type;
    }

    public String getDepositorFirstName() {
        return depositorFirstName;
    }

    public void setDepositorFirstName(String depositorFirstName) {
        this.depositorFirstName = depositorFirstName;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public double getProfitability() {
        return profitability;
    }

    public void setProfitability(double profitability) {
        this.profitability = profitability;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getDepositorLastName() {
        return depositorLastName;
    }

    public void setDepositorLastName(String depositorLastName) {
        this.depositorLastName = depositorLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deposit)) return false;
        Deposit deposit = (Deposit) o;
        return Objects.equals(getAmount(), deposit.getAmount()) &&
                Double.compare(deposit.getProfitability(), getProfitability()) == 0 &&
                Objects.equals(getDepositId(), deposit.getDepositId()) &&
                Objects.equals(getBank(), deposit.getBank()) &&
                Objects.equals(getCountry(), deposit.getCountry()) &&
                getType() == deposit.getType() &&
                Objects.equals(getDepositorFirstName(), deposit.getDepositorFirstName()) &&
                Objects.equals(getDepositorLastName(), deposit.getDepositorLastName()) &&
                Objects.equals(getTime(), deposit.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDepositId(), getBank(), getCountry(), getType(), getDepositorFirstName(), getAmount(), getProfitability(), getTime());
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy mm dd");
        return "Deposit{" +
                "depositId='" + depositId + '\'' +
                ", bank='" + bank + '\'' +
                ", country='" + country + '\'' +
                ", type=" + type +
                ", depositorFirstName='" + depositorFirstName + '\'' +
                ", depositorLastName='" + depositorLastName + '\'' +
                ", amount=" + amount +
                ", profitability=" + profitability + "%" +
                ", time=" + dateFormat.format(time.getTime()) +
                '}';
    }
}
