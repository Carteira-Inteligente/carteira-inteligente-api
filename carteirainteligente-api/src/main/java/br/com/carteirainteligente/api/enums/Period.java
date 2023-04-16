package br.com.carteirainteligente.api.enums;

public enum Period {
    UNIQUE("UNIQUE"),
    WEEKLY("WEEKLY"),
    MONTHLY("MONTHLY"),
    QUARTERLY("QUARTERLY"),
    SEMESTERLY("SEMESTERLY"),
    ANNUAL("ANNUAL");

    private final String periodValue;

    Period(String periodValue) {
        this.periodValue = periodValue;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

}
