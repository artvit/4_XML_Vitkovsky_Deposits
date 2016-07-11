package by.epam.banks.action;

import by.epam.banks.entity.Deposit;

import java.util.Set;

public class DepositsReporter {
    private Set<Deposit> deposits;

    public DepositsReporter() {
    }

    public DepositsReporter(Set<Deposit> deposits) {
        this.deposits = deposits;
    }

    public String getDepositsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Deposit deposit : deposits) {
            stringBuilder.append(deposit).append("\n");
        }
        return stringBuilder.toString();
    }

    public Set<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(Set<Deposit> deposits) {
        this.deposits = deposits;
    }
}
