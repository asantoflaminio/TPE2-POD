package ar.edu.itba.pod.query6;

import ar.edu.itba.pod.Pair;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Query6ReducerFactory implements ReducerFactory<Pair<String, String>, Integer, Integer> {

    @Override
    public Reducer<Integer, Integer> newReducer(Pair<String, String> pair) {
        return new Query6Reducer();
    }

    private class Query6Reducer extends Reducer<Integer, Integer> {

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
