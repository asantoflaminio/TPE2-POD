package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.Airport;
import ar.edu.itba.pod.Movement;
import com.hazelcast.core.HazelcastInstance;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class Query1 implements Query {
    private List<Airport> airports;
    private List<Movement> movements;
    private HazelcastInstance hz;

    public Query1(List<Airport> airports, List<Movement> movements, HazelcastInstance hz) {
        this.airports = airports;
        this.movements = movements;
        this.hz = hz;
    }

    @Override
    public void runQuery() throws InterruptedException, ExecutionException {

    }

}