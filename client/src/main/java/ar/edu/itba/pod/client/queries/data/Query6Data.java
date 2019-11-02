package ar.edu.itba.pod.client.queries.data;

import ar.edu.itba.pod.Pair;

/**
 * 
 * @author Grupo 2
 * 
 * QueryXData classes are designed to represent a row
 * that will be printed to the CSV file answering the query X. 
 *
 */
public class Query6Data implements Comparable<Query6Data> {
    private final Pair<String, String> statesPair;
    private int movements;

    public Query6Data(Pair<String, String> pair, int movements) {
        this.statesPair = pair;
        this.movements = movements;
    }

    @Override
    public String toString() {
        return statesPair.getElement0() + ";" + statesPair.getElement1() + ";" + movements;
    }

    @Override
    public int compareTo(Query6Data o) {
        return o.movements - movements;
    }
}

