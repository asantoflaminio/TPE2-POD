package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.Airport;
import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.Pair;
import ar.edu.itba.pod.client.FileManager;
import ar.edu.itba.pod.client.queries.data.Query3Data;
import ar.edu.itba.pod.query1.Query1CombinerFactory;
import ar.edu.itba.pod.query1.Query1Mapper;
import ar.edu.itba.pod.query1.Query1ReducerFactory;
import ar.edu.itba.pod.query3.Query3Collator;
import ar.edu.itba.pod.query3.Query3Mapper;
import ar.edu.itba.pod.query3.Query3ReducerFactory;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


/**
 * @author Grupo 2
 * <p>
 * Query 3 is set to answer "Airport pairs that have the same amount of flights in thousands".
 */
public class Query3 implements Query {
    private IList<Movement> movements;
    private HazelcastInstance hz;
    private FileManager fm;
    private IList<Airport> airports;

    public Query3(IList<Airport> airports, IList<Movement> movements, HazelcastInstance hz, String outPath) {
    	this.airports = airports;
        this.movements = movements;
        this.hz = hz;
        this.fm = new FileManager(outPath);
    }

    @Override
    public void runQuery() throws InterruptedException, ExecutionException {
        JobTracker jobTracker = hz.getJobTracker("Query3");

        Map<String, Integer> movesMap;

        /*
         * Idem que para Query 1. Necesitamos esos mismos datos.
         */
        KeyValueSource<String, Movement> kvs = KeyValueSource.fromList(movements);
        Job<String, Movement> job = jobTracker.newJob(kvs);
        
        List<String> oaciAirports = new LinkedList<>();

        for (Airport airport : airports) {
            oaciAirports.add(airport.getOaciCode());
        }
        
        ICompletableFuture<Map<String, Integer>> cf =
                job.mapper(new Query1Mapper(oaciAirports)).combiner(new Query1CombinerFactory()).reducer(new Query1ReducerFactory()).submit();
        movesMap = cf.get();


        IMap<String, Integer> IMapOaci = hz.getMap("movesOaci");
        IMapOaci.putAll(movesMap);


        KeyValueSource<String, Integer> kvs2 = KeyValueSource.fromMap(IMapOaci);
        Job<String, Integer> job2 = jobTracker.newJob(kvs2);
        ICompletableFuture<List<Map.Entry<Integer, Pair<String, String>>>> cf2 =
                job2.mapper(new Query3Mapper()).reducer(new Query3ReducerFactory()).submit(new Query3Collator());

        List<Query3Data> answer = new ArrayList<>();

        for (Map.Entry<Integer, Pair<String, String>> v : cf2.get()) {
            answer.add(new Query3Data(v.getKey(), v.getValue().getElement0(), v.getValue().getElement1()));
        }

        fm.appendToFile("Grupo;Aeropuerto A;Aeropuerto B" + "\r\n");
        for (Query3Data data : answer) {
            fm.appendToFile(data + "\r\n");
        }
        fm.close();

    }
}
