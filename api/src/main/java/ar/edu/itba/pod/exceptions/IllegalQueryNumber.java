package ar.edu.itba.pod.exceptions;

/**
 * 
 * @author Grupo 2
 * 
 * Query Exception to be thrown when the number is invalid. 
 *
 */
public class IllegalQueryNumber extends Exception {
    private static final long serialVersionUID = 1L;

    public IllegalQueryNumber(String message) {
        super(message);
    }
}