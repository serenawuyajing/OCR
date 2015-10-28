package edu.dal.mibio.corr.corrector;

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

import edu.dal.mibio.corr.perfectHash.Lookupa;
import edu.dal.mibio.corr.util.CommonFuntions;
import edu.dal.mibio.corr.util.LCS;
import edu.dal.mibio.corr.util.ResourceUtils;
import edu.dal.mibio.corr.util.Unigram;
import gnu.trove.map.hash.TObjectLongHashMap;


public class Google5gramCorrector implements ErrorCorrector{
	
	private List<List<Integer>> tabValues = new ArrayList<List<Integer>>(54);
    private static final int CANDIDATE_NUM = 50;
    private static final int EDIT_DISTANCE = 3;
    
	 public Google5gramCorrector(List<List<Integer>> tabValues)
	 {
		this.tabValues = tabValues;
	 }
	 
	 public List<Error> correct(Word word){
		 List<Error> erList = new ArrayList<Error>();
		 Set<String> dSet = new HashSet<String>();
		 List<Candidate> candList = new ArrayList<Candidate>();
		 CommonFuntions.oneDistanceWord(dSet, word.word(),EDIT_DISTANCE);
	     
	     /* Select unigram containing words. */
		 TObjectLongHashMap<String> map = Unigram.getInstance().map();
		 for(int i=0;i<word.contexts().size();i++)
 		 {
			 for(String dWord: dSet)
		     {
		    	if(map.containsKey(dWord) && CommonFuntions.hasEnoughFreq(dWord, map.get(dWord)))
		    	{
		    		candList = calFrequencyConfidence(dWord,word.contexts().get(i));
		    	} 
		     }
			 erList.add(new Error(word.word(), word.contexts().get(i).position(), candList));
 		 }
	  
		 return erList; 
	 }
	 
	 private List<Candidate> calFrequencyConfidence(String can, WordContext wc){
		List<Candidate> cans = new ArrayList<Candidate>();
		Map<String,Double> hash_freConfidence = new HashMap<String,Double>();
		Map<String,Long> hash_can_frequency = new HashMap<String,Long>();
		
		for(int j=1;j<=4;j++)
		{
			String[] contexts = wc.get(j);
			if(contexts.length > 0)
			{
				List<PhashValues> phs = Google5gram.getPhValues(can,contexts, tabValues);
				if(phs.size() > 0)
				{
					for(int phIndex =0;phIndex<phs.size();phIndex++)
					{
						if(j == phs.get(phIndex).getPosition())
						{
							// calFrequency
						    if(hash_can_frequency.containsKey(can))
							{
								Long tmpFreConfidence = hash_can_frequency.get(can)+phs.get(phIndex).getFrequency();
								hash_can_frequency.put(can, tmpFreConfidence);
							}
							else
							{
								hash_can_frequency.put(can,phs.get(phIndex).getFrequency());
							}
						}
					    // calCorherenceConfidence
//						double gtmConfidence = 0l;
//						try {
//							gtmConfidence = calCoherenceConfidence(candidate,contexts);
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						if(hash_Coherence_Confidence.containsKey(candidate))
//						{
//							double tmpGtmConfidence = hash_Coherence_Confidence.get(candidate)+ gtmConfidence;
//							hash_Coherence_Confidence.put(candidate, tmpGtmConfidence);
//						}
//						else
//						{
//							hash_Coherence_Confidence.put(candidate, gtmConfidence);
//						}
					}
				}
			}
		}
	   //calFrequencyAndSimilarityConfidence
		double maxFrequency = getMaxFrequency(hash_can_frequency);
		for(String key: hash_can_frequency.keySet())
		{
			//double simConfidence = LCS.lcs(word,key);
			//hash_Sim_Confidence.put(key,simConfidence);
			double tmpFreConfidence = hash_can_frequency.get(key)/maxFrequency;
			hash_freConfidence.put(key, tmpFreConfidence);
		}
		
		//sum all of the confidence
		for(String key: hash_can_frequency.keySet())
		{
			//double sumConfidence = (hash_freConfidence.get(key)+hash_Sim_Confidence.get(key)+hash_Coherence_Confidence.get(key))/3;
			Candidate c = new Candidate(key,hash_can_frequency.get(key));
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
