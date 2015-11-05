package edu.dal.mibio.corr.corrector;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.dal.mibio.corr.util.CommonFuntions;
import edu.dal.mibio.corr.util.Unigram;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import gnu.trove.map.hash.TObjectLongHashMap;

public class DocumentCorrector
{
  private static Pattern SPLIT_PATTERN = Pattern.compile("(\\w+)");

  public Map<String,List<Error>> correct(List<WordCorrector> correctors, String content)
  {
    /* Store eight latest reading consecutive tokens and positions. */
    String[] context = new String[8];
    int[] position = new int[8];
    for (int i = 0; i < 8; i++) {
      context[i] = "";
      position[i] = -1;
    }
    
    Map<String, Word> wordMap = new HashMap<String, Word>();

    /* Tokenize content using Peen Treebank. */
    PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<>(new StringReader(content),
        new CoreLabelTokenFactory(), "ptb3Escaping=false,normalizeOtherBrackets=false");
    int widx = 0;
    while (ptbt.hasNext()) {
      CoreLabel token = ptbt.next();

      /* Split if '-' in token. */
      Matcher m = SPLIT_PATTERN.matcher(token.toString());
      while(m.find()) {
        
        /* Add token to context. */
        int idx = widx % 8;
        context[idx] = m.group();
        position[idx] = token.beginPosition() + m.start();

        if (widx > 3) {

          /* Store the full-context token. */
          idx = (widx - 3) % 8;
          addToMap(wordMap, new WordContext(
              position[idx],
              context[(widx + 1) % 8],
              context[(widx + 2) % 8],
              context[(widx + 3) % 8],
              context[(widx + 4) % 8],
              context[idx],
              context[(widx - 2) % 8],
              context[(widx - 1) % 8],
              context[widx % 8]));
        }
        widx++;
      }
    }
    /* Store the tailing tokens. */
    for (int i = 0; i < 3; widx++, i++) {
      int idx = (widx + 5) % 8;
      if (position[idx] >= 0) {
        addToMap(wordMap, new WordContext(
            position[idx],
            context[(widx + 1) % 8],
            context[(widx + 2) % 8],
            context[(widx + 3) % 8],
            context[(widx + 4) % 8],
            context[idx],
            (i > 1 ? "" : context[(widx - 2) % 8]),
            (i > 0 ? "" : context[(widx - 1) % 8]),
            ""
        ));
      }
    }

    /* Filter words that exists in the unigram. */
    TObjectLongHashMap<String> map = Unigram.getInstance().map();
    List<Word> words = new LinkedList<Word>(wordMap.values());
    for (int i = 0; i < words.size();) {
      if (CommonFuntions.validUniGram(words.get(i).word(), map.get(words.get(i).word()))) {
        words.remove(words.get(i));
      } else {
        i++;
      }
    }

    return correct(correctors, words);
  }
  
  private void addToMap(Map<String, Word> map, WordContext context)
  {
    String tkn = context.word();
    if (map.containsKey(tkn))
      map.get(tkn).add(context);
    else
      map.put(tkn, new Word(tkn, context));
  }
  
