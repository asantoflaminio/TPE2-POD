package ar.edu.itba.pod.query1;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Query1ReducerFactory implements ReducerFactory<String, Integer, Integer> {

    @Override
    public Reducer<Integer, Integer> newReducer(String s) {
        return new Query1Reducer();
    }

    private class Query1Reducer extends Reducer<Integer, Integer> {
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
