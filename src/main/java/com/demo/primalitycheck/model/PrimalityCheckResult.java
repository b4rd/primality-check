package com.demo.primalitycheck.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;

public class PrimalityCheckResult {

    private final boolean isPrime;
    private final Long nextPrime;

    public PrimalityCheckResult(boolean isPrime, Long nextPrime) {
        this.isPrime = isPrime;
        this.nextPrime = nextPrime;
    }

    @JsonProperty(value="isPrime")
    public boolean isPrime() {
        return isPrime;
    }

    @Nullable
    public Long getNextPrime() {
        return nextPrime;
    }

    @Override
    public String toString() {
        return "PrimalityCheckResult{" +
                "isPrime=" + isPrime +
                ", nextPrime=" + nextPrime +
                '}';
    }
}
