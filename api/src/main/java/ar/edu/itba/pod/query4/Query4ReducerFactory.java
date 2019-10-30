package ar.edu.itba.pod.query4;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Query4ReducerFactory implements ReducerFactory<String, Integer, Integer> {

	@Override
    public Reducer<Integer, Integer> newReducer(String s) {
        return new Query4Reducer();
    }

    private class Query4Reducer extends Reducer<Integer, Integer> {
        private volatile int accum;

        @Override
        public void beginReduce() {
            accum = 0;
        }

        @Override
        public void reduce(Integer integer) {
            accum += integer;
        }

        @Override
        public Integer finalizeReduce() {
            return accum;
        }
    }
}
