package edu.dal.mibio.corr.corrector;

import java.io.File;
import java.util.HashMap;
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
		 System.out.println("five gram detect start");
		 boolean isErrorFlag = true;
		 for(int i=0;i<word.contexts().size();i++)
		 {
			 for(int j=1;j<=4;j++)
			 {
				String[] contexts = word.contexts().get(i).get(j);
				if(contexts.length > 0)
				{
					String firstContext = Integer.toString(unigram.get(contexts[0]));
					List<String> emValues = Google5gram.getValues(relaxMatchingFile,firstContext);
					HashMap<String,Long> map = Google5gram.isExactMatch(emValues, contexts, j);
					if(map.containsKey(word.word()))
					{
						isErrorFlag = false;
						break;
					}
				}
			 }
			 if(isErrorFlag == false)
			{
				break;
			} 
		 }
		 System.out.println("five gram detect end");
		 return isErrorFlag;
	}

}
