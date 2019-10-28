package ar.edu.itba.pod;

import java.io.Serializable;

public class Movement implements Serializable {
    private final FlightType flightType;
    private final MovementType movementType;
    private final String sourceOACI;
    private final String destinationOACI;

    public Movement(FlightType flightType, MovementType movementType, String sourceOACI, String destinationOACI) {
        this.flightType = flightType;
        this.movementType = movementType;
        this.sourceOACI = sourceOACI;
        this.destinationOACI = destinationOACI;
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
}
