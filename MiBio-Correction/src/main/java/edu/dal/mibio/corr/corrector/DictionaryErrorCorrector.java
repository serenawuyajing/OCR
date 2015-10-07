package edu.dal.mibio.corr.corrector;

import edu.dal.mibio.corr.util.LCS;
import gnu.trove.set.hash.THashSet;

public class DictionaryErrorCorrector
  extends EditDistanceErrorCorrector
{
  private THashSet<String> dictionary;

  protected DictionaryErrorCorrector(THashSet<String> dictionary)
  {
    this.dictionary = dictionary;
  }

  @Override
  protected boolean contains(String word)
  {
    return dictionary.contains(word);
  }

  @Override
  protected double score(String word, String candidate)
  {
    return LCS.lcs(word, candidate);
  }
}
