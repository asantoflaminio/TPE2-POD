package ar.edu.itba.pod.client.parsers;

import java.nio.file.Path;
import java.util.List;

import com.hazelcast.core.IList;

public interface Parser<T> {
    IList<T> loadCSVFile(Path path, IList<T> answer);
}
