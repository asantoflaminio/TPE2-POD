package ar.edu.itba.pod.query3;

import ar.edu.itba.pod.Pair;
import com.hazelcast.mapreduce.Collator;

import java.util.*;

public class Query3Collator implements Collator<Map.Entry<Integer, List<String>>,
        List<Map.Entry<Integer, Pair<String, String>>>> {

    @Override
    public List<Map.Entry<Integer, Pair<String, String>>> collate(Iterable<Map.Entry<Integer, List<String>>> values) {
        List<Map.Entry<Integer, Pair<String,String>>> answer;
        
        answer = generatePairs(values);

        answer.sort((Comparator<? super Map.Entry<Integer, Pair<String, String>>>) Comparator
                .comparing(Map.Entry<String, Double>::getValue)
                .reversed()
                .thenComparing(Map.Entry::getKey));

        return answer;
    }

    private List<Map.Entry<Integer, Pair<String, String>>> generatePairs(Iterable<Map.Entry<Integer,
            List<String>>> values) {
        List<Map.Entry<Integer, Pair<String, String>>> listOfPairs = new LinkedList<>();
        List<String> list;

        for (Map.Entry<Integer, List<String>> entry : values) {
            if (entry.getValue().size() > 1) {
                list = entry.getValue();
                Collections.sort(list);

                for (int i = 0; i < list.size(); i++) {
                    String code1 = list.get(i);

                    for (int j = i + 1; j < list.size(); j++) {
                        String code2 = list.get(j);
                        listOfPairs.add(new AbstractMap.SimpleEntry<>(entry.getKey(),
                                new Pair<>(code1, code2)));
                    }
                }
            }
        }

        return listOfPairs;
    }


}
