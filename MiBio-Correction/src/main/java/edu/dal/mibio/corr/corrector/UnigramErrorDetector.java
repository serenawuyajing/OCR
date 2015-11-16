package edu.dal.mibio.corr.corrector;

import java.util.HashSet;
import java.util.Set;

import edu.dal.mibio.corr.util.CommonFuntions;
import edu.dal.mibio.corr.util.Unigram;
import gnu.trove.map.hash.TObjectLongHashMap;

public class UnigramErrorDetector
  implements ErrorDetector
{
  private TObjectLongHashMap<String> unigram;

  public UnigramErrorDetector(Unigram unigram)
  {
    this.unigram = unigram.map();
  }

  @Override
  public boolean isError(Word word)
  {
    return true;
  }
}
