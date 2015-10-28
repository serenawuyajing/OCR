package edu.dal.mibio.corr.corrector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.dal.mibio.corr.util.ResourceUtils;

public class WikiWordCorrector
    extends DictionaryWordCorrector
{
  public WikiWordCorrector()
      throws FileNotFoundException, IOException
  {
    super(new FileReader(ResourceUtils.WIKI_LIST));
  }

  public WikiWordCorrector(File file)
      throws FileNotFoundException, IOException
  {
    super(new FileReader(file));
  }
}
