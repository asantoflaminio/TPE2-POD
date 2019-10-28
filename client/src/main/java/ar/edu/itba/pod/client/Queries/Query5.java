package ar.edu.itba.pod.client.Queries;

import ar.edu.itba.pod.Airport;
import ar.edu.itba.pod.Movement;
import com.hazelcast.core.HazelcastInstance;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Query5 implements Query {
    private List<Airport> airports;
    private List<Movement> movements;
    private HazelcastInstance hz;

    public Query5(List<Airport> airports, List<Movement> movements, HazelcastInstance hz) {
        this.airports = airports;
        this.movements = movements;
        this.hz = hz;
    }

    @Override
    public void runQuery() throws InterruptedException, ExecutionException {

    }
}

