package ar.edu.itba.pod.exceptions;

public class IllegalQueryNumber extends Exception {
    private static final long serialVersionUID = 1L;

    public IllegalQueryNumber(String message) {
        super(message);
    }
}