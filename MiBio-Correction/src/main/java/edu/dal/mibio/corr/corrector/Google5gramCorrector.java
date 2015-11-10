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
		 List<Error> erList = new ArrayList<Error>();
		 Set<String> dSet = new HashSet<String>();
		 List<Candidate> candList = new ArrayList<Candidate>();
		 Set<String> candidates = new HashSet<String>();
		 HashMap<String,Long> exactOrRelaxCans = new HashMap<String,Long>();
		 
		 /* Select unigram containing words. */
		 TObjectLongHashMap<String> map = Unigram.getInstance().map();
		 Set<String> tmpWords = new HashSet<String>();
		 tmpWords.add(word.word());
		 CommonFuntions.oneDistanceWord(dSet,tmpWords,EDIT_DISTANCE);
		
		 for(String dWord: dSet)
	     {
	    	if(map.containsKey(dWord))
	    	{
	    		candidates.add(dWord);
	    	} 
	     }
		 
		 if(word.contexts().size() == 1)
		 {
			 exactOrRelaxCans = getExactAndRelaxCandidates(word);
		 }
		 else
		 {
			 exactOrRelaxCans = getExactCandidates(word);
			 
			 if(exactOrRelaxCans.size() == 0)
			 {
				 exactOrRelaxCans = getRelaxCandidates(word);
			 }
			  
		 }
	     
		 Map<String,Double> hash_freConfidence = calFrequencyConfidence(candidates,exactOrRelaxCans);
	     candList = calConfidence(word.word(),hash_freConfidence);
	   
    	 for(int i=0;i<word.contexts().size();i++)
 		 {
    	  erList.add(new Error(word.word(), word.contexts().get(i).position(), candList)); 
 		 }
	     
		 return erList; 
	 }
	 
	 private HashMap<String,Long> getExactAndRelaxCandidates(Word word)
	 {
		 HashMap<String,Long> map = new HashMap<String,Long>();
		 for(int canpos=1;canpos<=4;canpos++)
		{
			 String[] contexts = word.contexts().get(0).get(canpos);
			
			 if(contexts.length == 4)
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
						 for(int ignorePos=1;ignorePos<=3;ignorePos++)
						 {
							 tmp = Google5gram.isRelaxMatch(emValues, contexts, canpos,ignorePos);
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
	 
	 private HashMap<String,Long> getExactCandidates(Word word)
	 {
		 HashMap<String,Long> map = new HashMap<String,Long>();
		 for(int i=0;i<word.contexts().size();i++)
 		 {
			 for(int canpos=1;canpos<=4;canpos++)
			{
				 String[] contexts = word.contexts().get(i).get(canpos);
				 if(contexts.length == 4)
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
				 if(contexts.length == 4)
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
	 
	 private List<Candidate> calConfidence(String word,Map<String,Double> hash_freConfidence){
		List<Candidate> cans = new ArrayList<Candidate>();
		
		for(String key: hash_freConfidence.keySet())
		{
			double simConfidence = LCS.lcs(word,key);
			double sumConfidence = hash_freConfidence.get(key)*0.1+simConfidence*0.9;
			Candidate c = new Candidate(key,sumConfidence);
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
	 
	 private Map<String,Double> calFrequencyConfidence(Set<String> candidates,HashMap<String,Long> exactOrRelaxCans)
	 {
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

		double maxFrequency = getMaxFrequency(hash_can_frequency);
		for(String key: hash_can_frequency.keySet())
		{
			double tmpFreConfidence = hash_can_frequency.get(key)/maxFrequency;
			hash_freConfidence.put(key, tmpFreConfidence);
		}
		return hash_freConfidence;
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
}
