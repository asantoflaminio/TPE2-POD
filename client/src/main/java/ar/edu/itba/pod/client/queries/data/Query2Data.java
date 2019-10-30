package ar.edu.itba.pod.client.queries.data;

import java.util.Comparator;

public class Query2Data implements Comparator {
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
        return airline + ";" + percentage;
    }

    @Override
    public int compare(Object o1, Object o2) {
        Query2Data q2data1 = (Query2Data) o1;
        Query2Data q2data2 = (Query2Data) o2;

        if (q2data1.percentage != q2data2.percentage) {
            return (int) (q2data1.percentage - q2data2.percentage);
        } else {
            return q2data1.airline.compareTo(q2data2.airline);
        }
    }

    public String getAirline() {
        return airline;
    }
}

