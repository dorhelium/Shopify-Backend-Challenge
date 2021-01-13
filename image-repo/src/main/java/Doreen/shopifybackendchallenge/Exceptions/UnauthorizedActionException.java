package Doreen.shopifybackendchallenge.Exceptions;

public class UnauthorizedActionException extends RuntimeException{

    public UnauthorizedActionException(String message) {
        super(message);
    }

}
