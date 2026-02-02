package br.edu.ufrn.expensify.transaction.exception;

public class TransactionNotFoundException extends RuntimeException {
    
    public TransactionNotFoundException(String message) {
        super(message);
    }

}
