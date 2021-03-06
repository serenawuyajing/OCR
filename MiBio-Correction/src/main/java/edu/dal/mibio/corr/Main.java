package edu.dal.mibio.corr;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.dal.mibio.corr.corrector.DocumentCorrector;
import edu.dal.mibio.corr.corrector.DomainWordCorrector;
import edu.dal.mibio.corr.corrector.Error;
import edu.dal.mibio.corr.corrector.LexiconWordCorrector;
import edu.dal.mibio.corr.corrector.UnigramWordCorrector;
import edu.dal.mibio.corr.corrector.WikiWordCorrector;
import edu.dal.mibio.corr.corrector.WordCorrector;
import edu.dal.mibio.corr.util.ReaderUtils;
import edu.dal.mibio.corr.util.ResourceUtils;

public class Main
{
  public static int GB = 1024*1024*1024;
  public static void main(String[] args)
      throws FileNotFoundException, IOException
  {
	long start = System.currentTimeMillis();
    List<WordCorrector> corrs = new ArrayList<WordCorrector>();
//    corrs.add(new WikiWordCorrector());
//    corrs.add(new DomainWordCorrector());
//    corrs.add(new LexiconWordCorrector());
    corrs.add(new UnigramWordCorrector());

    List<Error> errors = new DocumentCorrector().correct(corrs,
        ReaderUtils.read(new InputStreamReader(ResourceUtils.TEST_INPUT)));

    for (Error e : errors)
      System.out.println(e);
    
    System.out.println("--- Memory Usage:");   
    Runtime rt=Runtime.getRuntime( ); 
    System.out.println("Total Memory= "+rt.totalMemory()/GB+" Free Memory= "
    		+rt.freeMemory()/GB+" Used Memory= "+(rt.totalMemory()-rt.freeMemory())/GB);
    long end = System.currentTimeMillis();
    long runningTime = end -start;
    System.out.println("Total running time is  "+ runningTime);
  }
}
