package ar.edu.itba.pod;

import java.io.Serializable;

/**
 * 
 * @author Grupo 2
 * 
 * A flight can be of two types: cabotage or international.
 * This data can also be 'not available'.
 *
 */
public enum FlightType implements Serializable {
    CABOTAGE, INTERNATIONAL, NA
}
