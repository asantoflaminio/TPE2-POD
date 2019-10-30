package ar.edu.itba.pod.client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileManager {

    BufferedWriter bw;

    public FileManager(String name) {

        try {
        	bw = new BufferedWriter(new FileWriter(name, false));
            bw = new BufferedWriter(new FileWriter(name, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (bw != null) try {
            bw.flush();
            bw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    public void appendToFile(String data) {
        try {
            bw.write(data);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void log(String data) {
        appendToFile(LocalDateTime.now() + " INFO - " + data + "\n");
    }

    public void flush() {
        try {
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
