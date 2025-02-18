package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.Airport;
import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.client.FileManager;
import ar.edu.itba.pod.client.queries.data.Query5Data;
import ar.edu.itba.pod.query5.Query5Collator;
import ar.edu.itba.pod.query5.Query5CombinerFactory;
import ar.edu.itba.pod.query5.Query5Mapper;
import ar.edu.itba.pod.query5.Query5ReducerFactory;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IList;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @author Grupo 2
 * <p>
 * Query 5 is set to answer "N airports with the lowest percentage of private flights".
 */
public class Query5 implements Query {
    private IList<Airport> airports;
    private IList<Movement> movements;
    private HazelcastInstance hz;
    private FileManager fm;
    private int n;
    private DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    private DecimalFormat formatter = new DecimalFormat("####.##", symbols);

    public Query5(IList<Airport> airports, IList<Movement> movements, HazelcastInstance hz, String outPath, int n) {
        this.airports = airports;
        this.movements = movements;
        this.hz = hz;
        this.fm = new FileManager(outPath);
        this.n = n;
        formatter.setRoundingMode(RoundingMode.DOWN);
    }

    @Override
    public void runQuery() throws InterruptedException, ExecutionException {
        JobTracker jobTracker = hz.getJobTracker("Query5");

        List<String> oaciAirports = new LinkedList<>();

        for (Airport airport : airports) {
            oaciAirports.add(airport.getOaciCode());
        }

        KeyValueSource<String, Movement> kvs = KeyValueSource.fromList(movements);
        Job<String, Movement> job = jobTracker.newJob(kvs);

        ICompletableFuture<List<Map.Entry<String, Double>>> cf = job.mapper(new Query5Mapper(oaciAirports)).
                combiner(new Query5CombinerFactory()).reducer(new Query5ReducerFactory()).
                submit(new Query5Collator(n));

        List<Query5Data> answer = new ArrayList<>();

        for (Map.Entry<String, Double> entry : cf.get()) {
            answer.add(new Query5Data(entry.getKey(), Double.valueOf(formatter.format(entry.getValue()))));
        }

        fm.appendToFile("OACI;Porcentaje\n");
        for (Query5Data data : answer) {
            fm.appendToFile(data + "\r\n");
        }
        fm.close();
    }

}

