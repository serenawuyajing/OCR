package edu.dal.mibio.corr;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.dal.mibio.corr.corrector.DocumentCorrector;
import edu.dal.mibio.corr.corrector.DomainWordCorrector;
import edu.dal.mibio.corr.corrector.Error;
import edu.dal.mibio.corr.corrector.UnigramWordCorrector;
import edu.dal.mibio.corr.corrector.WikiWordCorrector;
import edu.dal.mibio.corr.corrector.WordCorrector;
import edu.dal.mibio.corr.util.FileUtils;
import edu.dal.mibio.corr.util.ResourceUtils;

public class Main
{
  public static void main(String[] args)
      throws FileNotFoundException, IOException
  {
    List<WordCorrector> corrs = new ArrayList<WordCorrector>();
    corrs.add(new WikiWordCorrector());
    corrs.add(new DomainWordCorrector());
    corrs.add(new UnigramWordCorrector());

    List<Error> errors = new DocumentCorrector().correct(corrs,
        FileUtils.read(ResourceUtils.TEST_INPUT_SEGMENT));

    for (Error e : errors)
      System.out.println(e);
  }
}
