package ar.edu.itba.pod.client.queries;

import java.util.concurrent.ExecutionException;

/**
 * 
 * @author Grupo 2
 * 
 * Query interface to be implemented by all queries. 
 *
 */
public interface Query {
    void runQuery() throws InterruptedException, ExecutionException;
}
