package ar.edu.itba.pod.exceptions;

public class InvalidArgumentsException extends Exception{
	private static final long serialVersionUID = 1L;

    public InvalidArgumentsException(String message) {
        super(message);
    }
}
