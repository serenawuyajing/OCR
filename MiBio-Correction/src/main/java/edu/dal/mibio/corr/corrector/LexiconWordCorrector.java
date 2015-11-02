package edu.dal.mibio.corr.corrector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.dal.mibio.corr.util.ResourceUtils;

public class LexiconWordCorrector
    extends DictionaryWordCorrector
{
  public LexiconWordCorrector()
      throws FileNotFoundException, IOException
  {
    super(new FileReader(ResourceUtils.LEXICON_LIST));
  }

  public LexiconWordCorrector(File file)
      throws FileNotFoundException, IOException
  {
    super(new FileReader(file));
  }
}
