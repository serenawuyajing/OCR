package edu.dal.mibio.corr.corrector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import edu.dal.mibio.corr.util.FileUtils;
import gnu.trove.set.hash.THashSet;

public abstract class DictionaryWordCorrector
    extends WordCorrector
{
  public DictionaryWordCorrector(File dictionary)
      throws FileNotFoundException, IOException
  {
    this(FileUtils.readList(dictionary));
  }

  private DictionaryWordCorrector(THashSet<String> dictionary)
  {
    super(new DictionaryErrorDetector(dictionary), new DictionaryErrorCorrector(dictionary),
        "typeDict");
  }

}
