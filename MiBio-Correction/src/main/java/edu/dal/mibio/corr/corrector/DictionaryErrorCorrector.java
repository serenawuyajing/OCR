package edu.dal.mibio.corr.corrector;

import java.util.Set;

import edu.dal.mibio.corr.util.LCS;

public class DictionaryErrorCorrector
  extends EditDistanceErrorCorrector
{
  private Set<String> dictionary;

  protected DictionaryErrorCorrector(Set<String> dictionary)
  {
    this.dictionary = dictionary;
  }

  @Override
  protected boolean contains(String word)
  {
     return dictionary.contains(word.toLowerCase());
  }

  @Override
  protected double score(String word, String candidate)
  {
    return LCS.lcs(word, candidate);
  }
}
