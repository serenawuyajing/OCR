package edu.dal.mibio.corr.corrector;

import java.io.File;
import java.util.List;

import gnu.trove.map.hash.TObjectIntHashMap;

public class Google5gramDetector implements ErrorDetector {
	
	private TObjectIntHashMap<String> unigram;
	private File[] relaxMatchingFile;

	public Google5gramDetector(TObjectIntHashMap<String> unigram,File[] relaxMatchingFile){
		this.unigram = unigram;
		this.relaxMatchingFile = relaxMatchingFile;
	}
	
	public boolean isError(Word word)
	{
		 System.out.println("detect start..."+ System.currentTimeMillis());
		 boolean isErrorFlag = true;
		 for(int i=0;i<word.contexts().size();i++)
		 {
			 isErrorFlag = true;
			 for(int j=1;j<=4;j++)
			 {
				isErrorFlag = true;
				String[] contexts = word.contexts().get(i).get(j);
				if(contexts.length > 0)
				{
					String firstContext = Integer.toString(unigram.get(contexts[0]));
					List<String> emValues = Google5gram.getValues(relaxMatchingFile,firstContext);
					long resFrequency = Google5gram.isExactMatch(emValues, contexts, word.word(), j);
					if(resFrequency!= 0)
					{
						isErrorFlag = false;
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
