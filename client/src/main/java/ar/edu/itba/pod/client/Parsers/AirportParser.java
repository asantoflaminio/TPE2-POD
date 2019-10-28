package ar.edu.itba.pod.client.Parsers;

import ar.edu.itba.pod.Airport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AirportParser implements Parser<Airport> {

    @Override
    public List<Airport> loadCSVFile(Path path) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Unable to load airports");
        }

        return parseAllLines(lines);
    }

    private List<Airport> parseAllLines(List<String> lines) {
        List<Airport> airports = new ArrayList<>();

        if (lines == null) {
            return airports;
        }

        lines.remove(0);

        for (String line : lines) {
            airports.add(parseLine(line));
        }

        return airports;
    }

    private Airport parseLine(String line) {
        String[] column = line.split(";");
        return new Airport(optionalFromStr(column[1]), optionalFromStr(column[2]), removeQuotes(column[4]), column[21]);
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
