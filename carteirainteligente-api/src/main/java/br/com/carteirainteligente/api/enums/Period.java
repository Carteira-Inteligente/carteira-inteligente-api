package br.com.carteirainteligente.api.enums;

public enum Period {
    UNICO("UNICO"),
    SEMANAL("SEMANAL"),
    MENSAL("MENSAL"),
    TRIMESTRAL("TRIMESTRAL"),
    SEMESTRAL("SEMESTRAL"),
    ANUAL("ANUAL");

    private final String periodValue;

    Period(String periodValue) {
        this.periodValue = periodValue;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

}
