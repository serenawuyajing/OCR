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

import edu.dal.mibio.corr.corrector.Candidate;
import edu.dal.mibio.corr.corrector.DocumentCorrector;
import edu.dal.mibio.corr.corrector.DomainWordCorrector;
import edu.dal.mibio.corr.corrector.Error;
import edu.dal.mibio.corr.corrector.Google5gram;
import edu.dal.mibio.corr.corrector.Google5gramWordCorrector;
import edu.dal.mibio.corr.corrector.LexiconWordCorrector;
import edu.dal.mibio.corr.corrector.PTBTokenization;
import edu.dal.mibio.corr.corrector.UnigramWordCorrector;
import edu.dal.mibio.corr.corrector.WikiWordCorrector;
import edu.dal.mibio.corr.corrector.Word;
import edu.dal.mibio.corr.corrector.WordContext;
import edu.dal.mibio.corr.corrector.WordCorrector;
import edu.dal.mibio.corr.util.CommonFuntions;
import edu.dal.mibio.corr.util.LCS;
import edu.dal.mibio.corr.util.ReaderUtils;
import edu.dal.mibio.corr.util.ResourceUtils;
import gnu.trove.map.hash.TObjectLongHashMap;

public class Main
{
  public static int GB = 1024*1024*1024;
  public static void main(String[] args)
      throws FileNotFoundException, IOException
  {
	 long start = System.currentTimeMillis();
     List<WordCorrector> corrs = new ArrayList<WordCorrector>();
     corrs.add(new WikiWordCorrector());
     corrs.add(new DomainWordCorrector());
     corrs.add(new LexiconWordCorrector());
     corrs.add(new UnigramWordCorrector());
     corrs.add(new Google5gramWordCorrector());
     
    List<Error> errs = new DocumentCorrector().correct(corrs,ReaderUtils.read(new FileReader(ResourceUtils.TEST_INPUT_SEGMENT)));
    
	for(Error e: errs)
	{
		System.out.println(e);
	}
	

//     TObjectLongHashMap<String> map = new TObjectLongHashMap<String>();
//     Map<String, Word> words = PTBTokenization.getTokens("j^ellowish-white ORIOL ID^E.", map);
//     for(String key: words.keySet())
//     {
//        Word w = words.get(key);
//       	System.out.print(w.word()+":");
//       	for(int i=0;i<w.contexts().size();i++)
//       	{
//            System.out.println(" positions is "+w.contexts().get(i).position());
//       		for(int j=1;j<=4;j++)
//       		{
//       			String[] tmp = w.contexts().get(i).get(j);
//       			System.out.println(tmp[0]+" "+tmp[1]+" "+tmp[2]+" "+tmp[3]);	
//       		}
//       		
//       	} 
//     }
	 
    System.out.println("--- Memory Usage:");   
    Runtime rt=Runtime.getRuntime( ); 
    System.out.println("Total Memory= "+rt.totalMemory()/GB+" Free Memory= "
    		+rt.freeMemory()/GB+" Used Memory= "+(rt.totalMemory()-rt.freeMemory())/GB);
    long end = System.currentTimeMillis();
    long runningTime = end -start;
    System.out.println("Total running time is  "+ runningTime);
  }
}
