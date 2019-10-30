package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.Airport;
import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.Pair;
import ar.edu.itba.pod.client.FileManager;
import ar.edu.itba.pod.client.queries.data.Query6Data;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/*
 * Pares de provincias que comparten al menos min movimientos.
 */
public class Query6 implements Query {
	private IList<Airport> airports;
    private IList<Movement> movements;
    private HazelcastInstance hz;
    private FileManager fm;
    private int min;

    public Query6(IList<Airport> airports, IList<Movement> movements, HazelcastInstance hz,  String outPath, int min) {
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

        for(Airport airport : airports) {
        	oaciStateMap.put(airport.getOaciCode(), airport.getState());
		}
        

        Map<Pair<String,String>, Integer> pairData = new HashMap<>();       
        KeyValueSource<String, Movement> kvs = KeyValueSource.fromList(movements);
        Job<String, Movement> job = jobTracker.newJob(kvs);

        ICompletableFuture<Map<Pair<String,String>, Integer>> cf = job.mapper(new Query6Mapper(oaciStateMap)).combiner(new Query6CombinerFactory()).reducer(new Query6ReducerFactory()).submit();
		pairData = cf.get();

        /* Ordenamiento de datos */
        List<Query6Data> answer = new ArrayList<>();
        System.out.println("size de pairdata " + pairData.size());
        System.out.println("min es " + min);
        for(Pair<String, String> pair : pairData.keySet()) {
        	
            int movements = pairData.get(pair);
            System.out.println("movements es " + movements);
            if(min <= movements) {
                String stateA = pair.getElement0();
                String stateB = pair.getElement1();

                if(pair.getElement1().compareTo(pair.getElement0()) < 0) {
                    stateA = pair.getElement1();
                    stateB = pair.getElement0();
                }
                System.out.println("AGREGO");
                answer.add(new Query6Data(new Pair<String,String>(stateA, stateB), movements));
            }
        }

		Collections.sort(answer);

        /* Vuelco a archivos */
        
		System.out.println("size de answer es " + answer.size());

		fm.appendToFile("Provincia A;Provincia B;Movimientos\r\n");

        for(Query6Data data: answer) {
            fm.appendToFile(data + "\r\n");
        }
        fm.close();
        
    }
}
