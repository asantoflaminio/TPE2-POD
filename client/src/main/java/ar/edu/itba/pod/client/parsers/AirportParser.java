package ar.edu.itba.pod.client.parsers;

import ar.edu.itba.pod.Airport;
import com.hazelcast.core.IList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Grupo 2
 * <p>
 * CSV file parser for aeropuertos.csv.
 * Parser is designed according to the way data is stored in the mentioned file.
 */
public class AirportParser implements Parser<Airport> {

    @Override
    public IList<Airport> loadCSVFile(Path path, IList<Airport> ans) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Unable to load airports");
        }

        return parseAllLines(lines, ans);
    }

    private IList<Airport> parseAllLines(List<String> lines, IList<Airport> ans) {
        if (lines == null) {
            return ans;
        }

        List<Airport> airports = new ArrayList<>();

        lines.remove(0);

        for (String line : lines) {
            String[] field = line.split(";");
            airports.add(new Airport(optionalFromStr(field[1]), removeQuotes(field[4]), field[21]));
        }

        ans.addAll(airports);
        return ans;
    }

    private String optionalFromStr(String s) {
        if (s.isEmpty()) {
            return null;
        }
        return s;
    }

    private String removeQuotes(String s) {
        return s.replaceAll("^\"|\"$", "");
    }

}
