package edu.dal.mibio.corr;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import edu.dal.mibio.corr.corrector.Candidate;
import edu.dal.mibio.corr.corrector.DocumentCorrector;
import edu.dal.mibio.corr.corrector.DomainWordCorrector;
import edu.dal.mibio.corr.corrector.Error;
import edu.dal.mibio.corr.corrector.Google5gram;
import edu.dal.mibio.corr.corrector.Google5gramWordCorrector;
import edu.dal.mibio.corr.corrector.LexiconWordCorrector;
import edu.dal.mibio.corr.corrector.UnigramWordCorrector;
import edu.dal.mibio.corr.corrector.WikiWordCorrector;
import edu.dal.mibio.corr.corrector.WordCorrector;
import edu.dal.mibio.corr.util.CommonFuntions;
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
    // corrs.add(new WikiWordCorrector());
     //corrs.add(new DomainWordCorrector());
     //corrs.add(new LexiconWordCorrector());
  //  corrs.add(new UnigramWordCorrector());
     corrs.add(new Google5gramWordCorrector());

    
    Map<String,List<Error>> errors = new DocumentCorrector().correct(corrs,
     ReaderUtils.read(new FileReader(ResourceUtils.TEST_INPUT_SIMPLE)));
    
    List<Error> errs = new ArrayList<Error>();
    for(Error e: errors.get("type5grams"))
    {
    	if(e.candidates().size() == 0)
    	{
    		if(errors.get("typeDict").contains(e))
    		{
    			int index = errors.get("typeDict").indexOf(e);
    			errs.add(errors.get("typeDict").get(index));
    		}
    		else
    		{
    			errs.add(e);
    		}
    	}
    	else
    	{
    		errs.add(e);
    	}
    }

	for(Error e: errs)
	{
		System.out.println(e);
	}
	

    System.out.println("--- Memory Usage:");   
    Runtime rt=Runtime.getRuntime( ); 
    System.out.println("Total Memory= "+rt.totalMemory()/GB+" Free Memory= "
    		+rt.freeMemory()/GB+" Used Memory= "+(rt.totalMemory()-rt.freeMemory())/GB);
    long end = System.currentTimeMillis();
    long runningTime = end -start;
    System.out.println("Total running time is  "+ runningTime);
  }
}
