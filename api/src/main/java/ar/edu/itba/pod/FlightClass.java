package ar.edu.itba.pod;

import java.io.Serializable;

/**
 * 
 * @author Grupo 2
 * 
 * A flight can have three types of classes: regular, not regular and private. 
 *
 */
public enum FlightClass implements Serializable {
    NOTREGULAR, REGULAR, PRIVATE
}
