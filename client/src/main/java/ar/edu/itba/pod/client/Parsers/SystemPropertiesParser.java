package ar.edu.itba.pod.client.Parsers;

import java.util.Optional;

public class SystemPropertiesParser {
    private String addresses;
    private String outPath;
    private String inPath;
    private Optional<String> n;
    private Optional<String> oaci;
    private Optional<String> min;

    public SystemPropertiesParser() {
        this.addresses = System.getProperty("addresses");
        this.outPath = System.getProperty("outPath");
        this.inPath = System.getProperty("inPath");
        this.n = Optional.ofNullable(System.getProperty("n"));
        this.oaci = Optional.ofNullable(System.getProperty("oaci"));
        this.min = Optional.ofNullable(System.getProperty("min"));
    }

    public String getAddresses() {
        return addresses;
    }

    public String getOutPath() {
        return outPath;
    }

    public String getInPath() {
        return inPath;
    }

    public Optional<String> getMin() {
        return min;
    }

    public Optional<String> getOaci() {
        return oaci;
    }

    public Optional<String> getN() {
        return n;
    }
}