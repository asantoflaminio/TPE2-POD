package ar.edu.itba.pod.client.parsers;

import com.hazelcast.core.IList;

import java.nio.file.Path;

public interface Parser<T> {
    IList<T> loadCSVFile(Path path, IList<T> answer);
}
