package ar.edu.itba.pod.Query1;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.MovementType;

public class Query1Mapper implements Mapper<String, Movement, String, Integer>{
	
	@Override
    public void map(String s, Movement movement, Context<String, Integer> context) {
		
		if(movement.getMovementType() == MovementType.LANDING) {
            context.emit(movement.getDestinationOACI(), 1);
        }
		
        if(movement.getMovementType() == MovementType.TAKEOFF) {
            context.emit(movement.getSourceOACI(), 1);
        }
        
	}

}
