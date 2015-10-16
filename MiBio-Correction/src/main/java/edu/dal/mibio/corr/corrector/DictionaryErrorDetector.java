package edu.dal.mibio.corr.corrector;

import gnu.trove.set.hash.THashSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DictionaryErrorDetector
    implements ErrorDetector
{
	
  private THashSet<String> dictionary;
  
  public DictionaryErrorDetector(THashSet<String> dictionary)
  {
    this.dictionary = dictionary;
  }
  
  @Override
  public boolean isError(Word word)
  {
    boolean isErrorFlag = true;
     
    if(word.word().length()<=1){
      isErrorFlag = false;
    }
    
    if(isRomanNumber(word.word())) {
      isErrorFlag = false;
    }
    
    if(dictionary.contains(word.word())) {
      isErrorFlag = false;
    }
    
    String tmppattern = "[^a-zA-Z]+";
    Pattern tmp= Pattern.compile(tmppattern);
    Matcher tm = tmp.matcher(word.word());
    if(tm.matches()) {
      isErrorFlag = false;
    }
    
    String pattern = "[a-zA-Z]";
    Pattern t= Pattern.compile(pattern);
    Matcher m = t.matcher(word.word());
    if(m.matches()) {
      isErrorFlag = false;
    }
    return isErrorFlag;
  }

	private boolean isRomanNumber(String word)
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
}
