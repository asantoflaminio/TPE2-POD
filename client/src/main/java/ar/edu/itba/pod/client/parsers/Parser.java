package ar.edu.itba.pod.client.parsers;

import com.hazelcast.core.IList;

import java.nio.file.Path;

/**
 * @param <T> Interface to be implemented by parsers that will use CSV files.
 * @author Grupo 2
 */
public interface Parser<T> {
    IList<T> loadCSVFile(Path path, IList<T> answer);
}
