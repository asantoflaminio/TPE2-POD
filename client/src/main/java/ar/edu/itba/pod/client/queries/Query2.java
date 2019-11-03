package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.Airport;
import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.client.FileManager;
import ar.edu.itba.pod.client.queries.data.Query2Data;
import ar.edu.itba.pod.query2.Query2Collator;
import ar.edu.itba.pod.query2.Query2CombinerFactory;
import ar.edu.itba.pod.query2.Query2Mapper;
import ar.edu.itba.pod.query2.Query2ReducerFactory;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IList;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
/**
 * 
 * @author Grupo 2
 * 
 * Query 2 is set to answer "Top N airlines according to movement percentage of domestic flights
 * sorted in descending order".
 *
 */
public class Query2 implements Query {
    private IList<Movement> movements;
    private HazelcastInstance hz;
    private FileManager fm;
    private int n;
    private DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    private DecimalFormat formatter = new DecimalFormat("####.##", symbols);

    public Query2(IList<Movement> movements, HazelcastInstance hz, String outPath, int n) {
        this.movements = movements;
        this.hz = hz;
        this.fm = new FileManager(outPath);
        this.n = n;
        formatter.setRoundingMode(RoundingMode.DOWN);
    }

    @Override
    public void runQuery() throws InterruptedException, ExecutionException {
        JobTracker jobTracker = hz.getJobTracker("Query2");

        KeyValueSource<String, Movement> kvs = KeyValueSource.fromList(movements);
        Job<String, Movement> job = jobTracker.newJob(kvs);

        ICompletableFuture<List<Map.Entry<String, Double>>> cf = job.mapper(new Query2Mapper()).
                combiner(new Query2CombinerFactory()).reducer(new Query2ReducerFactory()).submit(new Query2Collator(n));

        List<Query2Data> answer = new ArrayList<>();

        for (Map.Entry<String, Double> entry : cf.get()) {
            answer.add(new Query2Data(entry.getKey(), Double.valueOf(formatter.format(entry.getValue()))));
        }

        fm.appendToFile("Aerolínea;Porcentaje\n");
        for (Query2Data data : answer) {
            fm.appendToFile(data + "\r\n");
        }
        fm.close();
    }

}

