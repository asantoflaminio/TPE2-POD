package ar.edu.itba.pod.client.queries.data;

/**
 * 
 * @author Grupo 2
 * 
 * QueryXData classes are designed to represent a row
 * that will be printed to the CSV file answering the query X. 
 *
 */
public class Query4Data implements Comparable<Query4Data> {
    private final String oaci;
    private final int takeOffs;

    public Query4Data(int takeOffs, String oaci) {
        this.oaci = oaci;
        this.takeOffs = takeOffs;
    }

    @Override
    public String toString() {
        return oaci + ";" + takeOffs;
    }

    @Override
    public int compareTo(Query4Data o) {
        // TODO Auto-generated method stub
        return 0;
    }
}


