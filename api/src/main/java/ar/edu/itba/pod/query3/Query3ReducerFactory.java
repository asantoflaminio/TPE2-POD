package ar.edu.itba.pod.query3;



import java.util.ArrayList;
import java.util.List;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Query3ReducerFactory implements ReducerFactory<Integer, String, List<String>>{
	
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
