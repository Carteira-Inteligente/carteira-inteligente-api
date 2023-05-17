package br.com.carteirainteligente.api.enums;

public enum PeriodEnum {
    NOT_REPEAT("NOT_REPEAT"),
    DAILY("DAILY"),
    WEEKLY("WEEKLY"),
    BIWEEKLY("BIWEEKLY"),
    MONTHLY("MONTHLY"),
    BIMONTHLY("BIMONTHLY"),
    QUARTERLY("QUARTERLY"),
    SEMESTERLY("SEMESTERLY"),
    ANNUAL("ANNUAL");

    private final String periodValue;

    PeriodEnum(String periodValue) {
        this.periodValue = periodValue;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

}
