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
	boolean isErrorFlag = true;
	Set<String> posWords = new HashSet<String>();
	CommonFuntions.getFirstContexts(posWords, word.word());
	for(String posWord: posWords)
	{
		if(unigram.contains(posWord))
		{
			isErrorFlag = false;
			break;
		}
	}
    return isErrorFlag;
  }
}
