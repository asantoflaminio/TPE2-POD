package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.Airport;
import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.Pair;
import ar.edu.itba.pod.client.FileManager;
import ar.edu.itba.pod.client.queries.data.Query6Data;
import ar.edu.itba.pod.query6.Query6Collator;
import ar.edu.itba.pod.query6.Query6CombinerFactory;
import ar.edu.itba.pod.query6.Query6Mapper;
import ar.edu.itba.pod.query6.Query6ReducerFactory;
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
 * Query 6 is set to answer "State pairs that share at least min movements".
 *
 */
public class Query6 implements Query {
    private IList<Airport> airports;
    private IList<Movement> movements;
    private HazelcastInstance hz;
    private FileManager fm;
    private int min;

    public Query6(IList<Airport> airports, IList<Movement> movements, HazelcastInstance hz, String outPath, int min) {
        this.airports = airports;
        this.movements = movements;
        this.hz = hz;
        this.fm = new FileManager(outPath);
        this.min = min;
    }

    @Override
    public void runQuery() throws InterruptedException, ExecutionException {


        JobTracker jobTracker = hz.getJobTracker("Query6");

        Map<String, String> oaciStateMap = new HashMap<>();

        for (Airport airport : airports) {
            oaciStateMap.put(airport.getOaciCode(), airport.getState());
        }

        KeyValueSource<String, Movement> kvs = KeyValueSource.fromList(movements);
        Job<String, Movement> job = jobTracker.newJob(kvs);

        ICompletableFuture<List<Entry<Pair<String, String>, Integer>>> cf = job.mapper(new Query6Mapper(oaciStateMap)).combiner(new Query6CombinerFactory()).reducer(new Query6ReducerFactory()).submit(new Query6Collator(min));

        List<Query6Data> answer = new ArrayList<>();
        for(Map.Entry<Pair<String,String>, Integer> entry: cf.get()) {
        	answer.add(new Query6Data(new Pair<String, String>(entry.getKey().getElement1(), entry.getKey().getElement0()), entry.getValue()));
        }

        /* Vuelco a archivos */
        fm.appendToFile("Provincia A;Provincia B;Movimientos\r\n");

        for (Query6Data data : answer) {
            fm.appendToFile(data + "\r\n");
        }
        fm.close();

    }
}
