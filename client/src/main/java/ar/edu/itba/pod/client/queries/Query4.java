package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.Airport;
import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.client.FileManager;
import ar.edu.itba.pod.client.queries.data.Query4Data;
import ar.edu.itba.pod.query4.Query4Collator;
import ar.edu.itba.pod.query4.Query4CombinerFactory;
import ar.edu.itba.pod.query4.Query4Mapper;
import ar.edu.itba.pod.query4.Query4ReducerFactory;
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


/*
 * n aeropuertos destino con mayor cantidad de movimientos despegue que tienen como origen a un aeropuerto oaci
 */
public class Query4 implements Query {
    private IList<Airport> airports;
    private IList<Movement> movements;
    private HazelcastInstance hz;
    private FileManager fm;
    private int n;
    private String originOaci;

    public Query4(IList<Airport> airports, IList<Movement> movements, HazelcastInstance hz, String outPath, int n, String origin) {
        this.airports = airports;
        this.movements = movements;
        this.hz = hz;
        this.fm = new FileManager(outPath);
        this.n = n;
        this.originOaci = origin;
    }

    @Override
    public void runQuery() throws InterruptedException, ExecutionException {


        JobTracker jobTracker = hz.getJobTracker("Query4");
        // oaci destino con su respectiva cantidad de despegues
        Map<String, Integer> takeOffs = new HashMap<>();


        KeyValueSource<String, Movement> kvs = KeyValueSource.fromList(movements);
        Job<String, Movement> job = jobTracker.newJob(kvs);
        ICompletableFuture<List<Entry<String, Integer>>> cf = job.mapper(new Query4Mapper(originOaci)).combiner(new Query4CombinerFactory()).reducer(new Query4ReducerFactory()).submit(new Query4Collator(n));
        // takeOffs = cf.get();


        /* Vuelco de resultados */
        List<Query4Data> answer = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : cf.get()) {
            answer.add(new Query4Data(entry.getValue(), entry.getKey()));
        }

        /* Vuelco a archivos */
        fm.appendToFile("OACI;Despegues" + "\r\n");

        for (int i = 0; i < answer.size() && i < n; i++) {
            fm.appendToFile(answer.get(i) + "\r\n");
        }
        fm.close();

    }
}

