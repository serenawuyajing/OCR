package edu.dal.mibio.corr.corrector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;

import edu.dal.mibio.corr.util.ReaderUtils;
import gnu.trove.set.hash.THashSet;

public abstract class DictionaryWordCorrector
    extends WordCorrector
{
  public DictionaryWordCorrector(Reader reader)
      throws FileNotFoundException, IOException
  {
    this(ReaderUtils.readList(reader));
  }

  private DictionaryWordCorrector(Set<String> dictionary)
  {
    super(new DictionaryErrorDetector(dictionary), new DictionaryErrorCorrector(dictionary),
        "typeDict");
  }

}
