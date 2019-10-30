package ar.edu.itba.pod.query2;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class Query2CombinerFactory implements CombinerFactory<String, Integer, Long> {

    @Override
    public Combiner<Integer, Long> newCombiner(String s) {
        return new Query2Combiner();
    }

    private class Query2Combiner extends Combiner<Integer, Long> {
        private long accum = 0;

        @Override
        public void combine(Integer integer) {
            accum += integer;
        }

        @Override
        public Long finalizeChunk() {
            return accum;
        }

        @Override
        public void reset() {
            accum = 0;
        }
    }
}

