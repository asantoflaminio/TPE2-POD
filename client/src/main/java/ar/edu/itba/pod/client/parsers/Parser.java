package ar.edu.itba.pod.client.parsers;

import java.nio.file.Path;
import java.util.List;

public interface Parser<T> {
    List<T> loadCSVFile(Path path);
}
