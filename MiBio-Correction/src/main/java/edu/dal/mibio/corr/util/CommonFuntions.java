package edu.dal.mibio.corr.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonFuntions {
	
	public static final List<Character> ASCII_LIST = new ArrayList<Character>();
	static {
//	    for(int i = 65; i < 91; i++)
//	      ASCII_LIST.add((char)i);
	    for(int i = 97; i < 123; i++)
	      ASCII_LIST.add((char)i);
	    ((ArrayList<Character>)ASCII_LIST).trimToSize();
	 }
	
	public static boolean hasEnoughFreq(String word, long count) {
	    int threshold;
	    switch (word.length()) {
	      case 1:  threshold = 0; break;
	      case 2:  threshold = 10000000; break;
	      case 3:  threshold = 1000000; break;
	      case 4:  threshold = 100000; break;
	      case 5:  threshold = 100000; break;
	      case 6:  threshold = 10000; break;
	      case 7:  threshold = 10000; break;
	      case 8:  threshold = 10000; break;
	      case 9:  threshold = 10000; break;
	      case 10: threshold = 10000; break;
	      case 11: threshold = 1000; break;
	      case 12: threshold = 1000; break;
	      case 13: threshold = 1000; break;
	      case 14: threshold = 1000; break;
	      case 15: threshold = 1000; break;
	      case 16: threshold = 1000; break;
	      default: threshold = 200;
	    }
	    if (count > threshold) {
	      return true;
	    } else {
	      return false;
	    }
	  }
	
	public static boolean isRomanNumber(String word)
	{
		boolean romanflag = false;
		switch(word) {
			case "i":
			case "ii":
			case "iii":
			case "iv":
			case "v":
			case "vi":
			case "vii":
			case "viii":
			case "ix":
			case "x":
			case "xi":
			case "xii":
			case "xiii":
			case "xiv":
			case "xv":
			case "xvi":
			case "xvii":
			case "xviii":
			case "xix":
				romanflag = true;
				break;
			default:
				romanflag = false;
		}
		return romanflag;
	}
	
	 public static boolean validUniGram(String word, long count)
	  {
		String pattern = "[^a-zA-Z]+";
		Pattern p = Pattern.compile(pattern);
		Matcher tm = p.matcher(word);
		
	    if(word.length() <= 1 || isRomanNumber(word)|| tm.matches()){
	      return true;
	    }
	    return hasEnoughFreq(word, count);
	  }
	 
	 public static void oneDistanceWord(Set<String> distanceWords, Set<String> words, int distance)
	  {
		  if (distance-- > 0) {
		    for(String word: words)
			{
			      /* Deletion operation. */
			      for (int i = 0;i < word.length(); i++) {
			        StringBuffer e = new StringBuffer(word);
			        String newWord = e.deleteCharAt(i).toString();
			        if (newWord != "") {
			        	 if(!distanceWords.contains(newWord))
			        	 {
			        		 distanceWords.add(newWord);
			        	 }
			           
			        }
			      }
			      /* Insertion operation. */
			      for(int charIndex = 0; charIndex < ASCII_LIST.size(); charIndex++) {
			        for(int i = 0; i< word.length() + 1; i++) {
			          StringBuffer e = new StringBuffer(word);
			          String newWord = e.insert(i, ASCII_LIST.get(charIndex)).toString();
			          if(!distanceWords.contains(newWord))
			          {
			        	  distanceWords.add(newWord);
			          }
			          
			        }
			      }
			      /* Substitution operation. */
			      for(int charIndex = 0; charIndex < ASCII_LIST.size(); charIndex++) {
			        for(int i = 0;i < word.length(); i++) {
			          String newWord = word.replace(word.charAt(i), ASCII_LIST.get(charIndex));
			          if(!distanceWords.contains(newWord))
			          {
			        	  distanceWords.add(newWord);  
			          }
			          
			        }
			      } 
			  }
		   Set<String> tmpNewWords = new HashSet<String>(distanceWords);
		   oneDistanceWord(distanceWords, tmpNewWords, distance); 
		 }
		 
	  } 
	
	  
	  public static void getFirstContexts(Set<String> firstContexts,String firstContext)
	  {
	        for(int i = 0;i < firstContext.length(); i++) {
	          char c = firstContext.charAt(i);
	          String newWord = "";
	          if(Character.isLowerCase(c))
	          {
	        	  newWord = firstContext.replace(c,Character.toUpperCase(c));  
	          }
	          else
	          {
	        	  newWord = firstContext.replace(c,Character.toLowerCase(c));
	          }
	     
	          if(!firstContexts.contains(newWord))
	          {
	        	  firstContexts.add(newWord); 
	        	  getFirstContexts(firstContexts,newWord);
	          }
	        }  
	  }
}
