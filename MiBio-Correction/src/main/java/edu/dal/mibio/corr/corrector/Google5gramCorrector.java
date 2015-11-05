package edu.dal.mibio.corr.corrector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.dal.mibio.corr.util.CommonFuntions;
import edu.dal.mibio.corr.util.LCS;
import edu.dal.mibio.corr.util.ResourceUtils;
import edu.dal.mibio.corr.util.Unigram;
import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.map.hash.TObjectLongHashMap;


public class Google5gramCorrector implements ErrorCorrector{
	
	private TObjectIntHashMap<String> unigram;
    private static final int CANDIDATE_NUM = 50;
    private static final int EDIT_DISTANCE = 3;
    private File[] relaxMatchingFile;
    
	 public Google5gramCorrector(TObjectIntHashMap<String> unigram,File[] relaxMatchingFile)
	 {
		 this.unigram = unigram;
		 this.relaxMatchingFile = relaxMatchingFile;
	 }
	 
	 public List<Error> correct(Word word){
		 System.out.println(System.currentTimeMillis()+" correct start...");
		 List<Error> erList = new ArrayList<Error>();
		 Set<String> dSet = new HashSet<String>();
		 List<Candidate> candList = new ArrayList<Candidate>();
		 Set<String> candidates = new HashSet<String>();
		 
		 /* Select unigram containing words. */
		 TObjectLongHashMap<String> map = Unigram.getInstance().map();
		 CommonFuntions.oneDistanceWord(dSet, word.word(),EDIT_DISTANCE);
		
		 for(String dWord: dSet)
	     {
	    	if(map.containsKey(dWord))
	    	{
	    		candidates.add(dWord);
	    	} 
	     }
	     
		 System.out.println(System.currentTimeMillis()+"detect error word is "+word.word());
		 
		 System.out.println(System.currentTimeMillis()+" candidates number is "+dSet.size());
	
		 HashMap<String,Long> exactOrRelaxCans = getExactCandidates(word);
		 
		 System.out.println("after exact matching exactOrRelaxCans.size() is "+ exactOrRelaxCans.size());
		 if(exactOrRelaxCans.size() == 0)
		 {
			 exactOrRelaxCans = getRelaxCandidates(word);
		 }
		 
		 for(String key: exactOrRelaxCans.keySet()){
				System.out.println(System.currentTimeMillis()+"exactOrRelaxCans is "
		                               +key+" "+exactOrRelaxCans.get(key));
		 }
		 
	     candList = calFrequencyConfidence(candidates,exactOrRelaxCans);
	   
    	 for(int i=0;i<word.contexts().size();i++)
 		 {
    	  erList.add(new Error(word.word(), word.contexts().get(i).position(), candList)); 
 		 }
	     
		 return erList; 
	 }
	 
	 private HashMap<String,Long> getExactCandidates(Word word)
	 {
		 HashMap<String,Long> map = new HashMap<String,Long>();
		 for(int i=0;i<word.contexts().size();i++)
 		 {
			 for(int canpos=1;canpos<=4;canpos++)
			{
				 String[] contexts = word.contexts().get(i).get(canpos);
				 if(contexts.length > 0)
				 {
					 Set<String> firstContexts = new HashSet<String>();
					 CommonFuntions.getFirstContexts(firstContexts, contexts[0]);
					 for(String firstContextStr: firstContexts)
					 {
						 if(unigram.contains(firstContextStr))
						 {
							 String firstContextInt = Integer.toString(unigram.get(firstContextStr));
							 List<String> emValues = Google5gram.getValues(relaxMatchingFile,firstContextInt);
							 HashMap<String,Long> tmp = Google5gram.isExactMatch(emValues, contexts, canpos);
							 if(tmp.size() > 0)
							 {
								 for(String key: tmp.keySet())
								 {
									 if(map.containsKey(key))
									 {
										 long frequency = map.get(key)+tmp.get(key);
										 map.put(key, frequency);
									 }
									 else
									 {
										 map.put(key, tmp.get(key));
									 }
								 }
							 } 
						 } 
					 }			
			    }
			}
 		 }
		 return map;
	 }
	 
	 
	 
