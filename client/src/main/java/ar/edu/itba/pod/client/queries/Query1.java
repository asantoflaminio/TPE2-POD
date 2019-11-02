package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.Airport;
import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.client.FileManager;
import ar.edu.itba.pod.client.queries.data.Query1Data;
import ar.edu.itba.pod.query1.Query1Collator;
import ar.edu.itba.pod.query1.Query1CombinerFactory;
import ar.edu.itba.pod.query1.Query1Mapper;
import ar.edu.itba.pod.query1.Query1ReducerFactory;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IList;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;



/**
 * 
 * @author Grupo 2
 * 
 * Query 1 is set to answer "Movements per airport".
 *
 */
public class Query1 implements Query {
    private IList<Airport> airports;
    private IList<Movement> movements;
    private HazelcastInstance hz;
    private FileManager fm;

    public Query1(IList<Airport> airports, IList<Movement> movements, HazelcastInstance hz, String outPath) {
        this.airports = airports;
        this.movements = movements;
        this.hz = hz;
        this.fm = new FileManager(outPath);
    }

    @Override
    public void runQuery() throws InterruptedException, ExecutionException {

        JobTracker jobTracker = hz.getJobTracker("Query1");


        KeyValueSource<String, Movement> kvs = KeyValueSource.fromList(movements);
        Job<String, Movement> job = jobTracker.newJob(kvs);

        ICompletableFuture<List<Entry<String, Integer>>> cf = job.mapper(new Query1Mapper()).combiner(new Query1CombinerFactory()).reducer(new Query1ReducerFactory()).submit(new Query1Collator());
        List<Query1Data> answer = new ArrayList<>();

        Map<String, String> mapOaci = new HashMap<>();
        for (Airport airport : airports) {
            mapOaci.put(airport.getOaciCode(), airport.getName());
        }


        for (Map.Entry<String, Integer> entry : cf.get()) {
            answer.add(new Query1Data(mapOaci.get(entry.getKey()), entry.getKey(), entry.getValue()));
        }

        /* Print to file */
        // header
        fm.appendToFile("OACI;Denominación;Movimientos\n");
        for (Query1Data data : answer) {
            fm.appendToFile(data + "\r\n");
        }
        fm.close();

    }

}