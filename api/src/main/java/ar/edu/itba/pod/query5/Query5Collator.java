package ar.edu.itba.pod.query5;

import ar.edu.itba.pod.Pair;
import com.hazelcast.mapreduce.Collator;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class Query5Collator implements Collator<Map.Entry<String, Double>, List<Map.Entry<String, Double>>> {

    private final int n;
    private DecimalFormat formatter = new DecimalFormat("####.##");

    public Query5Collator(int n) {
        this.n = n;
        formatter.setRoundingMode(RoundingMode.DOWN);
    }

    @Override
    public List<Map.Entry<String, Double>> collate(Iterable<Map.Entry<String, Double>> values) {
        for(Map.Entry<String, Double> v : values)
            System.out.println(v.getKey() + "  - " + v.getValue());

        List<Map.Entry<String, Double>> topNAnswer;
        List<Map.Entry<String, Double>> allAnswer;

        allAnswer = getOrderAnswer(values);

        topNAnswer = allAnswer.subList(0,n);

        return topNAnswer;
    }

    private List<Map.Entry<String, Double>> getOrderAnswer(Iterable<Map.Entry<String, Double>> valuesWithPercentage) {
        final List<Map.Entry<String, Double>> answer = new LinkedList<>();

        for(Map.Entry<String, Double> v : valuesWithPercentage) {
            if(v.getValue() > 0.0) {
                answer.add(new AbstractMap.SimpleEntry<>(v.getKey(), Double.valueOf(formatter.format(v.getValue()))));
            }
        }

        answer.sort(Comparator
                .comparing(Map.Entry<String, Double>::getValue)
                .thenComparing(Map.Entry::getKey));

        return answer;
    }

}