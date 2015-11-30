package edu.dal.mibio.corr.corrector;

import edu.dal.mibio.corr.util.CommonFuntions;
import edu.dal.mibio.corr.util.LCS;
import edu.dal.mibio.corr.util.Unigram;
import gnu.trove.map.hash.TObjectLongHashMap;

public class UnigramErrorCorrector
  extends EditDistanceErrorCorrector
{
  private TObjectLongHashMap<String> unigram;

  public UnigramErrorCorrector(Unigram unigram)
  {
    this.unigram = unigram.map();
  }

  @Override
  protected boolean contains(String word)
  {
    return unigram.containsKey(word);
  }

  @Override
  protected double score(String word, String candidate)
  {
     return 0.9*LCS.lcs(word, candidate)
          +0.1*Math.log(unigram.get(candidate))/Math.log((double)MAX_FREQ);
  }
}
