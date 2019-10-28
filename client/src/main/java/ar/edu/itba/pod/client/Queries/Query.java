package ar.edu.itba.pod.client.Queries;

import java.util.concurrent.ExecutionException;

public interface Query {
    void runQuery() throws InterruptedException, ExecutionException;
}
