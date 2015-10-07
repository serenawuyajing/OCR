package edu.dal.mibio.corr.corrector;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.dal.mibio.corr.util.FileUtils;
import edu.dal.mibio.corr.util.ResourceUtils;
import gnu.trove.map.hash.TObjectLongHashMap;

public class UnigramWordCorrector
    extends WordCorrector
{
  public UnigramWordCorrector()
      throws FileNotFoundException, IOException
  {
    this(FileUtils.readUnigram(ResourceUtils.UNIGRAM));
  }

  private UnigramWordCorrector(TObjectLongHashMap<String> unigram)
  {
    super(new UnigramErrorDetector(unigram), new UnigramErrorCorrector(unigram),
        "typeUni");
  }
}
