package ar.edu.itba.pod.client.parsers;

import ar.edu.itba.pod.FlightClass;
import ar.edu.itba.pod.FlightType;
import ar.edu.itba.pod.Movement;
import ar.edu.itba.pod.MovementType;
import com.hazelcast.core.IList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Grupo 2
 * <p>
 * CSV file parser for movimientos.csv.
 * Parser is designed according to the way data is stored in the mentioned file.
 */
public class MovementParser implements Parser<Movement> {

    @Override
    public IList<Movement> loadCSVFile(Path path, IList<Movement> ans) {
        List<String> lines = null;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Unable to load movements");
            e.printStackTrace();
        }

        return parseAllLines(lines, ans);
    }

    private IList<Movement> parseAllLines(List<String> lines, IList<Movement> ans) {
        if (lines == null) {
            return ans;
        }

        List<Movement> movements = new ArrayList<>();

        lines.remove(0);

        for (String line : lines) {
            String[] field = line.split(";");
            movements.add(new Movement(getFlightType(field[3]), getMovementType(field[4]),
                    getClassType(field[2]), field[5], field[6], field[7]));
        }

        ans.addAll(movements);
        return ans;
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
        } else if (s.equalsIgnoreCase("n/a")) {
            return FlightType.NA;
        }

        return null;
    }

    private MovementType getMovementType(String s) {
        if (s.equalsIgnoreCase("despegue")) {
            return MovementType.TAKEOFF;
        } else if (s.equalsIgnoreCase("aterrizaje")) {
            return MovementType.LANDING;
        }

        return null;
    }
}
