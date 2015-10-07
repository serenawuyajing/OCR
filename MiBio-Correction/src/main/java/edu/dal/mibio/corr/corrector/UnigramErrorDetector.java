package edu.dal.mibio.corr.corrector;

import gnu.trove.map.hash.TObjectLongHashMap;

public class UnigramErrorDetector
  implements ErrorDetector
{
  private TObjectLongHashMap<String> unigram;

  public UnigramErrorDetector(TObjectLongHashMap<String> unigram)
  {
    this.unigram = unigram;
  }

  @Override
  public boolean isError(Word word)
  {
    return unigram.contains(word);
  }
}
