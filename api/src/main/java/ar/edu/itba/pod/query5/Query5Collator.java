package ar.edu.itba.pod.query5;

import com.hazelcast.mapreduce.Collator;

import java.text.DecimalFormat;
import java.util.*;

public class Query5Collator implements Collator<Map.Entry<String, Double>, List<Map.Entry<String, Double>>> {

    private final int n;
    private DecimalFormat formatter = new DecimalFormat("####.##");

    public Query5Collator(int n) {
        this.n = n;
    }

    @Override
    public List<Map.Entry<String, Double>> collate(Iterable<Map.Entry<String, Double>> values) {
        List<Map.Entry<String, Double>> topNAnswer;
        List<Map.Entry<String, Double>> allAnswer;

        allAnswer = getOrderAnswer(values);

        topNAnswer = allAnswer.subList(0, n);

        return topNAnswer;
    }

    private List<Map.Entry<String, Double>> getOrderAnswer(Iterable<Map.Entry<String, Double>> valuesWithPercentage) {
        final List<Map.Entry<String, Double>> answer = new LinkedList<>();

        for (Map.Entry<String, Double> v : valuesWithPercentage) {
            answer.add(new AbstractMap.SimpleEntry<>(v.getKey(), Double.valueOf(formatter.format(v.getValue()))));
        }

        answer.sort(Comparator
                .comparing(Map.Entry<String, Double>::getValue)
                .thenComparing(Map.Entry::getKey));

        return answer;
    }

}