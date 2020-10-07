package com.demo.primalitycheck;

import com.demo.primalitycheck.model.ApiError;
import com.demo.primalitycheck.model.PrimalityCheckResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DisplayName("PrimalityTestController")
@ExtendWith(MockitoExtension.class)
class PrimalityCheckControllerTest {

    @Mock
    private Primes primes;
    @InjectMocks
    private PrimalityCheckController controller;

    @Test
    @DisplayName("Should call 'nextPrime' and use it's return value in the result")
    void returnNextPrime() {
        when(primes.nextPrime(anyLong())).thenReturn(3L);
        PrimalityCheckResult result = controller.isPrimeNumber(3);

        assertThat(result).isEqualToComparingFieldByField(new PrimalityCheckResult(true, 3L));
        verify(primes).nextPrime(3);
    }

    @Test
    @DisplayName("Should set 'isPrime' to true if the next prime is equal to the input")
    void inputIsAPrime() {
        when(primes.nextPrime(anyLong())).thenReturn(2L);
        PrimalityCheckResult result = controller.isPrimeNumber(2);

        assertThat(result).isEqualToComparingFieldByField(new PrimalityCheckResult(true, 2L));
    }

    @Test
    @DisplayName("Should set 'isPrime' to false if the next prime is bigger than the input")
    void inputIsNotAPrime() {
        when(primes.nextPrime(anyLong())).thenReturn(5L);
        PrimalityCheckResult result = controller.isPrimeNumber(4);

        assertThat(result).isEqualToComparingFieldByField(new PrimalityCheckResult(false, 5L));
    }

    @Test
    @DisplayName("Should set 'isPrime' to false and 'nextPrime' to null if the next prime is too big")
    void nextPrimeTooBig() {
        when(primes.nextPrime(anyLong())).thenReturn(null);
        PrimalityCheckResult result = controller.isPrimeNumber(Long.MAX_VALUE);

        assertThat(result).isEqualToComparingFieldByField(new PrimalityCheckResult(false, null));
    }

    @Test
    @DisplayName("Should send a custom response in case the parameter is missing")
    void missingParameterHandler() {
        ResponseEntity<Object> response = controller.handleMissingServletRequestParameter();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualToComparingFieldByField(new ApiError("Missing required parameter 'number'"));
    }

    @Test
    @DisplayName("Should send a custom error message if the given number is too big to fit into a long")
    void numberTooBigHandler() {
        ResponseEntity<ApiError> response = controller.handleMethodArgumentTypeMismatch(argumentTypeMismatchException("9223372036854775808"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualToComparingFieldByField(new ApiError("Parameter 'number' must be between -2^63 and 2^63-1. Received: 9223372036854775808"));
    }

    @Test
    @DisplayName("Should send a custom error message if the given number is too small to fit into a long")
    void numberTooSmallHandler() {
        ResponseEntity<ApiError> response = controller.handleMethodArgumentTypeMismatch(argumentTypeMismatchException("-9223372036854775809"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualToComparingFieldByField(new ApiError("Parameter 'number' must be between -2^63 and 2^63-1. Received: -9223372036854775809"));
    }

    @Test
    @DisplayName("Should send a custom error message if the input is not an integer")
    void inputIsNotAnIntegerHandler() {
        ResponseEntity<ApiError> response = controller.handleMethodArgumentTypeMismatch(argumentTypeMismatchException("asd"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualToComparingFieldByField(new ApiError("Parameter 'number' must be an integer"));
    }

    private MethodArgumentTypeMismatchException argumentTypeMismatchException(String value) {
        return new MethodArgumentTypeMismatchException(value, long.class, "number", mock(MethodParameter.class), new NumberFormatException());
    }
}