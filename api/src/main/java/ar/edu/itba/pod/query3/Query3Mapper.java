package ar.edu.itba.pod.query3;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

public class Query3Mapper implements Mapper<String, Integer, Integer, String> {

    @Override
    public void map(String oaciCode, Integer movements, Context<Integer, String> context) {

        /*
         * Queremos los movimientos en el orden de miles de movimientos
         */
        int movementesInThousands = (movements / 1000) * 1000;

        if (movementesInThousands > 0) {
            context.emit(movementesInThousands, oaciCode);
        }
    }

}
