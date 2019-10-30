package ar.edu.itba.pod.query3;


import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import java.util.ArrayList;
import java.util.List;

public class Query3ReducerFactory implements ReducerFactory<Integer, String, List<String>> {

    @Override
    public Reducer<String, List<String>> newReducer(Integer integer) {
        return new Query3Reducer();
    }

    private class Query3Reducer extends Reducer<String, List<String>> {

        private volatile List<String> codes;

        @Override
        public void beginReduce() {
            codes = new ArrayList<>();
        }

        @Override
        public void reduce(String s) {
            codes.add(s);
        }

        @Override
        public List<String> finalizeReduce() {
            return codes;
        }
    }
}
