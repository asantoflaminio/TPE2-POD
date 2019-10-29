package ar.edu.itba.pod.client.queries;

import java.util.concurrent.ExecutionException;

public interface Query {
    void runQuery() throws InterruptedException, ExecutionException;
}
