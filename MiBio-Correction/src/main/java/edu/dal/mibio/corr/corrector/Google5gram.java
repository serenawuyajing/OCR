package edu.dal.mibio.corr.corrector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Google5gram {
	
	public static List<String> getValues(File[] relaxmatchingfiles,String firstContext)
	{
		 List<String> res = new ArrayList<String>();
		
		 if(relaxmatchingfiles!=null){
			for(int fileIndex=0;fileIndex<relaxmatchingfiles.length;fileIndex++){
				
				if(relaxmatchingfiles[fileIndex].getName().equals(firstContext))
				{
					FileReader fr = null;
					BufferedReader br = null;
					try {
						fr = new FileReader(relaxmatchingfiles[fileIndex]);
						br = new BufferedReader(fr);
						String line="";
						while((line = br.readLine())!=null){
							res.add(line);
						}
						fr.close();
						br.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            break;
				}
			}
		 }
		 return res;
	}
	
	public static long isExactMatch(List<String> values, String[] contexts, String word, int position)
	{
		long res = 0l;
		String[] tmpContexts = new String[contexts.length+1];
	
		for(int i=tmpContexts.length-1;i>position;i--)
		{
			tmpContexts[i]= contexts[i-1];
		}
		tmpContexts[position] = word;
		
		for(int i=0;i<position;i++)
		{
			tmpContexts[i]=contexts[i];
		}
		
		for(int i=0;i<values.size();i++)
		{
			boolean flag = true;
			String[] unigramWords = values.get(i).split("\\s+");
			for(int index=0;index<tmpContexts.length;index++)
			{
				if(!unigramWords[index].equals(tmpContexts[index]) && tmpContexts[index] != null)
				{
	                flag = false;
	                break;
				}
			}
			if(flag == true)
			{
				res = Integer.parseInt(unigramWords[unigramWords.length-1]);
				break;
			}
		}
		return res;
	}
	
	public static long isRelaxMatch(List<String> values, String[] contexts, String word, int canpos,int ignorepos)
	{
		String[] tmpContexts = new String[contexts.length+1];
		
		for(int i=tmpContexts.length-1;i>ignorepos;i--)
		{
			tmpContexts[i]= contexts[i-1];
		}
		tmpContexts[ignorepos]=null;
		
		for(int i=0;i<ignorepos;i++)
		{
			tmpContexts[i]=contexts[i];
		}
		
		return isExactMatch(values,tmpContexts,word,canpos);
		
	}

}
