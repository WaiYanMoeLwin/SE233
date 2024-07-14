package se233.chapter2.controller;

public class InvalidCurrencyCodeException extends RuntimeException {
    public InvalidCurrencyCodeException() {}

    public InvalidCurrencyCodeException(String base, String symbol) {
        super(String.format("The exchange rate for %s->%s is unavailable.", base, symbol));
    }
}
