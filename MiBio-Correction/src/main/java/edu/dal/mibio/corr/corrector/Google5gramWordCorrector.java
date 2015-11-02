package edu.dal.mibio.corr.corrector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import edu.dal.mibio.corr.util.ReaderUtils;
import edu.dal.mibio.corr.util.ResourceUtils;
import gnu.trove.map.hash.TObjectIntHashMap;

public class Google5gramWordCorrector extends WordCorrector{

	 public Google5gramWordCorrector()
		      throws FileNotFoundException, IOException
	  {
	    this(ReaderUtils.buildIntUnigram(new FileReader(ResourceUtils.UNIGRAM)),ReaderUtils.readRelaxMatching(ResourceUtils.RELAX_MATCHING));
	  }

	  private Google5gramWordCorrector(TObjectIntHashMap<String> unigram, File[] relaxMatchingFiles)
	  {
	    super(new Google5gramDetector(unigram,relaxMatchingFiles), new Google5gramCorrector(unigram,relaxMatchingFiles),
	        "type5grams");
	  }
}
