package ar.edu.itba.pod.query6;

import ar.edu.itba.pod.Pair;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class Query6CombinerFactory implements CombinerFactory<Pair<String, String>, Integer, Integer> {

    @Override
    public Combiner<Integer, Integer> newCombiner(Pair<String, String> pair) {
        return new Query6Combiner();
    }

    private class Query6Combiner extends Combiner<Integer, Integer> {
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
