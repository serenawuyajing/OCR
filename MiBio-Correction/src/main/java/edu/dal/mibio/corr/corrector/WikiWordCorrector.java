package edu.dal.mibio.corr.corrector;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.dal.mibio.corr.util.ResourceUtils;

public class WikiWordCorrector
    extends DictionaryWordCorrector
{

  public WikiWordCorrector()
      throws FileNotFoundException, IOException
  {
    super(ResourceUtils.WIKI_LIST);
  }

}
