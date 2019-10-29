package ar.edu.itba.pod.client.parsers;

import java.util.Optional;

public class SystemPropertiesParser {
    private String addresses;
    private String outPath;
    private String inPath;
    private Optional<String> n;
    private Optional<String> oaci;
    private Optional<String> min;
    private int queryNumber;

    public SystemPropertiesParser() {
        this.addresses = System.getProperty("addresses", "127.0.0.1");
        this.outPath = System.getProperty("outPath");
        this.inPath = System.getProperty("inPath");
        this.n = Optional.ofNullable(System.getProperty("n"));
        this.oaci = Optional.ofNullable(System.getProperty("oaci"));
        this.min = Optional.ofNullable(System.getProperty("min"));
        this.queryNumber = Integer.parseInt(System.getProperty("queryNumber"));
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

    public int getQueryNumber() {
        return queryNumber;
    }
}