package ar.edu.itba.pod.query4;

import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.MovementType;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query4Mapper implements Mapper<String, Movement, String, Integer> {

    private final String oaciCodeOrigin;

    public Query4Mapper(String oaciCode) {
        this.oaciCodeOrigin = oaciCode;
    }

    @Override
    public void map(String s, Movement movement, Context<String, Integer> context) {
        if (movement.getMovementType() == MovementType.TAKEOFF && movement.getSourceOACI().equalsIgnoreCase(oaciCodeOrigin)) {
            context.emit(movement.getDestinationOACI(), 1);
        }
    }
}
