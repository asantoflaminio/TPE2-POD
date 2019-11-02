package ar.edu.itba.pod.client.queries.data;

/**
 * 
 * @author Grupo 2
 * 
 * QueryXData classes are designed to represent a row
 * that will be printed to the CSV file answering the query X. 
 *
 */
public class Query1Data {
    private final String oaciCode;
    private final String airport;
    private final int accum;

    public Query1Data(String airport, String oaciCode, int accum) {
        this.airport = airport;
        this.oaciCode = oaciCode;
        this.accum = accum;
    }

    @Override
    public String toString() {
        /*
         * Formato de la consigna.
         */
        return oaciCode + ";" + airport + ";" + accum;
    }

    public String getOaciCode() {
        return oaciCode;
    }

    public String getAirport() {
        return airport;
    }

    public int getAccum() {
        return accum;
    }

}
