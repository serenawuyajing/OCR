package edu.dal.mibio.corr.corrector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.dal.mibio.corr.util.ResourceUtils;

public class DomainWordCorrector
    extends DictionaryWordCorrector
{
  public DomainWordCorrector()
      throws FileNotFoundException, IOException
  {
    super(new FileReader(ResourceUtils.SPECIAL_LIST));
  }

  public DomainWordCorrector(File file)
      throws FileNotFoundException, IOException
  {
    super(new FileReader(file));
  }
}
