package edu.dal.mibio.corr.corrector;

import java.util.ArrayList;
import java.util.List;

public class Google5gramDetector implements ErrorDetector {
	
	private List<List<Integer>> tabValues = new ArrayList<List<Integer>>(54);

	public Google5gramDetector(List<List<Integer>> tabValues){
		this.tabValues = tabValues;
	}
	
	public boolean isError(Word word)
	{
		 boolean isErrorFlag = true;
		 for(int i=0;i<word.contexts().size();i++)
		 {
			 for(int j=1;j<=4;j++)
			 {
				isErrorFlag = true;
				String[] contexts = word.contexts().get(i).get(j);
				if(contexts.length > 0)
				{
					List<PhashValues> phs = Google5gram.getPhValues(word.word(),contexts, tabValues);
					if(phs.size() >= 0)
					{
					  for(int phIndex =0; phIndex< phs.size();phIndex++)
					  {
						  if(j == phs.get(phIndex).getPosition())
						  {
							  //the position of this candidate is j.
							  isErrorFlag = false;
							  break;
						  }
					  }	
					}	
				}
				if(isErrorFlag == true)
				{
					break;
				}
			 }
			 if(isErrorFlag == true)
			{
				break;
			} 
		 }
		 return isErrorFlag;
	}
}
