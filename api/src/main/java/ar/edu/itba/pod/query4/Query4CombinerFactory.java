package ar.edu.itba.pod.query4;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class Query4CombinerFactory implements CombinerFactory<String, Integer, Integer> {

    @Override
    public Combiner<Integer, Integer> newCombiner(String s) {
        return new Query4Combiner();
    }

    private class Query4Combiner extends Combiner<Integer, Integer> {
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
