package ar.edu.itba.pod.client.Parsers;

import ar.edu.itba.pod.FlightType;
import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.MovementType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MovementParser implements Parser<Movement> {

    @Override
    public List<Movement> loadCSVFile(Path path) {
        List<String> lines = null;

        try {
             lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            System.out.println("unable to load file");
            e.printStackTrace();
        }

        return parseAllLines(lines);
    }

    private List<Movement> parseAllLines(List<String> lines) {
        List<Movement> movements = new ArrayList<>();

        if (lines == null) {
            return movements;
        }

        lines.remove(0);

        for (String line : lines) {
            movements.add(parseLine(line));
        }

        return movements;
    }

    private Movement parseLine(String line) {
        String[] column = line.split(";");
        return new Movement(getFlightType(column[3]), getMovementType(column[4]), column[5], column[6]);
    }

    private FlightType getFlightType(String s) {
        if(s.equalsIgnoreCase("cabotaje")) {
            return FlightType.CABOTAGE;
        }
        if(s.equalsIgnoreCase("internacional")) {
            return FlightType.INTERNATIONAL;
        }
        return null;
    }

    private MovementType getMovementType(String s) {
        if(s.equalsIgnoreCase("despegue")) {
            return MovementType.TAKEOFF;
        }

        if(s.equalsIgnoreCase("aterrizaje")) {
            return MovementType.LANDING;
        }

        throw new IllegalArgumentException("Illegal movement type: " + s);
    }
}
