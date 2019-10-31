package ar.edu.itba.pod.client.queries.data;

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
