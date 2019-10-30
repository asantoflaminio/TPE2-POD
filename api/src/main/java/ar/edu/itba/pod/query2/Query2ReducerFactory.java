package ar.edu.itba.pod.query2;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Query2ReducerFactory implements ReducerFactory<String, Long, Long> {

    @Override
    public Reducer<Long, Long> newReducer(String s) {
        return new Query2ReducerFactory.Query2Reducer();
    }

    private class Query2Reducer extends Reducer<Long, Long> {
        private volatile long accum;

        @Override
        public void beginReduce() {
            accum = 0;
        }

        @Override
        public void reduce(Long value) {
            accum += value;
        }

        @Override
        public Long finalizeReduce() {
            return accum;
        }
    }

}
