package ar.edu.itba.pod.client.queries.data;

/**
 * @author Grupo 2
 * <p>
 * QueryXData classes are designed to represent a row
 * that will be printed to the CSV file answering the query X.
 */
public class Query5Data {
    private final String oaciCode;
    private final double percentage;

    public Query5Data(String oaciCode, double percentage) {
        this.oaciCode = oaciCode;
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return oaciCode + ";" + percentage + "%";
    }
}
