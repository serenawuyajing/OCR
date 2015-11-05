package edu.dal.mibio.corr.util;

import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.map.hash.TObjectLongHashMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

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
      br.close();
    }
    return sb.toString();
  }

  public static Set<String> readList(Reader reader)
      throws IOException
  {
	System.out.println("load dictionary ....");
    Set<String> dic = new HashSet<String>();
    try (
      BufferedReader br = new BufferedReader(reader);
    ){
      for (String line; (line = br.readLine()) != null;) {
        dic.add(line);
      }
      br.close();
    }
    System.out.println("load dictionary done....");
    return dic;
  }

  public static TObjectLongHashMap<String> readUnigram(Reader reader)
      throws IOException
  {
	System.out.println("load unigram.....");
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
      br.close();
    }
    System.out.println("load unigram done.....");
    return uniMap;
  }
  
  public static TObjectIntHashMap<String> buildIntUnigram(Reader reader)
	      throws IOException
	{
	  System.out.println("build Int Unigram.....");
	 TObjectIntHashMap<String> uniMap = new TObjectIntHashMap<String>();
	try (
	  BufferedReader br = new BufferedReader(reader);
	){
	  int id =1;
	  for (String line; (line = br.readLine()) != null;) {
	    String[] fields = line.split("\t");
	    String gram = fields[0];
	    uniMap.put(gram, id);
	    id++;
	  }
	  br.close();
	}
	System.out.println("build Int Unigram done.....");
	return uniMap;
	}
  
  public static File[] readRelaxMatching(String relaxMatchingDir)
  {
	  File relaxmatchingDir = new File(relaxMatchingDir);
	  File[] relaxmatchingfiles = relaxmatchingDir.listFiles();
	  return relaxmatchingfiles;
  }
}