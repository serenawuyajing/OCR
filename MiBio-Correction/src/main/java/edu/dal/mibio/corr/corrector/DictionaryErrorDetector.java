package edu.dal.mibio.corr.corrector;

import gnu.trove.set.hash.THashSet;


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
    return !dictionary.contains(word);
	}
}
