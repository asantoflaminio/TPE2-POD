package ar.edu.itba.pod.client;

import ar.edu.itba.pod.Airport;
import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.client.parsers.AirportParser;
import ar.edu.itba.pod.client.parsers.MovementParser;
import ar.edu.itba.pod.client.parsers.Parser;
import ar.edu.itba.pod.client.parsers.SystemPropertiesParser;
import ar.edu.itba.pod.client.queries.*;
import ar.edu.itba.pod.exceptions.IllegalQueryNumber;
import ar.edu.itba.pod.exceptions.InvalidArgumentsException;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

public class Client {
    private static Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException, IllegalQueryNumber,
            InvalidArgumentsException {
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

        FileManager fm = new FileManager(sysinput.getOutPath() + "/query" + sysinput.getQueryNumber() + ".txt");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSSS");

        logger.info("Starting file reading");
        fm.appendToFile(LocalDateTime.now().format(formatter) + " INFO - " + "Starting file reading\r\n");
        IList<Airport> airports = loadAirportCSV(sysinput, airportsIList);
        IList<Movement> movements = loadMovementsCSV(sysinput, movementsIList);
        fm.appendToFile(LocalDateTime.now().format(formatter) + " INFO - " + "Ended file reading\r\n");
        logger.info("Ended file reading");

        int querynumber = sysinput.getQueryNumber();

        validateParameters(sysinput);

        int n;
        if (sysinput.getN().isPresent()) {
            n = Integer.parseInt(sysinput.getN().get());
        } else {
            n = -1;
            if (sysinput.getMin().isPresent()) {
                n = Integer.parseInt(sysinput.getMin().get());
            }
        }

        String oaci;
        if (sysinput.getOaci().isPresent()) {
            oaci = sysinput.getOaci().get();
        } else {
            oaci = null;
        }

        query = getQuery(querynumber, airports, movements, hz, sysinput.getOutPath(), n, oaci);

        logger.info("Starting map/reduce job for query number " + querynumber);
        fm.appendToFile(LocalDateTime.now().format(formatter) + " INFO - "
                + "Starting map/reduce job for query number\r\n");
        query.runQuery();
        fm.appendToFile(LocalDateTime.now().format(formatter) + " INFO - "
                + "Finished map/reduce job\r\n");
        logger.info("Finished map/reduce job");

        fm.close();
        airportsIList.destroy();
        movementsIList.destroy();
        movements.destroy();
        airports.destroy();
        hz.shutdown();
    }

    private static void validateParameters(SystemPropertiesParser sysinput) throws InvalidArgumentsException {

        int query = sysinput.getQueryNumber();
    	
        switch (query) {
            case 2:
                if (!sysinput.getN().isPresent()) {
                    throw new InvalidArgumentsException("Query 2 requires n as a parameter.");
                }
                break;
            case 4:
                if (!sysinput.getN().isPresent() && !sysinput.getOaci().isPresent()) {
                    throw new InvalidArgumentsException("Query 4 requires n and oaci as parameters.");
                } else if (!sysinput.getN().isPresent()) {
                    throw new InvalidArgumentsException("Query 4 requires n as a parameter.");
                } else if (!sysinput.getOaci().isPresent()) {
                    throw new InvalidArgumentsException("Query 4 requires oaci as a parameter.");
                }
                break;
            case 5:
                if (!sysinput.getN().isPresent()) {
                    throw new InvalidArgumentsException("Query 5 requires n as a parameter.");
                }
                break;
            case 6:
                if (!sysinput.getMin().isPresent()) {
                    throw new InvalidArgumentsException("Query 6 requires min as a parameter.");
                }
            default:
                break;
        }


    }

    private static IList<Airport> loadAirportCSV(SystemPropertiesParser sysinput, IList<Airport> airportsIList) {
        Parser<Airport> ap = new AirportParser();
        return ap.loadCSVFile(Paths.get(sysinput.getInPath().concat("aeropuertos.csv")), airportsIList);
    }

    private static IList<Movement> loadMovementsCSV(SystemPropertiesParser sysinput, IList<Movement> movementsIList) {
        Parser<Movement> mp = new MovementParser();
        return mp.loadCSVFile(Paths.get(sysinput.getInPath().concat("movimientos.csv")), movementsIList);
    }

    private static Query getQuery(int queryNumber, IList<Airport> airports, IList<Movement> movements,
                                  HazelcastInstance hz, String outPath, int n, String oaci)
            throws IllegalQueryNumber {
        Query query;
        outPath = outPath + "/query" + queryNumber + ".csv";
        switch (queryNumber) {
            case 1:
                query = new Query1(airports, movements, hz, outPath);
                break;
            case 2:
                query = new Query2(airports, movements, hz, outPath, n);
                break;
            case 3:
                query = new Query3(airports, movements, hz, outPath);
                break;
            case 4:
                query = new Query4(airports, movements, hz, outPath, n, oaci);
                break;
            case 5:
                query = new Query5(airports, movements, hz, outPath, n);
                break;
            case 6:
                query = new Query6(airports, movements, hz, outPath, n);
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
