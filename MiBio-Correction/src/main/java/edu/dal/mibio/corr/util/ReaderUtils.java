package edu.dal.mibio.corr.util;

import gnu.trove.map.hash.TObjectLongHashMap;
import gnu.trove.set.hash.THashSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReaderUtils
{
  public static String read(Reader reader)
      throws IOException
  {
    StringBuilder sb = new StringBuilder();
    try (
      BufferedReader br = new BufferedReader(reader);
    ){
      for (String line; (line = br.readLine()) != null;) {
        sb.append(line).append('\n');
      }
    }
    return sb.toString();
  }

  public static THashSet<String> readList(Reader reader)
      throws IOException
  {
    THashSet<String> dic = new THashSet<String>();
    try (
      BufferedReader br = new BufferedReader(reader);
    ){
      for (String line; (line = br.readLine()) != null;) {
        dic.add(line);
      }
    }
    return dic;
  }

  public static TObjectLongHashMap<String> readUnigram(Reader reader)
      throws IOException
  {
    TObjectLongHashMap<String> uniMap = new TObjectLongHashMap<String>();
    try (
      BufferedReader br = new BufferedReader(reader);
    ){
      for (String line; (line = br.readLine()) != null;) {
        String[] fields = line.split("\t");
        String gram = fields[0];
        long freq = Long.parseLong(fields[1]);
        uniMap.put(gram, freq);
      }
    }
    return uniMap;
  }
  
  public static List<List<Integer>> readTabValue(String tabValueDir) throws IOException
	{
	   System.out.println("load tabValue File.....");
	   List<File> tabValuefiles =  sortFiles(tabValueDir);
	   
	   List<List<Integer>> returnTabvalues = new ArrayList<List<Integer>>();
	  
	   for(int i=0;i<tabValuefiles.size();i++)
	   {
		 List<Integer> tabValues = new ArrayList<Integer>();
	     try (
		      BufferedReader br = new BufferedReader(new FileReader(tabValuefiles.get(i)));
		    ){
			  for (String line; (line = br.readLine()) != null;) {
				 if(Integer.parseInt(line)<=Integer.MAX_VALUE)
			     {
				   tabValues.add(Integer.parseInt(line)); 
			     } 
		    }
		 }
	     returnTabvalues.add(tabValues);
	   }
	  
	   System.out.println("load tabValue File done.....");
	   return returnTabvalues;
  }
  
  public static List<File> sortFiles(String fileDir)
  {
 	 List<File> files = Arrays.asList(new File(fileDir).listFiles());  
		 Collections.sort(files, new Comparator<File>() {  
		        public int compare(File o1, File o2) {  
		            if (o1.isDirectory() && o2.isFile())  
		                return -1;  
		            if (o1.isFile() && o2.isDirectory())  
		                return 1;  
		            return o1.getName().compareTo(o2.getName());  
		        }  
		    });
		 return files;
  }

}