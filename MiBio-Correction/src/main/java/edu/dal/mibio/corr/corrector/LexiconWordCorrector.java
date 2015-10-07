package edu.dal.mibio.corr.corrector;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.dal.mibio.corr.util.ResourceUtils;

public class LexiconWordCorrector
    extends DictionaryWordCorrector
{

  public LexiconWordCorrector()
      throws FileNotFoundException, IOException
  {
    super(ResourceUtils.LEXICON_LIST);
  }

}
