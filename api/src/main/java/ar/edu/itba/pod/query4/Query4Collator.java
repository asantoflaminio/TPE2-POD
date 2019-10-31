package ar.edu.itba.pod.query4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.hazelcast.mapreduce.Collator;

public class Query4Collator implements Collator<Map.Entry<String, Integer>, List<Map.Entry<String, Integer>>> {
	
	private final int n;

    public Query4Collator(int n) {
        this.n = n;
    }
    
    @Override
    public List<Map.Entry<String, Integer>> collate(Iterable<Map.Entry<String, Integer>> values) {
    	
        List<Map.Entry<String, Integer>> topNAnswer;
        List<Map.Entry<String, Integer>> allAnswer = new ArrayList<>();

        for (Map.Entry<String, Integer> v: values) {
            allAnswer.add(v);
        }   
        
        allAnswer.sort(Comparator
                .comparing(Map.Entry<String, Integer>::getValue)
                .reversed()
                .thenComparing(Map.Entry::getKey));

        topNAnswer = allAnswer.subList(0, n);

        return topNAnswer;
    }
    

}
