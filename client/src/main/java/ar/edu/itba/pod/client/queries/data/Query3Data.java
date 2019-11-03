package ar.edu.itba.pod.client.queries.data;

/**
 * @author Grupo 2
 * <p>
 * QueryXData classes are designed to represent a row
 * that will be printed to the CSV file answering the query X.
 */
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
