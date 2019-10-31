package ar.edu.itba.pod.query5;

import ar.edu.itba.pod.FlightClass;
import ar.edu.itba.pod.Movement;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import java.util.List;

public class Query5Mapper implements Mapper<String, Movement, String, Integer> {

    private final List<String> oaciAirports;

    public Query5Mapper(List<String> oaciAirports) {
        this.oaciAirports = oaciAirports;
    }

    @Override
    public void map(String s, Movement movement, Context<String, Integer> context) {

        boolean containsSource = oaciAirports.contains(movement.getSourceOACI());
        boolean containsDestination = oaciAirports.contains(movement.getDestinationOACI());

        if (movement.getFlightClass() == FlightClass.PRIVATE) {
            if (containsSource) {
                context.emit(movement.getSourceOACI(), 1);
            }

            if (containsDestination) {
                context.emit(movement.getDestinationOACI(), 1);
            }
        } else {
            if (containsSource) {
                context.emit(movement.getSourceOACI(), 0);
            }

            if (containsDestination) {
                context.emit(movement.getDestinationOACI(), 0);
            }
        }
    }
}

