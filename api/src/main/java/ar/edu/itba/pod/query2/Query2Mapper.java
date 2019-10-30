package ar.edu.itba.pod.query2;

import ar.edu.itba.pod.FlightType;
import ar.edu.itba.pod.Movement;
import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query2Mapper implements Mapper<String, Movement, String, Integer> {

    @Override
    public void map(String s, Movement movement, Context<String, Integer> context) {

        if(movement.getFlightType() == FlightType.CABOTAGE) {
            if (movement.getAirline().equals("N/A")) {
                context.emit("Otros", 1);
            } else {
                context.emit(movement.getAirline(), 1);
            }
        }
    }

}
