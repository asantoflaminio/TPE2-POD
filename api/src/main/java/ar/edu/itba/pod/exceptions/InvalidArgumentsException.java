package ar.edu.itba.pod.exceptions;

/**
 * 
 * @author Grupo 2
 * 
 * Exception to be thrown when the arguments are invalid. 
 *
 */
public class InvalidArgumentsException extends Exception{
	private static final long serialVersionUID = 1L;

    public InvalidArgumentsException(String message) {
        super(message);
    }
}
