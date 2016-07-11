package by.epam.banks.entity;

import java.util.Objects;

public class MetalAmount extends Amount {
    private String metal;

    public MetalAmount(String metal) {
        this.metal = metal;
    }

    public MetalAmount(String metal, double amount) {
        this.metal = metal;
        this.amount = amount;
    }

    public String getMetal() {
        return metal;
    }

    public void setMetal(String metal) {
        this.metal = metal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MetalAmount)) return false;
        MetalAmount that = (MetalAmount) o;
        return Objects.equals(getMetal(), that.getMetal()) &&
                Double.compare(getAmount(), that.getAmount()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMetal(), getAmount());
    }

    @Override
    public String toString() {
        return "MetalAmount{" +
                "metal='" + metal + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
