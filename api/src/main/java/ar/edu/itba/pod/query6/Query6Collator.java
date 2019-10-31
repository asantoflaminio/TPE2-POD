package ar.edu.itba.pod.query6;

import ar.edu.itba.pod.Pair;
import com.hazelcast.mapreduce.Collator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Query6Collator implements Collator<Map.Entry<Pair<String, String>, Integer>, List<Map.Entry<Pair<String, String>, Integer>>> {

    private final int min;

    public Query6Collator(int min) {
        this.min = min;
    }

    @Override
    public List<Map.Entry<Pair<String, String>, Integer>> collate(Iterable<Map.Entry<Pair<String, String>, Integer>> values) {
        List<Map.Entry<Pair<String, String>, Integer>> answer = new ArrayList<>();

        for (Map.Entry<Pair<String, String>, Integer> v : values) {
            if (v.getValue() >= min) {
                answer.add(v);
            }
        }

        answer.sort(Comparator
                .comparing(Map.Entry<Pair<String, String>, Integer>::getValue)
                .reversed());

        return answer;
    }

}
