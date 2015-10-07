package edu.dal.mibio.corr.corrector;

import edu.dal.mibio.corr.util.LCS;
import gnu.trove.map.hash.TObjectLongHashMap;

public class UnigramErrorCorrector
  extends EditDistanceErrorCorrector
{
  private TObjectLongHashMap<String> unigram;

  public UnigramErrorCorrector(TObjectLongHashMap<String> unigram)
  {
    this.unigram = unigram;
  }

  @Override
  protected boolean contains(String word)
  {
    return unigram.containsKey(word);
  }

  @Override
  protected double score(String word, String candidate)
  {
    return LCS.lcs(word, candidate)
        * (Math.log(Double.valueOf(unigram.get(candidate)))
            / Math.log((double)MAX_FREQ));
  }
}
