package edu.dal.mibio.corr.corrector;

import java.util.HashSet;
import java.util.Set;

import edu.dal.mibio.corr.util.CommonFuntions;

public class DictionaryErrorDetector
    implements ErrorDetector
{
	
  private Set<String> dictionary;
  
  public DictionaryErrorDetector(Set<String> dictionary)
  {
    this.dictionary = dictionary;
  }
  
  @Override
  public boolean isError(Word word)
  {
	System.out.println(System.currentTimeMillis()+" detect start....");
	boolean isErrorFlag = true;
	Set<String> posWords = new HashSet<String>();
	CommonFuntions.getFirstContexts(posWords, word.word());
	for(String posWord: posWords)
	{
		if(dictionary.contains(posWord))
		{
			isErrorFlag = false;
			break;
		}
	}
	System.out.println(System.currentTimeMillis()+" detect end....");
    return isErrorFlag;
  }
}
