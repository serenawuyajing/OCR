package edu.dal.mibio.corr.corrector;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.dal.mibio.corr.util.CommonFuntions;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import gnu.trove.map.hash.TObjectLongHashMap;

public class PTBTokenization {

	  private static Pattern SPLIT_PATTERN = Pattern.compile("^\\w+[-./]");

	  public static Map<String, Word> getTokens(String content,TObjectLongHashMap<String> map)
	  {
	    /* Store eight latest reading consecutive tokens and positions. */
	     String[] context = new String[8];
	     int[] position = new int[8];
	     for (int i = 0; i < 8; i++) {
	      context[i] = "";
	      position[i] = -1;
	     }
	     
		 Map<String, Word> wordMap = new HashMap<String, Word>();
		 int widx = 0;
		 int idx =0;
	    /* Tokenize content using Peen Treebank. */
	    PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<>(new StringReader(content),
	        new CoreLabelTokenFactory(), "ptb3Escaping=false,normalizeOtherBrackets=false,latexQuotes=false");
	   
	    Map<Integer,String> proProcessTokens = new HashMap<Integer,String>();
	    CoreLabel prevToken = null;
	    
	    /*after PTBtokenizer, do some special token processing, like compau}-*/
	    while (ptbt.hasNext()) {
	      CoreLabel token = ptbt.next();
	    
	      if(prevToken != null && prevToken.endPosition() == token.beginPosition())
	      {
	    	  if(!(token.toString().equals(",") || token.toString().equals(".") 
	    			  || token.toString().equals(":") || token.toString().equals("!") || token.toString().equals("?")
	    			  || token.toString().equals("\"") || token.toString().equals("(") || token.toString().equals(")") || token.toString().equals(";"))
	    			  && !(prevToken.toString().equals(",") || prevToken.toString().equals(".") 
	    	    			  || prevToken.toString().equals(":") || prevToken.toString().equals("!") || prevToken.toString().equals("?")
	    	    			  || prevToken.toString().equals("\"") || prevToken.toString().equals("(") || prevToken.toString().equals(")") || prevToken.toString().equals(";")))
	    	  {
	    		  token.setBeginPosition(prevToken.beginPosition());
	    		  if(proProcessTokens.containsKey(token.beginPosition()))
	    		  {
	    			 String tmpContext = proProcessTokens.get(token.beginPosition())+token.toString();
	    			 proProcessTokens.put(token.beginPosition(), tmpContext);
	    		  }  
	    	  }
	    	  else
	    	  {
	    		  proProcessTokens.put(token.beginPosition(), token.toString()); 
	    	  }
	      }
	      else
	      {
	    	  proProcessTokens.put(token.beginPosition(), token.toString());
	      }
	      
	       prevToken = token;
	    }
	    
	    SortedSet<Integer> keys = new TreeSet<Integer>(proProcessTokens.keySet());
	    for(Integer key:keys)
	    {
	    	String processToken = proProcessTokens.get(key);
	    	/*add some punctuation to tokens*/
	      if(processToken.equals(",") || processToken.equals(".")
	    		  || processToken.equals(";") || processToken.equals("\""))
	      {
	    	 idx = widx % 8;
	 		 context[idx] = processToken;
	 	     position[idx] = key;
	  	 
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
	  	   continue;
	     }
	      
	      /*processing tokens contains letters*/
	      String regex=".*[a-zA-Z]+.*";
	      Matcher m=Pattern.compile(regex).matcher(processToken);
	      if(m.matches())
	      {
	    	  if(processToken.contains("-"))
		      {
		    	 String tmp = processToken.replace("-", "");
		    	 if(!tmp.isEmpty() && CommonFuntions.validUniGram(tmp,map.get(tmp)))
		    	 {
		    		 idx = widx % 8;
		    		 context[idx] = tmp;
		    	     position[idx] = key;
		    	     if (widx > 3) {
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
		    	     continue;
		    	 }
		      } 
	    	  
	    	  /* Split if '-','.' in token. */
	    	    Matcher matchSplit = SPLIT_PATTERN.matcher(processToken);
		        if(matchSplit.find()) {
		        	Pattern TOKEN_PATTERN = Pattern.compile("(\\w+)");
		        	Matcher matchToken = TOKEN_PATTERN.matcher(processToken);
		        	while(matchToken.find())
		        	{
		        		  /* Add token to context. */
				    	 idx = widx % 8;
				 		 context[idx] = matchToken.group();
				 	     position[idx] = key+matchToken.start(); 

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
		       else
		      {
		    	 idx = widx % 8;
		 		 context[idx] = processToken;
		 	     position[idx] = key; 

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
	    	  
	      }
		    /* Store the tailing tokens. */
		    for (int i = 0; i < 3; widx++, i++) {
		      idx = (widx + 5) % 8;
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
	        return wordMap;
	    }
	  
	  public static void addToMap(Map<String, Word> map, WordContext context)
	  {
	    String tkn = context.word();
	    if (map.containsKey(tkn))
	      map.get(tkn).add(context);
	    else
	      map.put(tkn, new Word(tkn, context));
	  }
}
