package edu.dal.mibio.corr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class readBioFile {
	public static List<String> allWordsOneFile = new ArrayList<String>(); 
	
	public static List<File> readAllFile(String filePath) {
		File f = new File(filePath);
		File[] files = f.listFiles();
		List<File> list = new ArrayList<File>();
		for(File file:files){
			list.add(file);
		}
		return list;
	}
	
	public static void readFile(File file) throws IOException{
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line="";
		String prevLine="";
		while((line = br.readLine())!=null)
		{ 
			line = line.replaceAll("^\\s+|\\s+$","");
			if(prevLine.length()>0){
				String lastChar = prevLine.substring(prevLine.length()-1,prevLine.length());
				if(lastChar.equals("-")){
					prevLine = prevLine+line;
				}
				else
				{
					prevLine = prevLine+" "+line;
				}	
			}
			else{
				prevLine = line;
			}
		}
	   
		String[] tmpAllWords = prevLine.split("\\s+");
		for(int i=0;i<tmpAllWords.length;i++){
			tmpAllWords[i] = tmpAllWords[i].replaceAll("\\W+$","");
			if(tmpAllWords[i].length()>0){
				allWordsOneFile.add(tmpAllWords[i]);
			}
		}
		br.close();
		fr.close();	
	}
}