  private  List<Error> combineDictErrors(List<List<Error>> errors)
  {
	  List<Error> errs = new ArrayList<Error>();
	  HashMap<Error,Integer> map = new HashMap<Error,Integer>();
	  double weight = 1.0/errors.size();
	 
	  for(int i=0;i<errors.size();i++)
	  {
		  for(int j=0;j<errors.get(i).size();j++)
		  {
			  if(map.containsKey(errors.get(i).get(j)))
			  {
				  int tmp = map.get(errors.get(i).get(j))+1;
				  map.put(errors.get(i).get(j), tmp);
			  }
			  else
			  {
				  map.put(errors.get(i).get(j), 1);
			  }
			  
			  if(!errs.contains(errors.get(i).get(j)))
			  {
				  errs.add(errors.get(i).get(j));
			  }
			  else
			  {
				  int index = errs.indexOf(errors.get(i).get(j));
				  Map<String, Candidate> candSetInMap = new HashMap<String, Candidate>();
				  List<Candidate> candidates = new ArrayList<Candidate>();
				  
				  if(errors.get(i).get(j).candidates().isEmpty())
				  {
					  continue;
				  }
				  
				  if(errs.get(index).candidates().isEmpty())
				  {
					  candidates =new ArrayList<Candidate>(errors.get(i).get(j).candidates());
				  }
				  else
				  {
					  //candidates =  new ArrayList<Candidate>(errs.get(index).candidates());
					  for (Candidate c : errs.get(index).candidates())
		    	            candSetInMap.put(c.name(), c); 
					  for(Candidate c: errors.get(i).get(j).candidates())
					  {
						 if(candSetInMap.containsKey(c.name()))
						 {
							  Candidate candInMap = new Candidate(c.name(),
									  candSetInMap.get(c.name()).confidence() + c.confidence());
							 // candidates.remove(c);
		    	              //candidates.add(candInMap);
							  candSetInMap.put(c.name(), candInMap);
						 }
						 else
						 {
							 Candidate can = new Candidate(c.name(),c.confidence());
							 //candidates.add(can);
							 candSetInMap.put(c.name(), can);
						 }
					  }
					 
                      for(Candidate c: candSetInMap.values())
                      {
                    	  candidates.add(c);
                      }
				  }
				  errs.remove(index);
				  errs.add(new Error(errors.get(i).get(j).name(),errors.get(i).get(j).position(),candidates)); 
			  }
		  }
	  }
	  for(int i=0;i<errs.size();i++)
	  {
		  if(map.containsKey(errs.get(i)))
		  {
			  if(map.get(errs.get(i)) != errors.size())
			  {
				  errs.remove(i);
			  }
		  }
		  else
		  {
			  errs.remove(i);
		  }
	  }
	  
	  List<Error> res = new ArrayList<Error>();
	  for(Error e: errs)
	  {
		  List<Candidate> cans = new ArrayList<Candidate>();
		  for(Candidate c: e.candidates())
		  {
			  cans.add(new Candidate(c.name(),c.confidence()*weight));
		  }
		  res.add(new Error(e.name(),e.position(),cans));
	  }
	
	  return res;
  }
  
  private Map<String,List<Error>> correct(List<WordCorrector> correctors, List<Word> words)
  {
	 Map<String,List<Error>> errMap = new HashMap<String,List<Error>>();
	 List<List<Error>> dictErrors = new ArrayList<List<Error>>();
	 
     for (WordCorrector cor : correctors) {
	      List<Error> errs = cor.correct(words, correctors);
	      
	      if(cor.type().equals("typeDict"))
	      {
	    	  dictErrors.add(errs);
	    	  for(Error e:errs)
	    	  {
	    		  System.out.println(e);
	    	  }
	      }
	      else
	      {
	    	  errMap.put(cor.type(), errs);
	      }
    }
     if(dictErrors.size() > 0)
     {
    	 List<Error> errs= combineDictErrors(dictErrors); 
    	 List<Error> errors = new ArrayList<Error>();
    	 for (Error e : errs)
    	 {
    		 e.sort();
    		 errors.add(e);
    	 }
    	 errMap.put("typeDict", errors);
     }
     
//    if(errDicMap.size() > 0)
//    {
//    	List<Error> errors = new ArrayList<Error>();
//        for (Error e : errDicMap.values())
//        {
//        	 e.sort();
//        	 errors.add(e);
//        }
//        errMap.put("typeDict", errors);
//    }
//    
    /* Sort errors by position. */
//    Collections.sort(errors, new Comparator<Error>(){
//      @Override
//      public int compare(Error e1, Error e2)
//      {
//        return (int)(e1.position() - e2.position());
//      }
//    });
    return errMap;
  }
    
}
