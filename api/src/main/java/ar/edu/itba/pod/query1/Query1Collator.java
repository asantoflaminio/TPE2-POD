package ar.edu.itba.pod.query1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.hazelcast.mapreduce.Collator;


public class Query1Collator implements Collator<Map.Entry<String, Integer>, List<Map.Entry<String, Integer>>>{
	
	public Query1Collator() {
    }
	
	@Override
    public List<Map.Entry<String, Integer>> collate(Iterable<Map.Entry<String, Integer>> values) {
		
        List<Map.Entry<String, Integer>> answer = new ArrayList<>();
        

        for (Map.Entry<String, Integer> v: values) {
            answer.add(v);
        }       

        answer.sort(Comparator
                .comparing(Map.Entry<String, Integer>::getValue)
                .reversed().thenComparing(Map.Entry::getKey))
                ;

        return answer;
    }

}
