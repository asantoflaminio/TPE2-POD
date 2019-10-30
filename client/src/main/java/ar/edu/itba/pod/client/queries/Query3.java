package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.Airport;
import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.client.FileManager;
import ar.edu.itba.pod.client.queries.aux.Query3Data;
import ar.edu.itba.pod.query1.Query1CombinerFactory;
import ar.edu.itba.pod.query1.Query1Mapper;
import ar.edu.itba.pod.query1.Query1ReducerFactory;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/*
 * Pares de aeropuertos que registran la misma cantidad de miles de movimientos.
 */
public class Query3 implements Query {
	private IList<Airport> airports;
    private IList<Movement> movements;
    private HazelcastInstance hz;
    private FileManager fm;

    public Query3(IList<Airport> airports, IList<Movement> movements, HazelcastInstance hz,  String outPath) {
        this.airports = airports;
        this.movements = movements;
        this.hz = hz;
        this.fm = new FileManager(outPath);
    }

    @Override
    public void runQuery() throws InterruptedException, ExecutionException {
    	

        JobTracker jobTracker = hz.getJobTracker("Query3");

        Map<String, Integer> movesMap = new HashMap<>();
        		
        /*
         * Idem que para Query 1. Necesitamos esos mismos datos. 
         */
        KeyValueSource<String, Movement> kvs = KeyValueSource.fromList(movements);
        Job<String, Movement> job = jobTracker.newJob(kvs);
        ICompletableFuture<Map<String, Integer>> cf = job.mapper(new Query1Mapper()).combiner(new Query1CombinerFactory()).reducer(new Query1ReducerFactory()).submit();
		movesMap = cf.get();
        
        
        

        // La key van a ser el grupo de ese "mil", junto con la lista de OACI que corresponden a ese grupo
        Map<Integer, List<String>> movementsInThousands = new HashMap<>();
        
        IMap<String, Integer> IMapOaci = hz.getMap("movesOaci");
        IMapOaci.putAll(movesMap);


        KeyValueSource<String, Integer> kvs2 = KeyValueSource.fromMap(IMapOaci);
        Job<String, Integer> job2 = jobTracker.newJob(kvs2);
        ICompletableFuture<Map<Integer, List<String>>> cf2 = job2.mapper(new Query3Mapper()).reducer(new Query3ReducerFactory()).submit();
        movementsInThousands = cf2.get();


        IMapOaci.destroy();

        
        
        /*
         * Ordenamiento de datos a imprimir.
         * Nos piden desc por grupo y luego alfabetico. 
         */
        
        List<Query3Data> answer = new ArrayList<>();
        

        for(Integer group: movementsInThousands.keySet()) {
        	
            List<String> airportsWithinGroup = movementsInThousands.get(group);
            if(airportsWithinGroup.size() > 1) {
            	
                Collections.sort(airportsWithinGroup);
                for(int i = 0; i < airportsWithinGroup.size(); i++) {
                    String code1 = airportsWithinGroup.get(i);
                    
                    for(int j = i + 1; j < airportsWithinGroup.size(); j++) {
                        String code2= airportsWithinGroup.get(j);
                        answer.add(new Query3Data(code1, code2, group));
                    }
                }
            }
        }


        Collections.sort(answer);


      
        /* Impresion a archivos */
        // header
        fm.appendToFile("Grupo;Aeropuerto A;Aeropuerto B" + "\r\n");
        for(Query3Data data : answer) {
            fm.appendToFile(data +"\r\n");
        }
        fm.close();

    }
}
