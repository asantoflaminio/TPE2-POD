package ar.edu.itba.pod.client.queries.aux;

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
