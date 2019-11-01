package ar.edu.itba.pod.client.queries.data;

public class Query3Data implements Comparable<Query3Data> {
    private final int group;
    private final String oaciCode_1;
    private final String oaciCode_2;

    public Query3Data(int group, String oaciCode_1, String oaciCode_2) {
        this.group = group;
        this.oaciCode_1 = oaciCode_1;
        this.oaciCode_2 = oaciCode_2;
    }

    @Override
    public String toString() {
        return group + ";" + oaciCode_1 + ";" + oaciCode_2;
    }

    @Override
    public int compareTo(Query3Data o) {
        return o.group - group;
    }

}