	 private HashMap<String,Long> getRelaxCandidates(Word word)
	 {
		 HashMap<String,Long> map = new HashMap<String,Long>();
		 for(int i=0;i<word.contexts().size();i++)
 		 {
			 for(int canpos=1;canpos<=4;canpos++)
			{
				 String[] contexts = word.contexts().get(i).get(canpos);
				 if(contexts.length > 0)
				 {
					 Set<String> firstContexts = new HashSet<String>();
					 CommonFuntions.getFirstContexts(firstContexts, contexts[0]);
					 for(String firstContextStr: firstContexts)
					 {
						 if(unigram.contains(firstContextStr))
						 {
							 String firstContextInt = Integer.toString(unigram.get(firstContextStr));
							 List<String> emValues = Google5gram.getValues(relaxMatchingFile,firstContextInt);
							 for(int ignorePos=1;ignorePos<=3;ignorePos++)
							 {
								 HashMap<String,Long> tmp = Google5gram.isRelaxMatch(emValues, contexts, canpos,ignorePos);
								 if(tmp.size() > 0)
								 {
									 for(String key: tmp.keySet())
									 {
										 if(map.containsKey(key))
										 {
											 long frequency = map.get(key)+tmp.get(key);
											 map.put(key, frequency);
										 }
										 else
										 {
											 map.put(key, tmp.get(key));
										 }
									 }
								 } 
							 }  
						 } 
					 }
				 }
			}
 		 }
		 return map;
	 }
	 
	 private List<Candidate> calFrequencyConfidence(Set<String> candidates,HashMap<String,Long> exactOrRelaxCans){
		List<Candidate> cans = new ArrayList<Candidate>();
		Map<String,Double> hash_freConfidence = new HashMap<String,Double>();
		Map<String,Long> hash_can_frequency = new HashMap<String,Long>();
	 
		for(String key:exactOrRelaxCans.keySet())
		{
			if(candidates.contains(key))
			{
				if(hash_can_frequency.containsKey(key))
				{
					Long tmpFreConfidence = hash_can_frequency.get(key)+exactOrRelaxCans.get(key);
					hash_can_frequency.put(key, tmpFreConfidence);
				}
				else
				{
					hash_can_frequency.put(key,exactOrRelaxCans.get(key));
				} 
			}
		}

		for(String key: hash_can_frequency.keySet()){
			System.out.println(System.currentTimeMillis()+key+" "+hash_can_frequency.get(key));
		}
		
		double maxFrequency = getMaxFrequency(hash_can_frequency);
		for(String key: hash_can_frequency.keySet())
		{
			//double simConfidence = LCS.lcs(word,key);
			//hash_Sim_Confidence.put(key,simConfidence);
			double tmpFreConfidence = hash_can_frequency.get(key)/maxFrequency;
			hash_freConfidence.put(key, tmpFreConfidence);
		}
		
		//sum all of the confidence
		for(String key: hash_freConfidence.keySet())
		{
			//double sumConfidence = (hash_freConfidence.get(key)+hash_Sim_Confidence.get(key)+hash_Coherence_Confidence.get(key))/3;
			Candidate c = new Candidate(key,hash_freConfidence.get(key));
			cans.add(c);
		}
		
		 /* Sort candidates in the descending order by their confidence. */
	    Collections.sort(cans, new Comparator<Candidate>(){
	      public int compare(Candidate s1, Candidate s2) {
	        if(s1.confidence() < s2.confidence()) {
	          return 1;
	        } else if(s1.confidence() == s2.confidence()) {
	          return 0; 
	        } else {
	          return -1;
	        }
	      }
	    });
	    
	    /* Trim the first 50 elements in the candidate list. */
	    for (int i = cans.size() - 1; i >= CANDIDATE_NUM; i--) {
	    	cans.remove(i);
	    }
	    
		return cans;
	 }
	 
	 public long getMaxFrequency(Map<String,Long> hash_can_frequency)
	{
		long maxFrequency = 0l;
		for(long frequency: hash_can_frequency.values())
		{
			if(frequency > maxFrequency)
			{
				maxFrequency = frequency;
			}
		}
		
		return maxFrequency;
	}
	
	  // calCorherenceConfidence
//		double gtmConfidence = 0l;
//		try {
//			gtmConfidence = calCoherenceConfidence(candidate,contexts);
//			} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			}
//		if(hash_Coherence_Confidence.containsKey(candidate))
//		{
//			double tmpGtmConfidence = hash_Coherence_Confidence.get(candidate)+ gtmConfidence;
//			hash_Coherence_Confidence.put(candidate, tmpGtmConfidence);
//		}
//		else
//		{
//			hash_Coherence_Confidence.put(candidate, gtmConfidence);
//		}
//	 public double calCoherenceConfidence(String candidate,String[] contexts) throws IOException 
//	{
//		Islam08 gtm = new Islam08(Paths.get(ResourceUtils.DATA_PATHNAME));
//		double gtmConfidence = 0l;
//		
//		for(int i=0;i<contexts.length;i++)
//		{
//		   gtmConfidence += gtm.wordrt(candidate, contexts[i]);
//		}
//		
//		return gtmConfidence/4;
//	}
}
