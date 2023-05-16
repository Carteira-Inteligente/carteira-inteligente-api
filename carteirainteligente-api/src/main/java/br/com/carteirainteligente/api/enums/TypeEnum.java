package br.com.carteirainteligente.api.enums;

public enum TypeEnum {
    ACCOUNT("ACCOUNT"),
    CREDIT_CARD("CREDIT_CARD"),
    DEBIT_CARD("DEBIT_CARD"),
    WALLET("WALLET");

    private final String typeValue;

    TypeEnum(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getTypeValue() {
        return this.typeValue;
    }
}
