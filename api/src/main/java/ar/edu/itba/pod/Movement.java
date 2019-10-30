package ar.edu.itba.pod;

import java.io.Serializable;

public class Movement implements Serializable {
    private final FlightType flightType;
    private final MovementType movementType;
    private final String sourceOACI;
    private final String destinationOACI;
    private final String airline;

    public Movement(FlightType flightType, MovementType movementType, String sourceOACI, String destinationOACI,
                    String airline) {
        this.flightType = flightType;
        this.movementType = movementType;
        this.sourceOACI = sourceOACI;
        this.destinationOACI = destinationOACI;
        this.airline = airline;
    }

    public FlightType getFlightType() {
        return flightType;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public String getSourceOACI() {
        return sourceOACI;
    }

    public String getDestinationOACI() {
        return destinationOACI;
    }

    public String getAirline() {
        return airline;
    }
}
