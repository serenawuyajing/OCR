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
	  return errs;
  }
  
  private List<Error> correct(List<WordCorrector> correctors, List<Word> words)
  {
	 Map<String,List<Error>> errMap = new HashMap<String,List<Error>>();
	 int dictNum=0;
	 
     for (WordCorrector cor : correctors) {
	      List<Error> errs = cor.correct(words);
	      
	      if(cor.type().equals("typeDict"))
	      {
	    	  dictNum++;
	      }
	      
	      if(errs.size()>0)
	      {
	    	  if(cor.type().equals("typeDict"))
		      {
		    	  if(errMap.containsKey("typeDict"))
		    	  {
		    		  for(Error e: errs)
		    		  {
		    			  errMap.get("typeDict").add(e);
		    		  }
		    		  
		    	  }
		    	  else
		    	  {
		    		  errMap.put(cor.type(), errs); 
		    	  }  
		      }
		      else
		      {
		    	  errMap.put(cor.type(), errs);
		      }  
	      }
    }
     
     if(errMap.containsKey("typeDict") && dictNum >1)
     {
    	 List<Error> errs= combineDictErrors(errMap.get("typeDict"),dictNum);
    	 List<Error> errors = new ArrayList<Error>();
    	 for (Error e : errs)
        {
    		 e.sort();
    		 errors.add(e);
        }
        errMap.put("typeDict", errors);
     }
     
     List<Error> errs = new ArrayList<Error>();
     
     if(errMap.containsKey("type5grams"))
     {
    	 if(!errMap.get("type5grams").isEmpty())
         {
        	 for(Error e: errMap.get("type5grams"))
    	    {
    	    	if(e.candidates().size() == 0)
    	    	{
    	    		 if(errMap.get("typeDict").contains(e))
    	    		 {
    	    			 int index = errMap.get("typeDict").indexOf(e);
    	        		 Error newErr = new Error(e.name(),e.position(),errMap.get("typeDict").get(index).candidates());
    	        		 errs.add(newErr);
    	    		 }    		
    	    	}
    	    	else
    	    	{
    	    		errs.add(e);
    	    	}
    	    }
         }
    	 
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
