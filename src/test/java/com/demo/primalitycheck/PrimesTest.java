package com.demo.primalitycheck;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Primes")
class PrimesTest {

    private final Primes primes = new Primes();

    @Nested
    @DisplayName("isPrime")
    class IsPrime {

        @Test
        @DisplayName("Should return false for negative numbers")
        void negativeNumbers() {
            assertThat(primes.isPrime(-1)).isFalse();
            assertThat(primes.isPrime(Long.MIN_VALUE)).isFalse();
        }

        @Test
        @DisplayName("Should return false for zero")
        void zero() {
            assertThat(primes.isPrime(0)).isFalse();
        }

        @Test
        @DisplayName("Should return false for one")
        void one() {
            assertThat(primes.isPrime(1)).isFalse();
        }

        @ParameterizedTest
        @DisplayName("Should return false for composite numbers")
        @ValueSource(longs = {4, 6, 8, 9, 10})
        void compositeNumbers(long num) {
            assertThat(primes.isPrime(num)).isFalse();
        }

        @ParameterizedTest
        @DisplayName("Should return true for prime numbers")
        @ValueSource(longs = {2, 3, 5, 7, 11})
        void primeNumbers(long num) {
            assertThat(primes.isPrime(num)).isTrue();
        }
    }


    @Nested
    @DisplayName("nextPrime")
    class NextPrime {

        @ParameterizedTest
        @DisplayName("Should return 2 if 'start' is smaller than that")
        @ValueSource(longs = {Long.MIN_VALUE, -1, 0, 1})
        void firstPrimeIsTwo(long start) {
            assertThat(primes.nextPrime(start)).isEqualTo(2);
        }

        @ParameterizedTest
        @DisplayName("Should return the input if it's a prime")
        @ValueSource(longs = {2, 3, 5, 7, 11})
        void startIsInclusive(long start) {
            assertThat(primes.nextPrime(start)).isEqualTo(start);
        }

        @Test
        @DisplayName("Should find the next prime if 'start' is a composite number")
        void findsNext() {
            assertThat(primes.nextPrime(4)).isEqualTo(5);
            assertThat(primes.nextPrime(6)).isEqualTo(7);
            assertThat(primes.nextPrime(2_000_000)).isEqualTo(2_000_003);
        }

        @Test
        @DisplayName("Should return null if the next prime doesn't fit into a long")
        void returnNullIfNextIsTooBig() {
            assertThat(primes.nextPrime(Long.MAX_VALUE)).isNull();
        }
    }

}