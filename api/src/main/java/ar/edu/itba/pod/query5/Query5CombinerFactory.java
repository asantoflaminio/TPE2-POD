package ar.edu.itba.pod.query5;

import ar.edu.itba.pod.Pair;
import ar.edu.itba.pod.query2.Query2CombinerFactory;
import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class Query5CombinerFactory implements CombinerFactory<String, Integer, Pair<Long, Long>> {

    @Override
    public Combiner<Integer, Pair<Long, Long>> newCombiner(String s) {
        return new Query5CombinerFactory.Query5Combiner();
    }

    private class Query5Combiner extends Combiner<Integer, Pair<Long, Long>> {
        private long total = 0;
        private long accum = 0;

        @Override
        public void combine(Integer integer) {
            total++;
            accum += integer;
        }

        @Override
        public Pair<Long, Long> finalizeChunk() {
            return new Pair<>(accum, total);
        }

        @Override
        public void reset() {
            total = 0;
            accum = 0;
        }
    }
}

