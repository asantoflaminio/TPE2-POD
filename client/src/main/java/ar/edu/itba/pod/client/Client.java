package ar.edu.itba.pod.client;

import ar.edu.itba.pod.Airport;
import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.client.Parsers.AirportParser;
import ar.edu.itba.pod.client.Parsers.MovementParser;
import ar.edu.itba.pod.client.Parsers.Parser;
import ar.edu.itba.pod.client.Queries.Query;
import ar.edu.itba.pod.client.Queries.Query1.Query1;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Client {
    private static Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        logger.info("The client is starting");
        List<Airport> airports = loadAirportCSV();
        List<Movement> movements = loadMovementsCSV();
        Query query;

        HazelcastInstance hz = HazelcastClient.newHazelcastClient();

        query = new Query1(airports, movements, hz);
        query.runQuery();

        hz.shutdown();
    }

    private static List<Airport> loadAirportCSV() {
        Parser<Airport> ap = new AirportParser();
        return ap.loadCSVFile(Paths.get("aeropuertos.csv"));
    }

    private static List<Movement> loadMovementsCSV() {
        Parser<Movement> mp = new MovementParser();
        return mp.loadCSVFile(Paths.get("movimientos.csv"));
    }


}
