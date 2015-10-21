package edu.dal.mibio.corr.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonFuntions {
	
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
}
