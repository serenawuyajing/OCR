package edu.dal.mibio.corr.corrector;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.dal.mibio.corr.util.ResourceUtils;

public class DomainWordCorrector
    extends DictionaryWordCorrector
{

  public DomainWordCorrector()
      throws FileNotFoundException, IOException
  {
    super(ResourceUtils.SPECIAL_LIST);
  }

}
