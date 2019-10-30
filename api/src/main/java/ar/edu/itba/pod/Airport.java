package ar.edu.itba.pod;

import java.io.Serializable;

public class Airport implements Serializable {
    private final String oaciCode;
    private final String name;
    private final String state;

    public Airport(String oaciCode, String name, String state) {
        this.oaciCode = oaciCode;
        this.name = name;
        this.state = state;
    }

    public String getOaciCode() {
        return oaciCode;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Airport [" + "oaci: " + oaciCode + " - name: '" + name + '\'' + "- state: '" + state + '\'' + ']';
    }
}
