package ar.edu.itba.pod.client.parsers;

import ar.edu.itba.pod.Airport;
import ar.edu.itba.pod.Movement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.hazelcast.core.IList;

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

    private IList<Airport> parseAllLines(List<String> lines,  IList<Airport> ans) {
        List<Airport> airports = new ArrayList<>();

        if (lines == null) {
            return ans;
        }

        lines.remove(0);

        for (String line : lines) {
            airports.add(parseLine(line));
        }
        ans.addAll(airports);

        return ans;
    }

    private Airport parseLine(String line) {
        String[] column = line.split(";");
        return new Airport(optionalFromStr(column[1]), removeQuotes(column[4]), column[21]);
    }

    private String optionalFromStr(String s) {
        if(s.equals("")) {
            return null;
        }
        return s;
    }

    private String removeQuotes(String s) {
        return s.replaceAll("^\"|\"$", "");
    }

}
