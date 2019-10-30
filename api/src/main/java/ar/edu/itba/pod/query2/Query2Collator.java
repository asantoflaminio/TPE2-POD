package ar.edu.itba.pod.query2;

import com.hazelcast.mapreduce.Collator;

import java.util.*;

public class Query2Collator implements Collator<Map.Entry<String, Long>, List<Map.Entry<String, Double>>> {

    private final int n;

    public Query2Collator(int n) {
        this.n = n;
    }

    @Override
    public List<Map.Entry<String, Double>> collate(Iterable<Map.Entry<String, Long>> values) {
        List<Map.Entry<String, Double>> topNAnswer = new ArrayList<>();
        List<Map.Entry<String, Double>> allAnswer;
        Map<String, Double> valuesWithPercentage;
        long totalMovements = getTotalMovements(values);

        valuesWithPercentage = calculatePercentage(totalMovements, values);
        double sumOthers = valuesWithPercentage.remove("Otros");

        allAnswer = getOrderAnswer(valuesWithPercentage);
        List<Map.Entry<String, Double>> others = allAnswer.subList(n, allAnswer.size());

        topNAnswer = allAnswer.subList(0, n - 1);
        sumOthers = getOthersEntry(others, sumOthers);
        topNAnswer.add(new AbstractMap.SimpleEntry<>("Otros", sumOthers));

        return topNAnswer;
    }

    private double getOthersEntry(List<Map.Entry<String, Double>> others, double sumOthers) {
        for (Map.Entry<String, Double> other : others) {
            sumOthers += other.getValue();
        }

        return sumOthers;
    }


    private List<Map.Entry<String, Double>> getOrderAnswer(Map<String, Double> valuesWithPercentage) {
        final List<Map.Entry<String, Double>> answer = new ArrayList<>(valuesWithPercentage.size());

        answer.addAll(valuesWithPercentage.entrySet());
        answer.sort(Comparator
                .comparing(Map.Entry<String, Double>::getValue)
                .reversed()
                .thenComparing(Map.Entry::getKey));

        return answer;
    }

    private Map<String, Double> calculatePercentage(long totalMovements, Iterable<Map.Entry<String, Long>> values) {
        Map<String, Double> valuesWithPercentage = new HashMap<>();

        for (Map.Entry<String, Long> entry : values) {
            valuesWithPercentage.put(entry.getKey(), (double) (100 * (entry.getValue() / totalMovements)));
        }

        valuesWithPercentage.putIfAbsent("Otros", 0.0);
        return valuesWithPercentage;
    }

    private long getTotalMovements(Iterable<Map.Entry<String, Long>> airlineQuantity) {
        long totalMovements = 0L;

        for (Map.Entry<String, Long> airline : airlineQuantity) {
            totalMovements += airline.getValue();
        }

        return totalMovements;
    }
}
