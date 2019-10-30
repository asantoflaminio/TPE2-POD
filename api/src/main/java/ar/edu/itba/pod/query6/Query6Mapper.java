package ar.edu.itba.pod.query6;

import java.util.Map;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.Pair;

public class Query6Mapper implements Mapper<String, Movement, Pair<String, String>, Integer> {
	
    private final Map<String, String> mapOaciState;

    public Query6Mapper(Map<String, String> map) {
        this.mapOaciState = map;
    }

    @Override
    public void map(String s, Movement movement, Context<Pair<String,String>, Integer> context) {

        String origin= mapOaciState.get(movement.getSourceOACI());
        String destination = mapOaciState.get(movement.getDestinationOACI());

        if(origin != null && destination != null && !origin.equalsIgnoreCase(destination)) {
        	if(origin.compareTo(destination) < 0) {
                context.emit(new Pair<String, String>(destination, origin), 1);
            } else {
            	context.emit(new Pair<String, String>(origin, destination), 1);
            }        
        }
    }
}
