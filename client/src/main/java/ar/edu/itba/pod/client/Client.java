package ar.edu.itba.pod.client;

import ar.edu.itba.pod.Airport;
import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.client.parsers.AirportParser;
import ar.edu.itba.pod.client.parsers.MovementParser;
import ar.edu.itba.pod.client.parsers.Parser;
import ar.edu.itba.pod.client.parsers.SystemPropertiesParser;
import ar.edu.itba.pod.client.queries.*;
import ar.edu.itba.pod.exceptions.IllegalQueryNumber;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Client {
    private static Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException, IllegalQueryNumber {
        logger.info("The client is starting");

        SystemPropertiesParser sysinput = new SystemPropertiesParser();
        logger.info("Query number: " + sysinput.getQueryNumber());

        
        

        ClientConfig clientConfig = getConfig(sysinput);

        Query query;
        HazelcastInstance hz;

        try {
            hz = HazelcastClient.newHazelcastClient(clientConfig);
        } catch (IllegalStateException e) {
            System.out.println("Unable to connect to cluster");
            return;
        }
        
        
        IList<Airport> airportsIList = hz.getList("airports");
        IList<Movement> movementsIList = hz.getList("movements");
        
        IList<Airport> airports = loadAirportCSV(sysinput, airportsIList);
        IList<Movement> movements = loadMovementsCSV(sysinput, movementsIList);

        
        int querynumber = sysinput.getQueryNumber();
        System.out.println("Ans size (airports) es " + airports.size());
        System.out.println("Ans size (movmeents) es " + movements.size());
        query = getQuery(querynumber, airports, movements, hz, sysinput.getOutPath());

        logger.info("Starting map/reduce job for query number " + querynumber);
        query.runQuery();

        logger.info("Finished map/reduce job");
        hz.shutdown();
    }

    private static IList<Airport> loadAirportCSV(SystemPropertiesParser sysinput, IList<Airport> airportsIList) {
        Parser<Airport> ap = new AirportParser();
        return ap.loadCSVFile(Paths.get(sysinput.getInPath().concat("aeropuertos.csv")), airportsIList);
    }

    private static IList<Movement> loadMovementsCSV(SystemPropertiesParser sysinput, IList<Movement> movementsIList) {
        Parser<Movement> mp = new MovementParser();
        return mp.loadCSVFile(Paths.get(sysinput.getInPath().concat("movimientos.csv")), movementsIList);
    }

    private static Query getQuery(int queryNumber, IList<Airport> airports, IList<Movement> movements, HazelcastInstance hz, String outPath)
            throws IllegalQueryNumber {
        Query query;

        switch (queryNumber) {
            case 1:
                query = new Query1(airports, movements, hz, outPath);
                break;
            case 2:
                query = new Query2(airports, movements, hz);
                break;
            case 3:
                query = new Query3(airports, movements, hz);
                break;
            case 4:
                query = new Query4(airports, movements, hz);
                break;
            case 5:
                query = new Query5(airports, movements, hz);
                break;
            case 6:
                query = new Query6(airports, movements, hz);
                break;
            default:
                throw new IllegalQueryNumber("The query specified does not exist");
        }

        return query;
    }

    private static ClientConfig getConfig(SystemPropertiesParser sysinput) {
        ClientConfig clientConfig = new ClientConfig();
        ClientNetworkConfig clientNetworkConfig = new ClientNetworkConfig();

        clientNetworkConfig.addAddress(sysinput.getAddresses().split(";")); // ni idea si esto nada
        clientConfig.setNetworkConfig(clientNetworkConfig);

        clientConfig.setGroupConfig(new GroupConfig("g2", "g2"));

        return clientConfig;
    }


}
