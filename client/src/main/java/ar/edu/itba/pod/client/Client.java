package ar.edu.itba.pod.client;

import ar.edu.itba.pod.Airport;
import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.client.Parsers.AirportParser;
import ar.edu.itba.pod.client.Parsers.MovementParser;
import ar.edu.itba.pod.client.Parsers.Parser;
import ar.edu.itba.pod.client.Parsers.SystemPropertiesParser;
import ar.edu.itba.pod.client.Queries.*;
import ar.edu.itba.pod.exceptions.IllegalQueryNumber;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Client {
    private static Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException, IllegalQueryNumber {
        logger.info("The client is starting");
        List<Airport> airports = loadAirportCSV();
        List<Movement> movements = loadMovementsCSV();
        SystemPropertiesParser sysinput = new SystemPropertiesParser();
        ClientConfig clientConfig = getConfig(sysinput);

        Query query;
        HazelcastInstance hz;

        try {
            hz = HazelcastClient.newHazelcastClient(clientConfig);
        } catch (IllegalStateException e) {
            System.out.println("Unable to connect to cluster");
            return;
        }

        int querynumber = sysinput.getQueryNumber();

        query = getQuery(querynumber, airports, movements, hz);
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

    private static Query getQuery(int queryNumber, List<Airport> airports, List<Movement> movements, HazelcastInstance hz)
            throws IllegalQueryNumber {
        Query query;

        switch (queryNumber) {
            case 1:
                query = new Query1(airports, movements, hz);
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

        clientNetworkConfig.addAddress(sysinput.getAddresses());
        clientConfig.setNetworkConfig(clientNetworkConfig);

        clientConfig.setGroupConfig(new GroupConfig("g2", "1234"));

        return clientConfig;
    }


}
