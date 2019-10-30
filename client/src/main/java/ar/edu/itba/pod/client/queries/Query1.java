package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.Airport;
import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.client.FileManager;
import ar.edu.itba.pod.query1.Query1CombinerFactory;
import ar.edu.itba.pod.query1.Query1Mapper;
import ar.edu.itba.pod.query1.Query1ReducerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IList;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import java.util.*;
import java.util.concurrent.ExecutionException;

/*
 * Query 1: Movimientos por aeropuerto.
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

        /* Como fuente queremos a los movements, ya que
         * lo que pide esta query es movimientos por aeropuerto*/
        KeyValueSource<String, Movement> kvs = KeyValueSource.fromList(movements);
        Job<String, Movement> job = jobTracker.newJob(kvs);

        /*Aca viene lo que es el mapreduce, donde seteamos nuestro mapa, combiner y reducer de API */
        ICompletableFuture<Map<String, Integer>> cf = job.mapper(new Query1Mapper()).combiner(new Query1CombinerFactory()).reducer(new Query1ReducerFactory()).submit();

        /* Datos que obtenemos de la reduccion */
        Map<String, Integer> movementsPerAirport = cf.get();
        System.out.println("movementsPerAiprot keyset es : " + movementsPerAirport.size());
        
        List<Query1Data> answer = new ArrayList<>();

        Map<String, String> mapOaci = new HashMap<>();
        System.out.println("airports " + airports.size());
        for(Airport airport : airports) {
        	mapOaci.put(airport.getOaciCode(),airport.getName());
        }
        System.out.println("mapOaci es " + mapOaci.size());

        /* Generate output joining oaci with name */
        for(String oaciCode : movementsPerAirport.keySet()) {
            String name = mapOaci.get(oaciCode);
            if(name != null) {
            	//System.out.println("agrego");
            	answer.add(new Query1Data(name, oaciCode, movementsPerAirport.get(oaciCode)));
            }
        }
        
        System.out.println("antes del sort DATOS SON: " + answer.size());

        /* Orden descendente y alfabetico */
        answer.sort((Query1Data a, Query1Data b) -> {
            if(a.getAccum() == b.getAccum()) {
                return a.getOaciCode().compareTo(b.getOaciCode());
            }
            return a.getAccum() - b.getAccum();
		});
        
        
        /* Impresion a archivos */
        // header
        fm.appendToFile("OACI;Denominación;Movimientos\n");
        // data
        System.out.println("DATOS SON: " + answer.size());
        for(Query1Data data : answer) {
        	System.out.println(data);
            fm.appendToFile(data +"\r\n");
        }

    }

}