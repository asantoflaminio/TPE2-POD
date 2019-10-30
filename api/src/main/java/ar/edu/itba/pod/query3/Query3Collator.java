package ar.edu.itba.pod.query3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.hazelcast.mapreduce.Collator;

public class Query3Collator implements Collator<Map.Entry<Integer, List<String>>, List<Map.Entry<Integer, List<String>>>> {
	
	public Query3Collator() {
    }
	
	@Override
    public List<Map.Entry<Integer, List<String>>> collate(Iterable<Map.Entry<Integer, List<String>>> values) {
		
        List<Map.Entry<Integer, List<String>>> answer = new ArrayList<>();
        

        for (Map.Entry<Integer, List<String>> v: values) {
            answer.add(v);
        }       

        answer.sort(Comparator
                .comparing(Map.Entry<Integer, List<String>>::getKey)
                .reversed())
                ;

        return answer;
    }


}
