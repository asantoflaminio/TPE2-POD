package ar.edu.itba.pod.client.queries.data;

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


