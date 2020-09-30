package com.demo.primalitycheck;

import com.demo.primalitycheck.model.ApiError;
import com.demo.primalitycheck.model.PrimalityCheckResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
public class PrimalityCheckController {

    private final Primes primes;

    public PrimalityCheckController(Primes primes) {
        this.primes = primes;
    }

    @GetMapping("/primality-check")
    public PrimalityCheckResult isPrimeNumber(@RequestParam("number") long number) {
        Long nextPrime = primes.nextPrime(number);
        boolean isPrime = nextPrime != null && nextPrime == number;
        return new PrimalityCheckResult(isPrime, nextPrime);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameter() {
        return new ResponseEntity<>(new ApiError("Missing required parameter 'number'"), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String received = String.valueOf(ex.getValue());
        String message;
        if (received.matches("-?[1-9]\\d*")) {
            message = "Parameter 'number' must be between -2^63 and 2^63-1. Received: " + received;
        } else {
            message = "Parameter 'number' must be an integer";
        }
        return new ResponseEntity<>(new ApiError(message), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
