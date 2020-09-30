package com.demo.primalitycheck;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * This class consists of methods for working with prime numbers.
 *
 * <p>All operations in this class are thread-safe.
 */
@Component
class Primes {

    /**
     * Returns whether the provided number is a prime.
     * This method has an asymptotic complexity of O(sqrt(n)).
     *
     * @param number the number to test
     * @return {@code true} is the specified number is a prime, otherwise {@code false}.
     */
    boolean isPrime(long number) {
        if (number <= 1) {
            return false;
        }

        long sqrt = (long) Math.sqrt(number);
        for (long divisor = 2; divisor <= sqrt; divisor++) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the first integer greater than or equal to {@code start} that is a prime.
     *
     * @param start the number to start searching from (inclusive).
     *              The execution time of this method is proportional to the value of this parameter.
     * @return the first integer greater than or equal to {@code start} that is a prime,
     *         or {@code null} if such a prime would be too big to fit into a long.
     */
    @Nullable
    Long nextPrime(long start) {
        //noinspection OverflowingLoopIndex
        for (long i = Math.max(start, 2); i > 0; i++) {
            if (isPrime(i)) {
                return i;
            }
        }
        return null;
    }
}
