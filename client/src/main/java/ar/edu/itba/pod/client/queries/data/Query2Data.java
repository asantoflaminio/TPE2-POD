package ar.edu.itba.pod.client.queries.data;

/**
 * 
 * @author Grupo 2
 * 
 * QueryXData classes are designed to represent a row
 * that will be printed to the CSV file answering the query X. 
 *
 */
public class Query2Data {
    private final String airline;
    private final double percentage;

    public Query2Data(String airline, double percentage) {
        this.airline = airline;
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        /*
         * Formato de la consigna.
         */
        return airline + ";" + percentage + "%";
    }
}

