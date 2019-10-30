package ar.edu.itba.pod.query1;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class Query1CombinerFactory implements CombinerFactory<String, Integer, Integer> {

    @Override
    public Combiner<Integer, Integer> newCombiner(String s) {
        return new Query1Combiner();
    }

    private class Query1Combiner extends Combiner<Integer, Integer> {
        private int accum = 0;

        @Override
        public void combine(Integer integer) {
            accum += integer;
        }

        @Override
        public Integer finalizeChunk() {
            return accum;
        }

        @Override
        public void reset() {
            accum = 0;
        }
    }
}
