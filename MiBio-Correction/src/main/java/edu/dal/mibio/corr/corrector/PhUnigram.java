package edu.dal.mibio.corr.corrector;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PhUnigram {
	
	public static TObjectIntHashMap<String> hash_unigram_Dictionary = new TObjectIntHashMap<String>();
	public static TIntObjectHashMap<String> hash_IntString_unigram = new TIntObjectHashMap<String>();
	
	public static void buidUnigramHash(File unigramDictionaryFile) throws IOException{
		FileReader fr = new FileReader(unigramDictionaryFile);
		BufferedReader br = new BufferedReader(fr);
		String line= "";
	    int id= 1;
		while((line = br.readLine())!=null){
			String[] unigramWords = line.split("\\s+");
			if(unigramWords.length >1)
			{
				boolean result = isAllNumber(unigramWords[0]);
				if(result == true)
				{
					hash_unigram_Dictionary.put(unigramWords[0], 1);
					hash_IntString_unigram.put(1, "0");
				}
				else 
				{
					id++;
					hash_unigram_Dictionary.put(unigramWords[0], id);
					hash_IntString_unigram.put(id, unigramWords[0]);
				}
			}
		}
		fr.close();
		br.close();
    }
	
	public static boolean isAllNumber(String str)
	{
		boolean result = true;
		int digitNum =0;
		
		for(int i=0;i<str.length();i++)
		{
			int item = str.charAt(i);
		    if(item >= 48 && item <= 57)
			{
				digitNum++;
			}
		}
		
		if(digitNum == str.length())
		{
			result = true;
		}
		else 
		{
			result = false;
		}
		return result;	
	}

}
