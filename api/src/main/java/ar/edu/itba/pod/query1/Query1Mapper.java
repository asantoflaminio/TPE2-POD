package ar.edu.itba.pod.query1;

import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.MovementType;

import java.util.List;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query1Mapper implements Mapper<String, Movement, String, Integer> {
	
	private final List<String> oaciAirports;

    public Query1Mapper(List<String> oaciAirports) {
        this.oaciAirports = oaciAirports;
    }

    @Override
    public void map(String s, Movement movement, Context<String, Integer> context) {
    	
    	boolean containsSource = oaciAirports.contains(movement.getSourceOACI());
        boolean containsDestination = oaciAirports.contains(movement.getDestinationOACI());
    	
        if (containsDestination && movement.getMovementType() == MovementType.LANDING) {
            context.emit(movement.getDestinationOACI(), 1);
        }

        if (containsSource && movement.getMovementType() == MovementType.TAKEOFF) {
            context.emit(movement.getSourceOACI(), 1);
        }

    }

}
