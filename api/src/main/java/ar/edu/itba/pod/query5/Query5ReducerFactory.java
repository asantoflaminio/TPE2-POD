package ar.edu.itba.pod.query5;

import ar.edu.itba.pod.Pair;
import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class Query5ReducerFactory implements ReducerFactory<String, Pair<Long, Long>, Double> {

    @Override
    public Reducer<Pair<Long, Long>, Double> newReducer(String s) {
        return new Query5ReducerFactory.Query5Reducer();
    }

    private class Query5Reducer extends Reducer<Pair<Long, Long>, Double> {
        private volatile double accum;
        private double total;

        @Override
        public void beginReduce() {
            accum = 0;
            total = 0;
        }

        @Override
        public void reduce(Pair<Long, Long> pairAccumTotal) {
            accum += pairAccumTotal.getElement0();
            total += pairAccumTotal.getElement1();
        }

        @Override
        public Double finalizeReduce() {
            return ((100 * accum) / total);
        }
    }

}