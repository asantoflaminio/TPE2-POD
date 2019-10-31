package ar.edu.itba.pod.client.parsers;

import ar.edu.itba.pod.FlightClass;
import ar.edu.itba.pod.FlightType;
import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.MovementType;
import com.hazelcast.core.IList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MovementParser implements Parser<Movement> {

    @Override
    public IList<Movement> loadCSVFile(Path path, IList<Movement> ans) {
        List<String> lines = null;

        try {
            lines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            System.out.println("unable to load file");
            e.printStackTrace();
        }

        return parseAllLines(lines, ans);
    }

    private IList<Movement> parseAllLines(List<String> lines, IList<Movement> ans) {
        List<Movement> movements = new ArrayList<>();

        if (lines == null) {
            return ans;
        }

        lines.remove(0);

        for (String line : lines) {
            movements.add(parseLine(line));
        }
        ans.addAll(movements);

        return ans;
    }

    private Movement parseLine(String line) {
        String[] column = line.split(";");
        return new Movement(getFlightType(column[3]), getMovementType(column[4]), getClassType(column[2]), column[5], column[6], column[7]);
    }

    private FlightClass getClassType(String s) {
        if (s.equalsIgnoreCase("regular")) {
            return FlightClass.REGULAR;
        } else if (s.equalsIgnoreCase("no regular")) {
            return FlightClass.NOTREGULAR;
        } else {
            return FlightClass.PRIVATE;
        }
    }

    private FlightType getFlightType(String s) {
        if (s.equalsIgnoreCase("cabotaje")) {
            return FlightType.CABOTAGE;
        } else if (s.equalsIgnoreCase("internacional")) {
            return FlightType.INTERNATIONAL;
        }

        return null;
    }

    private MovementType getMovementType(String s) {
        if (s.equalsIgnoreCase("despegue")) {
            return MovementType.TAKEOFF;
        } else if (s.equalsIgnoreCase("aterrizaje")) {
            return MovementType.LANDING;
        }

        throw new IllegalArgumentException("Illegal movement type: " + s);
    }
}
