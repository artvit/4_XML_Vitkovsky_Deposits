package by.epam.banks.entity;

import java.util.Objects;

public class CurrencyAmount extends Amount {
    private String currency;

    public CurrencyAmount(String currency) {
        this.currency = currency;
    }

    public CurrencyAmount(String currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyAmount)) return false;
        CurrencyAmount that = (CurrencyAmount) o;
        return Objects.equals(getCurrency(), that.getCurrency()) &&
                Double.compare(getAmount(), that.getAmount()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrency(), getAmount());
    }

    @Override
    public String toString() {
        return "CurrencyAmount{" +
                "currency='" + currency + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
