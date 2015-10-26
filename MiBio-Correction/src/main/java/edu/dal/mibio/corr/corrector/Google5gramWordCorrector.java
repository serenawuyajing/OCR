package edu.dal.mibio.corr.corrector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import edu.dal.mibio.corr.util.ReaderUtils;
import edu.dal.mibio.corr.util.ResourceUtils;

public class Google5gramWordCorrector extends WordCorrector{

	 public Google5gramWordCorrector()
		      throws FileNotFoundException, IOException
	  {
	    this(ReaderUtils.readTabValue(ResourceUtils.tabValueDir));
	  }

	  private Google5gramWordCorrector(List<List<Integer>> tabValues)
	  {
	    super(new Google5gramDetector(tabValues), new Google5gramCorrector(tabValues),
	        "type5grams");
	  }
}
