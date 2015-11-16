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
     return !dictionary.contains(word.word().toLowerCase());
  }
}
