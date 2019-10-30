package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.Airport;
import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.client.FileManager;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;

import java.util.concurrent.ExecutionException;

public class Query5 implements Query {
    private IList<Airport> airports;
    private IList<Movement> movements;
    private HazelcastInstance hz;
    private FileManager fm;
    private int n;


    public Query5(IList<Airport> airports, IList<Movement> movements, HazelcastInstance hz, String outPath, int n) {
        this.airports = airports;
        this.movements = movements;
        this.hz = hz;
        this.fm = new FileManager(outPath);
        this.n = n;
    }

    @Override
    public void runQuery() throws InterruptedException, ExecutionException {

    }
}

