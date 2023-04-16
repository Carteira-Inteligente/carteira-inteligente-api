package br.com.carteirainteligente.api.enums;

public enum Type {
    ACCOUNT("ACCOUNT"),
    CREDIT_CARD("CREDIT_CARD"),
    DEBIT_CARD("DEBIT_CARD"),
    WALLET("WALLET");

    private final String typeValue;

    Type(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getTypeValue() {
        return this.typeValue;
    }
}
