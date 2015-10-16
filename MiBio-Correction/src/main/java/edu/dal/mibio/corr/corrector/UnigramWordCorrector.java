package edu.dal.mibio.corr.corrector;

import java.io.File;
import java.io.IOException;

import edu.dal.mibio.corr.util.Unigram;

public class UnigramWordCorrector
    extends WordCorrector
{
  public UnigramWordCorrector()
      throws IOException
  {
    super(new UnigramErrorDetector(Unigram.getInstance()),
        new UnigramErrorCorrector(Unigram.getInstance()), "typeUni");
  }

  public UnigramWordCorrector(File unigram)
      throws IOException
  {
    super(new UnigramErrorDetector(Unigram.getInstance(unigram)),
        new UnigramErrorCorrector(Unigram.getInstance(unigram)), "typeUni");
  }
}
