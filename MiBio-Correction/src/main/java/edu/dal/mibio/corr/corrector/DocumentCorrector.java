package edu.dal.mibio.corr.corrector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.dal.mibio.corr.util.CommonFuntions;
import edu.dal.mibio.corr.util.Unigram;
import gnu.trove.map.hash.TObjectLongHashMap;

public class DocumentCorrector
{
 
  public List<Error> correct(List<WordCorrector> correctors, String content)
  {
    TObjectLongHashMap<String> map = Unigram.getInstance().map();
    Map<String, Word> wordMap = PTBTokenization.getTokens(content,map);
    
    /* Filter words that exists in the unigram. */
    List<Word> words = new LinkedList<Word>(wordMap.values());
    for (int i = 0; i < words.size();) {
    		 if (CommonFuntions.validUniGram(words.get(i).word(), map.get(words.get(i).word()))) {
    		        words.remove(words.get(i));
    		      } else {
    		        i++;
    		 }
    }
    
//    for(Word w: words)
//    {
//    	System.out.println(w.word()+":");
//    	for(int i=0;i<w.contexts().size();i++)
//    	{
//    		System.out.println(" position is "+w.contexts().get(i).position());
//    		for(int j=1;j<=4;j++)
//    		{
//    			String[] tmp = w.contexts().get(i).get(j);
//    			System.out.println(tmp[0]+" "+tmp[1]+" "+tmp[2]+" "+tmp[3]);	
//    		}
//    		
//    	}
//    }
    
    return correct(correctors, words);
  }
  
 

  public static List<Error> combineDictErrors(List<Error> errors,int dictNum)
  {
	  List<Error> errs = new ArrayList<Error>();
	  double weight = 1.0/dictNum;
	 
	  for(Error e: errors)
	  {
		  if(e.candidates().size() == 1 && e.candidates().get(0).confidence() == 1.0)
		  {
			  if(!errs.contains(e))
			  {
				  errs.add(e); 
			  }
			  else
			  {
				  continue;
			  }
		  }
		  else
		  {
			  if(Collections.frequency(errors, e) == dictNum)
			  {
				  List<Candidate> candidates = new ArrayList<Candidate>();
				  List<Candidate> cans = new ArrayList<Candidate>();
				  if(errs.contains(e))
				  {
					  int index = errs.indexOf(e);
					  candidates = new ArrayList<Candidate>(errs.get(index).candidates());
					  Map<String, Candidate> candSetInMap = new HashMap<String, Candidate>();
					  
					  for(Candidate c: candidates)
					  {
						  candSetInMap.put(c.name(), c); 
					  }
					  
					  for(Candidate c : e.candidates())
					  {
						 if(candSetInMap.containsKey(c.name()))
						 {
							 Candidate candInMap = new Candidate(c.name(),
									  candSetInMap.get(c.name()).confidence() + c.confidence()*weight);
							  candSetInMap.put(c.name(), candInMap);
						 }
						 else
						 {
							 candSetInMap.put(c.name(), new Candidate(c.name(),c.confidence()*weight));
						 }
					  }
					  
					  for(Candidate c: candSetInMap.values())
			          {
						  cans.add(c);
			          }
					  errs.remove(index);
				  }
				  else
				  {
					  for(Candidate c: e.candidates())
					  {
						  Candidate tmp = new Candidate(c.name(),c.confidence()*weight);
						  cans.add(tmp);
					  }
				  }
				  Error newError = new Error(e.name(),e.position(),cans);
				  errs.add(newError);
			  } 
		  }
		  
	  }
	  return errs;
  }
  
  private List<Error> correct(List<WordCorrector> correctors, List<Word> words)
  {
	 Map<String,List<Error>> errMap = new HashMap<String,List<Error>>();
	 int dictNum = 0;
	 boolean dictFlag = false;
			 
	 for(Word word: words)
	 {
		 EditDistanceErrorCorrector.getEditDistanceResult(word.word());
		
		 for(WordCorrector cor: correctors)
		 {
			if(dictFlag == false)
			{
			    if(cor.type().equals("typeDict"))
		    	{
		    		dictNum++;
		    	}	
			}
		   
			 List<Error> errs = cor.correct(word);
			 if(errs.size()>0)
			 {
				 if(errMap.containsKey(cor.type()))
		    	 {
		    		  for(Error e: errs)
		    		  {
		    			  errMap.get(cor.type()).add(e);
		    		  } 
		    	 } 
		    	 else
		    	 {
		    		   errMap.put(cor.type(),errs);
		    	 } 
			 }
		 }
		 dictFlag = true;
	 }
		 
     if(errMap.containsKey("typeDict") && dictNum >1)
     {
    	 List<Error> errors = new ArrayList<Error>();
    	 for (Error e : combineDictErrors(errMap.get("typeDict"),dictNum))
        {
    		 e.sort();
    		 errors.add(e);
        }
        errMap.put("typeDict", errors);
     }
     
     List<Error> errors = new ArrayList<Error>();
     
     for(String type: errMap.keySet())
     {
    	 double con_weight  = 0.0;
    	 if(type.equals("type5grams"))
    	 {
    		 con_weight =4.0;
    	 }
    	 else if(type.equals("typeDict"))
    	 {
    		 con_weight =2.0;
    	 }
    	 else
    	 {
    		 con_weight =0.0;
    	 }
    	 for(Error e: errMap.get(type))
     	{
     		List<Candidate> canList = new ArrayList<Candidate>();
     		for(Candidate c: e.candidates())
     		{
     			canList.add(new Candidate(c.name(),c.confidence()+con_weight));
     		}
     		
     		if(errors.contains(e))
     		{
     			int index = errors.indexOf(e);
     			errors.get(index).candidates().addAll(canList);
     		}
     		else
     		{
     			errors.add(new Error(e.name(),e.position(),canList));
     		}
     	}
     }
     
     List<Error> errs = new ArrayList<Error>();
	 for(Error e : errors)
    {
		 /* Sort candidates in the descending order by their confidence. */
	    Collections.sort(e.candidates(), new Comparator<Candidate>(){
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
		errs.add(e);
    }
    
    /* Sort errors by position. */
    Collections.sort(errs, new Comparator<Error>(){
      @Override
      public int compare(Error e1, Error e2)
      {
        return (int)(e1.position() - e2.position());
      }
    });
    return errs;
  }
    
}
